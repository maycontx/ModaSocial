package controller;

import dao.CarrinhoJpaController;
import dao.ProdutoJpaController;
import dao.RelProdutoCarrinhoJpaController;
import helper.Session;
import java.io.IOException;
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
import model.Carrinho;
import model.Produto;
import model.RelProdutoCarrinho;
import model.Usuario;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        ProdutoJpaController productData = new ProdutoJpaController(emf);
        CarrinhoJpaController cartData = new CarrinhoJpaController(emf);
        RelProdutoCarrinhoJpaController cartProduct = new RelProdutoCarrinhoJpaController(emf);

        String p = request.getParameter("p");
        String status = request.getParameter("status");
        String refresh = request.getParameter("refresh");

        Usuario user = (Usuario) request.getSession().getAttribute("user");
        
        Carrinho cart = null;
        
        if ( user != null ){
            cart = cartData.findActiveCartByUser(user);
        }else{
            cart = (Carrinho) request.getSession().getAttribute("shoppingCart");
        }   
        

        if (p != null) {

            try {
                // FIND PRODUCT TO PUT ON THE CART
                Produto product = productData.findProduto(Integer.parseInt(p));
                
                if ( user != null ){
                    // CHECKING NULL CART 
                    if (cart == null) {
                        cart = new Carrinho();
                        cart.setUsuario(user);
                        cart.setStatus("ativo");
                        // CREATE A CART AND PUT HIS ID ON FIRE
                        cart.setIdcarrinho(cartData.create(cart));
                    }
                    
                     // FIND RELATION ABOUT THAT PRODUCT WITH THAT CART
                    RelProdutoCarrinho rel = cartProduct.findRelationByProductAndCart(product, cart);
                    // IF NOT HAVE, CREATE THEN
                    if (rel == null) {
                        rel = new RelProdutoCarrinho();
                        rel.setProduto(product);
                        rel.setCarrinho(cart);
                        rel.setQuantidade(1);
                        cartProduct.create(rel);                
                    } else 
                    // IF HAVE JUST SET AMOUNT
                    {
                        rel.setQuantidade(rel.getQuantidade() + 1);
                        cartProduct.edit(rel);
                    }
                    
                    // RELOADING CART
                    cart = cartData.findActiveCartByUser(user);

                }else{
                    Session.addProductInShoppingCart(product, request, -1);
                    cart = (Carrinho) request.getSession().getAttribute("shoppingCart");
                }
                
                RequestDispatcher rd = request.getRequestDispatcher("template.jsp");

                request.setAttribute("page", "cart");
                request.setAttribute("cart", cart);                
                rd.forward(request, response);

            } catch (Exception ex) {
                RequestDispatcher rd = request.getRequestDispatcher("template.jsp");

                request.setAttribute("page", "404");
                rd.forward(request, response);
            }

        } else if (status != null) {
            
            if ( status.equals("amountChange") ){
                Produto product = productData.findProduto(Integer.parseInt(request.getParameter("product")));
                Integer newValue = Integer.parseInt(request.getParameter("value")); 
                if ( user != null ){
                    try {                                           

                        RelProdutoCarrinho rel = cartProduct.findRelationByProductAndCart(product, cart);

                        if ( newValue < 1 ){
                            cartProduct.destroy(rel.getIdrelProdutoCarrinho());
                        }else{
                            rel.setQuantidade(newValue);
                            cartProduct.edit(rel);
                        }                    

                    } catch (Exception ex) {
                        Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    Session.addProductInShoppingCart(product, request, newValue);
                    cart = (Carrinho) request.getSession().getAttribute("shoppingCart");
                }
                
               
            }

        }else if (refresh != null && refresh.equals("true")) {
            if ( user != null )
                cart = cartData.findActiveCartByUser(user);
            else
                cart = (Carrinho) request.getSession().getAttribute("shoppingCart");
                
            RequestDispatcher rd = request.getRequestDispatcher("pages/cart/cart.jsp");
            request.setAttribute("cart", cart);
            rd.forward(request, response);
        }
        else{

            if ( user != null )
                cart = cartData.findActiveCartByUser(user);
            else
                cart = (Carrinho) request.getSession().getAttribute("shoppingCart");

            RequestDispatcher rd = request.getRequestDispatcher("template.jsp");

            request.setAttribute("page", "cart");
            request.setAttribute("cart", cart);
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
