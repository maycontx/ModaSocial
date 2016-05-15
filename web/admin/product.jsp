<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="product-header">
    <div class="modal fade" id="price-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" id="myModalLabel">Gerar novo preço</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-6">
                        <input type="radio" name="whatprice" data-id="new" value="new" checked> Primeira compra
                    </div>
                    <div class="col-lg-6">
                        <input type="radio" name="whatprice" data-id="update" value="update"> Atualização
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="col-lg-4">
                        Estoque antigo:
                    </div>
                    <div class="col-lg-8">
                        <input type="text" data-id="old-stock" <c:if test="${target != null}"> value="${target.estoque}" </c:if>>
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="col-lg-4">
                        Preço da compra:
                    </div>
                    <div class="col-lg-8">
                        <input type="text" data-id="old-price">
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="col-lg-4">
                        Novo estoque comprado:
                    </div>
                    <div class="col-lg-8">
                        <input type="text" data-id="new-stock">
                    </div>
                </div>
                <div class="row" style="margin-top: 10px;">
                    <div class="col-lg-4">
                        Preço da compra:
                    </div>
                    <div class="col-lg-8">
                        <input type="text" data-id="new-price">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
                <button type="button" data-id="save-update-price" class="btn btn-primary">Atualizar preço</button>
            </div>
          </div>
        </div>
      </div>
    
    
    
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
                        <option value="Calçados">Calçados</option>
                        <option value="Acessórios">Acessórios</option>
                    </select>
                </div>
                <div class="col-lg-3">
                    <label>Fornecedor</label>
                    <select class="form-control" name="product-owner">
                        <option value="Moda masculina">Fábrica Astolfo Dutra</option>
                        <option value="Moda feminina">Fábrica Bom Sucesso</option>
                        <option value="Calçados">Rua da Cortesia</option>
                        <option value="Acessórios">Fábrica Juiz de Fora</option>
                    </select>
                </div>
            </div>
            <div class="form-row">           
                <div class="col-lg-2">
                    <label>Status</label>
                    <select class="form-control" name="product-status">
                        <option value="Ativo">Ativo</option>
                        <option value="Inativo">Inativo</option>                   
                    </select>
                </div>
                <div class="col-lg-2">
                    <label>Coleção</label>
                    <select class="form-control" name="product-collection">
                        <option value="Verão">Verão</option>
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
                <div class="col-lg-1">
                    <label>Estoque</label>
                    <input class="form-control" type="text" name="product-stock" placeholder="Estq" <c:if test="${target != null}"> data-content="${target.estoque}" value="${target.estoque}" </c:if> <c:if test="${target == null}"> data-content="0" </c:if> >
                </div>
                <div class="col-lg-2">
                    <label>Preço</label>
                    <input class="form-control" type="text" name="product-price" placeholder="Preço" <c:if test="${target != null}"> value="${target.preco}" </c:if> >
                </div>
                <div class="col-lg-2">
                    <label>Gerar</label>
                    <button type="button" data-id="generate-price" class="btn btn-success" data-toggle="modal" data-target="#price-modal">Gerar preço</button>
                </div>
            </div>
            <div class="form-row">
                <div class="col-lg-12">
                    <label>Descrição</label>
                    <textarea class="form-control" rows="5" name="product-description" placeholder="Descrição..."><c:if test="${target != null}">${target.descricao}</c:if></textarea>
                </div>
            </div>
        <c:if test="${target == null}">
            <div class="form-row">
                <div class="col-lg-3" data-id="feature" data-info="first">
                    <div class="col-lg-10">
                        <label>Característica</label>
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
                        <label>Característica</label>
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
                            <label>Característica</label>
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
                    <a href="product-manager"><input type="button" class="btn btn-default" value="Cancelar edição"></a>
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
            <th>Preço</th>
            <th>Status</th>
            <th>Ações</th>
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