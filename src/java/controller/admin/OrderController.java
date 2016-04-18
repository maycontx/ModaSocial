package controller.admin;

import dao.VendaJpaController;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import model.Venda;

@WebServlet(name = "OrderController", urlPatterns = {"/order-manager"})
public class OrderController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          
            String stringStart = request.getParameter("start-date");
            String stringEnd = request.getParameter("end-date");
            
            Date startDate = null;
            Date endDate = null;
            
            if ( stringStart != null && !stringStart.equals("") )
                startDate = sdf.parse(stringStart);
            if ( stringEnd != null && !stringEnd.equals("") )
                endDate = sdf.parse(stringEnd);
            
            List<Venda> orders = new VendaJpaController(emf).findFilterOrder(startDate, endDate);
            
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");           
            request.setAttribute("orders", orders);
            request.setAttribute("page", "index");
            rd.forward(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
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
