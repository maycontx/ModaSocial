/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author asdfrofl
 */
@Entity
@Table(catalog = "modasocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preferencia.findAll", query = "SELECT p FROM Preferencia p"),
    @NamedQuery(name = "Preferencia.findByIdpreferencia", query = "SELECT p FROM Preferencia p WHERE p.idpreferencia = :idpreferencia"),
    @NamedQuery(name = "Preferencia.findByColecao", query = "SELECT p FROM Preferencia p WHERE p.colecao = :colecao"),
    @NamedQuery(name = "Preferencia.findByEstilo", query = "SELECT p FROM Preferencia p WHERE p.estilo = :estilo")})
public class Preferencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idpreferencia;
    @Column(length = 255)
    private String colecao;
    @Column(length = 255)
    private String estilo;
    @OneToMany(mappedBy = "preferencia")
    private List<Usuario> usuarioList;

    public Preferencia() {
    }

    public Preferencia(Integer idpreferencia) {
        this.idpreferencia = idpreferencia;
    }

    public Integer getIdpreferencia() {
        return idpreferencia;
    }

    public void setIdpreferencia(Integer idpreferencia) {
        this.idpreferencia = idpreferencia;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpreferencia != null ? idpreferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferencia)) {
            return false;
        }
        Preferencia other = (Preferencia) object;
        if ((this.idpreferencia == null && other.idpreferencia != null) || (this.idpreferencia != null && !this.idpreferencia.equals(other.idpreferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Preferencia[ idpreferencia=" + idpreferencia + " ]";
    }
    
}
