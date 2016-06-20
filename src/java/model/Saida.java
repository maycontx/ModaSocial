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
    @NamedQuery(name = "Saida.findAll", query = "SELECT s FROM Saida s"),
    @NamedQuery(name = "Saida.findByIdsaida", query = "SELECT s FROM Saida s WHERE s.idsaida = :idsaida"),
    @NamedQuery(name = "Saida.findByFornecedor", query = "SELECT s FROM Saida s WHERE s.fornecedor = :fornecedor"),
    @NamedQuery(name = "Saida.findByAguaELuz", query = "SELECT s FROM Saida s WHERE s.aguaELuz = :aguaELuz"),
    @NamedQuery(name = "Saida.findByTelefone", query = "SELECT s FROM Saida s WHERE s.telefone = :telefone"),
    @NamedQuery(name = "Saida.findByCombustivel", query = "SELECT s FROM Saida s WHERE s.combustivel = :combustivel"),
    @NamedQuery(name = "Saida.findByBanco", query = "SELECT s FROM Saida s WHERE s.banco = :banco"),
    @NamedQuery(name = "Saida.findByMateriaisConsumo", query = "SELECT s FROM Saida s WHERE s.materiaisConsumo = :materiaisConsumo"),
    @NamedQuery(name = "Saida.findByEquipamentos", query = "SELECT s FROM Saida s WHERE s.equipamentos = :equipamentos"),
    @NamedQuery(name = "Saida.findByImpostos", query = "SELECT s FROM Saida s WHERE s.impostos = :impostos"),
    @NamedQuery(name = "Saida.findByDataInicial", query = "SELECT s FROM Saida s WHERE s.dataInicial = :dataInicial"),
    @NamedQuery(name = "Saida.findByDataFinal", query = "SELECT s FROM Saida s WHERE s.dataFinal = :dataFinal")})
public class Saida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idsaida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fornecedor;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal aguaELuz;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal telefone;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal combustivel;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal banco;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal materiaisConsumo;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal equipamentos;
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal impostos;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicial;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    public Saida() {
    }

    public Saida(Integer idsaida) {
        this.idsaida = idsaida;
    }

    public Saida(Integer idsaida, BigDecimal fornecedor, BigDecimal aguaELuz, BigDecimal telefone, BigDecimal combustivel, BigDecimal banco, BigDecimal materiaisConsumo, BigDecimal equipamentos, BigDecimal impostos, Date dataInicial, Date dataFinal) {
        this.idsaida = idsaida;
        this.fornecedor = fornecedor;
        this.aguaELuz = aguaELuz;
        this.telefone = telefone;
        this.combustivel = combustivel;
        this.banco = banco;
        this.materiaisConsumo = materiaisConsumo;
        this.equipamentos = equipamentos;
        this.impostos = impostos;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Integer getIdsaida() {
        return idsaida;
    }

    public void setIdsaida(Integer idsaida) {
        this.idsaida = idsaida;
    }

    public BigDecimal getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(BigDecimal fornecedor) {
        this.fornecedor = fornecedor;
    }

    public BigDecimal getAguaELuz() {
        return aguaELuz;
    }

    public void setAguaELuz(BigDecimal aguaELuz) {
        this.aguaELuz = aguaELuz;
    }

    public BigDecimal getTelefone() {
        return telefone;
    }

    public void setTelefone(BigDecimal telefone) {
        this.telefone = telefone;
    }

    public BigDecimal getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(BigDecimal combustivel) {
        this.combustivel = combustivel;
    }

    public BigDecimal getBanco() {
        return banco;
    }

    public void setBanco(BigDecimal banco) {
        this.banco = banco;
    }

    public BigDecimal getMateriaisConsumo() {
        return materiaisConsumo;
    }

    public void setMateriaisConsumo(BigDecimal materiaisConsumo) {
        this.materiaisConsumo = materiaisConsumo;
    }

    public BigDecimal getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(BigDecimal equipamentos) {
        this.equipamentos = equipamentos;
    }

    public BigDecimal getImpostos() {
        return impostos;
    }

    public void setImpostos(BigDecimal impostos) {
        this.impostos = impostos;
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
        hash += (idsaida != null ? idsaida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saida)) {
            return false;
        }
        Saida other = (Saida) object;
        if ((this.idsaida == null && other.idsaida != null) || (this.idsaida != null && !this.idsaida.equals(other.idsaida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Saida[ idsaida=" + idsaida + " ]";
    }
    
    public String getDateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
        return sdf.format(date);
    }
    
    public BigDecimal total(){
        BigDecimal result = fornecedor;
        result = result.add(telefone);
        result = result.add(aguaELuz);
        result = result.add(combustivel);
        result = result.add(banco);
        result = result.add(materiaisConsumo);
        result = result.add(equipamentos);
        result = result.add(impostos);        
        return result;
    }
    
}
