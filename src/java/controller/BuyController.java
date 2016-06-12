package controller;

import dao.CarrinhoJpaController;
import dao.CartaoJpaController;
import dao.EndereçoJpaController;
import dao.PagamentoJpaController;
import dao.VendaJpaController;
import dao.exceptions.NonexistentEntityException;
import helper.WebServiceCart;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
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
import model.Cartao;
import model.Pagamento;
import model.Usuario;
import model.Venda;

@WebServlet(name = "BuyController", urlPatterns = {"/buy"})
public class BuyController extends HttpServlet {

    Cartao card;
    Carrinho cart;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ModaSocialPU");
            CarrinhoJpaController cartData = new CarrinhoJpaController(emf);

            Usuario user = (Usuario) request.getSession().getAttribute("user");
            cart = cartData.findActiveCartByUser(user);

            int address = Integer.parseInt(request.getParameter("address"));

            if (address == 0) {
                RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
                request.setAttribute("cart", cart);
                request.setAttribute("page", "address");
                rd.forward(request, response);
            } else {
                double shipping = Double.parseDouble(request.getParameter("shipping-value"));
                String paymentMethod = request.getParameter("payment-method");

                double value = cart.getFinalValue() + shipping;

                Venda order = new Venda();
                order.setCarrinho(cart);
                order.setEndereco(new EndereçoJpaController(emf).findEndereço(address));
                order.setFreteTipo("Transportadora");
                order.setFreteValor(BigDecimal.valueOf(shipping));
                order.setData(new Date());

                order = new VendaJpaController(emf).create(order);

                Pagamento payment = new Pagamento();
                payment.setMetodo(paymentMethod);
                payment.setStatus("Aguardando confirmação");
                payment.setUsuario(user);
                payment.setValor(BigDecimal.valueOf(value));
                payment.setVenda(order);

                new PagamentoJpaController(emf).create(payment);

                cart = cartData.findActiveCartByUser(user);
                cart.setStatus("fechado");
                cartData.edit(cart);

                Carrinho newCart = new Carrinho();
                newCart.setStatus("ativo");
                newCart.setUsuario(user);

                cartData.create(newCart);

                card = new Cartao();
                card.setTipo(request.getParameter("tipoCart"));
                card.setNumero(request.getParameter("numCart"));
                card.setSeguranca(Integer.parseInt(request.getParameter("codCart")));
                card.setNome(request.getParameter("nameCart"));
                card.setVencimento(request.getParameter("yearCart") + request.getParameter("monthCart"));
                card.setParcelas(Integer.parseInt(request.getParameter("numberInstallments")));
                card.setCnpjEmpresa("12345678");
                card.setNomeEmpresa("Modal Social");
                //falta setar o id pagamento no cartao

                String result = WebServiceCart.checkCard(card, payment);
                if (result.equals("AUTORIZADO")) {

                    new CartaoJpaController(emf).create(card);
                    //falta mudar status do pagamento

                    RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
                    request.setAttribute("page", "order");
                    rd.forward(request, response);
                }
            }

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BuyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            RequestDispatcher rd = request.getRequestDispatcher("template.jsp");
            request.setAttribute("cart", cart);
            request.setAttribute("page", "address");
            request.setAttribute("card", card);
            request.setAttribute("MessageErro", "Cartão Recusado! Entre em contato com sua administradora.");
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
