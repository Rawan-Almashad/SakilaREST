package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.entity.Language;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class LanguageDao {

    public Optional<Language> findById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Language language = em.find(Language.class, id);
            return Optional.ofNullable(language);
        } finally {
            em.close();
        }
    }

    public List<Language> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Language l ORDER BY l.name", Language.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Language save(Language language) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            language.setLastUpdate(Instant.now());
            em.persist(language);
            em.getTransaction().commit();
            return language;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save language", e);
        } finally {
            em.close();
        }
    }

    public Language update(Language language) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Language managed = em.merge(language);
            em.getTransaction().commit();
            return managed;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update language", e);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            Language language = em.find(Language.class, id);
            if (language == null) {
                em.getTransaction().commit();
                return false;
            }

            em.remove(language);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete language (may be referenced by films)", e);
        } finally {
            em.close();
        }
    }
}