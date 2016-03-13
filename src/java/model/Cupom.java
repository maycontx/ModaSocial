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
    @NamedQuery(name = "Cupom.findAll", query = "SELECT c FROM Cupom c"),
    @NamedQuery(name = "Cupom.findByIdcupom", query = "SELECT c FROM Cupom c WHERE c.idcupom = :idcupom"),
    @NamedQuery(name = "Cupom.findByCodigo", query = "SELECT c FROM Cupom c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Cupom.findByDesconto", query = "SELECT c FROM Cupom c WHERE c.desconto = :desconto"),
    @NamedQuery(name = "Cupom.findByStatus", query = "SELECT c FROM Cupom c WHERE c.status = :status")})
public class Cupom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idcupom;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String codigo;
    @Basic(optional = false)
    @Column(nullable = false)
    private int desconto;
    @Column(length = 7)
    private String status;

    public Cupom() {
    }

    public Cupom(Integer idcupom) {
        this.idcupom = idcupom;
    }

    public Cupom(Integer idcupom, String codigo, int desconto) {
        this.idcupom = idcupom;
        this.codigo = codigo;
        this.desconto = desconto;
    }

    public Integer getIdcupom() {
        return idcupom;
    }

    public void setIdcupom(Integer idcupom) {
        this.idcupom = idcupom;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcupom != null ? idcupom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cupom)) {
            return false;
        }
        Cupom other = (Cupom) object;
        if ((this.idcupom == null && other.idcupom != null) || (this.idcupom != null && !this.idcupom.equals(other.idcupom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cupom[ idcupom=" + idcupom + " ]";
    }
    
}
