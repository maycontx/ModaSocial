/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.ProdutoJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
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
    @NamedQuery(name = "Busca.findAll", query = "SELECT b FROM Busca b"),
    @NamedQuery(name = "Busca.findByIdbusca", query = "SELECT b FROM Busca b WHERE b.idbusca = :idbusca"),
    @NamedQuery(name = "Busca.findByTermo", query = "SELECT b FROM Busca b WHERE b.termo = :termo"),
    @NamedQuery(name = "Busca.findByData", query = "SELECT b FROM Busca b WHERE b.data = :data")})
public class Busca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idbusca;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String termo;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Busca() {
    }

    public Busca(Integer idbusca) {
        this.idbusca = idbusca;
    }

    public Busca(Integer idbusca, String termo, Date data) {
        this.idbusca = idbusca;
        this.termo = termo;
        this.data = data;
    }

    public Integer getIdbusca() {
        return idbusca;
    }

    public void setIdbusca(Integer idbusca) {
        this.idbusca = idbusca;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
        hash += (idbusca != null ? idbusca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Busca)) {
            return false;
        }
        Busca other = (Busca) object;
        if ((this.idbusca == null && other.idbusca != null) || (this.idbusca != null && !this.idbusca.equals(other.idbusca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Busca[ idbusca=" + idbusca + " ]";
    }
    
    // OTHERS METHODS
    public List<Produto> getProducts(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        return new ProdutoJpaController(emf).findSearchProduct(this);
    }
    
    public List<Filter> getFilters(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        List<Produto> products = new ProdutoJpaController(emf).findSearchProduct(this);
        
        List<Filter> filters = new ArrayList<Filter>();
        
        for ( Produto p : products ){
            String brand = p.getMarca();
            boolean exist = false;
            for ( Filter f : filters ){
                if ( f.getFeature().equals(brand) ){
                    f.setAmount(f.getAmount() + 1);
                    exist = true;
                }  
            }
            if ( exist == false ){
                Filter filter = new Filter();
                filter.setFeature(brand);
                filter.setAmount(1);

                filters.add(filter);
            }
        }
        
        return filters;
        
    }
    
}
