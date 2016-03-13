package controller;

import dao.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        
        String name = request.getParameter("reg-name");
        String aftername = request.getParameter("reg-aftername");
        String email = request.getParameter("reg-email");
        String password = request.getParameter("reg-pass");
        
        Usuario user = new Usuario();
        user.setNome(name);
        user.setSobrenome(aftername);
        user.setEmail(email);
        user.setSenha(password);
        user.setCredito(BigDecimal.ZERO);
        user.setPermissao("Cliente");
        
        new UsuarioJpaController(emf).create(user);
        
        RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
        
        request.setAttribute("user", user);
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
