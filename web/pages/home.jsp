<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid content categories">
    <div class="component-title">
        <div class="off-title">
            <div class="title">
                Categorias
            </div>
            <span class="title-icon glyphicon glyphicon-menu-down"></span>
        </div>        
    </div>
    <div class="col-lg-4">
        <div class="category-container">            
            <img src="img/moda-masculinaBG.jpg">
            <span>Moda masculina</span>
        </div>        
    </div>
    <div class="col-lg-4">
        <div class="category-container">
            <img src="img/moda-praia.jpg">
            <span>Moda praia</span>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="category-container">
            <img src="img/destaque-moda-infantil-01.jpg">
            <span>Moda infantil</span>
        </div>
    </div>
</div>
<div class="container content products">
    <div class="component-title">
        <div class="off-title">
            <div class="title">
                Produtos
            </div>
            <span class="title-icon glyphicon glyphicon-menu-down"></span>
        </div>        
    </div>
    <div class="products-container">
        <c:forEach items="${sortProducts}" var="product">    
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
                            <div class="col-lg-12 product-name">
                                <a href="#">${product.nome}</a>   
                            </div>
                            <div data-reactid="search-price" class="col-lg-12 product-price">
                                R$ ${product.preco}
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach> 

    </div>
    <c:if test="${user != null}">
        <div class="component-title">
            <div class="off-title">
                <div class="title">
                    Produtos sugeridos
                </div>
                <span class="title-icon glyphicon glyphicon-menu-down"></span>
            </div>        
        </div>
        <div class="products-container">
            <c:forEach items="${user.getPreferenceProduct()}" var="product">    
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
                                <div class="col-lg-12 product-name">
                                    <a href="#">${product.nome}</a>   
                                </div>
                                <div data-reactid="search-price" class="col-lg-12 product-price">
                                    R$ ${product.preco}
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach> 

        </div>
    </c:if>
</div>