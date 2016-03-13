<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="struct/head.jsp" />
    <body>
        <div class="container-fluid top-menu">
            <div class="container top-menu-content">
                <a href="home"><span class="glyphicon glyphicon-share-alt"></span> Ir para o site</a>
            </div>
        </div>        
        <div class="fix-menu">
            <div class="fix-item">
                <span class="glyphicon glyphicon-gift"></span> <a href="product-manager">Produtos</a>
            </div>
        </div>
        <div class="admin-content">
            <%
                // RECEBENDO O PARAMETRO POR GET
                String pageRequest = request.getParameter("page");
                try {
                    // SE A PAGINA NÃO FOR NULO
                    if (pageRequest != null) {
                        // COLOCAR O O LINK DA PAGINA DENTRO DA VARIAVEL
                        pageRequest = "admin/" + pageRequest + ".jsp";
            %>
            <jsp:include page="<%= pageRequest%>" />
            <%
            } else {
                // RECEBENDO O PARAMETRO POR POST
                pageRequest = (String) request.getAttribute("page");
                // SE A PAGINA NÃO FOR NULO
                if (pageRequest != null) {
                    // COLOCAR O O LINK DA PAGINA DENTRO DA VARIAVEL
                    pageRequest = "admin/" + pageRequest + ".jsp";
            %>
            <jsp:include page="<%= pageRequest%>" />
            <%
                // CASO NÃO HAJA PARÂMETRO
            } else {
            %>
            <jsp:include page="admin/404.jsp" />
            <%
                    }
                }
            } catch (Exception ex) {
            %>
            <jsp:include page="admin/404.jsp" />
            <%
                }

            %>           
        </div>
    </body>
</html>
