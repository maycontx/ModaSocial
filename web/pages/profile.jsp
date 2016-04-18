<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="profile-page">
        <div class="page-header">
            <h1>Minha conta</h1>
        </div>
        <div class="row">
            <div class="col-lg-3 side-menu">
                <div class="menu-title">Ajuda</div>
                <div class="menu-item">FAQ</div>
                <div class="menu-item">Opções de frete</div>
                <div class="menu-item">Métodos de pagamento</div>
                <div class="menu-title">Moda Social</div>
                <div class="menu-item">Contato</div>
                <div class="menu-item">Sobre nós</div>
                <div class="menu-item">Parcerias</div>
                <div class="menu-item">Projetos sociais</div>
            </div>
            <div class="col-lg-9 orders">
                <div class="profile-component-title">
                    <h3>Pedidos</h3>
                </div>
                <table class="table table-hover">
                    <tr>
                        <th>Data</th>
                        <th>Itens</th>
                        <th>Endereço</th>
                        <th>Entrega</th>
                        <th>Valor de entrega</th>
                        <th>Total</th>
                    </tr>
                    <c:if test="${user.getCarrinhoList().size() == 0}">
                        <tr><td colspan="6" class="no-order">Nenhum pedido foi realizado.</td></tr>
                    </c:if>
                    <c:forEach items="${user.getCarrinhoList()}" var="cart">
                        <c:forEach items="${cart.getVendaList()}" var="order">                           
                            <tr>                            
                                <td>${order.getFormatData()}</td>
                                <td><a href="#" data-toggle="modal" data-target="#cart-${order.idvenda}"><span class="glyphicon glyphicon-th-list"></span> Ver carrinho</a></td>
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
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-3 search-history">
                <div class="profile-component-title">
                    <h3>Histórico de buscas</h3>
                </div>
                <table class="table table-hover">
                    <tr>
                        <th>Data</th>
                        <th>Termo buscado</th>            
                    </tr>
                    <c:forEach items="${user.getBuscaList()}" var="search">
                        <tr>
                            <td>24/02/2016</td>
                            <td>${search.termo}</td>            
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col-lg-9 preferences">
                <div class="profile-component-title">
                    <form action="preference" method="GET">
                        <h3>Preferências</h3>                  
                        <div class="form-row" data-id="style" data-info="${user.preferencia.estilo}">
                            <label class="field-title">Estilo de preferência:</label>
                            <span><input type="checkbox" name="Despojado"> Despojado</span>
                            <span><input type="checkbox" name="Formal"> Formal</span>
                            <span><input type="checkbox" name="Casual"> Casual</span>                                      
                        </div>
                        <div class="form-row" data-id="collection" data-info="${user.preferencia.colecao}">
                            <label class="field-title">Coleções de preferência:</label>
                            <span><input type="checkbox" name="Verão"> Verão</span>
                            <span><input type="checkbox" name="Primavera"> Primavera</span>
                            <span><input type="checkbox" name="Inverno"> Inverno</span>
                            <span><input type="checkbox" name="Outono"> Outono</span>                        
                        </div>
                        <div class="form-row">
                            <label class="field-title">Preferência de notificações:</label>
                            <span><input type="checkbox" name="style1"> Novas promoções</span>
                            <span><input type="checkbox" name="style2"> Novos produtos</span>
                            <span><input type="checkbox" name="style3"> Novas coleções</span>
                            <span><input type="checkbox" name="style4"> Novas marcas</span>                    
                        </div>
                        <div class="form-row">
                            <label class="field-title">Receber e-mails:</label>
                            <span><input type="radio" name="pref-email" value="emailYes"> Todos</span>
                            <span><input type="radio" name="pref-email" value="emailSome"> Os mais importantes</span>
                            <span><input type="radio" name="pref-email" value="emailNo"> Nenhum</span>                                        
                        </div>
                        <div class="form-row">
                            <input type="submit" class="btn btn-primary" value="Enviar preferências">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>