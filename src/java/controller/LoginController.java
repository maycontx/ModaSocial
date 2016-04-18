/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CarrinhoJpaController;
import dao.RelProdutoCarrinhoJpaController;
import dao.exceptions.NonexistentEntityException;
import helper.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import model.Carrinho;
import model.RelProdutoCarrinho;
import model.Usuario;

/**
 *
 * @author asdfrofl
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        CarrinhoJpaController cartData = new CarrinhoJpaController(emf);
        RelProdutoCarrinhoJpaController cartProduct = new RelProdutoCarrinhoJpaController(emf);

        String email = request.getParameter("log-email");
        String password = request.getParameter("log-pass");
        boolean keep = request.getParameter("log-keep") != null;

        RequestDispatcher rd = request.getRequestDispatcher("template.jsp");

        Usuario user = Session.createCookie(keep, email, password, response, request);
        if (user == null) {
            
            request.setAttribute("login", false);
            request.setAttribute("status", "loginFail");
            
        } else {
            
            //RECUPERA CARRINHO NA SESSAO
            Carrinho cartSession = (Carrinho) request.getSession().getAttribute("shoppingCart");
            //SE CARRINHO SESSAO != NULO, RECUPERA DO BD
            
            if (cartSession != null) {
                Carrinho cartUser = cartData.findActiveCartByUser(user);
                List<RelProdutoCarrinho> relCartSession = cartSession.getRelProdutoCarrinhoList();
                
                //PASSANDO RELACAO DA SESSAO PARA LISTA AUXILIAR
                List<RelProdutoCarrinho> relCartAux = new ArrayList<RelProdutoCarrinho>();
                for(RelProdutoCarrinho relSession : relCartSession){
                    relCartAux.add(relSession);
                }
                
                if (cartUser != null) {
                    
                    //LISTA COM PRODUTOS DO CARRINHO DO BD
                    List<RelProdutoCarrinho> relCartUser = cartUser.getRelProdutoCarrinhoList();
                    
                    for (RelProdutoCarrinho relSession : relCartSession) {
                        for (RelProdutoCarrinho relUser : relCartUser) {
                            
                            //O ID É =
                            if (relSession.getProduto().getIdproduto() == relUser.getProduto().getIdproduto()) {
                                //REMOVE DA LSTA AUX A RELAÇÃO QUE JA FOI ENCOTRADA
                                relCartAux.remove(relSession);
                                // AUMENTA A QTD
                                relUser.setQuantidade(relUser.getQuantidade() + relSession.getQuantidade());
                                try {
                                    cartProduct.edit(relUser);
                                } catch (Exception ex) {
                                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    //INSERE COMO NOVO PRODUTO OS QUE NAO FORAM ENCONTRADOS MA RELACAO EXISTENTE
                    if(relCartAux.size() > 0){
                        for(RelProdutoCarrinho r: relCartAux){
                            r.setCarrinho(cartUser);
                            cartProduct.create(r);
                        }
                    }

                } else {
                    //CRIA NOVO CARRINHO
                    cartUser = new Carrinho();
                    cartUser.setUsuario(user);
                    cartUser.setStatus("Ativo");
                    cartData.create(cartUser);
                    //RECUPERA CARRINHO INSERIDO
                    Carrinho cartInserted = new CarrinhoJpaController(emf).findActiveCartByUser(user);
                    //INSERE RELACAO DA SESSAO
                    for (RelProdutoCarrinho rpc : relCartSession) {
                        rpc.setCarrinho(cartInserted);
                        cartProduct.create(rpc);
                    }
                }
            }
            request.setAttribute("login", true);
        }

        request.setAttribute("page", "home");
        rd.forward(request, response);

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
