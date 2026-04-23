package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.dto.CreateCityRequest;
import org.iti.rest.dto.UpdateCityRequest;
import org.iti.rest.entity.City;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CityDao {

    public Optional<City> findById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            City city = em.find(City.class, id);
            return Optional.ofNullable(city);
        } finally {
            em.close();
        }
    }

    public List<City> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM City c ORDER BY c.city",
                    City.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public CreateCityRequest save(City city) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            city.setLastUpdate(Instant.now());
            em.persist(city);

            em.getTransaction().commit();

            CreateCityRequest dto = new CreateCityRequest();
            dto.setCity(city.getCity());
            dto.setCountryId(city.getCountry().getId());

            return dto;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save city", e);
        } finally {
            em.close();
        }
    }

    public UpdateCityRequest update(City city) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            City managed = em.merge(city);

            em.getTransaction().commit();

            UpdateCityRequest dto = new UpdateCityRequest();
            dto.setCity(managed.getCity());
            dto.setCountryId(managed.getCountry().getId());
            dto.setId(managed.getId());
            return dto;

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