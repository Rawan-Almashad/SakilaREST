package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.entity.Store;

import java.util.List;
import java.util.Optional;

public class StoreDao {

    public Optional<Store> findById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Store store = em.find(Store.class, id);
            return Optional.ofNullable(store);
        } finally {
            em.close();
        }
    }

    public List<Store> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM Store s JOIN FETCH s.managerStaff JOIN FETCH s.address ORDER BY s.storeId",
                    Store.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Store save(Store store) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(store);
            em.getTransaction().commit();
            return store;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save store", e);
        } finally {
            em.close();
        }
    }

    public Store update(Store store) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Store managed = em.merge(store);
            em.getTransaction().commit();
            return managed;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update store", e);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Store store = em.find(Store.class, id);
            if (store == null) {
                em.getTransaction().commit();
                return false;
            }
            em.remove(store);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete store", e);
        } finally {
            em.close();
        }
    }
}