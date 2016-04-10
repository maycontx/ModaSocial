<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${user != null}">
    <div class="btn-group" data-toggle="buttons">
        <div class="col-lg-3 btn btn-primary">
            <input type="radio" name="address" id="0" autocomplete="off">
            <div class="title">Novo endere�o</div>
            <ul class="list-group">
                <li class="list-group-item"><div class="address-label">Pa�s</div> <input type="text" class="form-control" value="Brasil" readonly></li>
                <li class="list-group-item">
                    <div class="address-label">CEP</div> 
                    <input type="text" class="form-control" name="new-address-code">
                </li>
                <li class="list-group-item"><div class="address-label">Estado</div>
                    <select class="form-control" name="new-address-state">
                        <option value="AC">Acre</option>
                        <option value="AL">Alagoas</option>
                        <option value="AP">Amap�</option>
                        <option value="AM">Amazonas</option>
                        <option value="BA">Bahia</option>
                        <option value="CE">Cear�</option>
                        <option value="DF">Distrito Federal</option>
                        <option value="ES">Esp�rito Santo</option>
                        <option value="GO">Goi�s</option>
                        <option value="MA">Maranh�o</option>
                        <option value="AL">Mato Grosso</option>
                        <option value="AL">Mato Grosso do Sul</option>
                        <option value="MG">Minas Gerais</option>
                        <option value="PA">Par�</option>
                        <option value="PB">Para�ba</option>
                        <option value="PR">Paran�</option>
                        <option value="PE">Pernambuco</option>
                        <option value="PI">Piau�</option>
                        <option value="RJ">Rio de Janeiro</option>
                        <option value="RN">Rio Grande do Norte</option>
                        <option value="RS">Rio Grande do Sul</option>
                        <option value="RO">Rond�nia</option>
                        <option value="RR">Roraima</option>
                        <option value="SC">Santa Catarina</option>
                        <option value="SP">S�o Paulo</option>                                
                        <option value="SE">Sergipe</option>
                        <option value="TO">Tocantins</option>
                    </select>
                </li>
                <li class="list-group-item"><div class="address-label">Cidade</div> <input type="text" class="form-control" name="new-address-city"></li>
                <li class="list-group-item"><div class="address-label">Bairro</div> <input type="text" class="form-control" name="new-address-local"></li>
                <li class="list-group-item"><div class="address-label">Rua</div> <input type="text" class="form-control" name="new-address-street"></li>
                <li class="list-group-item"><div class="address-label">Lugradouro</div> <input type="text" class="form-control" name="new-address-local2"></li>
                <li class="list-group-item"><div class="address-label">Complemento</div> <input type="text" class="form-control" name="new-address-complement"></li>
                <li class="list-group-item"><div class="address-label">Telefone</div> <input type="text" class="form-control" name="new-address-phone"></li>
                <li class="list-group-item"><button type="button" class="btn btn-success" data-id="new-address">Inserir e calcular</button></li>
            </ul>
        </div>  
        <c:forEach items="${user.getEndere�oList()}" var="address">            
            <div class="col-lg-3 btn btn-primary">
                <input type="radio" name="address" id="${address.idendere�o}" autocomplete="off">
                <ul class="list-group">
                    <li class="list-group-item">Pa�s <span class="badge">${address.pais}</span></li>
                    <li class="list-group-item">Estado <span class="badge">${address.estado}</span></li>
                    <li class="list-group-item">Cidade <span class="badge">${address.cidade}</span></li>
                    <li class="list-group-item">Bairro <span class="badge">${address.bairro}</span></li>
                    <li class="list-group-item">Rua <span class="badge">${address.rua}</span></li>
                    <li class="list-group-item">Lugradouro <span class="badge">${address.lugradouro}</span></li>
                    <li class="list-group-item">Complemento <span class="badge">${address.complemento}</span></li>
                    <li class="list-group-item">Telefone <span class="badge">${address.telefone}</span></li>
                    <li class="list-group-item">CEP <span class="badge">${address.cep}</span></li>
                    <li class="list-group-item">Frete <span class="badge">R$ 10,00</span></li>
                </ul>
            </div>            
        </c:forEach>                   
    </div>
</c:if>