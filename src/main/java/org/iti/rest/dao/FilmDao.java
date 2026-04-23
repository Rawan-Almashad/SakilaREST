package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.dto.AddActorToFilmRequest;
import org.iti.rest.entity.Actor;
import org.iti.rest.entity.Film;
import org.iti.rest.entity.FilmActor;
import org.iti.rest.entity.FilmActorId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class FilmDao {
    public Film save(Film film)
    {
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(film);
            em.getTransaction().commit();
            return film;
        }
        catch (Exception e)
        {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw new RuntimeException("Couldn't save film");
        }
        finally {
            em.close();
        }
    }
    public Optional<Film> findById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return Optional.ofNullable(em.find(Film.class, id));
        }
        finally {
            em.close();
        }
    }
    public List<Film> findAll(){
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("select f from Film f",Film.class).getResultList();
    }
    public void delete(Short id) {
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Film film=em.find(Film.class,id);
            if(film==null)
                throw new RuntimeException("Film not found");
            em.getTransaction().begin();
            em.remove(film);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw new RuntimeException("Couldn't delete the film");
        }
        finally {
            em.close();
        }
    }
    public Film findWithLanguage(Short id)
    {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return (Film) em.createQuery("select f from Film f join fetch f.language where f.filmId =:id").setParameter("id",id)
                .getSingleResult();
    }
    public void addActorToFilm(AddActorToFilmRequest request)
    {
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();
        try{
            em.getTransaction().begin();
            Actor actor=em.find(Actor.class,request.getActorId());
            Film film= em.find(Film.class,request.getFilmId());
            if(actor==null||film==null)
                throw new RuntimeException("Actor or Film not found");
            FilmActorId id= new FilmActorId(request.getActorId(), request.getFilmId());
            if(em.find(FilmActor.class,id)!=null)
                throw new RuntimeException("Actor already is registered in the film");
            FilmActor filmActor =new FilmActor();
            filmActor.setId(id);
            filmActor.setActor(actor);
            filmActor.setFilm(film);
            filmActor.setLastUpdate(Instant.now());
            em.persist(filmActor);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException("Couldn't add actor to film "+ e.getMessage());

        }
        finally {
            em.close();
        }
    }
}
