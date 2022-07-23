package org.kleber.app.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Service<T> {
    @Autowired
    private Dao<T> dao;

    Class<T> classe;

    public Service(Class<T> classe) {
        this.classe = classe;
    }

    public void insert(T obj) {
        System.out.println("Inserting " + obj);
        dao.insert(obj);
    }

    public void update(T obj) {
        System.out.println("Updating " + obj);
        dao.update(obj);
    }

    public void delete(T obj) {
        System.out.println("Deleting " + obj);
        dao.delete(obj);
    }

    public List<T> select() {
        System.out.println("Selecting");
        return dao.select();
    }

    public T findById(Integer id) {
        System.out.println("Finding by id");
        return dao.findById(id);
    }

    public List<T> findBy(String key, Object value) {
        System.out.println("Finding by " + key);
        return dao.findBy(key, value);
    }

    public T object() throws Exception {
        return classe.getDeclaredConstructor().newInstance();
    }
}
