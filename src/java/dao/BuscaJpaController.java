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
import model.Busca;
import model.Usuario;

/**
 *
 * @author asdfrofl
 */
public class BuscaJpaController implements Serializable {

    public BuscaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Busca busca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = busca.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                busca.setUsuario(usuario);
            }
            em.persist(busca);
            if (usuario != null) {
                usuario.getBuscaList().add(busca);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Busca busca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Busca persistentBusca = em.find(Busca.class, busca.getIdbusca());
            Usuario usuarioOld = persistentBusca.getUsuario();
            Usuario usuarioNew = busca.getUsuario();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                busca.setUsuario(usuarioNew);
            }
            busca = em.merge(busca);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getBuscaList().remove(busca);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getBuscaList().add(busca);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = busca.getIdbusca();
                if (findBusca(id) == null) {
                    throw new NonexistentEntityException("The busca with id " + id + " no longer exists.");
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
            Busca busca;
            try {
                busca = em.getReference(Busca.class, id);
                busca.getIdbusca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The busca with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = busca.getUsuario();
            if (usuario != null) {
                usuario.getBuscaList().remove(busca);
                usuario = em.merge(usuario);
            }
            em.remove(busca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Busca> findBuscaEntities() {
        return findBuscaEntities(true, -1, -1);
    }

    public List<Busca> findBuscaEntities(int maxResults, int firstResult) {
        return findBuscaEntities(false, maxResults, firstResult);
    }

    private List<Busca> findBuscaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Busca.class));
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

    public Busca findBusca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Busca.class, id);
        } finally {
            em.close();
        }
    }

    public int getBuscaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Busca> rt = cq.from(Busca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
