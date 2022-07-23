package org.kleber.app.model.usuario;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.kleber.app.model.Dao;
import org.kleber.app.model.autorizacao.Autorizacao;
import org.kleber.app.model.credencial.Credencial;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDao extends Dao<Usuario> {
    UsuarioDao() {
        super(Usuario.class);
    }

    public Usuario findByUsername(String username) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Usuario usuario = (Usuario) entityManager.createQuery("SELECT a FROM Usuario a WHERE a.username = :value").setParameter("value", username).getSingleResult();
        List<Credencial> credenciais = usuario.getCredenciais();
        List<Autorizacao> autorizacaos = new ArrayList<Autorizacao>();
        for(Credencial credencial : credenciais) autorizacaos.addAll(credencial.getAutorizacoes());
        entityManager.getTransaction().commit();
        entityManager.close();
        return usuario;
    }
}
