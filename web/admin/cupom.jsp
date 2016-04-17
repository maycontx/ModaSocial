<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="product-header">
    <div class="page-header">
        <h1>Cupons (${allCupons.size()}) <small>Gerenciamento</small></h1>
        <h3><span class="glyphicon glyphicon-plus"></span> Novo cupom</h3>
        <form name="cupom-form" method="POST" action="cupom-manager"> 
            <div class="form-row">
                <div class="col-lg-3">
                    <label>Código</label>
                    <input class="form-control" type="text" name="cupom-code" data-reactid="cupom-generate" placeholder="Código" <c:if test="${target != null}"> value="${target.codigo}" </c:if> >
                </div>
                <div class="col-lg-1">
                    <label>Gerar código</label>
                    <button type="button" class="btn btn-primary" data-id="cupom-generate" >
                        Gerar
                    </button>
                </div>
                <div class="col-lg-1 col-lg-offset-1">
                    <label>Desconto (%)</label>
                    <input class="form-control" type="text" name="cupom-discount" placeholder="%" <c:if test="${target != null}"> value="${target.desconto}" </c:if> >
                </div>
                <div class="col-lg-2">
                    <label>Status</label>
                    <select class="form-control" name="cupom-status">
                        <option value="Ativo">Ativo</option>
                        <option value="Inativo">Inativo</option>                        
                    </select>
                </div>               
            </div>
            <div class="form-row">
                <div class="col-lg-12">
                    <c:if test="${target == null}"> 
                        <input type="hidden" name="action" value="add-cupom">
                        <input type="submit" class="btn btn-primary" value="Adicionar Cupom">
                    </c:if>
                    <c:if test="${target != null}"> 
                        <input type="hidden" name="action" value="edit-cupom">
                        <input type="hidden" name="id" value="${target.idcupom}">
                        <input type="submit" class="btn btn-primary" value="Editar cupom">
                        <a href="cupom-manager"><input type="button" class="btn btn-default" value="Cancelar edição"></a>
                    </c:if>
                </div>
            </div>    
        </form>
    </div>
</div>
<div class="product-body">
    <table class="table table-hover">
        <tr>
            <th>Código</th>
            <th>Desconto</th>
            <th>Status</th> 
            <th>Ações</th>
        </tr>
        <c:forEach items="${allCupons}" var="cupom">
            <tr>
                <td>${cupom.codigo}</td>
                <td>${cupom.desconto}</td>
                <td>${cupom.status}</td>                
                <td>
                    <a href="cupom-manager?action=edit-cupom&&cupom=${cupom.idcupom}"><span class="glyphicon glyphicon-edit"></span></a>
                    <span data-id="remove-cupom" data-info="${cupom.idcupom}" class="glyphicon glyphicon-trash"></span>
                </td>
            </tr>
        </c:forEach>
    </table>    
</div>