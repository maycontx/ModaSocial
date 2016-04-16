<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="product-header">
    <div class="page-header">
        <h1>Produtos (${allProducts.size()}) <small>Gerenciamento</small></h1>
    </div>
    <h3><span class="glyphicon glyphicon-plus"></span> Novo produto</h3>
    <form name="product-form" method="POST" action="product-manager">        
        <input type="hidden" name="feature-amount" value="0">
        <div class="form-row">
            <div class="col-lg-3">
                <label>Nome</label>
                <input class="form-control" type="text" name="product-name" placeholder="Nome" <c:if test="${target != null}"> value="${target.nome}" </c:if> >
            </div>
            <div class="col-lg-3">
                <label>Marca</label>
                <input class="form-control" type="text" name="product-brand" placeholder="Marca" <c:if test="${target != null}"> value="${target.marca}" </c:if> >
            </div>
            <div class="col-lg-3">
                <label>Categoria</label>
                <select class="form-control" name="product-category">
                    <option value="Moda masculina">Moda masculina</option>
                    <option value="Moda feminina">Moda feminina</option>
                    <option value="Cal�ados">Cal�ados</option>
                    <option value="Acess�rios">Acess�rios</option>
                </select>
            </div>
            <div class="col-lg-3">
                <label>Fornecedor</label>
                <select class="form-control" name="product-owner">
                    <option value="Moda masculina">F�brica Astolfo Dutra</option>
                    <option value="Moda feminina">F�brica Bom Sucesso</option>
                    <option value="Cal�ados">Rua da Cortesia</option>
                    <option value="Acess�rios">F�brica Juiz de Fora</option>
                </select>
            </div>
        </div>
        <div class="form-row">
            <div class="col-lg-2">
                <label>Pre�o</label>
                <input class="form-control" type="text" name="product-price" placeholder="Pre�o" <c:if test="${target != null}"> value="${target.preco}" </c:if> >
            </div>
            <div class="col-lg-1">
                <label>Estoque</label>
                <input class="form-control" type="text" name="product-stock" placeholder="Estq" <c:if test="${target != null}"> value="${target.estoque}" </c:if> >
            </div>
            <div class="col-lg-2">
                <label>Status</label>
                <select class="form-control" name="product-status">
                    <option value="Ativo">Ativo</option>
                    <option value="Inativo">Inativo</option>                   
                </select>
            </div>
            <div class="col-lg-2">
                <label>Cole��o</label>
                <select class="form-control" name="product-collection">
                    <option value="Ver�o">Ver�o</option>
                    <option value="Primavera">Primavera</option>
                    <option value="Inverno">Inverno</option>   
                    <option value="Outono">Outono</option>   
                </select>
            </div>
            <div class="col-lg-2">
                <label>Estilo</label>
                <select class="form-control" name="product-style">
                    <option value="Formal">Formal</option>
                    <option value="Despojado">Despojado</option>
                    <option value="Casual">Casual</option> 
                </select>
            </div>  
        </div>
        <div class="form-row">
            <div class="col-lg-12">
                <label>Descri��o</label>
                <textarea class="form-control" rows="5" name="product-description" placeholder="Descri��o..."><c:if test="${target != null}">${target.descricao}</c:if></textarea>
            </div>
        </div>
        <c:if test="${target == null}">
            <div class="form-row">
                <div class="col-lg-3" data-id="feature" data-info="first">
                    <div class="col-lg-10">
                        <label>Caracter�stica</label>
                        <input name="feature-field" class="form-control" placeholder="Campo">                
                        <input name="feature-value" class="form-control" placeholder="Valor">
                    </div>
                    <div class="col-lg-2">
                        <button type="button" data-id="more-feature"><span class="glyphicon glyphicon-plus"></span></button>
                    </div>               
                </div>
            </div> 
        </c:if>
        <c:if test="${target != null}">
              <div class="form-row">
                  <div class="col-lg-3" data-id="feature" data-info="first">
                        <div class="col-lg-10">
                            <label>Caracter�stica</label>
                            <input name="feature-field" class="form-control" placeholder="Campo">                
                            <input name="feature-value" class="form-control" placeholder="Valor">
                        </div>
                        <div class="col-lg-2">
                            <button type="button" data-id="more-feature"><span class="glyphicon glyphicon-plus"></span></button>
                        </div>               
                    </div>
                  <c:forEach items="${target.getCaracteristicaList()}" var="feature">
                    <div class="col-lg-3" data-id="feature">
                        <div class="col-lg-10">
                            <label>Caracter�stica</label>
                            <input name="feature-field" class="form-control" placeholder="Campo" value="${feature.campo}">                
                            <input name="feature-value" class="form-control" placeholder="Valor" value="${feature.valor}">
                        </div>
                        <div class="col-lg-2">
                            <button type="button" data-id="remove-feature" style="background-color: rgb(165, 78, 78)"><span class="glyphicon glyphicon-remove"></span></button>
                        </div>               
                    </div>
                </c:forEach>                  
            </div>        
        </c:if>
        <div class="form-row">
            <div class="col-lg-12">
                <c:if test="${target == null}"> 
                    <input type="hidden" name="action" value="add-product">
                    <input type="submit" class="btn btn-primary" value="Adicionar produto">
                </c:if>
                <c:if test="${target != null}"> 
                    <input type="hidden" name="action" value="edit-product">
                    <input type="hidden" name="id" value="${target.idproduto}">
                    <input type="submit" class="btn btn-primary" value="Editar produto">
                    <a href="product-manager"><input type="button" class="btn btn-default" value="Cancelar edi��o"></a>
                </c:if>
            </div>
        </div>
    </form>
</div>
<div class="product-body">
    <table class="table table-hover">
        <tr>
            <th>Nome</th>
            <th>Qtd. Estoque</th>
            <th>Fornecedor</th>
            <th>Pre�o</th>
            <th>Status</th>
            <th>A��es</th>
        </tr>
        <c:forEach items="${allProducts}" var="product">
            <tr>
                <td>${product.nome}</td>
                <td>${product.estoque}</td>
                <td>${product.fornecedor.nome}</td>
                <td>${product.preco}</td>
                <td>${product.status}</td>
                <td>
                    <a href="product-manager?action=edit-product&&product=${product.idproduto}"><span class="glyphicon glyphicon-edit"></span></a>
                    <span data-id="remove-product" data-info="${product.idproduto}" class="glyphicon glyphicon-trash"></span>
                </td>
            </tr>
        </c:forEach>
    </table>    
</div>