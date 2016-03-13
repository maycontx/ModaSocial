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
import model.Produto;
import model.Promocao;
import model.RelProdutoPromocao;

/**
 *
 * @author asdfrofl
 */
public class RelProdutoPromocaoJpaController implements Serializable {

    public RelProdutoPromocaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RelProdutoPromocao relProdutoPromocao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto = relProdutoPromocao.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                relProdutoPromocao.setProduto(produto);
            }
            Promocao promocao = relProdutoPromocao.getPromocao();
            if (promocao != null) {
                promocao = em.getReference(promocao.getClass(), promocao.getIdpromocao());
                relProdutoPromocao.setPromocao(promocao);
            }
            em.persist(relProdutoPromocao);
            if (produto != null) {
                produto.getRelProdutoPromocaoList().add(relProdutoPromocao);
                produto = em.merge(produto);
            }
            if (promocao != null) {
                promocao.getRelProdutoPromocaoList().add(relProdutoPromocao);
                promocao = em.merge(promocao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RelProdutoPromocao relProdutoPromocao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RelProdutoPromocao persistentRelProdutoPromocao = em.find(RelProdutoPromocao.class, relProdutoPromocao.getIdrelProdutoPromocao());
            Produto produtoOld = persistentRelProdutoPromocao.getProduto();
            Produto produtoNew = relProdutoPromocao.getProduto();
            Promocao promocaoOld = persistentRelProdutoPromocao.getPromocao();
            Promocao promocaoNew = relProdutoPromocao.getPromocao();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                relProdutoPromocao.setProduto(produtoNew);
            }
            if (promocaoNew != null) {
                promocaoNew = em.getReference(promocaoNew.getClass(), promocaoNew.getIdpromocao());
                relProdutoPromocao.setPromocao(promocaoNew);
            }
            relProdutoPromocao = em.merge(relProdutoPromocao);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getRelProdutoPromocaoList().remove(relProdutoPromocao);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getRelProdutoPromocaoList().add(relProdutoPromocao);
                produtoNew = em.merge(produtoNew);
            }
            if (promocaoOld != null && !promocaoOld.equals(promocaoNew)) {
                promocaoOld.getRelProdutoPromocaoList().remove(relProdutoPromocao);
                promocaoOld = em.merge(promocaoOld);
            }
            if (promocaoNew != null && !promocaoNew.equals(promocaoOld)) {
                promocaoNew.getRelProdutoPromocaoList().add(relProdutoPromocao);
                promocaoNew = em.merge(promocaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = relProdutoPromocao.getIdrelProdutoPromocao();
                if (findRelProdutoPromocao(id) == null) {
                    throw new NonexistentEntityException("The relProdutoPromocao with id " + id + " no longer exists.");
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
            RelProdutoPromocao relProdutoPromocao;
            try {
                relProdutoPromocao = em.getReference(RelProdutoPromocao.class, id);
                relProdutoPromocao.getIdrelProdutoPromocao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The relProdutoPromocao with id " + id + " no longer exists.", enfe);
            }
            Produto produto = relProdutoPromocao.getProduto();
            if (produto != null) {
                produto.getRelProdutoPromocaoList().remove(relProdutoPromocao);
                produto = em.merge(produto);
            }
            Promocao promocao = relProdutoPromocao.getPromocao();
            if (promocao != null) {
                promocao.getRelProdutoPromocaoList().remove(relProdutoPromocao);
                promocao = em.merge(promocao);
            }
            em.remove(relProdutoPromocao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RelProdutoPromocao> findRelProdutoPromocaoEntities() {
        return findRelProdutoPromocaoEntities(true, -1, -1);
    }

    public List<RelProdutoPromocao> findRelProdutoPromocaoEntities(int maxResults, int firstResult) {
        return findRelProdutoPromocaoEntities(false, maxResults, firstResult);
    }

    private List<RelProdutoPromocao> findRelProdutoPromocaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RelProdutoPromocao.class));
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

    public RelProdutoPromocao findRelProdutoPromocao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RelProdutoPromocao.class, id);
        } finally {
            em.close();
        }
    }

    public int getRelProdutoPromocaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RelProdutoPromocao> rt = cq.from(RelProdutoPromocao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
