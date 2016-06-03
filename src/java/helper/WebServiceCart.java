/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import model.Cartao;
import model.Pagamento;
import ws.clientjax.rpc.card.Card;
import ws.clientjax.rpc.card.TDadosCartao;

/**
 *
 * @author Tiago
 */
public class WebServiceCart {
    
    public static String checkCard(Cartao cartClient, Pagamento payment){
        
        try {
            URL url = new URL("http://tadeuclasse.esy.es/WsCartao/Server.php?wsdl");
            
            Card card = new Card(url, new QName("urn:Card", "Card"));
            
            TDadosCartao tdc = new TDadosCartao(cartClient.getNumero(), 
                    cartClient.getSeguranca(),
                    cartClient.getNome(),
                    cartClient.getVencimento(),
                    Double.parseDouble(payment.getValor().toString()),
                    cartClient.getParcelas(),
                    cartClient.getNomeEmpresa(), 
                    Integer.parseInt(cartClient.getCnpjEmpresa()));
            
            return card.getCardPort().validarCartao(tdc);
            
        } catch (MalformedURLException ex) {
            return "REPROVADO";
        }
    }
    
}
