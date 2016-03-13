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
import model.Ticket;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Endereço;
import model.Avaliacao;
import model.Carrinho;
import model.Pagamento;
import model.Usuario;

/**
 *
 * @author asdfrofl
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getTicketList() == null) {
            usuario.setTicketList(new ArrayList<Ticket>());
        }
        if (usuario.getEndereçoList() == null) {
            usuario.setEndereçoList(new ArrayList<Endereço>());
        }
        if (usuario.getAvaliacaoList() == null) {
            usuario.setAvaliacaoList(new ArrayList<Avaliacao>());
        }
        if (usuario.getCarrinhoList() == null) {
            usuario.setCarrinhoList(new ArrayList<Carrinho>());
        }
        if (usuario.getPagamentoList() == null) {
            usuario.setPagamentoList(new ArrayList<Pagamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ticket> attachedTicketList = new ArrayList<Ticket>();
            for (Ticket ticketListTicketToAttach : usuario.getTicketList()) {
                ticketListTicketToAttach = em.getReference(ticketListTicketToAttach.getClass(), ticketListTicketToAttach.getIdticket());
                attachedTicketList.add(ticketListTicketToAttach);
            }
            usuario.setTicketList(attachedTicketList);
            List<Endereço> attachedEndereçoList = new ArrayList<Endereço>();
            for (Endereço endereçoListEndereçoToAttach : usuario.getEndereçoList()) {
                endereçoListEndereçoToAttach = em.getReference(endereçoListEndereçoToAttach.getClass(), endereçoListEndereçoToAttach.getIdendereço());
                attachedEndereçoList.add(endereçoListEndereçoToAttach);
            }
            usuario.setEndereçoList(attachedEndereçoList);
            List<Avaliacao> attachedAvaliacaoList = new ArrayList<Avaliacao>();
            for (Avaliacao avaliacaoListAvaliacaoToAttach : usuario.getAvaliacaoList()) {
                avaliacaoListAvaliacaoToAttach = em.getReference(avaliacaoListAvaliacaoToAttach.getClass(), avaliacaoListAvaliacaoToAttach.getIdavaliacao());
                attachedAvaliacaoList.add(avaliacaoListAvaliacaoToAttach);
            }
            usuario.setAvaliacaoList(attachedAvaliacaoList);
            List<Carrinho> attachedCarrinhoList = new ArrayList<Carrinho>();
            for (Carrinho carrinhoListCarrinhoToAttach : usuario.getCarrinhoList()) {
                carrinhoListCarrinhoToAttach = em.getReference(carrinhoListCarrinhoToAttach.getClass(), carrinhoListCarrinhoToAttach.getIdcarrinho());
                attachedCarrinhoList.add(carrinhoListCarrinhoToAttach);
            }
            usuario.setCarrinhoList(attachedCarrinhoList);
            List<Pagamento> attachedPagamentoList = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListPagamentoToAttach : usuario.getPagamentoList()) {
                pagamentoListPagamentoToAttach = em.getReference(pagamentoListPagamentoToAttach.getClass(), pagamentoListPagamentoToAttach.getIdpagamento());
                attachedPagamentoList.add(pagamentoListPagamentoToAttach);
            }
            usuario.setPagamentoList(attachedPagamentoList);
            em.persist(usuario);
            for (Ticket ticketListTicket : usuario.getTicketList()) {
                Usuario oldUsuarioOfTicketListTicket = ticketListTicket.getUsuario();
                ticketListTicket.setUsuario(usuario);
                ticketListTicket = em.merge(ticketListTicket);
                if (oldUsuarioOfTicketListTicket != null) {
                    oldUsuarioOfTicketListTicket.getTicketList().remove(ticketListTicket);
                    oldUsuarioOfTicketListTicket = em.merge(oldUsuarioOfTicketListTicket);
                }
            }
            for (Endereço endereçoListEndereço : usuario.getEndereçoList()) {
                Usuario oldUsuarioOfEndereçoListEndereço = endereçoListEndereço.getUsuario();
                endereçoListEndereço.setUsuario(usuario);
                endereçoListEndereço = em.merge(endereçoListEndereço);
                if (oldUsuarioOfEndereçoListEndereço != null) {
                    oldUsuarioOfEndereçoListEndereço.getEndereçoList().remove(endereçoListEndereço);
                    oldUsuarioOfEndereçoListEndereço = em.merge(oldUsuarioOfEndereçoListEndereço);
                }
            }
            for (Avaliacao avaliacaoListAvaliacao : usuario.getAvaliacaoList()) {
                Usuario oldUsuarioOfAvaliacaoListAvaliacao = avaliacaoListAvaliacao.getUsuario();
                avaliacaoListAvaliacao.setUsuario(usuario);
                avaliacaoListAvaliacao = em.merge(avaliacaoListAvaliacao);
                if (oldUsuarioOfAvaliacaoListAvaliacao != null) {
                    oldUsuarioOfAvaliacaoListAvaliacao.getAvaliacaoList().remove(avaliacaoListAvaliacao);
                    oldUsuarioOfAvaliacaoListAvaliacao = em.merge(oldUsuarioOfAvaliacaoListAvaliacao);
                }
            }
            for (Carrinho carrinhoListCarrinho : usuario.getCarrinhoList()) {
                Usuario oldUsuarioOfCarrinhoListCarrinho = carrinhoListCarrinho.getUsuario();
                carrinhoListCarrinho.setUsuario(usuario);
                carrinhoListCarrinho = em.merge(carrinhoListCarrinho);
                if (oldUsuarioOfCarrinhoListCarrinho != null) {
                    oldUsuarioOfCarrinhoListCarrinho.getCarrinhoList().remove(carrinhoListCarrinho);
                    oldUsuarioOfCarrinhoListCarrinho = em.merge(oldUsuarioOfCarrinhoListCarrinho);
                }
            }
            for (Pagamento pagamentoListPagamento : usuario.getPagamentoList()) {
                Usuario oldUsuarioOfPagamentoListPagamento = pagamentoListPagamento.getUsuario();
                pagamentoListPagamento.setUsuario(usuario);
                pagamentoListPagamento = em.merge(pagamentoListPagamento);
                if (oldUsuarioOfPagamentoListPagamento != null) {
                    oldUsuarioOfPagamentoListPagamento.getPagamentoList().remove(pagamentoListPagamento);
                    oldUsuarioOfPagamentoListPagamento = em.merge(oldUsuarioOfPagamentoListPagamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
            List<Ticket> ticketListOld = persistentUsuario.getTicketList();
            List<Ticket> ticketListNew = usuario.getTicketList();
            List<Endereço> endereçoListOld = persistentUsuario.getEndereçoList();
            List<Endereço> endereçoListNew = usuario.getEndereçoList();
            List<Avaliacao> avaliacaoListOld = persistentUsuario.getAvaliacaoList();
            List<Avaliacao> avaliacaoListNew = usuario.getAvaliacaoList();
            List<Carrinho> carrinhoListOld = persistentUsuario.getCarrinhoList();
            List<Carrinho> carrinhoListNew = usuario.getCarrinhoList();
            List<Pagamento> pagamentoListOld = persistentUsuario.getPagamentoList();
            List<Pagamento> pagamentoListNew = usuario.getPagamentoList();
            List<String> illegalOrphanMessages = null;
            for (Ticket ticketListOldTicket : ticketListOld) {
                if (!ticketListNew.contains(ticketListOldTicket)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ticket " + ticketListOldTicket + " since its usuario field is not nullable.");
                }
            }
            for (Endereço endereçoListOldEndereço : endereçoListOld) {
                if (!endereçoListNew.contains(endereçoListOldEndereço)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Endere\u00e7o " + endereçoListOldEndereço + " since its usuario field is not nullable.");
                }
            }
            for (Avaliacao avaliacaoListOldAvaliacao : avaliacaoListOld) {
                if (!avaliacaoListNew.contains(avaliacaoListOldAvaliacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Avaliacao " + avaliacaoListOldAvaliacao + " since its usuario field is not nullable.");
                }
            }
            for (Carrinho carrinhoListOldCarrinho : carrinhoListOld) {
                if (!carrinhoListNew.contains(carrinhoListOldCarrinho)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carrinho " + carrinhoListOldCarrinho + " since its usuario field is not nullable.");
                }
            }
            for (Pagamento pagamentoListOldPagamento : pagamentoListOld) {
                if (!pagamentoListNew.contains(pagamentoListOldPagamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagamento " + pagamentoListOldPagamento + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ticket> attachedTicketListNew = new ArrayList<Ticket>();
            for (Ticket ticketListNewTicketToAttach : ticketListNew) {
                ticketListNewTicketToAttach = em.getReference(ticketListNewTicketToAttach.getClass(), ticketListNewTicketToAttach.getIdticket());
                attachedTicketListNew.add(ticketListNewTicketToAttach);
            }
            ticketListNew = attachedTicketListNew;
            usuario.setTicketList(ticketListNew);
            List<Endereço> attachedEndereçoListNew = new ArrayList<Endereço>();
            for (Endereço endereçoListNewEndereçoToAttach : endereçoListNew) {
                endereçoListNewEndereçoToAttach = em.getReference(endereçoListNewEndereçoToAttach.getClass(), endereçoListNewEndereçoToAttach.getIdendereço());
                attachedEndereçoListNew.add(endereçoListNewEndereçoToAttach);
            }
            endereçoListNew = attachedEndereçoListNew;
            usuario.setEndereçoList(endereçoListNew);
            List<Avaliacao> attachedAvaliacaoListNew = new ArrayList<Avaliacao>();
            for (Avaliacao avaliacaoListNewAvaliacaoToAttach : avaliacaoListNew) {
                avaliacaoListNewAvaliacaoToAttach = em.getReference(avaliacaoListNewAvaliacaoToAttach.getClass(), avaliacaoListNewAvaliacaoToAttach.getIdavaliacao());
                attachedAvaliacaoListNew.add(avaliacaoListNewAvaliacaoToAttach);
            }
            avaliacaoListNew = attachedAvaliacaoListNew;
            usuario.setAvaliacaoList(avaliacaoListNew);
            List<Carrinho> attachedCarrinhoListNew = new ArrayList<Carrinho>();
            for (Carrinho carrinhoListNewCarrinhoToAttach : carrinhoListNew) {
                carrinhoListNewCarrinhoToAttach = em.getReference(carrinhoListNewCarrinhoToAttach.getClass(), carrinhoListNewCarrinhoToAttach.getIdcarrinho());
                attachedCarrinhoListNew.add(carrinhoListNewCarrinhoToAttach);
            }
            carrinhoListNew = attachedCarrinhoListNew;
            usuario.setCarrinhoList(carrinhoListNew);
            List<Pagamento> attachedPagamentoListNew = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListNewPagamentoToAttach : pagamentoListNew) {
                pagamentoListNewPagamentoToAttach = em.getReference(pagamentoListNewPagamentoToAttach.getClass(), pagamentoListNewPagamentoToAttach.getIdpagamento());
                attachedPagamentoListNew.add(pagamentoListNewPagamentoToAttach);
            }
            pagamentoListNew = attachedPagamentoListNew;
            usuario.setPagamentoList(pagamentoListNew);
            usuario = em.merge(usuario);
            for (Ticket ticketListNewTicket : ticketListNew) {
                if (!ticketListOld.contains(ticketListNewTicket)) {
                    Usuario oldUsuarioOfTicketListNewTicket = ticketListNewTicket.getUsuario();
                    ticketListNewTicket.setUsuario(usuario);
                    ticketListNewTicket = em.merge(ticketListNewTicket);
                    if (oldUsuarioOfTicketListNewTicket != null && !oldUsuarioOfTicketListNewTicket.equals(usuario)) {
                        oldUsuarioOfTicketListNewTicket.getTicketList().remove(ticketListNewTicket);
                        oldUsuarioOfTicketListNewTicket = em.merge(oldUsuarioOfTicketListNewTicket);
                    }
                }
            }
            for (Endereço endereçoListNewEndereço : endereçoListNew) {
                if (!endereçoListOld.contains(endereçoListNewEndereço)) {
                    Usuario oldUsuarioOfEndereçoListNewEndereço = endereçoListNewEndereço.getUsuario();
                    endereçoListNewEndereço.setUsuario(usuario);
                    endereçoListNewEndereço = em.merge(endereçoListNewEndereço);
                    if (oldUsuarioOfEndereçoListNewEndereço != null && !oldUsuarioOfEndereçoListNewEndereço.equals(usuario)) {
                        oldUsuarioOfEndereçoListNewEndereço.getEndereçoList().remove(endereçoListNewEndereço);
                        oldUsuarioOfEndereçoListNewEndereço = em.merge(oldUsuarioOfEndereçoListNewEndereço);
                    }
                }
            }
            for (Avaliacao avaliacaoListNewAvaliacao : avaliacaoListNew) {
                if (!avaliacaoListOld.contains(avaliacaoListNewAvaliacao)) {
                    Usuario oldUsuarioOfAvaliacaoListNewAvaliacao = avaliacaoListNewAvaliacao.getUsuario();
                    avaliacaoListNewAvaliacao.setUsuario(usuario);
                    avaliacaoListNewAvaliacao = em.merge(avaliacaoListNewAvaliacao);
                    if (oldUsuarioOfAvaliacaoListNewAvaliacao != null && !oldUsuarioOfAvaliacaoListNewAvaliacao.equals(usuario)) {
                        oldUsuarioOfAvaliacaoListNewAvaliacao.getAvaliacaoList().remove(avaliacaoListNewAvaliacao);
                        oldUsuarioOfAvaliacaoListNewAvaliacao = em.merge(oldUsuarioOfAvaliacaoListNewAvaliacao);
                    }
                }
            }
            for (Carrinho carrinhoListNewCarrinho : carrinhoListNew) {
                if (!carrinhoListOld.contains(carrinhoListNewCarrinho)) {
                    Usuario oldUsuarioOfCarrinhoListNewCarrinho = carrinhoListNewCarrinho.getUsuario();
                    carrinhoListNewCarrinho.setUsuario(usuario);
                    carrinhoListNewCarrinho = em.merge(carrinhoListNewCarrinho);
                    if (oldUsuarioOfCarrinhoListNewCarrinho != null && !oldUsuarioOfCarrinhoListNewCarrinho.equals(usuario)) {
                        oldUsuarioOfCarrinhoListNewCarrinho.getCarrinhoList().remove(carrinhoListNewCarrinho);
                        oldUsuarioOfCarrinhoListNewCarrinho = em.merge(oldUsuarioOfCarrinhoListNewCarrinho);
                    }
                }
            }
            for (Pagamento pagamentoListNewPagamento : pagamentoListNew) {
                if (!pagamentoListOld.contains(pagamentoListNewPagamento)) {
                    Usuario oldUsuarioOfPagamentoListNewPagamento = pagamentoListNewPagamento.getUsuario();
                    pagamentoListNewPagamento.setUsuario(usuario);
                    pagamentoListNewPagamento = em.merge(pagamentoListNewPagamento);
                    if (oldUsuarioOfPagamentoListNewPagamento != null && !oldUsuarioOfPagamentoListNewPagamento.equals(usuario)) {
                        oldUsuarioOfPagamentoListNewPagamento.getPagamentoList().remove(pagamentoListNewPagamento);
                        oldUsuarioOfPagamentoListNewPagamento = em.merge(oldUsuarioOfPagamentoListNewPagamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdusuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ticket> ticketListOrphanCheck = usuario.getTicketList();
            for (Ticket ticketListOrphanCheckTicket : ticketListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Ticket " + ticketListOrphanCheckTicket + " in its ticketList field has a non-nullable usuario field.");
            }
            List<Endereço> endereçoListOrphanCheck = usuario.getEndereçoList();
            for (Endereço endereçoListOrphanCheckEndereço : endereçoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Endere\u00e7o " + endereçoListOrphanCheckEndereço + " in its endere\u00e7oList field has a non-nullable usuario field.");
            }
            List<Avaliacao> avaliacaoListOrphanCheck = usuario.getAvaliacaoList();
            for (Avaliacao avaliacaoListOrphanCheckAvaliacao : avaliacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Avaliacao " + avaliacaoListOrphanCheckAvaliacao + " in its avaliacaoList field has a non-nullable usuario field.");
            }
            List<Carrinho> carrinhoListOrphanCheck = usuario.getCarrinhoList();
            for (Carrinho carrinhoListOrphanCheckCarrinho : carrinhoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Carrinho " + carrinhoListOrphanCheckCarrinho + " in its carrinhoList field has a non-nullable usuario field.");
            }
            List<Pagamento> pagamentoListOrphanCheck = usuario.getPagamentoList();
            for (Pagamento pagamentoListOrphanCheckPagamento : pagamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Pagamento " + pagamentoListOrphanCheckPagamento + " in its pagamentoList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
