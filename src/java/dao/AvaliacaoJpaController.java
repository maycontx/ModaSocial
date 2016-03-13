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
import model.Avaliacao;
import model.Produto;
import model.Usuario;

/**
 *
 * @author asdfrofl
 */
public class AvaliacaoJpaController implements Serializable {

    public AvaliacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avaliacao avaliacao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto produto = avaliacao.getProduto();
            if (produto != null) {
                produto = em.getReference(produto.getClass(), produto.getIdproduto());
                avaliacao.setProduto(produto);
            }
            Usuario usuario = avaliacao.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                avaliacao.setUsuario(usuario);
            }
            em.persist(avaliacao);
            if (produto != null) {
                produto.getAvaliacaoList().add(avaliacao);
                produto = em.merge(produto);
            }
            if (usuario != null) {
                usuario.getAvaliacaoList().add(avaliacao);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avaliacao avaliacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacao persistentAvaliacao = em.find(Avaliacao.class, avaliacao.getIdavaliacao());
            Produto produtoOld = persistentAvaliacao.getProduto();
            Produto produtoNew = avaliacao.getProduto();
            Usuario usuarioOld = persistentAvaliacao.getUsuario();
            Usuario usuarioNew = avaliacao.getUsuario();
            if (produtoNew != null) {
                produtoNew = em.getReference(produtoNew.getClass(), produtoNew.getIdproduto());
                avaliacao.setProduto(produtoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                avaliacao.setUsuario(usuarioNew);
            }
            avaliacao = em.merge(avaliacao);
            if (produtoOld != null && !produtoOld.equals(produtoNew)) {
                produtoOld.getAvaliacaoList().remove(avaliacao);
                produtoOld = em.merge(produtoOld);
            }
            if (produtoNew != null && !produtoNew.equals(produtoOld)) {
                produtoNew.getAvaliacaoList().add(avaliacao);
                produtoNew = em.merge(produtoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getAvaliacaoList().remove(avaliacao);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getAvaliacaoList().add(avaliacao);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avaliacao.getIdavaliacao();
                if (findAvaliacao(id) == null) {
                    throw new NonexistentEntityException("The avaliacao with id " + id + " no longer exists.");
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
            Avaliacao avaliacao;
            try {
                avaliacao = em.getReference(Avaliacao.class, id);
                avaliacao.getIdavaliacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avaliacao with id " + id + " no longer exists.", enfe);
            }
            Produto produto = avaliacao.getProduto();
            if (produto != null) {
                produto.getAvaliacaoList().remove(avaliacao);
                produto = em.merge(produto);
            }
            Usuario usuario = avaliacao.getUsuario();
            if (usuario != null) {
                usuario.getAvaliacaoList().remove(avaliacao);
                usuario = em.merge(usuario);
            }
            em.remove(avaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avaliacao> findAvaliacaoEntities() {
        return findAvaliacaoEntities(true, -1, -1);
    }

    public List<Avaliacao> findAvaliacaoEntities(int maxResults, int firstResult) {
        return findAvaliacaoEntities(false, maxResults, firstResult);
    }

    private List<Avaliacao> findAvaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avaliacao.class));
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

    public Avaliacao findAvaliacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avaliacao> rt = cq.from(Avaliacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
