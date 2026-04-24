package org.iti.rest.dao;

import jakarta.persistence.EntityManager;
import org.iti.rest.config.JPAUtil;
import org.iti.rest.entity.Customer;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CustomerDao {

    public Optional<Customer> findById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return Optional.ofNullable(customer);
        } finally {
            em.close();
        }
    }

    public List<Customer> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Customer c " +
                            "JOIN FETCH c.store " +
                            "JOIN FETCH c.address addr " +
                            "JOIN FETCH addr.city city " +
                            "JOIN FETCH city.country " +
                            "ORDER BY c.id",
                    Customer.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Customer save(Customer customer) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            customer.setCreateDate(Instant.now());
            customer.setLastUpdate(Instant.now());
            if (customer.getActive() == null) {
                customer.setActive(true);
            }
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save customer", e);
        } finally {
            em.close();
        }
    }

    public Customer update(Customer customer) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            customer.setLastUpdate(Instant.now());
            Customer managed = em.merge(customer);
            em.getTransaction().commit();
            return managed;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update customer", e);
        } finally {
            em.close();
        }
    }

    public boolean deleteById(Short id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Customer customer = em.find(Customer.class, id);
            if (customer == null) {
                em.getTransaction().commit();
                return false;
            }
            em.remove(customer);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete customer (may have rental history)", e);
        } finally {
            em.close();
        }
    }
}