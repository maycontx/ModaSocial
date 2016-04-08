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
import model.RelProdutoCarrinho;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Carrinho;
import model.Produto;
import model.Venda;

/**
 *
 * @author asdfrofl
 */
public class CarrinhoJpaController implements Serializable {

    public CarrinhoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public int create(Carrinho carrinho) {
        if (carrinho.getRelProdutoCarrinhoList() == null) {
            carrinho.setRelProdutoCarrinhoList(new ArrayList<RelProdutoCarrinho>());
        }
        if (carrinho.getVendaList() == null) {
            carrinho.setVendaList(new ArrayList<Venda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = carrinho.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                carrinho.setUsuario(usuario);
            }
            List<RelProdutoCarrinho> attachedRelProdutoCarrinhoList = new ArrayList<RelProdutoCarrinho>();
            for (RelProdutoCarrinho relProdutoCarrinhoListRelProdutoCarrinhoToAttach : carrinho.getRelProdutoCarrinhoList()) {
                relProdutoCarrinhoListRelProdutoCarrinhoToAttach = em.getReference(relProdutoCarrinhoListRelProdutoCarrinhoToAttach.getClass(), relProdutoCarrinhoListRelProdutoCarrinhoToAttach.getIdrelProdutoCarrinho());
                attachedRelProdutoCarrinhoList.add(relProdutoCarrinhoListRelProdutoCarrinhoToAttach);
            }
            carrinho.setRelProdutoCarrinhoList(attachedRelProdutoCarrinhoList);
            List<Venda> attachedVendaList = new ArrayList<Venda>();
            for (Venda vendaListVendaToAttach : carrinho.getVendaList()) {
                vendaListVendaToAttach = em.getReference(vendaListVendaToAttach.getClass(), vendaListVendaToAttach.getIdvenda());
                attachedVendaList.add(vendaListVendaToAttach);
            }
            carrinho.setVendaList(attachedVendaList);
            em.persist(carrinho);
            if (usuario != null) {
                usuario.getCarrinhoList().add(carrinho);
                usuario = em.merge(usuario);
            }
            for (RelProdutoCarrinho relProdutoCarrinhoListRelProdutoCarrinho : carrinho.getRelProdutoCarrinhoList()) {
                Carrinho oldCarrinhoOfRelProdutoCarrinhoListRelProdutoCarrinho = relProdutoCarrinhoListRelProdutoCarrinho.getCarrinho();
                relProdutoCarrinhoListRelProdutoCarrinho.setCarrinho(carrinho);
                relProdutoCarrinhoListRelProdutoCarrinho = em.merge(relProdutoCarrinhoListRelProdutoCarrinho);
                if (oldCarrinhoOfRelProdutoCarrinhoListRelProdutoCarrinho != null) {
                    oldCarrinhoOfRelProdutoCarrinhoListRelProdutoCarrinho.getRelProdutoCarrinhoList().remove(relProdutoCarrinhoListRelProdutoCarrinho);
                    oldCarrinhoOfRelProdutoCarrinhoListRelProdutoCarrinho = em.merge(oldCarrinhoOfRelProdutoCarrinhoListRelProdutoCarrinho);
                }
            }
            for (Venda vendaListVenda : carrinho.getVendaList()) {
                Carrinho oldCarrinhoOfVendaListVenda = vendaListVenda.getCarrinho();
                vendaListVenda.setCarrinho(carrinho);
                vendaListVenda = em.merge(vendaListVenda);
                if (oldCarrinhoOfVendaListVenda != null) {
                    oldCarrinhoOfVendaListVenda.getVendaList().remove(vendaListVenda);
                    oldCarrinhoOfVendaListVenda = em.merge(oldCarrinhoOfVendaListVenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return carrinho.getIdcarrinho();
    }

    public void edit(Carrinho carrinho) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrinho persistentCarrinho = em.find(Carrinho.class, carrinho.getIdcarrinho());
            Usuario usuarioOld = persistentCarrinho.getUsuario();
            Usuario usuarioNew = carrinho.getUsuario();
            List<RelProdutoCarrinho> relProdutoCarrinhoListOld = persistentCarrinho.getRelProdutoCarrinhoList();
            List<RelProdutoCarrinho> relProdutoCarrinhoListNew = carrinho.getRelProdutoCarrinhoList();
            List<Venda> vendaListOld = persistentCarrinho.getVendaList();
            List<Venda> vendaListNew = carrinho.getVendaList();
            List<String> illegalOrphanMessages = null;
            for (RelProdutoCarrinho relProdutoCarrinhoListOldRelProdutoCarrinho : relProdutoCarrinhoListOld) {
                if (!relProdutoCarrinhoListNew.contains(relProdutoCarrinhoListOldRelProdutoCarrinho)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RelProdutoCarrinho " + relProdutoCarrinhoListOldRelProdutoCarrinho + " since its carrinho field is not nullable.");
                }
            }
            for (Venda vendaListOldVenda : vendaListOld) {
                if (!vendaListNew.contains(vendaListOldVenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venda " + vendaListOldVenda + " since its carrinho field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                carrinho.setUsuario(usuarioNew);
            }
            List<RelProdutoCarrinho> attachedRelProdutoCarrinhoListNew = new ArrayList<RelProdutoCarrinho>();
            for (RelProdutoCarrinho relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach : relProdutoCarrinhoListNew) {
                relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach = em.getReference(relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach.getClass(), relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach.getIdrelProdutoCarrinho());
                attachedRelProdutoCarrinhoListNew.add(relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach);
            }
            relProdutoCarrinhoListNew = attachedRelProdutoCarrinhoListNew;
            carrinho.setRelProdutoCarrinhoList(relProdutoCarrinhoListNew);
            List<Venda> attachedVendaListNew = new ArrayList<Venda>();
            for (Venda vendaListNewVendaToAttach : vendaListNew) {
                vendaListNewVendaToAttach = em.getReference(vendaListNewVendaToAttach.getClass(), vendaListNewVendaToAttach.getIdvenda());
                attachedVendaListNew.add(vendaListNewVendaToAttach);
            }
            vendaListNew = attachedVendaListNew;
            carrinho.setVendaList(vendaListNew);
            carrinho = em.merge(carrinho);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCarrinhoList().remove(carrinho);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCarrinhoList().add(carrinho);
                usuarioNew = em.merge(usuarioNew);
            }
            for (RelProdutoCarrinho relProdutoCarrinhoListNewRelProdutoCarrinho : relProdutoCarrinhoListNew) {
                if (!relProdutoCarrinhoListOld.contains(relProdutoCarrinhoListNewRelProdutoCarrinho)) {
                    Carrinho oldCarrinhoOfRelProdutoCarrinhoListNewRelProdutoCarrinho = relProdutoCarrinhoListNewRelProdutoCarrinho.getCarrinho();
                    relProdutoCarrinhoListNewRelProdutoCarrinho.setCarrinho(carrinho);
                    relProdutoCarrinhoListNewRelProdutoCarrinho = em.merge(relProdutoCarrinhoListNewRelProdutoCarrinho);
                    if (oldCarrinhoOfRelProdutoCarrinhoListNewRelProdutoCarrinho != null && !oldCarrinhoOfRelProdutoCarrinhoListNewRelProdutoCarrinho.equals(carrinho)) {
                        oldCarrinhoOfRelProdutoCarrinhoListNewRelProdutoCarrinho.getRelProdutoCarrinhoList().remove(relProdutoCarrinhoListNewRelProdutoCarrinho);
                        oldCarrinhoOfRelProdutoCarrinhoListNewRelProdutoCarrinho = em.merge(oldCarrinhoOfRelProdutoCarrinhoListNewRelProdutoCarrinho);
                    }
                }
            }
            for (Venda vendaListNewVenda : vendaListNew) {
                if (!vendaListOld.contains(vendaListNewVenda)) {
                    Carrinho oldCarrinhoOfVendaListNewVenda = vendaListNewVenda.getCarrinho();
                    vendaListNewVenda.setCarrinho(carrinho);
                    vendaListNewVenda = em.merge(vendaListNewVenda);
                    if (oldCarrinhoOfVendaListNewVenda != null && !oldCarrinhoOfVendaListNewVenda.equals(carrinho)) {
                        oldCarrinhoOfVendaListNewVenda.getVendaList().remove(vendaListNewVenda);
                        oldCarrinhoOfVendaListNewVenda = em.merge(oldCarrinhoOfVendaListNewVenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carrinho.getIdcarrinho();
                if (findCarrinho(id) == null) {
                    throw new NonexistentEntityException("The carrinho with id " + id + " no longer exists.");
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
            Carrinho carrinho;
            try {
                carrinho = em.getReference(Carrinho.class, id);
                carrinho.getIdcarrinho();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrinho with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RelProdutoCarrinho> relProdutoCarrinhoListOrphanCheck = carrinho.getRelProdutoCarrinhoList();
            for (RelProdutoCarrinho relProdutoCarrinhoListOrphanCheckRelProdutoCarrinho : relProdutoCarrinhoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrinho (" + carrinho + ") cannot be destroyed since the RelProdutoCarrinho " + relProdutoCarrinhoListOrphanCheckRelProdutoCarrinho + " in its relProdutoCarrinhoList field has a non-nullable carrinho field.");
            }
            List<Venda> vendaListOrphanCheck = carrinho.getVendaList();
            for (Venda vendaListOrphanCheckVenda : vendaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Carrinho (" + carrinho + ") cannot be destroyed since the Venda " + vendaListOrphanCheckVenda + " in its vendaList field has a non-nullable carrinho field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = carrinho.getUsuario();
            if (usuario != null) {
                usuario.getCarrinhoList().remove(carrinho);
                usuario = em.merge(usuario);
            }
            em.remove(carrinho);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrinho> findCarrinhoEntities() {
        return findCarrinhoEntities(true, -1, -1);
    }

    public List<Carrinho> findCarrinhoEntities(int maxResults, int firstResult) {
        return findCarrinhoEntities(false, maxResults, firstResult);
    }

    private List<Carrinho> findCarrinhoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrinho.class));
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

    public Carrinho findCarrinho(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrinho.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarrinhoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrinho> rt = cq.from(Carrinho.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Carrinho findActiveCartByUser(Usuario user){
        String query = "SELECT c FROM Carrinho c WHERE c.usuario = :user AND c.status = 'ativo'";

        Query q = getEntityManager().createQuery(query);
        q.setParameter("user", user);
        
        try{
            return (Carrinho) q.getSingleResult();
        }catch(Exception ex){
            return null;
        }
        
    }
    
}
