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
import model.ImagemProduto;
import model.Produto;

/**
 *
 * @author asdfrofl
 */
public class ImagemProdutoJpaController implements Serializable {

    public ImagemProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImagemProduto imagemProduto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto = imagemProduto.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                imagemProduto.setProduto(produto);
            }
            em.persist(imagemProduto);
            if (produto != null) {
                produto.getImagemProdutoList().add(imagemProduto);
                produto = em.merge(produto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagemProduto imagemProduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagemProduto persistentImagemProduto = em.find(ImagemProduto.class, imagemProduto.getIdimagemProduto());
            Produto produtoOld = persistentImagemProduto.getProduto();
            Produto produtoNew = imagemProduto.getProduto();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                imagemProduto.setProduto(produtoNew);
            }
            imagemProduto = em.merge(imagemProduto);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getImagemProdutoList().remove(imagemProduto);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getImagemProdutoList().add(imagemProduto);
                produtoNew = em.merge(produtoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imagemProduto.getIdimagemProduto();
                if (findImagemProduto(id) == null) {
                    throw new NonexistentEntityException("The imagemProduto with id " + id + " no longer exists.");
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
            ImagemProduto imagemProduto;
            try {
                imagemProduto = em.getReference(ImagemProduto.class, id);
                imagemProduto.getIdimagemProduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagemProduto with id " + id + " no longer exists.", enfe);
            }
            Produto produto = imagemProduto.getProduto();
            if (produto != null) {
                produto.getImagemProdutoList().remove(imagemProduto);
                produto = em.merge(produto);
            }
            em.remove(imagemProduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagemProduto> findImagemProdutoEntities() {
        return findImagemProdutoEntities(true, -1, -1);
    }

    public List<ImagemProduto> findImagemProdutoEntities(int maxResults, int firstResult) {
        return findImagemProdutoEntities(false, maxResults, firstResult);
    }

    private List<ImagemProduto> findImagemProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagemProduto.class));
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

    public ImagemProduto findImagemProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagemProduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagemProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagemProduto> rt = cq.from(ImagemProduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
