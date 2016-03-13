/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Categoria;
import model.ImagemCategoria;

/**
 *
 * @author asdfrofl
 */
public class ImagemCategoriaJpaController implements Serializable {

    public ImagemCategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImagemCategoria imagemCategoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria = imagemCategoria.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getIdcategoria());
                imagemCategoria.setCategoria(categoria);
            }
            em.persist(imagemCategoria);
            if (categoria != null) {
                categoria.getImagemCategoriaList().add(imagemCategoria);
                categoria = em.merge(categoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImagemCategoria(imagemCategoria.getIdimagemCategoria()) != null) {
                throw new PreexistingEntityException("ImagemCategoria " + imagemCategoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagemCategoria imagemCategoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagemCategoria persistentImagemCategoria = em.find(ImagemCategoria.class, imagemCategoria.getIdimagemCategoria());
            Categoria categoriaOld = persistentImagemCategoria.getCategoria();
            Categoria categoriaNew = imagemCategoria.getCategoria();
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getIdcategoria());
                imagemCategoria.setCategoria(categoriaNew);
            }
            imagemCategoria = em.merge(imagemCategoria);
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getImagemCategoriaList().remove(imagemCategoria);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getImagemCategoriaList().add(imagemCategoria);
                categoriaNew = em.merge(categoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imagemCategoria.getIdimagemCategoria();
                if (findImagemCategoria(id) == null) {
                    throw new NonexistentEntityException("The imagemCategoria with id " + id + " no longer exists.");
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
            ImagemCategoria imagemCategoria;
            try {
                imagemCategoria = em.getReference(ImagemCategoria.class, id);
                imagemCategoria.getIdimagemCategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagemCategoria with id " + id + " no longer exists.", enfe);
            }
            Categoria categoria = imagemCategoria.getCategoria();
            if (categoria != null) {
                categoria.getImagemCategoriaList().remove(imagemCategoria);
                categoria = em.merge(categoria);
            }
            em.remove(imagemCategoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagemCategoria> findImagemCategoriaEntities() {
        return findImagemCategoriaEntities(true, -1, -1);
    }

    public List<ImagemCategoria> findImagemCategoriaEntities(int maxResults, int firstResult) {
        return findImagemCategoriaEntities(false, maxResults, firstResult);
    }

    private List<ImagemCategoria> findImagemCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagemCategoria.class));
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

    public ImagemCategoria findImagemCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagemCategoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagemCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagemCategoria> rt = cq.from(ImagemCategoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
