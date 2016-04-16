/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Caracteristica;
import model.Produto;

/**
 *
 * @author asdfrofl
 */
public class CaracteristicaJpaController implements Serializable {

    public CaracteristicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caracteristica caracteristica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto = caracteristica.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                caracteristica.setProduto(produto);
            }
            em.persist(caracteristica);
            if (produto != null) {
                produto.getCaracteristicaList().add(caracteristica);
                produto = em.merge(produto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caracteristica caracteristica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracteristica persistentCaracteristica = em.find(Caracteristica.class, caracteristica.getIdcaracteristica());
            Produto produtoOld = persistentCaracteristica.getProduto();
            Produto produtoNew = caracteristica.getProduto();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                caracteristica.setProduto(produtoNew);
            }
            caracteristica = em.merge(caracteristica);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getCaracteristicaList().remove(caracteristica);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getCaracteristicaList().add(caracteristica);
                produtoNew = em.merge(produtoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caracteristica.getIdcaracteristica();
                if (findCaracteristica(id) == null) {
                    throw new NonexistentEntityException("The caracteristica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracteristica caracteristica;
            try {
                caracteristica = em.getReference(Caracteristica.class, id);
                caracteristica.getIdcaracteristica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caracteristica with id " + id + " no longer exists.", enfe);
            }
            Produto produto = caracteristica.getProduto();
            if (produto != null) {
                produto.getCaracteristicaList().remove(caracteristica);
                produto = em.merge(produto);
            }
            em.remove(caracteristica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caracteristica> findCaracteristicaEntities() {
        return findCaracteristicaEntities(true, -1, -1);
    }

    public List<Caracteristica> findCaracteristicaEntities(int maxResults, int firstResult) {
        return findCaracteristicaEntities(false, maxResults, firstResult);
    }

    private List<Caracteristica> findCaracteristicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caracteristica.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Caracteristica findCaracteristica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caracteristica.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaracteristicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caracteristica> rt = cq.from(Caracteristica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Caracteristica> findAllFeatureByProduct(Produto product){
        String query = "SELECT f FROM Caracteristica f WHERE f.produto = :product";
        
        Query q = getEntityManager().createQuery(query);
        q.setParameter("product", product);
        
        return q.getResultList();
    }
    
}
