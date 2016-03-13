<article class="container-fluid">           
    <%
        // RECEBENDO O PARAMETRO POR GET
        String pageRequest = request.getParameter("page");
        try {
            // SE A PAGINA NÃO FOR NULO
            if (pageRequest != null) {
                // COLOCAR O O LINK DA PAGINA DENTRO DA VARIAVEL
                pageRequest = "../pages/" + pageRequest + ".jsp";
    %>
    <jsp:include page="<%= pageRequest%>" />
    <%
    } else {
        // RECEBENDO O PARAMETRO POR POST
        pageRequest = (String) request.getAttribute("page");
        // SE A PAGINA NÃO FOR NULO
        if (pageRequest != null) {
            // COLOCAR O O LINK DA PAGINA DENTRO DA VARIAVEL
            pageRequest = "../pages/" + pageRequest + ".jsp";
    %>
    <jsp:include page="<%= pageRequest%>" />
    <%
        // CASO NÃO HAJA PARÂMETRO
    } else {
    %>
    <jsp:include page="../pages/404.jsp" />
    <%
            }
        }
    } catch (Exception ex) {
    %>
    <jsp:include page="../pages/404.jsp" />
    <%
        }

    %>           
</article>