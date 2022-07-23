package org.kleber.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.kleber.app.model.usuario.UsuarioDao;
import org.kleber.app.model.credencial.CredencialDao;
import org.kleber.app.model.usuario.Usuario;
import org.kleber.app.model.credencial.Credencial;

@SpringBootApplication
@Controller
public class App extends SpringBootServletInitializer {
	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	CredencialDao credencialDao;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().disable()
			.authorizeHttpRequests()
				.antMatchers("/", "/login", "/logout", "/register", "/error", "/css/**", "/js/**", "/img/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/doLogin")
				.defaultSuccessUrl("/")
				.failureUrl("/login?error=true")
			.and()
			.authenticationProvider(authProvider());
		return http.build();
	}

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				System.out.println("loadUserByUsername: " + username);
				return usuarioDao.findByUsername(username);
			}
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(rawPassword.toString().getBytes());
					byte[] digest = md.digest();

					StringBuilder sb = new StringBuilder();
					for(int i=0; i<digest.length; i++) sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
					return sb.toString();
				} catch (Exception e) {
					return null;
				}
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(encode(rawPassword));
			}
		};
	}

    @RequestMapping(value = "/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("obj", new Usuario());
		return "register";
	}

	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String register(@ModelAttribute Usuario usuario) {
		try {
			usuario.setPassword(passwordEncoder().encode(usuario.getPassword()));
			usuario.setCredenciais(new ArrayList<Credencial>());
			usuario.getCredenciais().add(credencialDao.findBy("nome", "USER").get(0));
			usuarioDao.insert(usuario);
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			return "register";
		}
	}
}
