package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.entity.Actor;

import java.util.List;
import java.util.Optional;

public class ActorDao {
    public Actor save(Actor actor)
    {
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(actor);
            em.getTransaction().commit();
            return actor;
        }catch (Exception e)
        {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw new RuntimeException("Couldn't save Actor");
        }
        finally {
            em.close();
        }
    }
    public Optional<Actor> findById(Short id) {
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        return Optional.ofNullable(em.find(Actor.class,id));
    }
    public List<Actor> findAll(){
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("select a from Actor a",Actor.class).getResultList();
    }
    public void delete(Short id) {
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Actor actor=em.find(Actor.class,id);
            em.getTransaction().begin();
            em.remove(actor);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw new RuntimeException("Couldn't delete the Actor");
        }
        finally {
            em.close();
        }
    }
}
