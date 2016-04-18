<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${cart != null}">
    <c:if test="${cart.getRelProdutoCarrinhoList().size() > 0}">
        <table class="table table-hover">
            <tr>
                <th></th>
                <th>Produto</th>
                <th>Preço</th>
                <th>Quantidade</th>
                <th></th>
            </tr>
            <c:forEach items="${cart.getRelProdutoCarrinhoList()}" var="rel">
                <tr>
                    <td>
                        <div class="product-img-main">
                            <img src="img/crew-tshirt-resized-680x680.png" width="80">
                        </div>
                    </td>
                    <td>${rel.produto.nome}</td>
                    <td>${rel.produto.preco}</td>
                    <td><input type="number" data-id="amount-cart" name="item-${rel.produto.idproduto}" value="${rel.quantidade}"></td>
                    <td><div><span class="glyphicon glyphicon-remove" data-id="remove-amount-cart" name="remove-${rel.produto.idproduto}"></span></div></td>
                </tr>
            </c:forEach>
        </table>
        <div class="cart-info">
            <div class="value-total">
                Valor da compra: <span>${cart.getTotalValue()}</span>
            </div>
            <div class="delivery-postal">
                <div class="col-lg-2">
                    CEP: 
                </div>
                <div class="col-lg-6">
                    <input type="text" class="form-control" name="CEP"> 
                </div>
                <div class="col-lg-4">
                    <button type="button" class="btn btn-default">Calcular</button> 
                </div>  
            </div>
            <div class="delivery-postal">
                <c:if test="${cart.cupom != null}">
                    <div class="col-lg-6">
                        Cupom: ${cart.cupom.codigo}
                    </div>
                    <div class="col-lg-6">
                        Desconto: <strong>${cart.cupom.desconto}%</strong>
                    </div>
                </c:if>
                <c:if test="${cart.cupom == null}">
                    <form method="POST" action="cupom">
                        <div class="col-lg-2">
                            Cupom:
                        </div>
                        <div class="col-lg-8">
                            <input class="form-control" name="cupom-code" type="text">
                        </div> 
                        <div class="col-lg-1">
                            <button type="submit" class="btn btn-primary">Usar</button> 
                        </div>  
                    </form>
                </c:if>                
            </div>           
            <div class="value-final">
                Valor total: <span>${cart.getFinalValue()}</span>
            </div>                    
        </div>
        <div class="cart-btn">
            <c:if test="${user != null}"><a href="step1"><button class="btn btn-success buy"><i class="fa fa-cart-arrow-down"></i> Finalizar compra</button></a></c:if>
            <c:if test="${user == null}"><div class="menu-item" data-id="login-open"><button class="btn btn-success">Entre</button></div></c:if>
        </div>
    </c:if>
    <c:if test="${cart.getRelProdutoCarrinhoList().size() <= 0}">
        <div class="no-item">Nenhum item ;(</div>
    </c:if>
</c:if>
<c:if test="${cart == null}">
    <div class="no-item">Ocorreu um erro =(</div>
</c:if>