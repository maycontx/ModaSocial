<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid header-up">
    <div class="container">        
            <c:if test="${user != null}">
                <c:if test="${user.permissao == 'Admin'}">
                    <a href="admin-panel">Painel de Controle</a> |
                </c:if>
            </c:if>            
        FAQ | Contato
        <c:if test="${user != null}">| Sair</c:if>
    </div>
    <div class="menu">
        <div class="menu-item">
            <span class="glyphicon glyphicon-menu-hamburger"></span>
        </div>
        <c:if test="${user == null}"><div class="menu-item" data-id="register-open">Cadastrar</div></c:if>
        <c:if test="${user == null}"><div class="menu-item" data-id="login-open">Entrar</div></c:if>
        <c:if test="${user != null}"><div class="menu-item"><a href="profile">Minha conta</a></div></c:if>
        <c:if test="${user != null}"><div class="menu-item"><a href="cart">Meu carrinho</a></div></c:if>
        <div class="menu-item search-box">
            <form method="POST" name="search-form" action="search">
                <div class="input-group">
                    <input type="text" class="form-control" name="search-tag" placeholder="Buscar produto...">
                    <span class="input-group-addon" data-id="search-trigger">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
            </form>
        </div>
        <div class="menu-item"><i class="fa fa-shopping-basket"></i></div>        
    </div>    
</div>
<c:if test="${page == 'home'}">
    <jsp:include page="carousel.jsp" />
</c:if>