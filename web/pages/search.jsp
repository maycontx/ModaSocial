<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="page-header">
        <h1>Buscando por <small>${search.termo}</small></h1>
    </div>
    <div class="col-lg-3">
        <div class="search-filter">
            <div class="filter-box">
                <div class="filter-title">
                    Marcas
                </div>
                <c:forEach items="${search.getFilters()}" var="filter">
                    <c:if test="${filter.type == 'brand'}">
                        <div search-active="false" data-id="search-brand" class="filter-item">
                            ${filter.feature} (${filter.amount})
                        </div>   
                    </c:if>                         
                </c:forEach>
            </div>
            <div class="filter-box">
                <div class="filter-title">
                    Preço
                </div>
                <c:forEach items="${search.getFilters()}" var="filter">
                    <c:if test="${filter.type == 'price'}">
                        <div search-active="false" data-id="search-price" class="filter-item">
                            ${filter.feature} (${filter.amount})
                        </div>   
                    </c:if>                         
                </c:forEach>
            </div>
            <div class="filter-box">
                <div class="filter-title">
                    Categoria
                </div>
                <c:forEach items="${search.getFilters()}" var="filter">
                    <c:if test="${filter.type == 'category'}">
                        <div search-active="false" data-id="search-category" class="filter-item">
                            ${filter.feature} (${filter.amount})
                        </div>   
                    </c:if>                         
                </c:forEach>
            </div> 
        </div>
    </div>
    <div class="col-lg-9">
        <c:forEach items="${search.getProducts()}" var="product">    
            <div data-id="search-product" class="col-lg-4">
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
</div>

