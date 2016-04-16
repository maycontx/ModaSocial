package controller;

import dao.PreferenciaJpaController;
import dao.UsuarioJpaController;
import dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
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
import model.Preferencia;
import model.Usuario;

@WebServlet(name = "PreferenceController", urlPatterns = {"/preference"})
public class PreferenceController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
            
            Usuario user = (Usuario) request.getSession().getAttribute("user");
            Preferencia preference = null;
            if ( user.getPreferencia() == null )
                preference = new Preferencia();
            else
                preference = user.getPreferencia();
            
            
            String despojado = request.getParameter("Despojado");
            String casual = request.getParameter("Casual");
            String formal = request.getParameter("Formal");
            List<String> styles = new ArrayList<String>();            
          
            if ( despojado != null && despojado.equals("on") )
                styles.add("Despojado");
            
            if ( casual != null && casual.equals("on") )
                styles.add("Casual");
            
            if ( formal != null && formal.equals("on") )
                styles.add("Formal");
            
            StringJoiner sj = new StringJoiner(",");
            if ( styles.size() > 0 )                
                for(String s : styles) sj.add(s);
            
            preference.setEstilo(String.valueOf(sj));
            
            String verao = request.getParameter("Verão");
            String outono = request.getParameter("Outono");
            String inverno = request.getParameter("Inverno");
            String primavera = request.getParameter("Primavera");
            List<String> collections = new ArrayList<String>();
            
            if ( verao != null && verao.equals("on") )
                collections.add("Verão");
            
            if ( outono != null && outono.equals("on") )
                collections.add("Outono");
            
            if ( inverno != null && inverno.equals("on") )
                collections.add("Inverno");
            
            if ( primavera != null && primavera.equals("on") )
                collections.add("Primavera");
            
            sj = new StringJoiner(",");
            if ( collections.size() > 0 )                
                for(String s : collections) sj.add(s);
            
            preference.setColecao(String.valueOf(sj));
            
            if ( user.getPreferencia() == null )
                preference = new PreferenciaJpaController(emf).create(preference);
            else
                new PreferenciaJpaController(emf).edit(preference);
            
            user.setPreferencia(preference);
            new UsuarioJpaController(emf).edit(user);
            
            RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
            request.setAttribute("page", "profile");   
            rd.forward(request, response);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PreferenceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PreferenceController.class.getName()).log(Level.SEVERE, null, ex);
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
