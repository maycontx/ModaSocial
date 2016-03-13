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
import model.RelProdutoPromocao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Promocao;

/**
 *
 * @author asdfrofl
 */
public class PromocaoJpaController implements Serializable {

    public PromocaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocao promocao) {
        if (promocao.getRelProdutoPromocaoList() == null) {
            promocao.setRelProdutoPromocaoList(new ArrayList<RelProdutoPromocao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RelProdutoPromocao> attachedRelProdutoPromocaoList = new ArrayList<RelProdutoPromocao>();
            for (RelProdutoPromocao relProdutoPromocaoListRelProdutoPromocaoToAttach : promocao.getRelProdutoPromocaoList()) {
                relProdutoPromocaoListRelProdutoPromocaoToAttach = em.getReference(relProdutoPromocaoListRelProdutoPromocaoToAttach.getClass(), relProdutoPromocaoListRelProdutoPromocaoToAttach.getIdrelProdutoPromocao());
                attachedRelProdutoPromocaoList.add(relProdutoPromocaoListRelProdutoPromocaoToAttach);
            }
            promocao.setRelProdutoPromocaoList(attachedRelProdutoPromocaoList);
            em.persist(promocao);
            for (RelProdutoPromocao relProdutoPromocaoListRelProdutoPromocao : promocao.getRelProdutoPromocaoList()) {
                Promocao oldPromocaoOfRelProdutoPromocaoListRelProdutoPromocao = relProdutoPromocaoListRelProdutoPromocao.getPromocao();
                relProdutoPromocaoListRelProdutoPromocao.setPromocao(promocao);
                relProdutoPromocaoListRelProdutoPromocao = em.merge(relProdutoPromocaoListRelProdutoPromocao);
                if (oldPromocaoOfRelProdutoPromocaoListRelProdutoPromocao != null) {
                    oldPromocaoOfRelProdutoPromocaoListRelProdutoPromocao.getRelProdutoPromocaoList().remove(relProdutoPromocaoListRelProdutoPromocao);
                    oldPromocaoOfRelProdutoPromocaoListRelProdutoPromocao = em.merge(oldPromocaoOfRelProdutoPromocaoListRelProdutoPromocao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promocao promocao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocao persistentPromocao = em.find(Promocao.class, promocao.getIdpromocao());
            List<RelProdutoPromocao> relProdutoPromocaoListOld = persistentPromocao.getRelProdutoPromocaoList();
            List<RelProdutoPromocao> relProdutoPromocaoListNew = promocao.getRelProdutoPromocaoList();
            List<String> illegalOrphanMessages = null;
            for (RelProdutoPromocao relProdutoPromocaoListOldRelProdutoPromocao : relProdutoPromocaoListOld) {
                if (!relProdutoPromocaoListNew.contains(relProdutoPromocaoListOldRelProdutoPromocao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RelProdutoPromocao " + relProdutoPromocaoListOldRelProdutoPromocao + " since its promocao field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RelProdutoPromocao> attachedRelProdutoPromocaoListNew = new ArrayList<RelProdutoPromocao>();
            for (RelProdutoPromocao relProdutoPromocaoListNewRelProdutoPromocaoToAttach : relProdutoPromocaoListNew) {
                relProdutoPromocaoListNewRelProdutoPromocaoToAttach = em.getReference(relProdutoPromocaoListNewRelProdutoPromocaoToAttach.getClass(), relProdutoPromocaoListNewRelProdutoPromocaoToAttach.getIdrelProdutoPromocao());
                attachedRelProdutoPromocaoListNew.add(relProdutoPromocaoListNewRelProdutoPromocaoToAttach);
            }
            relProdutoPromocaoListNew = attachedRelProdutoPromocaoListNew;
            promocao.setRelProdutoPromocaoList(relProdutoPromocaoListNew);
            promocao = em.merge(promocao);
            for (RelProdutoPromocao relProdutoPromocaoListNewRelProdutoPromocao : relProdutoPromocaoListNew) {
                if (!relProdutoPromocaoListOld.contains(relProdutoPromocaoListNewRelProdutoPromocao)) {
                    Promocao oldPromocaoOfRelProdutoPromocaoListNewRelProdutoPromocao = relProdutoPromocaoListNewRelProdutoPromocao.getPromocao();
                    relProdutoPromocaoListNewRelProdutoPromocao.setPromocao(promocao);
                    relProdutoPromocaoListNewRelProdutoPromocao = em.merge(relProdutoPromocaoListNewRelProdutoPromocao);
                    if (oldPromocaoOfRelProdutoPromocaoListNewRelProdutoPromocao != null && !oldPromocaoOfRelProdutoPromocaoListNewRelProdutoPromocao.equals(promocao)) {
                        oldPromocaoOfRelProdutoPromocaoListNewRelProdutoPromocao.getRelProdutoPromocaoList().remove(relProdutoPromocaoListNewRelProdutoPromocao);
                        oldPromocaoOfRelProdutoPromocaoListNewRelProdutoPromocao = em.merge(oldPromocaoOfRelProdutoPromocaoListNewRelProdutoPromocao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promocao.getIdpromocao();
                if (findPromocao(id) == null) {
                    throw new NonexistentEntityException("The promocao with id " + id + " no longer exists.");
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
            Promocao promocao;
            try {
                promocao = em.getReference(Promocao.class, id);
                promocao.getIdpromocao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RelProdutoPromocao> relProdutoPromocaoListOrphanCheck = promocao.getRelProdutoPromocaoList();
            for (RelProdutoPromocao relProdutoPromocaoListOrphanCheckRelProdutoPromocao : relProdutoPromocaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Promocao (" + promocao + ") cannot be destroyed since the RelProdutoPromocao " + relProdutoPromocaoListOrphanCheckRelProdutoPromocao + " in its relProdutoPromocaoList field has a non-nullable promocao field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(promocao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promocao> findPromocaoEntities() {
        return findPromocaoEntities(true, -1, -1);
    }

    public List<Promocao> findPromocaoEntities(int maxResults, int firstResult) {
        return findPromocaoEntities(false, maxResults, firstResult);
    }

    private List<Promocao> findPromocaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promocao.class));
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

    public Promocao findPromocao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promocao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promocao> rt = cq.from(Promocao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
