package org.kleber.app.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Dao<T> {
    @Autowired
    private EntityManagerFactory factory;

    Class<T> classe;

    public Dao(Class<T> classe) {
        this.classe = classe;
    }

    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public void insert(T obj) {
        System.out.println("Inserting " + obj);
        EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
    }

    public void update(T obj) {
        System.out.println("Updating " + obj);
        EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
    }

    public void delete(T obj) {
        System.out.println("Deleting " + obj);
        EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.contains(obj) ? obj : entityManager.merge(obj));
		entityManager.getTransaction().commit();
		entityManager.close();
    }

    public List<T> select() {
        System.out.println("Selecting");
        EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		List<T> lista = entityManager.createQuery("SELECT a FROM "+classe.getSimpleName()+" a").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return lista;
    }

    public T findById(Integer id) {
        System.out.println("Finding by id");
        EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		List<T> lista = entityManager.createQuery("SELECT a FROM "+classe.getSimpleName()+" a WHERE a.id = :id").setParameter("id", id).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
        if(lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }

    public List<T> findBy(String key, Object value) {
        System.out.println("Finding by " + key);
        EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		List<T> lista = entityManager.createQuery("SELECT a FROM "+classe.getSimpleName()+" a WHERE a."+key+" = :value").setParameter("value", value).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
        return lista;
    }
}
