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
@Table(name = "rel_produto_promocao", catalog = "modasocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelProdutoPromocao.findAll", query = "SELECT r FROM RelProdutoPromocao r"),
    @NamedQuery(name = "RelProdutoPromocao.findByIdrelProdutoPromocao", query = "SELECT r FROM RelProdutoPromocao r WHERE r.idrelProdutoPromocao = :idrelProdutoPromocao")})
public class RelProdutoPromocao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrel_produto_promocao", nullable = false)
    private Integer idrelProdutoPromocao;
    @JoinColumn(name = "produto", referencedColumnName = "idproduto", nullable = false)
    @ManyToOne(optional = false)
    private Produto produto;
    @JoinColumn(name = "promocao", referencedColumnName = "idpromocao", nullable = false)
    @ManyToOne(optional = false)
    private Promocao promocao;

    public RelProdutoPromocao() {
    }

    public RelProdutoPromocao(Integer idrelProdutoPromocao) {
        this.idrelProdutoPromocao = idrelProdutoPromocao;
    }

    public Integer getIdrelProdutoPromocao() {
        return idrelProdutoPromocao;
    }

    public void setIdrelProdutoPromocao(Integer idrelProdutoPromocao) {
        this.idrelProdutoPromocao = idrelProdutoPromocao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelProdutoPromocao != null ? idrelProdutoPromocao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelProdutoPromocao)) {
            return false;
        }
        RelProdutoPromocao other = (RelProdutoPromocao) object;
        if ((this.idrelProdutoPromocao == null && other.idrelProdutoPromocao != null) || (this.idrelProdutoPromocao != null && !this.idrelProdutoPromocao.equals(other.idrelProdutoPromocao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RelProdutoPromocao[ idrelProdutoPromocao=" + idrelProdutoPromocao + " ]";
    }
    
}
