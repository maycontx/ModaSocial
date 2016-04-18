/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.ProdutoJpaController;
import helper.Session;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
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
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByIdproduto", query = "SELECT p FROM Produto p WHERE p.idproduto = :idproduto"),
    @NamedQuery(name = "Produto.findByNome", query = "SELECT p FROM Produto p WHERE p.nome = :nome"),
    @NamedQuery(name = "Produto.findByMarca", query = "SELECT p FROM Produto p WHERE p.marca = :marca"),
    @NamedQuery(name = "Produto.findByPreco", query = "SELECT p FROM Produto p WHERE p.preco = :preco"),
    @NamedQuery(name = "Produto.findByEstoque", query = "SELECT p FROM Produto p WHERE p.estoque = :estoque"),
    @NamedQuery(name = "Produto.findByStatus", query = "SELECT p FROM Produto p WHERE p.status = :status")})
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idproduto;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String nome;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 65535)
    private String descricao;    
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String marca;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    @Basic(optional = false)
    @Column(nullable = false)
    private int estoque;
    @Column(length = 7)
    private String status;
    private String colecao;
    private String estilo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<Caracteristica> caracteristicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<ImagemProduto> imagemProdutoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<RelProdutoCarrinho> relProdutoCarrinhoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<Avaliacao> avaliacaoList;
    @JoinColumn(name = "categoria", referencedColumnName = "idcategoria", nullable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @JoinColumn(name = "fornecedor", referencedColumnName = "idfornecedor", nullable = false)
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<RelProdutoPromocao> relProdutoPromocaoList;
    
    transient EntityManagerFactory emf; 
    
    public Produto() {
    }

    public Produto(Integer idproduto) {
        this.idproduto = idproduto;
    }

    public Produto(Integer idproduto, String nome, String descricao, String marca, BigDecimal preco, int estoque) {
        this.idproduto = idproduto;
        this.nome = nome;
        this.descricao = descricao;
        this.marca = marca;
        this.preco = preco;
        this.estoque = estoque;
    }
    
    public List<Produto> getSortProdutos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        return new ProdutoJpaController(emf).findSortProdutos();
    }
    
    public List<Produto> getRelated(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        return new ProdutoJpaController(emf).findRelated(this);
    }

    public Integer getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Integer idproduto) {
        this.idproduto = idproduto;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    public List<Caracteristica> getCaracteristicaList() {
        return caracteristicaList;
    }

    public void setCaracteristicaList(List<Caracteristica> caracteristicaList) {
        this.caracteristicaList = caracteristicaList;
    }

    @XmlTransient
    public List<ImagemProduto> getImagemProdutoList() {
        return imagemProdutoList;
    }

    public void setImagemProdutoList(List<ImagemProduto> imagemProdutoList) {
        this.imagemProdutoList = imagemProdutoList;
    }

    @XmlTransient
    public List<RelProdutoCarrinho> getRelProdutoCarrinhoList() {
        return relProdutoCarrinhoList;
    }

    public void setRelProdutoCarrinhoList(List<RelProdutoCarrinho> relProdutoCarrinhoList) {
        this.relProdutoCarrinhoList = relProdutoCarrinhoList;
    }

    @XmlTransient
    public List<Avaliacao> getAvaliacaoList() {
        return avaliacaoList;
    }

    public void setAvaliacaoList(List<Avaliacao> avaliacaoList) {
        this.avaliacaoList = avaliacaoList;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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
        hash += (idproduto != null ? idproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.idproduto == null && other.idproduto != null) || (this.idproduto != null && !this.idproduto.equals(other.idproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Produto[ idproduto=" + idproduto + " ]";
    }
    
    /* OTHERS METHODS */
    public String getShortDescription(){
        if ( this.descricao.length() > 300 ) 
            return this.descricao.substring(0, 300) + "...";
        else
            return this.descricao;
    }
    
    public String getRateBalance(){
        
        List<Avaliacao> ratings = this.getAvaliacaoList();
        
        if ( ratings.size() == 0 ){
            return  "Nenhuma avaliação deste produto.";
        }
        
        double total = ratings.size();
        double nota1 = 0;
        double nota2 = 0;
        double nota3 = 0;
        double nota4 = 0;
        double nota5 = 0;
        
        for ( Avaliacao r : ratings ){
            switch (r.getNota()){
                case 1:
                    nota1++;
                    break;
                case 2:
                    nota2++;
                    break;
                case 3:
                    nota3++;
                    break;
                case 4:
                    nota4++;
                    break;
                case 5:
                    nota5++;
                    break;
            }
        }
        
        return (( nota1/total ) * 100) + "% Muito ruim <br/>" + 
               (( nota2/total ) * 100) + "% Ruim <br/>" + 
               (( nota3/total ) * 100) + "% Razoável <br/>" + 
               (( nota4/total ) * 100) + "% Bom <br/>" + 
               (( nota5/total ) * 100) + "% Muito bom ";
        
    }
    
    public void addProductInShoppingCart(HttpServletRequest request){
        Session.addProductInShoppingCart(this, request, -1);
    }
   
    
}
