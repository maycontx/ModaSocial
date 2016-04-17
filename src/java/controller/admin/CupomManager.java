package controller.admin;

import dao.CaracteristicaJpaController;
import dao.CupomJpaController;
import dao.ProdutoJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
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
import model.Cupom;
import model.Produto;

@WebServlet(name = "CupomManager", urlPatterns = {"/cupom-manager"})
public class CupomManager extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        
        String action = request.getParameter("action");
        
        if ( action == null ){
            
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");        
            request.setAttribute("page", "cupom");
            request.setAttribute("allCupons", new CupomJpaController(emf).findCupomEntities());            
            rd.forward(request, response);        
           
        } else if ( action.equals("add-cupom") ){
            
            String code = request.getParameter("cupom-code");
            int discount = Integer.parseInt(request.getParameter("cupom-discount"));
            String status = request.getParameter("cupom-status");
                      
            Cupom cupom = new Cupom();
            cupom.setCodigo(code);
            cupom.setDesconto(discount);
            cupom.setStatus(status); 
            
            new CupomJpaController(emf).create(cupom);
            
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");        
            request.setAttribute("page", "cupom");
            request.setAttribute("allCupons", new CupomJpaController(emf).findCupomEntities());            
            rd.forward(request, response);
            
        } else if ( action.equals("remove-cupom") ){
            
            try {
                Integer cupom = Integer.parseInt(request.getParameter("cupom"));
                
                new CupomJpaController(emf).destroy(cupom);
                
                RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");        
                request.setAttribute("page", "cupom");
                request.setAttribute("allCupons", new CupomJpaController(emf).findCupomEntities());            
                rd.forward(request, response);
                
                rd.forward(request, response);            
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ( action.equals("edit-cupom") ){
            
            String idcupom = request.getParameter("id");
            
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");
            
            if ( idcupom == null ){
                
                Integer cupom = Integer.parseInt(request.getParameter("cupom"));  
                
                request.setAttribute("target", new CupomJpaController(emf).findCupom(cupom));               
            
            }else{
            
                try {
                    Integer id = Integer.parseInt(idcupom);
                    Cupom cupom = new CupomJpaController(emf).findCupom(id);
                    
                    String code = request.getParameter("cupom-code");
                    int discount = Integer.parseInt(request.getParameter("cupom-discount"));
                    String status = request.getParameter("cupom-status");
                    
                    cupom = new Cupom();
                    cupom.setIdcupom(id);
                    cupom.setCodigo(code);
                    cupom.setDesconto(discount);
                    cupom.setStatus(status); 
                    
                    new CupomJpaController(emf).edit(cupom);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }  
              
            request.setAttribute("page", "cupom");
            request.setAttribute("allCupons", new CupomJpaController(emf).findCupomEntities());            
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
