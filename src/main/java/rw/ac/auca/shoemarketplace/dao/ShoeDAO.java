package rw.ac.auca.shoemarketplace.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import rw.ac.auca.shoemarketplace.model.Shoe;
import rw.ac.auca.shoemarketplace.util.JPAUtil;

import java.util.List;

public class ShoeDAO {

    // ---------- CREATE ----------
    public void save(Shoe shoe) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(shoe);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- READ (all) ----------
    public List<Shoe> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JOIN FETCH loads the vendor eagerly so we can show vendor name in the list
            // without hitting a LazyInitializationException after em.close()
            return em.createQuery(
                            "SELECT s FROM Shoe s JOIN FETCH s.vendor ORDER BY s.id", Shoe.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- READ (one, by id) ----------
    public Shoe findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT s FROM Shoe s JOIN FETCH s.vendor WHERE s.id = :id", Shoe.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    // ---------- UPDATE ----------
    public void update(Shoe shoe) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(shoe);
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
            Shoe shoe = em.find(Shoe.class, id);
            if (shoe != null) {
                em.remove(shoe);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ---------- Extra: filter by size (useful for your buyer-search use case) ----------
    public List<Shoe> findBySize(Integer size) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT s FROM Shoe s JOIN FETCH s.vendor WHERE s.size = :size ORDER BY s.price", Shoe.class)
                    .setParameter("size", size)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}