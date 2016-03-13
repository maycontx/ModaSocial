<article class="container-fluid">           
    <%
        // RECEBENDO O PARAMETRO POR GET
        String pageRequest = request.getParameter("page");
        try {
            // SE A PAGINA N�O FOR NULO
            if (pageRequest != null) {
                // COLOCAR O O LINK DA PAGINA DENTRO DA VARIAVEL
                pageRequest = "../pages/" + pageRequest + ".jsp";
    %>
    <jsp:include page="<%= pageRequest%>" />
    <%
    } else {
        // RECEBENDO O PARAMETRO POR POST
        pageRequest = (String) request.getAttribute("page");
        // SE A PAGINA N�O FOR NULO
        if (pageRequest != null) {
            // COLOCAR O O LINK DA PAGINA DENTRO DA VARIAVEL
            pageRequest = "../pages/" + pageRequest + ".jsp";
    %>
    <jsp:include page="<%= pageRequest%>" />
    <%
        // CASO N�O HAJA PAR�METRO
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