package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.entity.Staff;

import java.util.List;
import java.util.Optional;

public class StaffDao {

    public Optional<Staff> findById(Byte id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Staff staff = em.find(Staff.class, id);
            return Optional.ofNullable(staff);
        } finally {
            em.close();
        }
    }

    public List<Staff> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM Staff s JOIN FETCH s.address JOIN FETCH s.store ORDER BY s.id",
                    Staff.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Staff save(Staff staff) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            staff.setActive(true);
            em.persist(staff);
            em.getTransaction().commit();
            return staff;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save staff", e);
        } finally {
            em.close();
        }
    }

    public Staff update(Staff staff) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Staff managed = em.merge(staff);
            em.getTransaction().commit();
            return managed;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update staff", e);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Byte id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Staff staff = em.find(Staff.class, id);
            if (staff == null) {
                em.getTransaction().commit();
                return false;
            }
            em.remove(staff);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete staff", e);
        } finally {
            em.close();
        }
    }
}