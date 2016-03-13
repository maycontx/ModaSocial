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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Endereço;

/**
 *
 * @author asdfrofl
 */
public class EndereçoJpaController implements Serializable {

    public EndereçoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endereço endereço) {
        if (endereço.getVendaList() == null) {
            endereço.setVendaList(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = endereço.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                endereço.setUsuario(usuario);
            }
            List<Venda> attachedVendaList = new ArrayList<Venda>();
            for (Venda vendaListVendaToAttach : endereço.getVendaList()) {
                vendaListVendaToAttach = em.getReference(vendaListVendaToAttach.getClass(), vendaListVendaToAttach.getIdvenda());
                attachedVendaList.add(vendaListVendaToAttach);
            }
            endereço.setVendaList(attachedVendaList);
            em.persist(endereço);
            if (usuario != null) {
                usuario.getEndereçoList().add(endereço);
                usuario = em.merge(usuario);
            }
            for (Venda vendaListVenda : endereço.getVendaList()) {
                Endereço oldEnderecoOfVendaListVenda = vendaListVenda.getEndereco();
                vendaListVenda.setEndereco(endereço);
                vendaListVenda = em.merge(vendaListVenda);
                if (oldEnderecoOfVendaListVenda != null) {
                    oldEnderecoOfVendaListVenda.getVendaList().remove(vendaListVenda);
                    oldEnderecoOfVendaListVenda = em.merge(oldEnderecoOfVendaListVenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endereço endereço) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endereço persistentEndereço = em.find(Endereço.class, endereço.getIdendereço());
            Usuario usuarioOld = persistentEndereço.getUsuario();
            Usuario usuarioNew = endereço.getUsuario();
            List<Venda> vendaListOld = persistentEndereço.getVendaList();
            List<Venda> vendaListNew = endereço.getVendaList();
            List<String> illegalOrphanMessages = null;
            for (Venda vendaListOldVenda : vendaListOld) {
                if (!vendaListNew.contains(vendaListOldVenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venda " + vendaListOldVenda + " since its endereco field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                endereço.setUsuario(usuarioNew);
            }
            List<Venda> attachedVendaListNew = new ArrayList<Venda>();
            for (Venda vendaListNewVendaToAttach : vendaListNew) {
                vendaListNewVendaToAttach = em.getReference(vendaListNewVendaToAttach.getClass(), vendaListNewVendaToAttach.getIdvenda());
                attachedVendaListNew.add(vendaListNewVendaToAttach);
            }
            vendaListNew = attachedVendaListNew;
            endereço.setVendaList(vendaListNew);
            endereço = em.merge(endereço);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getEndereçoList().remove(endereço);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getEndereçoList().add(endereço);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Venda vendaListNewVenda : vendaListNew) {
                if (!vendaListOld.contains(vendaListNewVenda)) {
                    Endereço oldEnderecoOfVendaListNewVenda = vendaListNewVenda.getEndereco();
                    vendaListNewVenda.setEndereco(endereço);
                    vendaListNewVenda = em.merge(vendaListNewVenda);
                    if (oldEnderecoOfVendaListNewVenda != null && !oldEnderecoOfVendaListNewVenda.equals(endereço)) {
                        oldEnderecoOfVendaListNewVenda.getVendaList().remove(vendaListNewVenda);
                        oldEnderecoOfVendaListNewVenda = em.merge(oldEnderecoOfVendaListNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endereço.getIdendereço();
                if (findEndereço(id) == null) {
                    throw new NonexistentEntityException("The endere\u00e7o with id " + id + " no longer exists.");
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
            Endereço endereço;
            try {
                endereço = em.getReference(Endereço.class, id);
                endereço.getIdendereço();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endere\u00e7o with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Venda> vendaListOrphanCheck = endereço.getVendaList();
            for (Venda vendaListOrphanCheckVenda : vendaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Endere\u00e7o (" + endereço + ") cannot be destroyed since the Venda " + vendaListOrphanCheckVenda + " in its vendaList field has a non-nullable endereco field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = endereço.getUsuario();
            if (usuario != null) {
                usuario.getEndereçoList().remove(endereço);
                usuario = em.merge(usuario);
            }
            em.remove(endereço);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endereço> findEndereçoEntities() {
        return findEndereçoEntities(true, -1, -1);
    }

    public List<Endereço> findEndereçoEntities(int maxResults, int firstResult) {
        return findEndereçoEntities(false, maxResults, firstResult);
    }

    private List<Endereço> findEndereçoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Endereço.class));
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

    public Endereço findEndereço(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endereço.class, id);
        } finally {
            em.close();
        }
    }

    public int getEndereçoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Endereço> rt = cq.from(Endereço.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
