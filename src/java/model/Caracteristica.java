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
@Table(catalog = "modasocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caracteristica.findAll", query = "SELECT c FROM Caracteristica c"),
    @NamedQuery(name = "Caracteristica.findByIdcaracteristica", query = "SELECT c FROM Caracteristica c WHERE c.idcaracteristica = :idcaracteristica"),
    @NamedQuery(name = "Caracteristica.findByCampo", query = "SELECT c FROM Caracteristica c WHERE c.campo = :campo"),
    @NamedQuery(name = "Caracteristica.findByValor", query = "SELECT c FROM Caracteristica c WHERE c.valor = :valor")})
public class Caracteristica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idcaracteristica;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String campo;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String valor;
    @JoinColumn(name = "produto", referencedColumnName = "idproduto", nullable = false)
    @ManyToOne(optional = false)
    private Produto produto;

    public Caracteristica() {
    }

    public Caracteristica(Integer idcaracteristica) {
        this.idcaracteristica = idcaracteristica;
    }

    public Caracteristica(Integer idcaracteristica, String campo, String valor) {
        this.idcaracteristica = idcaracteristica;
        this.campo = campo;
        this.valor = valor;
    }

    public Integer getIdcaracteristica() {
        return idcaracteristica;
    }

    public void setIdcaracteristica(Integer idcaracteristica) {
        this.idcaracteristica = idcaracteristica;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
        hash += (idcaracteristica != null ? idcaracteristica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caracteristica)) {
            return false;
        }
        Caracteristica other = (Caracteristica) object;
        if ((this.idcaracteristica == null && other.idcaracteristica != null) || (this.idcaracteristica != null && !this.idcaracteristica.equals(other.idcaracteristica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Caracteristica[ idcaracteristica=" + idcaracteristica + " ]";
    }
    
}
