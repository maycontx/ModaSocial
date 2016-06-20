/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dao.CupomJpaController;
import dao.EntradaJpaController;
import dao.SaidaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import model.Entrada;
import model.Saida;

/**
 *
 * @author Tiago
 */
@WebServlet(name = "CaixaManager", urlPatterns = {"/caixa-manager"})
public class CaixaManager extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
        
        String action = request.getParameter("action");
        
        if ( action == null ){
            
            List<Entrada> entradas = new EntradaJpaController(emf).findEntradaEntities();
            List<Saida> saidas = new SaidaJpaController(emf).findSaidaEntities();
                       
            RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");
            request.setAttribute("entradas", entradas);
            request.setAttribute("saidas", saidas);
            request.setAttribute("page", "caixa");
            rd.forward(request, response);       
           
        } else if ( action.equals("add-fluxo") ){
            
            try {
                Entrada entrada = new Entrada();
                Saida saida = new Saida();
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                Date dateStart = sdf.parse(request.getParameter("fluxo-dateStart"));
                Date dateEnd = sdf.parse(request.getParameter("fluxo-dateEnd"));
                
                entrada.setDataInicial(dateStart);
                entrada.setDataFinal(dateEnd);
                saida.setDataInicial(dateStart);
                saida.setDataFinal(dateEnd);
                
                entrada.setSaldo(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-cash"))));
                entrada.setVenda(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-sells"))));
                entrada.setOutros(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-others"))));
                
                saida.setAguaELuz(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-little-tax"))));
                saida.setBanco(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-bank-tax"))));
                saida.setCombustivel(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-gasoline-tax"))));
                saida.setEquipamentos(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-equipment-tax"))));
                saida.setFornecedor(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-seller-tax"))));
                saida.setImpostos(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-tax"))));
                saida.setMateriaisConsumo(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-materials-tax"))));
                saida.setTelefone(BigDecimal.valueOf(Double.parseDouble(request.getParameter("fluxo-phone-tax"))));
                
                new EntradaJpaController(emf).create(entrada);
                new SaidaJpaController(emf).create(saida);
                
                List<Entrada> entradas = new EntradaJpaController(emf).findEntradaEntities();
                List<Saida> saidas = new SaidaJpaController(emf).findSaidaEntities();

                RequestDispatcher rd = request.getRequestDispatcher("admin-template.jsp");
                request.setAttribute("entradas", entradas);
                request.setAttribute("saidas", saidas);
                request.setAttribute("page", "caixa");   
                rd.forward(request, response);
            } catch (ParseException ex) {
                Logger.getLogger(CaixaManager.class.getName()).log(Level.SEVERE, null, ex);
            }
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
