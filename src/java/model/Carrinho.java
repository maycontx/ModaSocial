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
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Carrinho.findAll", query = "SELECT c FROM Carrinho c"),
    @NamedQuery(name = "Carrinho.findByIdcarrinho", query = "SELECT c FROM Carrinho c WHERE c.idcarrinho = :idcarrinho")})
public class Carrinho implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idcarrinho;
    @JoinColumn(name = "cupom", referencedColumnName = "idcupom", nullable = false)
    @ManyToOne(optional = false)
    private Cupom cupom;    
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carrinho")
    private List<RelProdutoCarrinho> relProdutoCarrinhoList;   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carrinho")
    private List<Venda> vendaList;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Carrinho() {
    }
    
    public Carrinho(String status) {
        this.status = status;
    }

    public Carrinho(Integer idcarrinho) {
        this.idcarrinho = idcarrinho;
    }

    public Integer getIdcarrinho() {
        return idcarrinho;
    }

    public void setIdcarrinho(Integer idcarrinho) {
        this.idcarrinho = idcarrinho;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }
    
    @XmlTransient
    public List<RelProdutoCarrinho> getRelProdutoCarrinhoList() {
        return relProdutoCarrinhoList;
    }

    public void setRelProdutoCarrinhoList(List<RelProdutoCarrinho> relProdutoCarrinhoList) {
        this.relProdutoCarrinhoList = relProdutoCarrinhoList;
    }

    @XmlTransient
    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
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
        hash += (idcarrinho != null ? idcarrinho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrinho)) {
            return false;
        }
        Carrinho other = (Carrinho) object;
        if ((this.idcarrinho == null && other.idcarrinho != null) || (this.idcarrinho != null && !this.idcarrinho.equals(other.idcarrinho))) {
            return false;
        }
        return true;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    @Override
    public String toString() {
        return "model.Carrinho[ idcarrinho=" + idcarrinho + " ]";
    }
    
    public double getTotalValue(){
        
        double total = 0;
        
        for ( RelProdutoCarrinho rel : this.getRelProdutoCarrinhoList() ){
            total += ( Integer.valueOf(rel.getProduto().getPreco().intValue()) * rel.getQuantidade() );
        }
        
        return total;
        
    }
    
    public double getFinalValue(){
        
        if ( this.cupom == null )
            return this.getTotalValue();
        else{
            
            return this.getTotalValue() - (( this.getTotalValue() * this.cupom.getDesconto()) / 100);
        
        } 
        
    }
    
}
