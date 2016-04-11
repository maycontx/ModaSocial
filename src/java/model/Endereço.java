/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
    @NamedQuery(name = "Endere\u00e7o.findAll", query = "SELECT e FROM Endere\u00e7o e"),
    @NamedQuery(name = "Endere\u00e7o.findByIdendere\u00e7o", query = "SELECT e FROM Endere\u00e7o e WHERE e.idendere\u00e7o = :idendere\u00e7o"),
    @NamedQuery(name = "Endere\u00e7o.findByPais", query = "SELECT e FROM Endere\u00e7o e WHERE e.pais = :pais"),
    @NamedQuery(name = "Endere\u00e7o.findByEstado", query = "SELECT e FROM Endere\u00e7o e WHERE e.estado = :estado"),
    @NamedQuery(name = "Endere\u00e7o.findByCidade", query = "SELECT e FROM Endere\u00e7o e WHERE e.cidade = :cidade"),
    @NamedQuery(name = "Endere\u00e7o.findByBairro", query = "SELECT e FROM Endere\u00e7o e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "Endere\u00e7o.findByCep", query = "SELECT e FROM Endere\u00e7o e WHERE e.cep = :cep"),
    @NamedQuery(name = "Endere\u00e7o.findByLugradouro", query = "SELECT e FROM Endere\u00e7o e WHERE e.lugradouro = :lugradouro"),
    @NamedQuery(name = "Endere\u00e7o.findByComplemento", query = "SELECT e FROM Endere\u00e7o e WHERE e.complemento = :complemento"),
    @NamedQuery(name = "Endere\u00e7o.findByTelefone", query = "SELECT e FROM Endere\u00e7o e WHERE e.telefone = :telefone")})
public class Endereço implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idendereço;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String pais;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String estado;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String cidade;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String bairro;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String rua;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String cep;
    @Column(length = 255)
    private String lugradouro;
    @Column(length = 255)
    private String complemento;
    @Column(length = 255)
    private String telefone;
    @Column(length = 255)
    private String nome;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "endereco")
    private List<Venda> vendaList;

    public Endereço() {
    }

    public Endereço(Integer idendereço) {
        this.idendereço = idendereço;
    }

    public Endereço(Integer idendereço, String pais, String estado, String cidade, String bairro, String cep, String nome) {
        this.idendereço = idendereço;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
        this.nome = nome;
    }

    public Integer getIdendereço() {
        return idendereço;
    }

    public void setIdendereço(Integer idendereço) {
        this.idendereço = idendereço;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }  

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLugradouro() {
        return lugradouro;
    }

    public void setLugradouro(String lugradouro) {
        this.lugradouro = lugradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idendereço != null ? idendereço.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endereço)) {
            return false;
        }
        Endereço other = (Endereço) object;
        if ((this.idendereço == null && other.idendereço != null) || (this.idendereço != null && !this.idendereço.equals(other.idendereço))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Endere\u00e7o[ idendere\u00e7o=" + idendereço + " ]";
    }
    
}
