package org.kleber.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Controller<T> {
    @Autowired
    private Service<T> service;

    Class<T> classe;

    public Controller(Class<T> classe) {
        this.classe = classe;
    }

    @RequestMapping(value="insert", method = RequestMethod.POST)
    public void insert(@ModelAttribute("obj") T obj) {
        System.out.println("Inserting " + obj);
        service.insert(obj);
    }

    @RequestMapping(value="update", method = RequestMethod.POST)
    public void update(@ModelAttribute("obj") T obj) {
        System.out.println("Updating " + obj);
        service.update(obj);
    }

    @RequestMapping(value="delete", method = RequestMethod.POST)
    public void delete(@ModelAttribute("obj") T obj) {
        System.out.println("Deleting " + obj);
        service.delete(obj);
    }

    @RequestMapping(value="insert", method = RequestMethod.GET)
    public String insert(Model model) throws Exception {
        model.addAttribute("obj", service.object());
        return "model/insert";
    }

    @RequestMapping(value="update/{id}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("obj", service.findById(id));
        return "model/update";
    }

    @RequestMapping(value="delete/{id}", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("obj", service.findById(id));
        return "model/delete";
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index() {
        return "model/index";
    }

    @RequestMapping(value="list.json", method = RequestMethod.GET)
    @ResponseBody
    public String list() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(service.select());
    }
}
