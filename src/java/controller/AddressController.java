package controller;

import dao.EndereçoJpaController;
import dao.UsuarioJpaController;
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
import model.Endereço;
import model.Usuario;

@WebServlet(name = "AddressController", urlPatterns = {"/step1"})
public class AddressController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
      
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        
        Usuario user = (Usuario) request.getSession().getAttribute("user");
        if (user != null){
            user = new UsuarioJpaController(emf).findUsuario(user.getIdusuario());
            request.getSession().setAttribute("user", user);
        }
            
        String refresh = request.getParameter("refresh");
        String status = request.getParameter("status");
        String cep2 = request.getParameter("newcep");
        
        if ( status != null && status.equals("new-address") ){
        
            String state = request.getParameter("state");
            String city = request.getParameter("city");
            String local = request.getParameter("local");
            String cep = request.getParameter("cep");
            String local2 = request.getParameter("local2");
            String street = request.getParameter("street");
            String phone = request.getParameter("phone");
            String complement = request.getParameter("complement");
            
            Endereço address = new Endereço();
            address.setPais("Brasil");
            address.setCep(cep);
            address.setEstado(state);
            address.setCidade(city);
            address.setBairro(local);
            address.setRua(street);
            address.setLugradouro(local2);
            address.setComplemento(complement);
            address.setTelefone(phone);
            address.setUsuario(user);
            
            new EndereçoJpaController(emf).create(address);           
            
            
        } if ( status != null && status.equals("new-cep") ){
            // SEU CODIGO AQUI TIAGO
        }else if (refresh != null && refresh.equals("true")){  
            RequestDispatcher rd = request.getRequestDispatcher("pages/address/address.jsp");
            request.setAttribute("user", user);
            // SEU OBJETO AQUI
            //request.setAttribute("address", endereco);
            rd.forward(request, response);
        }else{
            RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
            request.setAttribute("page", "address");
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
