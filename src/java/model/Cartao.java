/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asdfrofl
 */
@Entity
@Table(catalog = "modasocial", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cartao.findAll", query = "SELECT c FROM Cartao c"),
    @NamedQuery(name = "Cartao.findByIdcartao", query = "SELECT c FROM Cartao c WHERE c.idcartao = :idcartao"),
    @NamedQuery(name = "Cartao.findByNumero", query = "SELECT c FROM Cartao c WHERE c.numero = :numero"),
    @NamedQuery(name = "Cartao.findByNome", query = "SELECT c FROM Cartao c WHERE c.nome = :nome"),
    @NamedQuery(name = "Cartao.findByTipo", query = "SELECT c FROM Cartao c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Cartao.findBySeguranca", query = "SELECT c FROM Cartao c WHERE c.seguranca = :seguranca"),
    @NamedQuery(name = "Cartao.findByVencimento", query = "SELECT c FROM Cartao c WHERE c.vencimento = :vencimento")})
public class Cartao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idcartao;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String numero;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String nome;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String tipo;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String seguranca;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vencimento;
    @JoinColumn(name = "pagamento", referencedColumnName = "idpagamento", nullable = false)
    @ManyToOne(optional = false)
    private Pagamento pagamento;

    public Cartao() {
    }

    public Cartao(Integer idcartao) {
        this.idcartao = idcartao;
    }

    public Cartao(Integer idcartao, String numero, String nome, String tipo, String seguranca, Date vencimento) {
        this.idcartao = idcartao;
        this.numero = numero;
        this.nome = nome;
        this.tipo = tipo;
        this.seguranca = seguranca;
        this.vencimento = vencimento;
    }

    public Integer getIdcartao() {
        return idcartao;
    }

    public void setIdcartao(Integer idcartao) {
        this.idcartao = idcartao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSeguranca() {
        return seguranca;
    }

    public void setSeguranca(String seguranca) {
        this.seguranca = seguranca;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcartao != null ? idcartao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cartao)) {
            return false;
        }
        Cartao other = (Cartao) object;
        if ((this.idcartao == null && other.idcartao != null) || (this.idcartao != null && !this.idcartao.equals(other.idcartao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cartao[ idcartao=" + idcartao + " ]";
    }
    
}
