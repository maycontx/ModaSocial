<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="product-header">
    <div class="page-header">
        <h1>Vendas (${orders.size()})<small>Gerenciamento</small></h1>
    </div>
</div>
<div class="product-body">
    <table class="table table-hover">
        <tr>
            <th>Data</th>
            <th>Cliente</th>
            <th>Itens</th>
            <th>Status</th>
            <th>Endereço</th>
            <th>Entrega</th>
            <th>Valor de entrega</th>
            <th>Total</th>
        </tr>
        <c:if test="${orders.size() == 0}">
            <tr><td colspan="6" class="no-order">Nenhum pedido foi realizado.</td></tr>
        </c:if>

        <c:forEach items="${orders}" var="order">
            <tr>
                <td>24/02/2016</td>
                <td>${order.carrinho.usuario.nome}</td>
                <td><a href="#"><span class="glyphicon glyphicon-th-list"></span> Ver carrinho</a></td>
                <c:forEach items="${order.getPagamentoList()}" var="pay">
                    <td>${pay.status}</td>
                </c:forEach>
                <td><a href="#">${order.endereco.bairro}</a></td>
                <td>${order.freteTipo}</td>
                <td>${order.freteValor}</td>
                <c:forEach items="${order.getPagamentoList()}" var="pay">
                    <td>${pay.valor}</td>
                </c:forEach>
            </tr>
        </c:forEach>

    </table>    
</div>