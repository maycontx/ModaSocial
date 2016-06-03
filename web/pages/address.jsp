<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $('#a a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
</script>
<div class="container">
    <div class="page-header">
        <h1>Endereço de entrega</h1>
    </div>
    <div id="address" class="address">
        <jsp:include page="address/address.jsp" />
    </div>
    <div class="page-header">
        <h1>Métodos de pagamentos</h1>
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist" id="a">
                <li role="presentation" class="active"><a href="#creditcard" aria-controls="creditcard" role="tab" data-toggle="tab">Cartão de Crédito</a></li>
                <li role="presentation"><a href="#transfer" aria-controls="transfer" role="tab" data-toggle="tab">Transferência Online</a></li>
                <li role="presentation"><a href="#billet" aria-controls="billet" role="tab" data-toggle="tab">Boleto Bancário</a></li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="creditcard">
                    <div class="page-header">
                        <h1>Cartão de crédito</h1>
                    </div>
                    <form action="buy" method="GET">                        
                        <input type="hidden" name="payment-method" value="Credito">
                        <input type="hidden" name="address" value="0">
                        <input type="hidden" name="shipping-value" value="0">
                        
                        <img src="img/visa.png" style="width: 80px; margin-right: 20px">
                        <img src="img/master.png" style="width: 80px; "><br>
                        <input type="radio" name="tipoCart" value="visa" style="margin-left: 30px;" checked/>
                        <input type="radio" name="tipoCart" value="master" style="margin-left: 90px;" /><br><br>

                        Número Cartão<br>
                        <input type="text" id="numCart" name="numCart" required><br><br>

                        Código de Segurança<br>
                        <input type="text" id="codCart" name="codCart" style="width: 50px;" required><br><br>

                        Nome Titular<br>
                        <input type="text" id="nameCart" name="nameCart" required><br><br>

                        Validade<br>
                        <input type="text" id="yearCart" name="yearCart" style="width: 100px;" required>
                        /<input type="text" id="monthCart" name="monthCart" style="width: 50px;" required><br><br>
                        
                        Numero de Parcelas<br>
                        <select name="numberInstallments">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select><br><br>


                        Valor: R$ ${cart.getFinalValue()}<br/>
                        <button type="submit" class="btn btn-success" >Fechar pedido</button><br><br>
                        <c:if test="${MessageErro != null}">
                            <div class="alert alert-danger"><h4>${MessageErro}</h4></div>
                        </c:if>
                        
                    </form>
                </div>
                <div role="tabpanel" class="tab-pane" id="transfer">
                    <div class="page-header">
                        <h1>Transferência online</h1>
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
                        <h1>Boleto bancário</h1>
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
