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
import model.Cartao;
import model.Pagamento;

/**
 *
 * @author asdfrofl
 */
public class CartaoJpaController implements Serializable {

    public CartaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cartao cartao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento pagamento = cartao.getPagamento();
            if (pagamento != null) {
                pagamento = em.getReference(pagamento.getClass(), pagamento.getIdpagamento());
                cartao.setPagamento(pagamento);
            }
            em.persist(cartao);
            if (pagamento != null) {
                pagamento.getCartaoList().add(cartao);
                pagamento = em.merge(pagamento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cartao cartao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cartao persistentCartao = em.find(Cartao.class, cartao.getIdcartao());
            Pagamento pagamentoOld = persistentCartao.getPagamento();
            Pagamento pagamentoNew = cartao.getPagamento();
            if (pagamentoNew != null) {
                pagamentoNew = em.getReference(pagamentoNew.getClass(), pagamentoNew.getIdpagamento());
                cartao.setPagamento(pagamentoNew);
            }
            cartao = em.merge(cartao);
            if (pagamentoOld != null && !pagamentoOld.equals(pagamentoNew)) {
                pagamentoOld.getCartaoList().remove(cartao);
                pagamentoOld = em.merge(pagamentoOld);
            }
            if (pagamentoNew != null && !pagamentoNew.equals(pagamentoOld)) {
                pagamentoNew.getCartaoList().add(cartao);
                pagamentoNew = em.merge(pagamentoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cartao.getIdcartao();
                if (findCartao(id) == null) {
                    throw new NonexistentEntityException("The cartao with id " + id + " no longer exists.");
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
            Cartao cartao;
            try {
                cartao = em.getReference(Cartao.class, id);
                cartao.getIdcartao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cartao with id " + id + " no longer exists.", enfe);
            }
            Pagamento pagamento = cartao.getPagamento();
            if (pagamento != null) {
                pagamento.getCartaoList().remove(cartao);
                pagamento = em.merge(pagamento);
            }
            em.remove(cartao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cartao> findCartaoEntities() {
        return findCartaoEntities(true, -1, -1);
    }

    public List<Cartao> findCartaoEntities(int maxResults, int firstResult) {
        return findCartaoEntities(false, maxResults, firstResult);
    }

    private List<Cartao> findCartaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cartao.class));
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

    public Cartao findCartao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cartao.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cartao> rt = cq.from(Cartao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
