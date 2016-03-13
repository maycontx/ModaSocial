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
import model.Carrinho;
import model.RelProdutoCarrinho;

/**
 *
 * @author asdfrofl
 */
public class RelProdutoCarrinhoJpaController implements Serializable {

    public RelProdutoCarrinhoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RelProdutoCarrinho relProdutoCarrinho) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto = relProdutoCarrinho.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                relProdutoCarrinho.setProduto(produto);
            }
            Carrinho carrinho = relProdutoCarrinho.getCarrinho();
            if (carrinho != null) {
                carrinho = em.getReference(carrinho.getClass(), carrinho.getIdcarrinho());
                relProdutoCarrinho.setCarrinho(carrinho);
            }
            em.persist(relProdutoCarrinho);
            if (produto != null) {
                produto.getRelProdutoCarrinhoList().add(relProdutoCarrinho);
                produto = em.merge(produto);
            }
            if (carrinho != null) {
                carrinho.getRelProdutoCarrinhoList().add(relProdutoCarrinho);
                carrinho = em.merge(carrinho);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RelProdutoCarrinho relProdutoCarrinho) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RelProdutoCarrinho persistentRelProdutoCarrinho = em.find(RelProdutoCarrinho.class, relProdutoCarrinho.getIdrelProdutoCarrinho());
            Produto produtoOld = persistentRelProdutoCarrinho.getProduto();
            Produto produtoNew = relProdutoCarrinho.getProduto();
            Carrinho carrinhoOld = persistentRelProdutoCarrinho.getCarrinho();
            Carrinho carrinhoNew = relProdutoCarrinho.getCarrinho();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                relProdutoCarrinho.setProduto(produtoNew);
            }
            if (carrinhoNew != null) {
                carrinhoNew = em.getReference(carrinhoNew.getClass(), carrinhoNew.getIdcarrinho());
                relProdutoCarrinho.setCarrinho(carrinhoNew);
            }
            relProdutoCarrinho = em.merge(relProdutoCarrinho);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getRelProdutoCarrinhoList().remove(relProdutoCarrinho);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getRelProdutoCarrinhoList().add(relProdutoCarrinho);
                produtoNew = em.merge(produtoNew);
            }
            if (carrinhoOld != null && !carrinhoOld.equals(carrinhoNew)) {
                carrinhoOld.getRelProdutoCarrinhoList().remove(relProdutoCarrinho);
                carrinhoOld = em.merge(carrinhoOld);
            }
            if (carrinhoNew != null && !carrinhoNew.equals(carrinhoOld)) {
                carrinhoNew.getRelProdutoCarrinhoList().add(relProdutoCarrinho);
                carrinhoNew = em.merge(carrinhoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = relProdutoCarrinho.getIdrelProdutoCarrinho();
                if (findRelProdutoCarrinho(id) == null) {
                    throw new NonexistentEntityException("The relProdutoCarrinho with id " + id + " no longer exists.");
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
            RelProdutoCarrinho relProdutoCarrinho;
            try {
                relProdutoCarrinho = em.getReference(RelProdutoCarrinho.class, id);
                relProdutoCarrinho.getIdrelProdutoCarrinho();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The relProdutoCarrinho with id " + id + " no longer exists.", enfe);
            }
            Produto produto = relProdutoCarrinho.getProduto();
            if (produto != null) {
                produto.getRelProdutoCarrinhoList().remove(relProdutoCarrinho);
                produto = em.merge(produto);
            }
            Carrinho carrinho = relProdutoCarrinho.getCarrinho();
            if (carrinho != null) {
                carrinho.getRelProdutoCarrinhoList().remove(relProdutoCarrinho);
                carrinho = em.merge(carrinho);
            }
            em.remove(relProdutoCarrinho);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RelProdutoCarrinho> findRelProdutoCarrinhoEntities() {
        return findRelProdutoCarrinhoEntities(true, -1, -1);
    }

    public List<RelProdutoCarrinho> findRelProdutoCarrinhoEntities(int maxResults, int firstResult) {
        return findRelProdutoCarrinhoEntities(false, maxResults, firstResult);
    }

    private List<RelProdutoCarrinho> findRelProdutoCarrinhoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RelProdutoCarrinho.class));
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

    public RelProdutoCarrinho findRelProdutoCarrinho(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RelProdutoCarrinho.class, id);
        } finally {
            em.close();
        }
    }

    public int getRelProdutoCarrinhoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RelProdutoCarrinho> rt = cq.from(RelProdutoCarrinho.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
