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
import model.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Fornecedor;

/**
 *
 * @author asdfrofl
 */
public class FornecedorJpaController implements Serializable {

    public FornecedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornecedor fornecedor) {
        if (fornecedor.getProdutoList() == null) {
            fornecedor.setProdutoList(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Produto> attachedProdutoList = new ArrayList<Produto>();
            for (Produto produtoListProdutoToAttach : fornecedor.getProdutoList()) {
                produtoListProdutoToAttach = em.getReference(produtoListProdutoToAttach.getClass(), produtoListProdutoToAttach.getIdproduto());
                attachedProdutoList.add(produtoListProdutoToAttach);
            }
            fornecedor.setProdutoList(attachedProdutoList);
            em.persist(fornecedor);
            for (Produto produtoListProduto : fornecedor.getProdutoList()) {
                Fornecedor oldFornecedorOfProdutoListProduto = produtoListProduto.getFornecedor();
                produtoListProduto.setFornecedor(fornecedor);
                produtoListProduto = em.merge(produtoListProduto);
                if (oldFornecedorOfProdutoListProduto != null) {
                    oldFornecedorOfProdutoListProduto.getProdutoList().remove(produtoListProduto);
                    oldFornecedorOfProdutoListProduto = em.merge(oldFornecedorOfProdutoListProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fornecedor fornecedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor persistentFornecedor = em.find(Fornecedor.class, fornecedor.getIdfornecedor());
            List<Produto> produtoListOld = persistentFornecedor.getProdutoList();
            List<Produto> produtoListNew = fornecedor.getProdutoList();
            List<String> illegalOrphanMessages = null;
            for (Produto produtoListOldProduto : produtoListOld) {
                if (!produtoListNew.contains(produtoListOldProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Produto " + produtoListOldProduto + " since its fornecedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Produto> attachedProdutoListNew = new ArrayList<Produto>();
            for (Produto produtoListNewProdutoToAttach : produtoListNew) {
                produtoListNewProdutoToAttach = em.getReference(produtoListNewProdutoToAttach.getClass(), produtoListNewProdutoToAttach.getIdproduto());
                attachedProdutoListNew.add(produtoListNewProdutoToAttach);
            }
            produtoListNew = attachedProdutoListNew;
            fornecedor.setProdutoList(produtoListNew);
            fornecedor = em.merge(fornecedor);
            for (Produto produtoListNewProduto : produtoListNew) {
                if (!produtoListOld.contains(produtoListNewProduto)) {
                    Fornecedor oldFornecedorOfProdutoListNewProduto = produtoListNewProduto.getFornecedor();
                    produtoListNewProduto.setFornecedor(fornecedor);
                    produtoListNewProduto = em.merge(produtoListNewProduto);
                    if (oldFornecedorOfProdutoListNewProduto != null && !oldFornecedorOfProdutoListNewProduto.equals(fornecedor)) {
                        oldFornecedorOfProdutoListNewProduto.getProdutoList().remove(produtoListNewProduto);
                        oldFornecedorOfProdutoListNewProduto = em.merge(oldFornecedorOfProdutoListNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fornecedor.getIdfornecedor();
                if (findFornecedor(id) == null) {
                    throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.");
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
            Fornecedor fornecedor;
            try {
                fornecedor = em.getReference(Fornecedor.class, id);
                fornecedor.getIdfornecedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Produto> produtoListOrphanCheck = fornecedor.getProdutoList();
            for (Produto produtoListOrphanCheckProduto : produtoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fornecedor (" + fornecedor + ") cannot be destroyed since the Produto " + produtoListOrphanCheckProduto + " in its produtoList field has a non-nullable fornecedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fornecedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fornecedor> findFornecedorEntities() {
        return findFornecedorEntities(true, -1, -1);
    }

    public List<Fornecedor> findFornecedorEntities(int maxResults, int firstResult) {
        return findFornecedorEntities(false, maxResults, firstResult);
    }

    private List<Fornecedor> findFornecedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedor.class));
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

    public Fornecedor findFornecedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fornecedor> rt = cq.from(Fornecedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
