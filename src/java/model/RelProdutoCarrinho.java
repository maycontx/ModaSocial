/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asdfrofl
 */
@Entity
@Table(name = "rel_produto_carrinho", catalog = "modasocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelProdutoCarrinho.findAll", query = "SELECT r FROM RelProdutoCarrinho r"),
    @NamedQuery(name = "RelProdutoCarrinho.findByIdrelProdutoCarrinho", query = "SELECT r FROM RelProdutoCarrinho r WHERE r.idrelProdutoCarrinho = :idrelProdutoCarrinho"),
    @NamedQuery(name = "RelProdutoCarrinho.findByQuantidade", query = "SELECT r FROM RelProdutoCarrinho r WHERE r.quantidade = :quantidade")})
public class RelProdutoCarrinho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrel_produto_carrinho", nullable = false)
    private Integer idrelProdutoCarrinho;
    @Basic(optional = false)
    @Column(nullable = false)
    private int quantidade;
    @JoinColumn(name = "produto", referencedColumnName = "idproduto", nullable = false)
    @ManyToOne(optional = false)
    private Produto produto;
    @JoinColumn(name = "carrinho", referencedColumnName = "idcarrinho", nullable = false)
    @ManyToOne(optional = false)
    private Carrinho carrinho;

    public RelProdutoCarrinho() {
    }

    public RelProdutoCarrinho(Integer idrelProdutoCarrinho) {
        this.idrelProdutoCarrinho = idrelProdutoCarrinho;
    }

    public RelProdutoCarrinho(Integer idrelProdutoCarrinho, int quantidade) {
        this.idrelProdutoCarrinho = idrelProdutoCarrinho;
        this.quantidade = quantidade;
    }

    public Integer getIdrelProdutoCarrinho() {
        return idrelProdutoCarrinho;
    }

    public void setIdrelProdutoCarrinho(Integer idrelProdutoCarrinho) {
        this.idrelProdutoCarrinho = idrelProdutoCarrinho;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelProdutoCarrinho != null ? idrelProdutoCarrinho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelProdutoCarrinho)) {
            return false;
        }
        RelProdutoCarrinho other = (RelProdutoCarrinho) object;
        if ((this.idrelProdutoCarrinho == null && other.idrelProdutoCarrinho != null) || (this.idrelProdutoCarrinho != null && !this.idrelProdutoCarrinho.equals(other.idrelProdutoCarrinho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RelProdutoCarrinho[ idrelProdutoCarrinho=" + idrelProdutoCarrinho + " ]";
    }
    
}
