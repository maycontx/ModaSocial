<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="product-header">
    <div class="page-header" style="width: 100%; float: left;">
        <h1>Fluxo Caixa  <small>Gerenciamento</small></h1>
        <h3><span class="glyphicon glyphicon-plus"></span> Nova semana</h3>
        <form name="cupom-form" method="POST" action="caixa-manager"> 
            <h4 style="margin-top: 20px; float: left; margin-bottom: -10px;">Per�odo</h4>
            <div class="form-row">                
                <div class="col-lg-3">
                    <label>Per�odo Inicial</label>
                    <input class="form-control" type="date" name="fluxo-dateStart" data-reactid="cupom-generate" placeholder="C�digo" >
                </div>
                <div class="col-lg-3">
                    <label>Per�odo Final</label>
                    <input class="form-control" type="date" name="fluxo-dateEnd" data-reactid="cupom-generate" placeholder="C�digo" >
                </div>  
            </div>
            <h4 style="margin-top: 20px; float: left; margin-bottom: -10px;">Entradas</h4>
            <div class="form-row">
                <div class="col-lg-2">
                    <label>Saldo inicial</label>
                    <input class="form-control" type="text" name="fluxo-cash" data-reactid="cupom-generate" value="0" >
                </div>
                <div class="col-lg-2">
                    <label>Vendas</label>
                    <input class="form-control" type="text" name="fluxo-sells" data-reactid="cupom-generate" value="0" >
                </div> 
                <div class="col-lg-2">
                    <label>Outros</label>
                    <input class="form-control" type="text" name="fluxo-others" data-reactid="cupom-generate" value="0" >
                </div> 
            </div>
            <h4 style="margin-top: 20px; float: left; margin-bottom: -10px;">Sa�das</h4>
            <div class="form-row">
                <div class="col-lg-2">
                    <label>Fornecedor</label>
                    <input class="form-control" type="text" name="fluxo-seller-tax" data-reactid="cupom-generate" value="0" >
                </div>
                <div class="col-lg-2">
                    <label>�gua/Luz</label>
                    <input class="form-control" type="text" name="fluxo-little-tax" data-reactid="cupom-generate" value="0" >
                </div> 
                <div class="col-lg-2">
                    <label>Telefone/Intenet</label>
                    <input class="form-control" type="text" name="fluxo-phone-tax" data-reactid="cupom-generate" value="0" >
                </div>
                <div class="col-lg-2">
                    <label>Combust�vel</label>
                    <input class="form-control" type="text" name="fluxo-gasoline-tax" data-reactid="cupom-generate" value="0" >
                </div>
                <div class="col-lg-2">
                    <label>Taxas banc�rias</label>
                    <input class="form-control" type="text" name="fluxo-bank-tax" data-reactid="cupom-generate" value="0" >
                </div> 
                <div class="col-lg-2">
                    <label>Materiais</label>
                    <input class="form-control" type="text" name="fluxo-materials-tax" data-reactid="cupom-generate" value="0" >
                </div>
                <div class="col-lg-2">
                    <label>Equipamentos</label>
                    <input class="form-control" type="text" name="fluxo-equipment-tax" data-reactid="cupom-generate" value="0" >
                </div> 
                <div class="col-lg-2">
                    <label>Impostos</label>
                    <input class="form-control" type="text" name="fluxo-tax" data-reactid="cupom-generate" value="0" >
                </div> 
            </div>
            <div class="form-row">
                <div class="col-lg-12">
                    <c:if test="${target == null}"> 
                        <input type="hidden" name="action" value="add-fluxo">
                        <input type="submit" class="btn btn-primary" value="Adicionar Fluxo">
                    </c:if>
                    <c:if test="${target != null}"> 
                        <input type="hidden" name="action" value="edit-fluxo">
                        <input type="hidden" name="id" value="${target.idcupom}">
                        <input type="submit" class="btn btn-primary" value="Editar cupom">
                        <a href="cupom-manager"><input type="button" class="btn btn-default" value="Cancelar edi��o"></a>
                    </c:if>
                </div>
            </div>    
        </form>    
    </div>
        <h3>Per�odo: Abril 2016</h3>
        <h4>Entradas</h4>
        <table class="table">
            <c:forEach items="${entradas}" var="entrada">
                <tr>                        
                    <th>Per�odo</th>
                    <th>Saldo inicial</th>
                    <th>Vendas</th>
                    <th>Outros</th>  
                    <th>Total</th> 
                </tr>
                <tr>
                    <td>De ${entrada.getDateFormat(entrada.dataInicial)} a ${entrada.getDateFormat(entrada.dataFinal)}</td>
                    <td>R$ ${entrada.saldo}</td>
                    <td>R$ ${entrada.venda}</td>
                    <td>R$ ${entrada.outros}</td>
                    <td>R$ ${entrada.total()}</td>
                    
                </tr>  
            </c:forEach>    
        </table>
        <h4>Sa�das</h4>
        <table class="table">
            <c:forEach items="${saidas}" var="saida">
                <tr>                        
                    <th>Per�odo</th>
                    <th>Fornecedor</th>
                    <th>�gua/Luz</th>
                    <th>Telefone</th>  
                    <th>Combustivel</th>
                    <th>Tarifas banc�rias</th>  
                    <th>Materiais de consumo</th> 
                    <th>Equipamentos</th>  
                    <th>Impostos</th>
                    <th>Total</th> 
                </tr>
                <tr>
                    <td>De ${saida.getDateFormat(saida.dataInicial)} a ${saida.getDateFormat(saida.dataFinal)}</td>
                    <td>R$ ${saida.fornecedor}</td>
                    <td>R$ ${saida.aguaELuz}</td>
                    <td>R$ ${saida.telefone}</td>
                    <td>R$ ${saida.combustivel}</td>
                    <td>R$ ${saida.banco}</td>
                    <td>R$ ${saida.materiaisConsumo}</td>
                    <td>R$ ${saida.equipamentos}</td>
                    <td>R$ ${saida.impostos}</td>
                    <td>R$ ${saida.total()}</td>
                    
                </tr>  
            </c:forEach>    
        </table>           
        
</div>

