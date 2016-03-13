/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Usuario;
import model.Venda;
import model.Cartao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Pagamento;

/**
 *
 * @author asdfrofl
 */
public class PagamentoJpaController implements Serializable {

    public PagamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagamento pagamento) {
        if (pagamento.getCartaoList() == null) {
            pagamento.setCartaoList(new ArrayList<Cartao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = pagamento.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                pagamento.setUsuario(usuario);
            }
            Venda venda = pagamento.getVenda();
            if (venda != null) {
                venda = em.getReference(venda.getClass(), venda.getIdvenda());
                pagamento.setVenda(venda);
            }
            List<Cartao> attachedCartaoList = new ArrayList<Cartao>();
            for (Cartao cartaoListCartaoToAttach : pagamento.getCartaoList()) {
                cartaoListCartaoToAttach = em.getReference(cartaoListCartaoToAttach.getClass(), cartaoListCartaoToAttach.getIdcartao());
                attachedCartaoList.add(cartaoListCartaoToAttach);
            }
            pagamento.setCartaoList(attachedCartaoList);
            em.persist(pagamento);
            if (usuario != null) {
                usuario.getPagamentoList().add(pagamento);
                usuario = em.merge(usuario);
            }
            if (venda != null) {
                venda.getPagamentoList().add(pagamento);
                venda = em.merge(venda);
            }
            for (Cartao cartaoListCartao : pagamento.getCartaoList()) {
                Pagamento oldPagamentoOfCartaoListCartao = cartaoListCartao.getPagamento();
                cartaoListCartao.setPagamento(pagamento);
                cartaoListCartao = em.merge(cartaoListCartao);
                if (oldPagamentoOfCartaoListCartao != null) {
                    oldPagamentoOfCartaoListCartao.getCartaoList().remove(cartaoListCartao);
                    oldPagamentoOfCartaoListCartao = em.merge(oldPagamentoOfCartaoListCartao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagamento pagamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento persistentPagamento = em.find(Pagamento.class, pagamento.getIdpagamento());
            Usuario usuarioOld = persistentPagamento.getUsuario();
            Usuario usuarioNew = pagamento.getUsuario();
            Venda vendaOld = persistentPagamento.getVenda();
            Venda vendaNew = pagamento.getVenda();
            List<Cartao> cartaoListOld = persistentPagamento.getCartaoList();
            List<Cartao> cartaoListNew = pagamento.getCartaoList();
            List<String> illegalOrphanMessages = null;
            for (Cartao cartaoListOldCartao : cartaoListOld) {
                if (!cartaoListNew.contains(cartaoListOldCartao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cartao " + cartaoListOldCartao + " since its pagamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                pagamento.setUsuario(usuarioNew);
            }
            if (vendaNew != null) {
                vendaNew = em.getReference(vendaNew.getClass(), vendaNew.getIdvenda());
                pagamento.setVenda(vendaNew);
            }
            List<Cartao> attachedCartaoListNew = new ArrayList<Cartao>();
            for (Cartao cartaoListNewCartaoToAttach : cartaoListNew) {
                cartaoListNewCartaoToAttach = em.getReference(cartaoListNewCartaoToAttach.getClass(), cartaoListNewCartaoToAttach.getIdcartao());
                attachedCartaoListNew.add(cartaoListNewCartaoToAttach);
            }
            cartaoListNew = attachedCartaoListNew;
            pagamento.setCartaoList(cartaoListNew);
            pagamento = em.merge(pagamento);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPagamentoList().remove(pagamento);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPagamentoList().add(pagamento);
                usuarioNew = em.merge(usuarioNew);
            }
            if (vendaOld != null && !vendaOld.equals(vendaNew)) {
                vendaOld.getPagamentoList().remove(pagamento);
                vendaOld = em.merge(vendaOld);
            }
            if (vendaNew != null && !vendaNew.equals(vendaOld)) {
                vendaNew.getPagamentoList().add(pagamento);
                vendaNew = em.merge(vendaNew);
            }
            for (Cartao cartaoListNewCartao : cartaoListNew) {
                if (!cartaoListOld.contains(cartaoListNewCartao)) {
                    Pagamento oldPagamentoOfCartaoListNewCartao = cartaoListNewCartao.getPagamento();
                    cartaoListNewCartao.setPagamento(pagamento);
                    cartaoListNewCartao = em.merge(cartaoListNewCartao);
                    if (oldPagamentoOfCartaoListNewCartao != null && !oldPagamentoOfCartaoListNewCartao.equals(pagamento)) {
                        oldPagamentoOfCartaoListNewCartao.getCartaoList().remove(cartaoListNewCartao);
                        oldPagamentoOfCartaoListNewCartao = em.merge(oldPagamentoOfCartaoListNewCartao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagamento.getIdpagamento();
                if (findPagamento(id) == null) {
                    throw new NonexistentEntityException("The pagamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento pagamento;
            try {
                pagamento = em.getReference(Pagamento.class, id);
                pagamento.getIdpagamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cartao> cartaoListOrphanCheck = pagamento.getCartaoList();
            for (Cartao cartaoListOrphanCheckCartao : cartaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pagamento (" + pagamento + ") cannot be destroyed since the Cartao " + cartaoListOrphanCheckCartao + " in its cartaoList field has a non-nullable pagamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = pagamento.getUsuario();
            if (usuario != null) {
                usuario.getPagamentoList().remove(pagamento);
                usuario = em.merge(usuario);
            }
            Venda venda = pagamento.getVenda();
            if (venda != null) {
                venda.getPagamentoList().remove(pagamento);
                venda = em.merge(venda);
            }
            em.remove(pagamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagamento> findPagamentoEntities() {
        return findPagamentoEntities(true, -1, -1);
    }

    public List<Pagamento> findPagamentoEntities(int maxResults, int firstResult) {
        return findPagamentoEntities(false, maxResults, firstResult);
    }

    private List<Pagamento> findPagamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagamento.class));
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

    public Pagamento findPagamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagamento> rt = cq.from(Pagamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
