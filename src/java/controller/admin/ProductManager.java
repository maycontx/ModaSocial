package controller.admin;

import dao.CategoriaJpaController;
import dao.FornecedorJpaController;
import dao.ProdutoJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
        String description = request.getParameter("product-description");        
        
        product.setNome(name);
        product.setMarca(brand);
        product.setCategoria(new CategoriaJpaController(emf).findCategoria(1));        
        product.setFornecedor(new FornecedorJpaController(emf).findFornecedor(1));
        product.setPreco(BigDecimal.valueOf(price));
        product.setEstoque(Integer.parseInt(stock));
        product.setStatus(status);
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
            new ProdutoJpaController(emf).create(product);
            
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
