<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $('#a a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
</script>
<div class="container">
    <div class="page-header">
        <h1>Endere�o de entrega</h1>
    </div>
    <div id="address" class="address">
        <jsp:include page="address/address.jsp" />
    </div>
    <div class="page-header">
        <h1>M�todos de pagamentos</h1>
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist" id="a">
              <li role="presentation" class="active"><a href="#creditcard" aria-controls="creditcard" role="tab" data-toggle="tab">Cart�o de Cr�dito</a></li>
              <li role="presentation"><a href="#transfer" aria-controls="transfer" role="tab" data-toggle="tab">Transfer�ncia Online</a></li>
              <li role="presentation"><a href="#billet" aria-controls="billet" role="tab" data-toggle="tab">Boleto Banc�rio</a></li>
              
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="creditcard">
                    <div class="page-header">
                        <h1>Cart�o de cr�dito</h1>
                    </div>
                    <form action="buy" method="POST">                        
                        <input type="hidden" name="payment-method" value="Credito">
                        <input type="hidden" name="address" value="0">
                        <input type="hidden" name="shipping-value" value="0">
                        <div class="row" style="padding:10px;">                           
                            <div class="col-lg-1">${cart.getFinalValue()}</div>
                            <div class="col-lg-2"><button type="submit" class="btn btn-success">Fechar pedido</button></div>
                        </div>
                    </form>
                </div>
                <div role="tabpanel" class="tab-pane" id="transfer">
                    <div class="page-header">
                        <h1>Transfer�ncia online</h1>
                    </div> 
                    <form action="buy" method="POST">
                        <input type="hidden" name="payment-method" value="Transferencia">
                        <input type="hidden" name="address" value="0">
                        <input type="hidden" name="shipping-value" value="0">
                        <div class="row" style="padding:10px;">
                            <div class="col-lg-1">${cart.getFinalValue()}</div>
                            <div class="col-lg-2"><button type="submit" class="btn btn-success">Fechar pedido</button></div>
                        </div>
                    </form>
                </div>
                <div role="tabpanel" class="tab-pane" id="billet">
                    <div class="page-header">
                        <h1>Boleto banc�rio</h1>
                    </div>                    
                    <form action="buy" method="POST">
                        <input type="hidden" name="payment-method" value="Boleto">
                        <input type="hidden" name="address" value="0">
                        <input type="hidden" name="shipping-value" value="0">
                        <div class="row" style="padding:10px;">                           
                            <div class="col-lg-1">${cart.getFinalValue()}</div>
                            <div class="col-lg-2"><button type="submit" class="btn btn-success">Fechar pedido</button></div>
                        </div>
                    </form>
                </div>              
            </div>

        </div>
    </div>
</div>