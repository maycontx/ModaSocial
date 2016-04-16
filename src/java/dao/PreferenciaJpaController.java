/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Preferencia;

/**
 *
 * @author asdfrofl
 */
public class PreferenciaJpaController implements Serializable {

    public PreferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Preferencia create(Preferencia preferencia) {
        if (preferencia.getUsuarioList() == null) {
            preferencia.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : preferencia.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdusuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            preferencia.setUsuarioList(attachedUsuarioList);
            em.persist(preferencia);
            for (Usuario usuarioListUsuario : preferencia.getUsuarioList()) {
                Preferencia oldPreferenciaOfUsuarioListUsuario = usuarioListUsuario.getPreferencia();
                usuarioListUsuario.setPreferencia(preferencia);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldPreferenciaOfUsuarioListUsuario != null) {
                    oldPreferenciaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldPreferenciaOfUsuarioListUsuario = em.merge(oldPreferenciaOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return preferencia;
    }

    public void edit(Preferencia preferencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preferencia persistentPreferencia = em.find(Preferencia.class, preferencia.getIdpreferencia());
            List<Usuario> usuarioListOld = persistentPreferencia.getUsuarioList();
            List<Usuario> usuarioListNew = preferencia.getUsuarioList();
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdusuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            preferencia.setUsuarioList(usuarioListNew);
            preferencia = em.merge(preferencia);
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setPreferencia(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Preferencia oldPreferenciaOfUsuarioListNewUsuario = usuarioListNewUsuario.getPreferencia();
                    usuarioListNewUsuario.setPreferencia(preferencia);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldPreferenciaOfUsuarioListNewUsuario != null && !oldPreferenciaOfUsuarioListNewUsuario.equals(preferencia)) {
                        oldPreferenciaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldPreferenciaOfUsuarioListNewUsuario = em.merge(oldPreferenciaOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preferencia.getIdpreferencia();
                if (findPreferencia(id) == null) {
                    throw new NonexistentEntityException("The preferencia with id " + id + " no longer exists.");
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
            Preferencia preferencia;
            try {
                preferencia = em.getReference(Preferencia.class, id);
                preferencia.getIdpreferencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preferencia with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> usuarioList = preferencia.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.setPreferencia(null);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(preferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preferencia> findPreferenciaEntities() {
        return findPreferenciaEntities(true, -1, -1);
    }

    public List<Preferencia> findPreferenciaEntities(int maxResults, int firstResult) {
        return findPreferenciaEntities(false, maxResults, firstResult);
    }

    private List<Preferencia> findPreferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preferencia.class));
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

    public Preferencia findPreferencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preferencia> rt = cq.from(Preferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
