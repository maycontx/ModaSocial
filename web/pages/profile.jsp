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
                                <td>24/02/2016</td>
                                <td><a href="#"><span class="glyphicon glyphicon-th-list"></span> Ver carrinho</a></td>
                                <td><a href="#">${order.endereco.bairro}</a></td>
                                <td>${order.freteTipo}</td>
                                <td>${order.freteValor}</td>
                                <td>${order.valor}</td>
                            </tr>
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
                    <h3>Preferências</h3>
                    <div class="form-row">
                        <label class="field-title">Escolher produtos:</label>
                        <span><input type="checkbox" name="product1"> Por estilos</span>
                        <span><input type="checkbox" name="product2"> Por preços</span>
                        <span><input type="checkbox" name="product3"> Por coleções</span>
                        <span><input type="checkbox" name="product4"> Por marcas  </span>                 
                    </div>
                    <div class="form-row">
                        <label class="field-title">Estilo de preferência:</label>
                        <span><input type="checkbox" name="style1"> Despojado</span>
                        <span><input type="checkbox" name="style2"> Formal</span>
                        <span><input type="checkbox" name="style3"> Casual</span>
                        <span><input type="checkbox" name="style4"> Todos</span>                 
                    </div>
                    <div class="form-row">
                        <label class="field-title">Coleções de preferência:</label>
                        <span><input type="checkbox" name="style1"> Verão</span>
                        <span><input type="checkbox" name="style2"> Primavera</span>
                        <span><input type="checkbox" name="style3"> Inverno</span>
                        <span><input type="checkbox" name="style4"> Outono</span>
                        <span><input type="checkbox" name="style4"> Todas</span>
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
                </div>
            </div>
        </div>
    </div>
</div>