package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.dto.CreateCountry;
import org.iti.rest.entity.Country;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CountryDao {

    public Optional<Country> findById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Country country = em.find(Country.class, id);
            return Optional.ofNullable(country);
        } finally {
            em.close();
        }
    }

    public List<Country> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Country c ORDER BY c.country",
                    Country.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public CreateCountry save(Country country) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            country.setLastUpdate(Instant.now());
            em.persist(country);

            em.getTransaction().commit();
            CreateCountry returnedCountry=new CreateCountry();
            returnedCountry.setCountry(country.getCountry());
            return returnedCountry;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save country", e);
        } finally {
            em.close();
        }
    }

    public CreateCountry update(Country country) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Country managed = em.merge(country);
            em.getTransaction().commit();
            CreateCountry returnedCountry=new CreateCountry();
            returnedCountry.setCountry(country.getCountry());
            return returnedCountry;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update country", e);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            Country country = em.find(Country.class, id);
            if (country == null) {
                em.getTransaction().commit();
                return false;
            }

            em.remove(country);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete country (may be referenced by cities)", e);
        } finally {
            em.close();
        }
    }
}