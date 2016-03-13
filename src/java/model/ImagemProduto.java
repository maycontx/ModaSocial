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
@Table(name = "imagem_produto", catalog = "modasocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImagemProduto.findAll", query = "SELECT i FROM ImagemProduto i"),
    @NamedQuery(name = "ImagemProduto.findByIdimagemProduto", query = "SELECT i FROM ImagemProduto i WHERE i.idimagemProduto = :idimagemProduto"),
    @NamedQuery(name = "ImagemProduto.findByLink", query = "SELECT i FROM ImagemProduto i WHERE i.link = :link"),
    @NamedQuery(name = "ImagemProduto.findByOrdem", query = "SELECT i FROM ImagemProduto i WHERE i.ordem = :ordem")})
public class ImagemProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idimagem_produto", nullable = false)
    private Integer idimagemProduto;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String link;
    @Column(length = 10)
    private String ordem;
    @JoinColumn(name = "produto", referencedColumnName = "idproduto", nullable = false)
    @ManyToOne(optional = false)
    private Produto produto;

    public ImagemProduto() {
    }

    public ImagemProduto(Integer idimagemProduto) {
        this.idimagemProduto = idimagemProduto;
    }

    public ImagemProduto(Integer idimagemProduto, String link) {
        this.idimagemProduto = idimagemProduto;
        this.link = link;
    }

    public Integer getIdimagemProduto() {
        return idimagemProduto;
    }

    public void setIdimagemProduto(Integer idimagemProduto) {
        this.idimagemProduto = idimagemProduto;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idimagemProduto != null ? idimagemProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagemProduto)) {
            return false;
        }
        ImagemProduto other = (ImagemProduto) object;
        if ((this.idimagemProduto == null && other.idimagemProduto != null) || (this.idimagemProduto != null && !this.idimagemProduto.equals(other.idimagemProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ImagemProduto[ idimagemProduto=" + idimagemProduto + " ]";
    }
    
}
