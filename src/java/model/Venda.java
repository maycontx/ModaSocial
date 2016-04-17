/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v"),
    @NamedQuery(name = "Venda.findByIdvenda", query = "SELECT v FROM Venda v WHERE v.idvenda = :idvenda"),
    @NamedQuery(name = "Venda.findByFreteTipo", query = "SELECT v FROM Venda v WHERE v.freteTipo = :freteTipo"),
    @NamedQuery(name = "Venda.findByFreteValor", query = "SELECT v FROM Venda v WHERE v.freteValor = :freteValor")})
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idvenda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation    
    @Basic(optional = false)
    @Column(name = "frete_tipo", nullable = false, length = 255)
    private String freteTipo;
    @Basic(optional = false)
    @Column(name = "frete_valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal freteValor;
    @JoinColumn(name = "carrinho", referencedColumnName = "idcarrinho", nullable = false)
    @ManyToOne(optional = false)
    private Carrinho carrinho;
    @JoinColumn(name = "endereco", referencedColumnName = "idendere\u00e7o", nullable = false)
    @ManyToOne(optional = false)
    private Endereço endereco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda")
    private List<Pagamento> pagamentoList;

    public Venda() {
    }

    public Venda(Integer idvenda) {
        this.idvenda = idvenda;
    }

    public Venda(Integer idvenda, String freteTipo, BigDecimal freteValor) {
        this.idvenda = idvenda;       
        this.freteTipo = freteTipo;
        this.freteValor = freteValor;
    }

    public Integer getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(Integer idvenda) {
        this.idvenda = idvenda;
    }   

    public String getFreteTipo() {
        return freteTipo;
    }

    public void setFreteTipo(String freteTipo) {
        this.freteTipo = freteTipo;
    }

    public BigDecimal getFreteValor() {
        return freteValor;
    }

    public void setFreteValor(BigDecimal freteValor) {
        this.freteValor = freteValor;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public Endereço getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereço endereco) {
        this.endereco = endereco;
    }

    @XmlTransient
    public List<Pagamento> getPagamentoList() {
        return pagamentoList;
    }

    public void setPagamentoList(List<Pagamento> pagamentoList) {
        this.pagamentoList = pagamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvenda != null ? idvenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.idvenda == null && other.idvenda != null) || (this.idvenda != null && !this.idvenda.equals(other.idvenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Venda[ idvenda=" + idvenda + " ]";
    }
    
}
