<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $('#a a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    });
</script>
<div class="row product-row">
    <div class="col-lg-4">
        <div class="product-img-main">
            <img src="img/crew-tshirt-resized-680x680.png">
        </div>
        <div class="product-img-secundary">
            <div class="col-lg-3">
                <img src="img/crew-tshirt-resized-680x680.png">
            </div>
            <div class="col-lg-3">
                <img src="img/crew-tshirt-resized-680x680.png">
            </div>
            <div class="col-lg-3">
                <img src="img/crew-tshirt-resized-680x680.png">
            </div>
            <div class="col-lg-3">
                <img src="img/crew-tshirt-resized-680x680.png">
            </div>
            <div class="col-lg-3">
                <img src="img/crew-tshirt-resized-680x680.png">
            </div> 
        </div>
    </div>
    <div class="col-lg-4">
        <div class="product-info">
            <ul class="list-group">
                <li class="list-group-item product-name">${product.nome}</li>
                <li class="list-group-item product-brand">${product.marca}</li>
                <li class="list-group-item product-description">
                    <div class="description-title">
                        Sobre
                    </div>
                    ${product.getShortDescription()}
                </li>
                <li class="list-group-item product-price">
                    R$ ${product.preco}
                </li>
                <li class="list-group-item product-rate">
                    ${product.getRateBalance()}
                </li>
                <li class="list-group-item product-status">
                    <c:if test="${product.estoque > 10}">                   
                        <span class="glyphicon glyphicon-thumbs-up"></span> Disponível                   
                    </c:if>               
                </li>                
            </ul>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="product-feature">
            <ul class="list-group">
                <li class="list-group-item product-name">Características</li>
                    <c:if test="${product.getCaracteristicaList().size() == 0}">
                    <li class="list-group-item"><span class="no-feature">Nenhuma Característica</span></li>
                    </c:if>
                    <c:forEach items="${product.getCaracteristicaList()}" var="feature">
                    <li class="list-group-item"><span class="feature-title">${feature.campo}:</span> ${feature.valor}</li>
                    </c:forEach>                                
            </ul>
        </div>
        <div class="buttons">
            <button class="btn btn-success buy"><i class="fa fa-cart-plus"></i> Adicionar ao carrinho</button>
            <button class="btn btn-success favorite"><i class="fa fa-heart-o"></i> Adicionar aos favoritos</button>
        </div>
    </div>
</div>
<div class="row product-row">    
    <ul class="nav nav-tabs product-tab" role="tablist">        
        <li role="presentation" class="active" id="a"><a href="#rating" aria-controls="rating" role="tab" data-toggle="tab">Avaliações</a></li>        
        <li role="presentation"><a href="#description" aria-controls="description" role="tab" data-toggle="tab">Descrição</a></li>
    </ul>

    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade" id="description">
            ${product.descricao}            
        </div>
        <div role="tabpanel" class="tab-pane fade active in" id="rating">
            <div class="product-new-rating">
                <c:if test="${user != null}">
                    <h3>Deixe sua avaliação abaixo!</h3>
                    <div class="new-rating-box">
                        <div class="new-rate">
                            <span>Deixe sua nota: </span>
                            <span data-reactid="fav-star" class="glyphicon glyphicon-star" data-info="1"></span>
                            <span data-reactid="fav-star" class="glyphicon glyphicon-star" data-info="2"></span>
                            <span data-reactid="fav-star" class="glyphicon glyphicon-star" data-info="3"></span>
                            <span data-reactid="fav-star" class="glyphicon glyphicon-star-empty" data-info="4"></span>
                            <span data-reactid="fav-star" class="glyphicon glyphicon-star-empty" data-info="5"></span>
                        </div>
                        <div class="new-comment">
                            <form method="POST" action="rating">
                                <input type="hidden" name="rating-product" value="${product.idproduto}">
                                <input type="hidden" name="rating-rate" value="3">
                                <textarea class="form-control" name="rating-comment" placeholder="Comente sobre sua experiência com este produto!" rows="6"></textarea>
                                <input type="submit" class="btn btn-primary" value="Avaliar">
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="product-rating">
                <c:if test="${product.getAvaliacaoList().size() == 0}">
                    <div class="no-rating">Nenhuma avaliação sobre este produto :(</div>
                </c:if>
                <c:forEach items="${product.getAvaliacaoList()}" var="rating">
                    <div class="rating-box">
                        <div class="rating-header">
                            <div class="header-user">${rating.usuario.nome} ${rating.usuario.sobrenome}</div>
                            <div data-id="rate-box" data-info="${rating.nota}" class="header-rate">
                                <span data-reactid="icon-star" data-info="1" class="glyphicon glyphicon-star-empty"></span>
                                <span data-reactid="icon-star" data-info="2" class="glyphicon glyphicon-star-empty"></span>
                                <span data-reactid="icon-star" data-info="3" class="glyphicon glyphicon-star-empty"></span>
                                <span data-reactid="icon-star" data-info="4" class="glyphicon glyphicon-star-empty"></span>
                                <span data-reactid="icon-star" data-info="5" class="glyphicon glyphicon-star-empty"></span>
                            </div>
                        </div>
                        <div class="rating-body">
                            <div class="body-comment">${rating.comentario}</div>
                            <div class="body-date">25/02/2016 às 14:10</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>        
    </div>
</div>
<div class="row product-row">
    <div class="component-title">
        <div class="off-title">
            <div class="title">
                Outros: ${product.categoria.nome}
            </div>
            <span class="title-icon glyphicon glyphicon-menu-down"></span>
        </div>        
    </div>
    <c:forEach items="${product.getRelated()}" var="product">    
        <div data-id="search-product" class="col-lg-3">
            <a href="product?p=${product.idproduto}">
                <div class="product">
                    <div class="product-img">
                        <img src="img/crew-tshirt-resized-680x680.png">
                        <div class="product-info">                       
                            <div class="info-header">
                                <span data-reactid="search-brand">${product.marca}</span><span data-reactid="search-category">${product.categoria.nome}</span>
                            </div>
                            <div class="info-body">
                                <div class="feature">
                                    <span class="badge">Cor:</span> Vermelho
                                </div>
                                <div class="feature">
                                    <span class="badge">Material:</span> Malha fina
                                </div>  
                            </div>                       
                            <div class="info-footer">
                                <div class="info-footer-btn">
                                    <i class="fa fa-heart-o"></i>
                                </div>
                                <div class="info-footer-btn">
                                    <i class="fa fa-cart-plus"></i>
                                </div>
                            </div>
                        </div>
                    </div>                
                    <div class="product-basic-info">
                        <div class="col-lg-8 product-name">
                            <a href="#">${product.nome}</a>   
                        </div>
                        <div data-reactid="search-price" class="col-lg-4 product-price">
                            R$ ${product.preco}
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </c:forEach>        
</div>        