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
import model.ImagemCategoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Categoria;
import model.Produto;

/**
 *
 * @author asdfrofl
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getImagemCategoriaList() == null) {
            categoria.setImagemCategoriaList(new ArrayList<ImagemCategoria>());
        }
        if (categoria.getProdutoList() == null) {
            categoria.setProdutoList(new ArrayList<Produto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ImagemCategoria> attachedImagemCategoriaList = new ArrayList<ImagemCategoria>();
            for (ImagemCategoria imagemCategoriaListImagemCategoriaToAttach : categoria.getImagemCategoriaList()) {
                imagemCategoriaListImagemCategoriaToAttach = em.getReference(imagemCategoriaListImagemCategoriaToAttach.getClass(), imagemCategoriaListImagemCategoriaToAttach.getIdimagemCategoria());
                attachedImagemCategoriaList.add(imagemCategoriaListImagemCategoriaToAttach);
            }
            categoria.setImagemCategoriaList(attachedImagemCategoriaList);
            List<Produto> attachedProdutoList = new ArrayList<Produto>();
            for (Produto produtoListProdutoToAttach : categoria.getProdutoList()) {
                produtoListProdutoToAttach = em.getReference(produtoListProdutoToAttach.getClass(), produtoListProdutoToAttach.getIdproduto());
                attachedProdutoList.add(produtoListProdutoToAttach);
            }
            categoria.setProdutoList(attachedProdutoList);
            em.persist(categoria);
            for (ImagemCategoria imagemCategoriaListImagemCategoria : categoria.getImagemCategoriaList()) {
                Categoria oldCategoriaOfImagemCategoriaListImagemCategoria = imagemCategoriaListImagemCategoria.getCategoria();
                imagemCategoriaListImagemCategoria.setCategoria(categoria);
                imagemCategoriaListImagemCategoria = em.merge(imagemCategoriaListImagemCategoria);
                if (oldCategoriaOfImagemCategoriaListImagemCategoria != null) {
                    oldCategoriaOfImagemCategoriaListImagemCategoria.getImagemCategoriaList().remove(imagemCategoriaListImagemCategoria);
                    oldCategoriaOfImagemCategoriaListImagemCategoria = em.merge(oldCategoriaOfImagemCategoriaListImagemCategoria);
                }
            }
            for (Produto produtoListProduto : categoria.getProdutoList()) {
                Categoria oldCategoriaOfProdutoListProduto = produtoListProduto.getCategoria();
                produtoListProduto.setCategoria(categoria);
                produtoListProduto = em.merge(produtoListProduto);
                if (oldCategoriaOfProdutoListProduto != null) {
                    oldCategoriaOfProdutoListProduto.getProdutoList().remove(produtoListProduto);
                    oldCategoriaOfProdutoListProduto = em.merge(oldCategoriaOfProdutoListProduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdcategoria());
            List<ImagemCategoria> imagemCategoriaListOld = persistentCategoria.getImagemCategoriaList();
            List<ImagemCategoria> imagemCategoriaListNew = categoria.getImagemCategoriaList();
            List<Produto> produtoListOld = persistentCategoria.getProdutoList();
            List<Produto> produtoListNew = categoria.getProdutoList();
            List<String> illegalOrphanMessages = null;
            for (ImagemCategoria imagemCategoriaListOldImagemCategoria : imagemCategoriaListOld) {
                if (!imagemCategoriaListNew.contains(imagemCategoriaListOldImagemCategoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ImagemCategoria " + imagemCategoriaListOldImagemCategoria + " since its categoria field is not nullable.");
                }
            }
            for (Produto produtoListOldProduto : produtoListOld) {
                if (!produtoListNew.contains(produtoListOldProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Produto " + produtoListOldProduto + " since its categoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ImagemCategoria> attachedImagemCategoriaListNew = new ArrayList<ImagemCategoria>();
            for (ImagemCategoria imagemCategoriaListNewImagemCategoriaToAttach : imagemCategoriaListNew) {
                imagemCategoriaListNewImagemCategoriaToAttach = em.getReference(imagemCategoriaListNewImagemCategoriaToAttach.getClass(), imagemCategoriaListNewImagemCategoriaToAttach.getIdimagemCategoria());
                attachedImagemCategoriaListNew.add(imagemCategoriaListNewImagemCategoriaToAttach);
            }
            imagemCategoriaListNew = attachedImagemCategoriaListNew;
            categoria.setImagemCategoriaList(imagemCategoriaListNew);
            List<Produto> attachedProdutoListNew = new ArrayList<Produto>();
            for (Produto produtoListNewProdutoToAttach : produtoListNew) {
                produtoListNewProdutoToAttach = em.getReference(produtoListNewProdutoToAttach.getClass(), produtoListNewProdutoToAttach.getIdproduto());
                attachedProdutoListNew.add(produtoListNewProdutoToAttach);
            }
            produtoListNew = attachedProdutoListNew;
            categoria.setProdutoList(produtoListNew);
            categoria = em.merge(categoria);
            for (ImagemCategoria imagemCategoriaListNewImagemCategoria : imagemCategoriaListNew) {
                if (!imagemCategoriaListOld.contains(imagemCategoriaListNewImagemCategoria)) {
                    Categoria oldCategoriaOfImagemCategoriaListNewImagemCategoria = imagemCategoriaListNewImagemCategoria.getCategoria();
                    imagemCategoriaListNewImagemCategoria.setCategoria(categoria);
                    imagemCategoriaListNewImagemCategoria = em.merge(imagemCategoriaListNewImagemCategoria);
                    if (oldCategoriaOfImagemCategoriaListNewImagemCategoria != null && !oldCategoriaOfImagemCategoriaListNewImagemCategoria.equals(categoria)) {
                        oldCategoriaOfImagemCategoriaListNewImagemCategoria.getImagemCategoriaList().remove(imagemCategoriaListNewImagemCategoria);
                        oldCategoriaOfImagemCategoriaListNewImagemCategoria = em.merge(oldCategoriaOfImagemCategoriaListNewImagemCategoria);
                    }
                }
            }
            for (Produto produtoListNewProduto : produtoListNew) {
                if (!produtoListOld.contains(produtoListNewProduto)) {
                    Categoria oldCategoriaOfProdutoListNewProduto = produtoListNewProduto.getCategoria();
                    produtoListNewProduto.setCategoria(categoria);
                    produtoListNewProduto = em.merge(produtoListNewProduto);
                    if (oldCategoriaOfProdutoListNewProduto != null && !oldCategoriaOfProdutoListNewProduto.equals(categoria)) {
                        oldCategoriaOfProdutoListNewProduto.getProdutoList().remove(produtoListNewProduto);
                        oldCategoriaOfProdutoListNewProduto = em.merge(oldCategoriaOfProdutoListNewProduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getIdcategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ImagemCategoria> imagemCategoriaListOrphanCheck = categoria.getImagemCategoriaList();
            for (ImagemCategoria imagemCategoriaListOrphanCheckImagemCategoria : imagemCategoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the ImagemCategoria " + imagemCategoriaListOrphanCheckImagemCategoria + " in its imagemCategoriaList field has a non-nullable categoria field.");
            }
            List<Produto> produtoListOrphanCheck = categoria.getProdutoList();
            for (Produto produtoListOrphanCheckProduto : produtoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Produto " + produtoListOrphanCheckProduto + " in its produtoList field has a non-nullable categoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
