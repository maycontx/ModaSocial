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
            <tr><td colspan="8" class="no-order">Nenhum pedido foi realizado.</td></tr>
        </c:if>

        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.getFormatData()}</td>
                <td>${order.carrinho.usuario.nome}</td>
                <td><a href="#" data-toggle="modal" data-target="#cart-${order.idvenda}"><span class="glyphicon glyphicon-th-list"></span> Ver carrinho</a></td>
                <c:forEach items="${order.getPagamentoList()}" var="pay">
                    <td>${pay.status}</td>
                </c:forEach>
                <td><a href="#" data-toggle="modal" data-target="#address-${order.idvenda}">${order.endereco.bairro}</a></td>
                <td>${order.freteTipo}</td>
                <td>${order.freteValor}</td>
                <c:forEach items="${order.getPagamentoList()}" var="pay">
                    <td>${pay.valor}</td>
                </c:forEach>
            </tr>
            <div class="modal fade" tabindex="-1" role="dialog" id="cart-${order.idvenda}">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Itens</h4>
                        </div>
                        <div class="modal-body">                                       
                            <div class="row" style="padding:5px;">
                                <div class="col-lg-4"><strong>Produto</strong></div>
                                <div class="col-lg-1"><strong>Qtd.</strong></div>
                                <div class="col-lg-1"><strong>Preço</strong></div>
                            </div>
                            <c:forEach items="${order.carrinho.getRelProdutoCarrinhoList()}" var="rel">
                                <div class="row" style="padding:5px;">
                                    <div class="col-lg-4">${rel.produto.nome}</div>
                                    <div class="col-lg-1">${rel.quantidade}</div>
                                    <div class="col-lg-1">${rel.produto.preco}</div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>                                      
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <div class="modal fade" tabindex="-1" role="dialog" id="address-${order.idvenda}">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Endereço: ${order.endereco.nome}</h4>
                        </div>
                        <div class="modal-body">                                       
                            <div class="row" style="padding:5px;">
                                <div class="col-lg-2"><strong>Estado</strong></div>
                                <div class="col-lg-2"><strong>Cidade</strong></div>
                                <div class="col-lg-2"><strong>Bairro</strong></div>
                                <div class="col-lg-2"><strong>Rua</strong></div>
                                <div class="col-lg-2"><strong>Número</strong></div>
                                <div class="col-lg-2"><strong>Telefone</strong></div>
                            </div>
                            <div class="row" style="padding:5px;">
                                <div class="col-lg-2">${order.endereco.estado}</div>
                                <div class="col-lg-2">${order.endereco.cidade}</div>
                                <div class="col-lg-2">${order.endereco.bairro}</div>
                                <div class="col-lg-2">${order.endereco.rua}</div>
                                <div class="col-lg-2">${order.endereco.lugradouro}</div>
                                <div class="col-lg-2">${order.endereco.telefone}</div>
                            </div>                          
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>                                      
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </c:forEach>

    </table>
    <h1>Filtro</h1>
    <form method="POST" action="order-manager"> 
        <div class="form-row">
            <div class="col-lg-3">
                <label>Data incial</label>
                <input type="date" name="start-date" class="form-control">
            </div>
            <div class="col-lg-3">
                <label>Data final</label>
                <input type="date" name="end-date" class="form-control">
            </div>
            <div class="col-lg-2">                
                <label>Confirmar</label>
                <input style="width:100%;" type="submit" class="btn btn-primary" value="Filtrar">                    
            </div>
        </div>        
    </form>
</div>