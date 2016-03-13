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
@Table(name = "imagem_categoria", catalog = "modasocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImagemCategoria.findAll", query = "SELECT i FROM ImagemCategoria i"),
    @NamedQuery(name = "ImagemCategoria.findByIdimagemCategoria", query = "SELECT i FROM ImagemCategoria i WHERE i.idimagemCategoria = :idimagemCategoria"),
    @NamedQuery(name = "ImagemCategoria.findByLink", query = "SELECT i FROM ImagemCategoria i WHERE i.link = :link")})
public class ImagemCategoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idimagem_categoria", nullable = false)
    private Integer idimagemCategoria;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String link;
    @JoinColumn(name = "categoria", referencedColumnName = "idcategoria", nullable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;

    public ImagemCategoria() {
    }

    public ImagemCategoria(Integer idimagemCategoria) {
        this.idimagemCategoria = idimagemCategoria;
    }

    public ImagemCategoria(Integer idimagemCategoria, String link) {
        this.idimagemCategoria = idimagemCategoria;
        this.link = link;
    }

    public Integer getIdimagemCategoria() {
        return idimagemCategoria;
    }

    public void setIdimagemCategoria(Integer idimagemCategoria) {
        this.idimagemCategoria = idimagemCategoria;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idimagemCategoria != null ? idimagemCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagemCategoria)) {
            return false;
        }
        ImagemCategoria other = (ImagemCategoria) object;
        if ((this.idimagemCategoria == null && other.idimagemCategoria != null) || (this.idimagemCategoria != null && !this.idimagemCategoria.equals(other.idimagemCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ImagemCategoria[ idimagemCategoria=" + idimagemCategoria + " ]";
    }
    
}
