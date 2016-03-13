<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid header-up">
    <div class="container">
        <a href="admin-panel">Painel de Controle</a> |
        FAQ | Contato
    </div>
    <div class="menu">
        <div class="menu-item">
            <span class="glyphicon glyphicon-menu-hamburger"></span>
        </div>
        <div class="menu-item" data-id="register-open">Cadastrar</div>
        <div class="menu-item">Entrar</div>
        <div class="menu-item search-box">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Buscar produto...">
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-search"></span>
                </span>
            </div>
        </div>
        <div class="menu-item"><i class="fa fa-shopping-basket"></i></div>        
    </div>    
</div>
<div class="carousel-content">
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active">
                <img src="img/moda-masculinaBG.jpg" alt="...">
                <div class="carousel-caption">
                    <h3>Lorem Ispum</h3>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas imperdiet dictum elit tincidunt ullamcorper. Maecenas sed justo pulvinar, accumsan lorem non, finibus est.
                    </p>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>    
</div>