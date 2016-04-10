<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${user != null}">
    <div class="btn-group" data-toggle="buttons">
        <div class="col-lg-3 btn btn-primary">
            <input type="radio" name="address" id="0" autocomplete="off">
            <div class="title">Novo endereço</div>
            <ul class="list-group">
                <li class="list-group-item"><div class="address-label">País</div> <input type="text" class="form-control" value="Brasil" readonly></li>
                <li class="list-group-item">
                    <div class="address-label">CEP</div> 
                    <input type="text" class="form-control" name="new-address-code">
                </li>
                <li class="list-group-item"><div class="address-label">Estado</div>
                    <select class="form-control" name="new-address-state">
                        <option value="AC">Acre</option>
                        <option value="AL">Alagoas</option>
                        <option value="AP">Amapá</option>
                        <option value="AM">Amazonas</option>
                        <option value="BA">Bahia</option>
                        <option value="CE">Ceará</option>
                        <option value="DF">Distrito Federal</option>
                        <option value="ES">Espírito Santo</option>
                        <option value="GO">Goiás</option>
                        <option value="MA">Maranhão</option>
                        <option value="AL">Mato Grosso</option>
                        <option value="AL">Mato Grosso do Sul</option>
                        <option value="MG">Minas Gerais</option>
                        <option value="PA">Pará</option>
                        <option value="PB">Paraíba</option>
                        <option value="PR">Paraná</option>
                        <option value="PE">Pernambuco</option>
                        <option value="PI">Piauí</option>
                        <option value="RJ">Rio de Janeiro</option>
                        <option value="RN">Rio Grande do Norte</option>
                        <option value="RS">Rio Grande do Sul</option>
                        <option value="RO">Rondônia</option>
                        <option value="RR">Roraima</option>
                        <option value="SC">Santa Catarina</option>
                        <option value="SP">São Paulo</option>                                
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
        <c:forEach items="${user.getEndereçoList()}" var="address">            
            <div class="col-lg-3 btn btn-primary">
                <input type="radio" name="address" id="${address.idendereço}" autocomplete="off">
                <ul class="list-group">
                    <li class="list-group-item">País <span class="badge">${address.pais}</span></li>
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