package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.entity.City;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CityDao {

    public Optional<City> findById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Use JOIN FETCH to load country eagerly
            City city = em.createQuery(
                    "SELECT c FROM City c JOIN FETCH c.country WHERE c.id = :id",
                    City.class
            ).setParameter("id", id).getSingleResult();
            return Optional.ofNullable(city);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public List<City> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Use JOIN FETCH to load country eagerly
            return em.createQuery(
                    "SELECT c FROM City c JOIN FETCH c.country ORDER BY c.city",
                    City.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public City save(City city) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            if (city.getLastUpdate() == null) {
                city.setLastUpdate(Instant.now());
            }
            em.persist(city);

            em.getTransaction().commit();
            return city;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save city", e);
        } finally {
            em.close();
        }
    }

    public City update(City city) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            City managed = em.merge(city);

            em.getTransaction().commit();
            return managed;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update city", e);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            City city = em.find(City.class, id);
            if (city == null) {
                em.getTransaction().commit();
                return false;
            }

            em.remove(city);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete city", e);
        } finally {
            em.close();
        }
    }
}