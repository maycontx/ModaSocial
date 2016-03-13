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
import model.Categoria;
import model.Fornecedor;
import model.Caracteristica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.ImagemProduto;
import model.RelProdutoCarrinho;
import model.Avaliacao;
import model.Produto;
import model.RelProdutoPromocao;

/**
 *
 * @author asdfrofl
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        if (produto.getCaracteristicaList() == null) {
            produto.setCaracteristicaList(new ArrayList<Caracteristica>());
        }
        if (produto.getImagemProdutoList() == null) {
            produto.setImagemProdutoList(new ArrayList<ImagemProduto>());
        }
        if (produto.getRelProdutoCarrinhoList() == null) {
            produto.setRelProdutoCarrinhoList(new ArrayList<RelProdutoCarrinho>());
        }
        if (produto.getAvaliacaoList() == null) {
            produto.setAvaliacaoList(new ArrayList<Avaliacao>());
        }
        if (produto.getRelProdutoPromocaoList() == null) {
            produto.setRelProdutoPromocaoList(new ArrayList<RelProdutoPromocao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria = produto.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getIdcategoria());
                produto.setCategoria(categoria);
            }
            Fornecedor fornecedor = produto.getFornecedor();
            if (fornecedor != null) {
                fornecedor = em.getReference(fornecedor.getClass(), fornecedor.getIdfornecedor());
                produto.setFornecedor(fornecedor);
            }
            List<Caracteristica> attachedCaracteristicaList = new ArrayList<Caracteristica>();
            for (Caracteristica caracteristicaListCaracteristicaToAttach : produto.getCaracteristicaList()) {
                caracteristicaListCaracteristicaToAttach = em.getReference(caracteristicaListCaracteristicaToAttach.getClass(), caracteristicaListCaracteristicaToAttach.getIdcaracteristica());
                attachedCaracteristicaList.add(caracteristicaListCaracteristicaToAttach);
            }
            produto.setCaracteristicaList(attachedCaracteristicaList);
            List<ImagemProduto> attachedImagemProdutoList = new ArrayList<ImagemProduto>();
            for (ImagemProduto imagemProdutoListImagemProdutoToAttach : produto.getImagemProdutoList()) {
                imagemProdutoListImagemProdutoToAttach = em.getReference(imagemProdutoListImagemProdutoToAttach.getClass(), imagemProdutoListImagemProdutoToAttach.getIdimagemProduto());
                attachedImagemProdutoList.add(imagemProdutoListImagemProdutoToAttach);
            }
            produto.setImagemProdutoList(attachedImagemProdutoList);
            List<RelProdutoCarrinho> attachedRelProdutoCarrinhoList = new ArrayList<RelProdutoCarrinho>();
            for (RelProdutoCarrinho relProdutoCarrinhoListRelProdutoCarrinhoToAttach : produto.getRelProdutoCarrinhoList()) {
                relProdutoCarrinhoListRelProdutoCarrinhoToAttach = em.getReference(relProdutoCarrinhoListRelProdutoCarrinhoToAttach.getClass(), relProdutoCarrinhoListRelProdutoCarrinhoToAttach.getIdrelProdutoCarrinho());
                attachedRelProdutoCarrinhoList.add(relProdutoCarrinhoListRelProdutoCarrinhoToAttach);
            }
            produto.setRelProdutoCarrinhoList(attachedRelProdutoCarrinhoList);
            List<Avaliacao> attachedAvaliacaoList = new ArrayList<Avaliacao>();
            for (Avaliacao avaliacaoListAvaliacaoToAttach : produto.getAvaliacaoList()) {
                avaliacaoListAvaliacaoToAttach = em.getReference(avaliacaoListAvaliacaoToAttach.getClass(), avaliacaoListAvaliacaoToAttach.getIdavaliacao());
                attachedAvaliacaoList.add(avaliacaoListAvaliacaoToAttach);
            }
            produto.setAvaliacaoList(attachedAvaliacaoList);
            List<RelProdutoPromocao> attachedRelProdutoPromocaoList = new ArrayList<RelProdutoPromocao>();
            for (RelProdutoPromocao relProdutoPromocaoListRelProdutoPromocaoToAttach : produto.getRelProdutoPromocaoList()) {
                relProdutoPromocaoListRelProdutoPromocaoToAttach = em.getReference(relProdutoPromocaoListRelProdutoPromocaoToAttach.getClass(), relProdutoPromocaoListRelProdutoPromocaoToAttach.getIdrelProdutoPromocao());
                attachedRelProdutoPromocaoList.add(relProdutoPromocaoListRelProdutoPromocaoToAttach);
            }
            produto.setRelProdutoPromocaoList(attachedRelProdutoPromocaoList);
            em.persist(produto);
            if (categoria != null) {
                categoria.getProdutoList().add(produto);
                categoria = em.merge(categoria);
            }
            if (fornecedor != null) {
                fornecedor.getProdutoList().add(produto);
                fornecedor = em.merge(fornecedor);
            }
            for (Caracteristica caracteristicaListCaracteristica : produto.getCaracteristicaList()) {
                Produto oldProdutoOfCaracteristicaListCaracteristica = caracteristicaListCaracteristica.getProduto();
                caracteristicaListCaracteristica.setProduto(produto);
                caracteristicaListCaracteristica = em.merge(caracteristicaListCaracteristica);
                if (oldProdutoOfCaracteristicaListCaracteristica != null) {
                    oldProdutoOfCaracteristicaListCaracteristica.getCaracteristicaList().remove(caracteristicaListCaracteristica);
                    oldProdutoOfCaracteristicaListCaracteristica = em.merge(oldProdutoOfCaracteristicaListCaracteristica);
                }
            }
            for (ImagemProduto imagemProdutoListImagemProduto : produto.getImagemProdutoList()) {
                Produto oldProdutoOfImagemProdutoListImagemProduto = imagemProdutoListImagemProduto.getProduto();
                imagemProdutoListImagemProduto.setProduto(produto);
                imagemProdutoListImagemProduto = em.merge(imagemProdutoListImagemProduto);
                if (oldProdutoOfImagemProdutoListImagemProduto != null) {
                    oldProdutoOfImagemProdutoListImagemProduto.getImagemProdutoList().remove(imagemProdutoListImagemProduto);
                    oldProdutoOfImagemProdutoListImagemProduto = em.merge(oldProdutoOfImagemProdutoListImagemProduto);
                }
            }
            for (RelProdutoCarrinho relProdutoCarrinhoListRelProdutoCarrinho : produto.getRelProdutoCarrinhoList()) {
                Produto oldProdutoOfRelProdutoCarrinhoListRelProdutoCarrinho = relProdutoCarrinhoListRelProdutoCarrinho.getProduto();
                relProdutoCarrinhoListRelProdutoCarrinho.setProduto(produto);
                relProdutoCarrinhoListRelProdutoCarrinho = em.merge(relProdutoCarrinhoListRelProdutoCarrinho);
                if (oldProdutoOfRelProdutoCarrinhoListRelProdutoCarrinho != null) {
                    oldProdutoOfRelProdutoCarrinhoListRelProdutoCarrinho.getRelProdutoCarrinhoList().remove(relProdutoCarrinhoListRelProdutoCarrinho);
                    oldProdutoOfRelProdutoCarrinhoListRelProdutoCarrinho = em.merge(oldProdutoOfRelProdutoCarrinhoListRelProdutoCarrinho);
                }
            }
            for (Avaliacao avaliacaoListAvaliacao : produto.getAvaliacaoList()) {
                Produto oldProdutoOfAvaliacaoListAvaliacao = avaliacaoListAvaliacao.getProduto();
                avaliacaoListAvaliacao.setProduto(produto);
                avaliacaoListAvaliacao = em.merge(avaliacaoListAvaliacao);
                if (oldProdutoOfAvaliacaoListAvaliacao != null) {
                    oldProdutoOfAvaliacaoListAvaliacao.getAvaliacaoList().remove(avaliacaoListAvaliacao);
                    oldProdutoOfAvaliacaoListAvaliacao = em.merge(oldProdutoOfAvaliacaoListAvaliacao);
                }
            }
            for (RelProdutoPromocao relProdutoPromocaoListRelProdutoPromocao : produto.getRelProdutoPromocaoList()) {
                Produto oldProdutoOfRelProdutoPromocaoListRelProdutoPromocao = relProdutoPromocaoListRelProdutoPromocao.getProduto();
                relProdutoPromocaoListRelProdutoPromocao.setProduto(produto);
                relProdutoPromocaoListRelProdutoPromocao = em.merge(relProdutoPromocaoListRelProdutoPromocao);
                if (oldProdutoOfRelProdutoPromocaoListRelProdutoPromocao != null) {
                    oldProdutoOfRelProdutoPromocaoListRelProdutoPromocao.getRelProdutoPromocaoList().remove(relProdutoPromocaoListRelProdutoPromocao);
                    oldProdutoOfRelProdutoPromocaoListRelProdutoPromocao = em.merge(oldProdutoOfRelProdutoPromocaoListRelProdutoPromocao);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getIdproduto());
            Categoria categoriaOld = persistentProduto.getCategoria();
            Categoria categoriaNew = produto.getCategoria();
            Fornecedor fornecedorOld = persistentProduto.getFornecedor();
            Fornecedor fornecedorNew = produto.getFornecedor();
            List<Caracteristica> caracteristicaListOld = persistentProduto.getCaracteristicaList();
            List<Caracteristica> caracteristicaListNew = produto.getCaracteristicaList();
            List<ImagemProduto> imagemProdutoListOld = persistentProduto.getImagemProdutoList();
            List<ImagemProduto> imagemProdutoListNew = produto.getImagemProdutoList();
            List<RelProdutoCarrinho> relProdutoCarrinhoListOld = persistentProduto.getRelProdutoCarrinhoList();
            List<RelProdutoCarrinho> relProdutoCarrinhoListNew = produto.getRelProdutoCarrinhoList();
            List<Avaliacao> avaliacaoListOld = persistentProduto.getAvaliacaoList();
            List<Avaliacao> avaliacaoListNew = produto.getAvaliacaoList();
            List<RelProdutoPromocao> relProdutoPromocaoListOld = persistentProduto.getRelProdutoPromocaoList();
            List<RelProdutoPromocao> relProdutoPromocaoListNew = produto.getRelProdutoPromocaoList();
            List<String> illegalOrphanMessages = null;
            for (Caracteristica caracteristicaListOldCaracteristica : caracteristicaListOld) {
                if (!caracteristicaListNew.contains(caracteristicaListOldCaracteristica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Caracteristica " + caracteristicaListOldCaracteristica + " since its produto field is not nullable.");
                }
            }
            for (ImagemProduto imagemProdutoListOldImagemProduto : imagemProdutoListOld) {
                if (!imagemProdutoListNew.contains(imagemProdutoListOldImagemProduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ImagemProduto " + imagemProdutoListOldImagemProduto + " since its produto field is not nullable.");
                }
            }
            for (RelProdutoCarrinho relProdutoCarrinhoListOldRelProdutoCarrinho : relProdutoCarrinhoListOld) {
                if (!relProdutoCarrinhoListNew.contains(relProdutoCarrinhoListOldRelProdutoCarrinho)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RelProdutoCarrinho " + relProdutoCarrinhoListOldRelProdutoCarrinho + " since its produto field is not nullable.");
                }
            }
            for (Avaliacao avaliacaoListOldAvaliacao : avaliacaoListOld) {
                if (!avaliacaoListNew.contains(avaliacaoListOldAvaliacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Avaliacao " + avaliacaoListOldAvaliacao + " since its produto field is not nullable.");
                }
            }
            for (RelProdutoPromocao relProdutoPromocaoListOldRelProdutoPromocao : relProdutoPromocaoListOld) {
                if (!relProdutoPromocaoListNew.contains(relProdutoPromocaoListOldRelProdutoPromocao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RelProdutoPromocao " + relProdutoPromocaoListOldRelProdutoPromocao + " since its produto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getIdcategoria());
                produto.setCategoria(categoriaNew);
            }
            if (fornecedorNew != null) {
                fornecedorNew = em.getReference(fornecedorNew.getClass(), fornecedorNew.getIdfornecedor());
                produto.setFornecedor(fornecedorNew);
            }
            List<Caracteristica> attachedCaracteristicaListNew = new ArrayList<Caracteristica>();
            for (Caracteristica caracteristicaListNewCaracteristicaToAttach : caracteristicaListNew) {
                caracteristicaListNewCaracteristicaToAttach = em.getReference(caracteristicaListNewCaracteristicaToAttach.getClass(), caracteristicaListNewCaracteristicaToAttach.getIdcaracteristica());
                attachedCaracteristicaListNew.add(caracteristicaListNewCaracteristicaToAttach);
            }
            caracteristicaListNew = attachedCaracteristicaListNew;
            produto.setCaracteristicaList(caracteristicaListNew);
            List<ImagemProduto> attachedImagemProdutoListNew = new ArrayList<ImagemProduto>();
            for (ImagemProduto imagemProdutoListNewImagemProdutoToAttach : imagemProdutoListNew) {
                imagemProdutoListNewImagemProdutoToAttach = em.getReference(imagemProdutoListNewImagemProdutoToAttach.getClass(), imagemProdutoListNewImagemProdutoToAttach.getIdimagemProduto());
                attachedImagemProdutoListNew.add(imagemProdutoListNewImagemProdutoToAttach);
            }
            imagemProdutoListNew = attachedImagemProdutoListNew;
            produto.setImagemProdutoList(imagemProdutoListNew);
            List<RelProdutoCarrinho> attachedRelProdutoCarrinhoListNew = new ArrayList<RelProdutoCarrinho>();
            for (RelProdutoCarrinho relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach : relProdutoCarrinhoListNew) {
                relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach = em.getReference(relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach.getClass(), relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach.getIdrelProdutoCarrinho());
                attachedRelProdutoCarrinhoListNew.add(relProdutoCarrinhoListNewRelProdutoCarrinhoToAttach);
            }
            relProdutoCarrinhoListNew = attachedRelProdutoCarrinhoListNew;
            produto.setRelProdutoCarrinhoList(relProdutoCarrinhoListNew);
            List<Avaliacao> attachedAvaliacaoListNew = new ArrayList<Avaliacao>();
            for (Avaliacao avaliacaoListNewAvaliacaoToAttach : avaliacaoListNew) {
                avaliacaoListNewAvaliacaoToAttach = em.getReference(avaliacaoListNewAvaliacaoToAttach.getClass(), avaliacaoListNewAvaliacaoToAttach.getIdavaliacao());
                attachedAvaliacaoListNew.add(avaliacaoListNewAvaliacaoToAttach);
            }
            avaliacaoListNew = attachedAvaliacaoListNew;
            produto.setAvaliacaoList(avaliacaoListNew);
            List<RelProdutoPromocao> attachedRelProdutoPromocaoListNew = new ArrayList<RelProdutoPromocao>();
            for (RelProdutoPromocao relProdutoPromocaoListNewRelProdutoPromocaoToAttach : relProdutoPromocaoListNew) {
                relProdutoPromocaoListNewRelProdutoPromocaoToAttach = em.getReference(relProdutoPromocaoListNewRelProdutoPromocaoToAttach.getClass(), relProdutoPromocaoListNewRelProdutoPromocaoToAttach.getIdrelProdutoPromocao());
                attachedRelProdutoPromocaoListNew.add(relProdutoPromocaoListNewRelProdutoPromocaoToAttach);
            }
            relProdutoPromocaoListNew = attachedRelProdutoPromocaoListNew;
            produto.setRelProdutoPromocaoList(relProdutoPromocaoListNew);
            produto = em.merge(produto);
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getProdutoList().remove(produto);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getProdutoList().add(produto);
                categoriaNew = em.merge(categoriaNew);
            }
            if (fornecedorOld != null && !fornecedorOld.equals(fornecedorNew)) {
                fornecedorOld.getProdutoList().remove(produto);
                fornecedorOld = em.merge(fornecedorOld);
            }
            if (fornecedorNew != null && !fornecedorNew.equals(fornecedorOld)) {
                fornecedorNew.getProdutoList().add(produto);
                fornecedorNew = em.merge(fornecedorNew);
            }
            for (Caracteristica caracteristicaListNewCaracteristica : caracteristicaListNew) {
                if (!caracteristicaListOld.contains(caracteristicaListNewCaracteristica)) {
                    Produto oldProdutoOfCaracteristicaListNewCaracteristica = caracteristicaListNewCaracteristica.getProduto();
                    caracteristicaListNewCaracteristica.setProduto(produto);
                    caracteristicaListNewCaracteristica = em.merge(caracteristicaListNewCaracteristica);
                    if (oldProdutoOfCaracteristicaListNewCaracteristica != null && !oldProdutoOfCaracteristicaListNewCaracteristica.equals(produto)) {
                        oldProdutoOfCaracteristicaListNewCaracteristica.getCaracteristicaList().remove(caracteristicaListNewCaracteristica);
                        oldProdutoOfCaracteristicaListNewCaracteristica = em.merge(oldProdutoOfCaracteristicaListNewCaracteristica);
                    }
                }
            }
            for (ImagemProduto imagemProdutoListNewImagemProduto : imagemProdutoListNew) {
                if (!imagemProdutoListOld.contains(imagemProdutoListNewImagemProduto)) {
                    Produto oldProdutoOfImagemProdutoListNewImagemProduto = imagemProdutoListNewImagemProduto.getProduto();
                    imagemProdutoListNewImagemProduto.setProduto(produto);
                    imagemProdutoListNewImagemProduto = em.merge(imagemProdutoListNewImagemProduto);
                    if (oldProdutoOfImagemProdutoListNewImagemProduto != null && !oldProdutoOfImagemProdutoListNewImagemProduto.equals(produto)) {
                        oldProdutoOfImagemProdutoListNewImagemProduto.getImagemProdutoList().remove(imagemProdutoListNewImagemProduto);
                        oldProdutoOfImagemProdutoListNewImagemProduto = em.merge(oldProdutoOfImagemProdutoListNewImagemProduto);
                    }
                }
            }
            for (RelProdutoCarrinho relProdutoCarrinhoListNewRelProdutoCarrinho : relProdutoCarrinhoListNew) {
                if (!relProdutoCarrinhoListOld.contains(relProdutoCarrinhoListNewRelProdutoCarrinho)) {
                    Produto oldProdutoOfRelProdutoCarrinhoListNewRelProdutoCarrinho = relProdutoCarrinhoListNewRelProdutoCarrinho.getProduto();
                    relProdutoCarrinhoListNewRelProdutoCarrinho.setProduto(produto);
                    relProdutoCarrinhoListNewRelProdutoCarrinho = em.merge(relProdutoCarrinhoListNewRelProdutoCarrinho);
                    if (oldProdutoOfRelProdutoCarrinhoListNewRelProdutoCarrinho != null && !oldProdutoOfRelProdutoCarrinhoListNewRelProdutoCarrinho.equals(produto)) {
                        oldProdutoOfRelProdutoCarrinhoListNewRelProdutoCarrinho.getRelProdutoCarrinhoList().remove(relProdutoCarrinhoListNewRelProdutoCarrinho);
                        oldProdutoOfRelProdutoCarrinhoListNewRelProdutoCarrinho = em.merge(oldProdutoOfRelProdutoCarrinhoListNewRelProdutoCarrinho);
                    }
                }
            }
            for (Avaliacao avaliacaoListNewAvaliacao : avaliacaoListNew) {
                if (!avaliacaoListOld.contains(avaliacaoListNewAvaliacao)) {
                    Produto oldProdutoOfAvaliacaoListNewAvaliacao = avaliacaoListNewAvaliacao.getProduto();
                    avaliacaoListNewAvaliacao.setProduto(produto);
                    avaliacaoListNewAvaliacao = em.merge(avaliacaoListNewAvaliacao);
                    if (oldProdutoOfAvaliacaoListNewAvaliacao != null && !oldProdutoOfAvaliacaoListNewAvaliacao.equals(produto)) {
                        oldProdutoOfAvaliacaoListNewAvaliacao.getAvaliacaoList().remove(avaliacaoListNewAvaliacao);
                        oldProdutoOfAvaliacaoListNewAvaliacao = em.merge(oldProdutoOfAvaliacaoListNewAvaliacao);
                    }
                }
            }
            for (RelProdutoPromocao relProdutoPromocaoListNewRelProdutoPromocao : relProdutoPromocaoListNew) {
                if (!relProdutoPromocaoListOld.contains(relProdutoPromocaoListNewRelProdutoPromocao)) {
                    Produto oldProdutoOfRelProdutoPromocaoListNewRelProdutoPromocao = relProdutoPromocaoListNewRelProdutoPromocao.getProduto();
                    relProdutoPromocaoListNewRelProdutoPromocao.setProduto(produto);
                    relProdutoPromocaoListNewRelProdutoPromocao = em.merge(relProdutoPromocaoListNewRelProdutoPromocao);
                    if (oldProdutoOfRelProdutoPromocaoListNewRelProdutoPromocao != null && !oldProdutoOfRelProdutoPromocaoListNewRelProdutoPromocao.equals(produto)) {
                        oldProdutoOfRelProdutoPromocaoListNewRelProdutoPromocao.getRelProdutoPromocaoList().remove(relProdutoPromocaoListNewRelProdutoPromocao);
                        oldProdutoOfRelProdutoPromocaoListNewRelProdutoPromocao = em.merge(oldProdutoOfRelProdutoPromocaoListNewRelProdutoPromocao);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getIdproduto();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
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
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getIdproduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Caracteristica> caracteristicaListOrphanCheck = produto.getCaracteristicaList();
            for (Caracteristica caracteristicaListOrphanCheckCaracteristica : caracteristicaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the Caracteristica " + caracteristicaListOrphanCheckCaracteristica + " in its caracteristicaList field has a non-nullable produto field.");
            }
            List<ImagemProduto> imagemProdutoListOrphanCheck = produto.getImagemProdutoList();
            for (ImagemProduto imagemProdutoListOrphanCheckImagemProduto : imagemProdutoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the ImagemProduto " + imagemProdutoListOrphanCheckImagemProduto + " in its imagemProdutoList field has a non-nullable produto field.");
            }
            List<RelProdutoCarrinho> relProdutoCarrinhoListOrphanCheck = produto.getRelProdutoCarrinhoList();
            for (RelProdutoCarrinho relProdutoCarrinhoListOrphanCheckRelProdutoCarrinho : relProdutoCarrinhoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the RelProdutoCarrinho " + relProdutoCarrinhoListOrphanCheckRelProdutoCarrinho + " in its relProdutoCarrinhoList field has a non-nullable produto field.");
            }
            List<Avaliacao> avaliacaoListOrphanCheck = produto.getAvaliacaoList();
            for (Avaliacao avaliacaoListOrphanCheckAvaliacao : avaliacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the Avaliacao " + avaliacaoListOrphanCheckAvaliacao + " in its avaliacaoList field has a non-nullable produto field.");
            }
            List<RelProdutoPromocao> relProdutoPromocaoListOrphanCheck = produto.getRelProdutoPromocaoList();
            for (RelProdutoPromocao relProdutoPromocaoListOrphanCheckRelProdutoPromocao : relProdutoPromocaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the RelProdutoPromocao " + relProdutoPromocaoListOrphanCheckRelProdutoPromocao + " in its relProdutoPromocaoList field has a non-nullable produto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria categoria = produto.getCategoria();
            if (categoria != null) {
                categoria.getProdutoList().remove(produto);
                categoria = em.merge(categoria);
            }
            Fornecedor fornecedor = produto.getFornecedor();
            if (fornecedor != null) {
                fornecedor.getProdutoList().remove(produto);
                fornecedor = em.merge(fornecedor);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
