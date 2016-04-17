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
                    <input type="text" class="form-control" name="new-address-code" value="${address.cep}">
                </li>
                <li class="list-group-item"><div class="address-label">Estado</div>
                    <select class="form-control" name="new-address-state" >
                        <option value="AC" ${address.uf=='AC' ? 'selected' : ''}>Acre</option>
                        <option value="AL" ${address.uf=='AL' ? 'selected' : ''}>Alagoas</option>
                        <option value="AP" ${address.uf=='AP' ? 'selected' : ''}>Amapá</option>
                        <option value="AM" ${address.uf=='AM' ? 'selected' : ''}>Amazonas</option>
                        <option value="BA" ${address.uf=='BA' ? 'selected' : ''}>Bahia</option>
                        <option value="CE" ${address.uf=='CE' ? 'selected' : ''}>Ceará</option>
                        <option value="DF" ${address.uf=='DF' ? 'selected' : ''}>Distrito Federal</option>
                        <option value="ES" ${address.uf=='ES' ? 'selected' : ''}>Espírito Santo</option>
                        <option value="GO" ${address.uf=='GO' ? 'selected' : ''}>Goiás</option>
                        <option value="MA" ${address.uf=='MA' ? 'selected' : ''}>Maranhão</option>
                        <option value="MT" ${address.uf=='MT' ? 'selected' : ''}>Mato Grosso</option>
                        <option value="MS" ${address.uf=='MS' ? 'selected' : ''}>Mato Grosso do Sul</option>
                        <option value="MG" ${address.uf=='MG' ? 'selected' : ''}>Minas Gerais</option>
                        <option value="PA" ${address.uf=='PA' ? 'selected' : ''}>Pará</option>
                        <option value="PB" ${address.uf=='PB' ? 'selected' : ''}>Paraíba</option>
                        <option value="PR" ${address.uf=='PR' ? 'selected' : ''}>Paraná</option>
                        <option value="PE" ${address.uf=='PE' ? 'selected' : ''}>Pernambuco</option>
                        <option value="PI" ${address.uf=='PI' ? 'selected' : ''}>Piauí</option>
                        <option value="RJ" ${address.uf=='RJ' ? 'selected' : ''}>Rio de Janeiro</option>
                        <option value="RN" ${address.uf=='RN' ? 'selected' : ''}>Rio Grande do Norte</option>
                        <option value="RS" ${address.uf=='RS' ? 'selected' : ''}>Rio Grande do Sul</option>
                        <option value="RO" ${address.uf=='RO' ? 'selected' : ''}>Rondônia</option>
                        <option value="RR" ${address.uf=='RR' ? 'selected' : ''}>Roraima</option>
                        <option value="SC" ${address.uf=='SC' ? 'selected' : ''}>Santa Catarina</option>
                        <option value="SP" ${address.uf=='SP' ? 'selected' : ''}>São Paulo</option>                                
                        <option value="SE" ${address.uf=='SE' ? 'selected' : ''}>Sergipe</option>
                        <option value="TO" ${address.uf=='TO' ? 'selected' : ''}>Tocantins</option>
                    </select>
                </li>
                <li class="list-group-item"><div class="address-label">Cidade</div> <input type="text" class="form-control" name="new-address-city" value="${address.cidade}"></li>
                <li class="list-group-item"><div class="address-label">Bairro</div> <input type="text" class="form-control" name="new-address-local" value="${address.bairro}"></li>
                <li class="list-group-item"><div class="address-label">Rua</div> <input type="text" class="form-control" name="new-address-street"value="${address.logradouro}"></li>
                <li class="list-group-item"><div class="address-label">Número</div> <input type="text" class="form-control" name="new-address-number"></li>
                <li class="list-group-item"><div class="address-label">Complemento</div> <input type="text" class="form-control" name="new-address-complement"></li>
                <li class="list-group-item"><div class="address-label">Telefone</div> <input type="text" class="form-control" name="new-address-phone"></li>
                <li class="list-group-item"><div class="address-label">Identificação do endereço</div> <input type="text" class="form-control"  name="new-address-name"></li>
                <li class="list-group-item"><button type="button" class="btn btn-success" data-id="new-address">Inserir e calcular</button></li>
            </ul>
        </div>  
        <c:forEach items="${user.getEndereçoList()}" var="address">            
            <div class="col-lg-3 btn btn-primary" data-id="address" data-info="${address.idendereço}" shipping-value="10.00">
                <input type="radio" name="address" autocomplete="off">
                <div class="title">${address.nome}</div>
                <ul class="list-group">
                    <li class="list-group-item">País <span class="badge">${address.pais}</span></li>
                    <li class="list-group-item">Estado <span class="badge">${address.estado}</span></li>
                    <li class="list-group-item">Cidade <span class="badge">${address.cidade}</span></li>
                    <li class="list-group-item">Bairro <span class="badge">${address.bairro}</span></li>
                    <li class="list-group-item">Rua <span class="badge">${address.rua}</span></li>
                    <li class="list-group-item">Número <span class="badge">${address.lugradouro}</span></li>
                    <li class="list-group-item">Complemento <span class="badge">${address.complemento}</span></li>
                    <li class="list-group-item">Telefone <span class="badge">${address.telefone}</span></li>
                    <li class="list-group-item">CEP <span class="badge">${address.cep}</span></li>
                    <li class="list-group-item">Frete <span class="badge">R$ 10,00</span></li>
                </ul>
            </div>            
        </c:forEach>                   
    </div>
</c:if>