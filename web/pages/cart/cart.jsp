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
                <div class="col-lg-5">
                    <input type="text" class="form-control" name="CEP"> 
                </div>
                <div class="col-lg-4">
                    <button type="button" class="btn btn-default">Calcular</button> 
                </div>

            </div>
            <div class="value-final">
                Valor total: <span>202020</span>
            </div>                    
        </div>
        <div class="cart-btn">
            <a href="step1"><button class="btn btn-success buy"><i class="fa fa-cart-arrow-down"></i> Finalizar compra</button></a>
        </div>
    </c:if>
    <c:if test="${cart.getRelProdutoCarrinhoList().size() <= 0}">
        <div class="no-item">Nenhum item ;(</div>
    </c:if>
</c:if>
<c:if test="${cart == null}">
    <div class="no-item">Ocorreu um erro =(</div>
</c:if>