package org.iti.rest.service;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.dao.ActorDao;
import org.iti.rest.dto.CreateActorRequest;
import org.iti.rest.entity.Actor;

import java.time.Instant;
import java.util.List;

public class ActorService {
    private ActorDao actorDao;
    public ActorService(ActorDao actorDao)
    {
        this.actorDao=actorDao;
    }
    public Actor create(CreateActorRequest request)
    {
        Actor actor=new Actor();
        actor.setFirstName(request.getFirstName());
        actor.setLastName(request.getLastName());
        actor.setLastUpdate(Instant.now());
        return actorDao.save(actor);
    }
    public Actor findById(Short id)throws RuntimeException {

        return actorDao.findById(id).orElseThrow(() -> new RuntimeException("Actor not found"));
    }
    public List<Actor> findAll(){
      return actorDao.findAll();
    }
    public void delete(Short id) {
        EntityManager em= JPAUtil.getEntityManagerFactory().createEntityManager();

            Actor actor=em.find(Actor.class,id);
          if(actor==null)
          {
              throw new RuntimeException("Actor not found");
          }
          actorDao.delete(id);
    }
}
