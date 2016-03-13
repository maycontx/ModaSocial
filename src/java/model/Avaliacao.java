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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Avaliacao.findAll", query = "SELECT a FROM Avaliacao a"),
    @NamedQuery(name = "Avaliacao.findByIdavaliacao", query = "SELECT a FROM Avaliacao a WHERE a.idavaliacao = :idavaliacao"),
    @NamedQuery(name = "Avaliacao.findByNota", query = "SELECT a FROM Avaliacao a WHERE a.nota = :nota")})
public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idavaliacao;
    @Basic(optional = false)
    @Column(nullable = false)
    private int nota;
    @Lob
    @Column(length = 65535)
    private String comentario;
    @JoinColumn(name = "produto", referencedColumnName = "idproduto", nullable = false)
    @ManyToOne(optional = false)
    private Produto produto;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Avaliacao() {
    }

    public Avaliacao(Integer idavaliacao) {
        this.idavaliacao = idavaliacao;
    }

    public Avaliacao(Integer idavaliacao, int nota) {
        this.idavaliacao = idavaliacao;
        this.nota = nota;
    }

    public Integer getIdavaliacao() {
        return idavaliacao;
    }

    public void setIdavaliacao(Integer idavaliacao) {
        this.idavaliacao = idavaliacao;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idavaliacao != null ? idavaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacao)) {
            return false;
        }
        Avaliacao other = (Avaliacao) object;
        if ((this.idavaliacao == null && other.idavaliacao != null) || (this.idavaliacao != null && !this.idavaliacao.equals(other.idavaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Avaliacao[ idavaliacao=" + idavaliacao + " ]";
    }
    
}
