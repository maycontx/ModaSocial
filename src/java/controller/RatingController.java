package controller;

import dao.AvaliacaoJpaController;
import dao.ProdutoJpaController;
import helper.Session;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Avaliacao;
import model.Produto;
import model.Usuario;

@WebServlet(name = "RatingController", urlPatterns = {"/rating"})
public class RatingController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Integer idProduct = Integer.parseInt(request.getParameter("rating-product"));
        Integer rate = Integer.parseInt(request.getParameter("rating-rate"));
        String comment = request.getParameter("rating-comment").trim();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        
        Produto product = new ProdutoJpaController(emf).findProduto(idProduct);
        
        Usuario user = (Usuario) request.getSession().getAttribute("user");
        
        if ( rate < 1 || rate > 5 ){
            RequestDispatcher rd = request.getRequestDispatcher("template.jsp");        
            request.setAttribute("page", product);
            request.setAttribute("page", "product");
            rd.forward(request, response);
        }
        
        if ( comment.equals("") ){
            comment = user.getNome() + "deu nota " + rate + " à este produto!";  
        }
        
        Avaliacao rating = new Avaliacao();
        rating.setUsuario(user);
        rating.setProduto(product);
        rating.setNota(rate);
        rating.setComentario(comment);
        
        new AvaliacaoJpaController(emf).create(rating);
        
        product = new ProdutoJpaController(emf).findProduto(idProduct);
        
        RequestDispatcher rd = request.getRequestDispatcher("template.jsp");        
        request.setAttribute("page", "product");
        request.setAttribute("product", product);
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
