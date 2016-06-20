/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Entrada.findAll", query = "SELECT e FROM Entrada e"),
    @NamedQuery(name = "Entrada.findByIdentrada", query = "SELECT e FROM Entrada e WHERE e.identrada = :identrada"),
    @NamedQuery(name = "Entrada.findBySaldo", query = "SELECT e FROM Entrada e WHERE e.saldo = :saldo"),
    @NamedQuery(name = "Entrada.findByVenda", query = "SELECT e FROM Entrada e WHERE e.venda = :venda"),
    @NamedQuery(name = "Entrada.findByOutros", query = "SELECT e FROM Entrada e WHERE e.outros = :outros"),
    @NamedQuery(name = "Entrada.findByDataInicial", query = "SELECT e FROM Entrada e WHERE e.dataInicial = :dataInicial"),
    @NamedQuery(name = "Entrada.findByDataFinal", query = "SELECT e FROM Entrada e WHERE e.dataFinal = :dataFinal")})
public class Entrada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer identrada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal venda;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal outros;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicial;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    public Entrada() {
    }

    public Entrada(Integer identrada) {
        this.identrada = identrada;
    }

    public Entrada(Integer identrada, BigDecimal saldo, BigDecimal venda, BigDecimal outros, Date dataInicial, Date dataFinal) {
        this.identrada = identrada;
        this.saldo = saldo;
        this.venda = venda;
        this.outros = outros;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Integer getIdentrada() {
        return identrada;
    }

    public void setIdentrada(Integer identrada) {
        this.identrada = identrada;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getVenda() {
        return venda;
    }

    public void setVenda(BigDecimal venda) {
        this.venda = venda;
    }

    public BigDecimal getOutros() {
        return outros;
    }

    public void setOutros(BigDecimal outros) {
        this.outros = outros;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identrada != null ? identrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrada)) {
            return false;
        }
        Entrada other = (Entrada) object;
        if ((this.identrada == null && other.identrada != null) || (this.identrada != null && !this.identrada.equals(other.identrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Entrada[ identrada=" + identrada + " ]";
    }
    
    public String getDateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
        return sdf.format(date);
    }
    
    public BigDecimal total(){
        BigDecimal result = saldo;
        result = result.add(venda);
        result = result.add(outros);
        return result;
    }
    
}
