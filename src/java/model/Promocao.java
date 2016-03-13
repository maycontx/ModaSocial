/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Promocao.findAll", query = "SELECT p FROM Promocao p"),
    @NamedQuery(name = "Promocao.findByIdpromocao", query = "SELECT p FROM Promocao p WHERE p.idpromocao = :idpromocao"),
    @NamedQuery(name = "Promocao.findByNome", query = "SELECT p FROM Promocao p WHERE p.nome = :nome"),
    @NamedQuery(name = "Promocao.findByEncerramento", query = "SELECT p FROM Promocao p WHERE p.encerramento = :encerramento"),
    @NamedQuery(name = "Promocao.findByDesconto", query = "SELECT p FROM Promocao p WHERE p.desconto = :desconto"),
    @NamedQuery(name = "Promocao.findByStatus", query = "SELECT p FROM Promocao p WHERE p.status = :status")})
public class Promocao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idpromocao;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String nome;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 65535)
    private String descricao;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date encerramento;
    @Basic(optional = false)
    @Column(nullable = false)
    private int desconto;
    @Column(length = 10)
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "promocao")
    private List<RelProdutoPromocao> relProdutoPromocaoList;

    public Promocao() {
    }

    public Promocao(Integer idpromocao) {
        this.idpromocao = idpromocao;
    }

    public Promocao(Integer idpromocao, String nome, String descricao, Date encerramento, int desconto) {
        this.idpromocao = idpromocao;
        this.nome = nome;
        this.descricao = descricao;
        this.encerramento = encerramento;
        this.desconto = desconto;
    }

    public Integer getIdpromocao() {
        return idpromocao;
    }

    public void setIdpromocao(Integer idpromocao) {
        this.idpromocao = idpromocao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getEncerramento() {
        return encerramento;
    }

    public void setEncerramento(Date encerramento) {
        this.encerramento = encerramento;
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

    @XmlTransient
    public List<RelProdutoPromocao> getRelProdutoPromocaoList() {
        return relProdutoPromocaoList;
    }

    public void setRelProdutoPromocaoList(List<RelProdutoPromocao> relProdutoPromocaoList) {
        this.relProdutoPromocaoList = relProdutoPromocaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpromocao != null ? idpromocao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocao)) {
            return false;
        }
        Promocao other = (Promocao) object;
        if ((this.idpromocao == null && other.idpromocao != null) || (this.idpromocao != null && !this.idpromocao.equals(other.idpromocao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Promocao[ idpromocao=" + idpromocao + " ]";
    }
    
}
