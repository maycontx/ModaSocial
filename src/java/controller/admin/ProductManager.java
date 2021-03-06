package controller.admin;

import dao.CaracteristicaJpaController;
import dao.CategoriaJpaController;
import dao.FornecedorJpaController;
import dao.ProdutoJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Caracteristica;
import model.Produto;

@WebServlet(name = "ProductManager", urlPatterns = {"/product-manager"})
public class ProductManager extends HttpServlet {

    private Produto constructProduct(HttpServletRequest request, Produto product){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        
        String name = request.getParameter("product-name");
        String brand = request.getParameter("product-brand");
        String category = request.getParameter("product-category");
        String owner = request.getParameter("product-owner");
        String priceString = request.getParameter("product-price").trim().replace(",", ".");        
        double price = Double.parseDouble(priceString);
        String stock = request.getParameter("product-stock");
        String status = request.getParameter("product-status");
        String collection = request.getParameter("product-collection");
        String style = request.getParameter("product-style");
        String description = request.getParameter("product-description");       
        
        product.setNome(name);
        product.setMarca(brand);
        product.setCategoria(new CategoriaJpaController(emf).findCategoria(1));        
        product.setFornecedor(new FornecedorJpaController(emf).findFornecedor(1));
        product.setPreco(BigDecimal.valueOf(price));
        product.setEstoque(Integer.parseInt(stock));
        product.setStatus(status);
        product.setColecao(collection);
        product.setEstilo(style);
        product.setDescricao(description);
        
        return product;
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        
        String action = request.getParameter("action");
        
        if ( action == null ){
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");
        
            request.setAttribute("page", "product");
            request.setAttribute("allProducts", new ProdutoJpaController(emf).findProdutoEntities());
        
            rd.forward(request, response);
        } else if ( action.equals("add-product") ){
            
            Produto product = new Produto();
            product = this.constructProduct(request, product);
            product = new ProdutoJpaController(emf).create(product);
            
            int featureAmount = Integer.parseInt(request.getParameter("feature-amount"));
            for ( int i = 1; i <= featureAmount - 1; i++ ){
                
                String field = request.getParameter("feature-field" + i);
                String value = request.getParameter("feature-value" + i);
                
                Caracteristica feature = new Caracteristica();
                feature.setCampo(field);
                feature.setValor(value);
                feature.setProduto(product);
                
                new CaracteristicaJpaController(emf).create(feature);
                
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");
        
            request.setAttribute("page", "product");
            request.setAttribute("allProducts", new ProdutoJpaController(emf).findProdutoEntities());
        
            rd.forward(request, response);
            
        } else if ( action.equals("remove-product") ){
            
            try {
                Integer product = Integer.parseInt(request.getParameter("product"));
                
                new ProdutoJpaController(emf).destroy(product);
                
                RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");
                
                request.setAttribute("page", "product");
                request.setAttribute("allProducts", new ProdutoJpaController(emf).findProdutoEntities());
                
                rd.forward(request, response);
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ( action.equals("edit-product") ){
            
            String idproduct = request.getParameter("id");
            
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");
            
            if ( idproduct == null ){
                
                Integer product = Integer.parseInt(request.getParameter("product"));  
                
                request.setAttribute("target", new ProdutoJpaController(emf).findProduto(product));
               
            
            }else{
            
                try {
                    Integer id = Integer.parseInt(idproduct);
                    Produto product = new ProdutoJpaController(emf).findProduto(id);                    
                    
                    product = this.constructProduct(request, product);
                    
                    List<Caracteristica> featureList = new CaracteristicaJpaController(emf).findAllFeatureByProduct(product);
                    for ( Caracteristica f : featureList ){
                        new CaracteristicaJpaController(emf).destroy(f.getIdcaracteristica());
                    }
                    
                    
                    int featureAmount = Integer.parseInt(request.getParameter("feature-amount"));
                    for ( int i = 1; i <= featureAmount - 1; i++ ){

                        String field = request.getParameter("feature-field" + i);
                        String value = request.getParameter("feature-value" + i);

                        Caracteristica feature = new Caracteristica();
                        feature.setCampo(field);
                        feature.setValor(value);
                        feature.setProduto(product);

                        new CaracteristicaJpaController(emf).create(feature);

                    }
                    
                    new ProdutoJpaController(emf).edit(product);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }  
            
            request.setAttribute("page", "product");
                
            request.setAttribute("allProducts", new ProdutoJpaController(emf).findProdutoEntities());

            rd.forward(request, response);
            
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
