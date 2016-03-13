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
import model.Carrinho;
import model.Endereço;
import model.Pagamento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Venda;

/**
 *
 * @author asdfrofl
 */
public class VendaJpaController implements Serializable {

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) {
        if (venda.getPagamentoList() == null) {
            venda.setPagamentoList(new ArrayList<Pagamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrinho carrinho = venda.getCarrinho();
            if (carrinho != null) {
                carrinho = em.getReference(carrinho.getClass(), carrinho.getIdcarrinho());
                venda.setCarrinho(carrinho);
            }
            Endereço endereco = venda.getEndereco();
            if (endereco != null) {
                endereco = em.getReference(endereco.getClass(), endereco.getIdendereço());
                venda.setEndereco(endereco);
            }
            List<Pagamento> attachedPagamentoList = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListPagamentoToAttach : venda.getPagamentoList()) {
                pagamentoListPagamentoToAttach = em.getReference(pagamentoListPagamentoToAttach.getClass(), pagamentoListPagamentoToAttach.getIdpagamento());
                attachedPagamentoList.add(pagamentoListPagamentoToAttach);
            }
            venda.setPagamentoList(attachedPagamentoList);
            em.persist(venda);
            if (carrinho != null) {
                carrinho.getVendaList().add(venda);
                carrinho = em.merge(carrinho);
            }
            if (endereco != null) {
                endereco.getVendaList().add(venda);
                endereco = em.merge(endereco);
            }
            for (Pagamento pagamentoListPagamento : venda.getPagamentoList()) {
                Venda oldVendaOfPagamentoListPagamento = pagamentoListPagamento.getVenda();
                pagamentoListPagamento.setVenda(venda);
                pagamentoListPagamento = em.merge(pagamentoListPagamento);
                if (oldVendaOfPagamentoListPagamento != null) {
                    oldVendaOfPagamentoListPagamento.getPagamentoList().remove(pagamentoListPagamento);
                    oldVendaOfPagamentoListPagamento = em.merge(oldVendaOfPagamentoListPagamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda venda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVenda = em.find(Venda.class, venda.getIdvenda());
            Carrinho carrinhoOld = persistentVenda.getCarrinho();
            Carrinho carrinhoNew = venda.getCarrinho();
            Endereço enderecoOld = persistentVenda.getEndereco();
            Endereço enderecoNew = venda.getEndereco();
            List<Pagamento> pagamentoListOld = persistentVenda.getPagamentoList();
            List<Pagamento> pagamentoListNew = venda.getPagamentoList();
            List<String> illegalOrphanMessages = null;
            for (Pagamento pagamentoListOldPagamento : pagamentoListOld) {
                if (!pagamentoListNew.contains(pagamentoListOldPagamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagamento " + pagamentoListOldPagamento + " since its venda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (carrinhoNew != null) {
                carrinhoNew = em.getReference(carrinhoNew.getClass(), carrinhoNew.getIdcarrinho());
                venda.setCarrinho(carrinhoNew);
            }
            if (enderecoNew != null) {
                enderecoNew = em.getReference(enderecoNew.getClass(), enderecoNew.getIdendereço());
                venda.setEndereco(enderecoNew);
            }
            List<Pagamento> attachedPagamentoListNew = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListNewPagamentoToAttach : pagamentoListNew) {
                pagamentoListNewPagamentoToAttach = em.getReference(pagamentoListNewPagamentoToAttach.getClass(), pagamentoListNewPagamentoToAttach.getIdpagamento());
                attachedPagamentoListNew.add(pagamentoListNewPagamentoToAttach);
            }
            pagamentoListNew = attachedPagamentoListNew;
            venda.setPagamentoList(pagamentoListNew);
            venda = em.merge(venda);
            if (carrinhoOld != null && !carrinhoOld.equals(carrinhoNew)) {
                carrinhoOld.getVendaList().remove(venda);
                carrinhoOld = em.merge(carrinhoOld);
            }
            if (carrinhoNew != null && !carrinhoNew.equals(carrinhoOld)) {
                carrinhoNew.getVendaList().add(venda);
                carrinhoNew = em.merge(carrinhoNew);
            }
            if (enderecoOld != null && !enderecoOld.equals(enderecoNew)) {
                enderecoOld.getVendaList().remove(venda);
                enderecoOld = em.merge(enderecoOld);
            }
            if (enderecoNew != null && !enderecoNew.equals(enderecoOld)) {
                enderecoNew.getVendaList().add(venda);
                enderecoNew = em.merge(enderecoNew);
            }
            for (Pagamento pagamentoListNewPagamento : pagamentoListNew) {
                if (!pagamentoListOld.contains(pagamentoListNewPagamento)) {
                    Venda oldVendaOfPagamentoListNewPagamento = pagamentoListNewPagamento.getVenda();
                    pagamentoListNewPagamento.setVenda(venda);
                    pagamentoListNewPagamento = em.merge(pagamentoListNewPagamento);
                    if (oldVendaOfPagamentoListNewPagamento != null && !oldVendaOfPagamentoListNewPagamento.equals(venda)) {
                        oldVendaOfPagamentoListNewPagamento.getPagamentoList().remove(pagamentoListNewPagamento);
                        oldVendaOfPagamentoListNewPagamento = em.merge(oldVendaOfPagamentoListNewPagamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venda.getIdvenda();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
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
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getIdvenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pagamento> pagamentoListOrphanCheck = venda.getPagamentoList();
            for (Pagamento pagamentoListOrphanCheckPagamento : pagamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Venda (" + venda + ") cannot be destroyed since the Pagamento " + pagamentoListOrphanCheckPagamento + " in its pagamentoList field has a non-nullable venda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Carrinho carrinho = venda.getCarrinho();
            if (carrinho != null) {
                carrinho.getVendaList().remove(venda);
                carrinho = em.merge(carrinho);
            }
            Endereço endereco = venda.getEndereco();
            if (endereco != null) {
                endereco.getVendaList().remove(venda);
                endereco = em.merge(endereco);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
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

    public Venda findVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
