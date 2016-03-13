<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="product-header">
    <div class="page-header">
        <h1>Produtos (${allProducts.size()}) <small>Gerenciamento</small></h1>
    </div>
    <h3><span class="glyphicon glyphicon-plus"></span> Novo produto</h3>
    <form method="POST" action="product-manager">        
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
                <label>Preço</label>
                <input class="form-control" type="text" name="product-price" placeholder="Preço" <c:if test="${target != null}"> value="${target.preco}" </c:if> >
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
        </div>
        <div class="form-row">
            <div class="col-lg-12">
                <label>Descrição</label>
                <textarea class="form-control" rows="5" name="product-description" placeholder="Descrição..."><c:if test="${target != null}">${target.descricao}</c:if></textarea>
            </div>
        </div>
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