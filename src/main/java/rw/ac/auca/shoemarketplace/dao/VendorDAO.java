package rw.ac.auca.shoemarketplace.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import rw.ac.auca.shoemarketplace.model.Vendor;
import rw.ac.auca.shoemarketplace.util.JPAUtil;

import java.util.List;

public class VendorDAO {

    // ---------- CREATE ----------
    public void save(Vendor vendor) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(vendor);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- READ (all) ----------
    public List<Vendor> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Vendor v ORDER BY v.id", Vendor.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- READ (one, by id) ----------
    public Vendor findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Vendor.class, id);
        } finally {
            em.close();
        }
    }

    // ---------- UPDATE ----------
    public void update(Vendor vendor) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vendor);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- DELETE ----------
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Vendor vendor = em.find(Vendor.class, id);
            if (vendor != null) {
                em.remove(vendor);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- VALIDATION HELPER: check duplicate email ----------
    public boolean isEmailTaken(String email, Long excludeId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(v) FROM Vendor v WHERE v.email = :email AND v.id != :excludeId";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("email", email)
                    .setParameter("excludeId", excludeId == null ? -1L : excludeId)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}