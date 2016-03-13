<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <jsp:include page="struct/head.jsp" />
    <div class="global-content container-fluid">
        <div class="fade-screen" data-id="fade" data-reactid="hide-modal">
            <div class="component-register" data-reactid="register-open">    
                <form method="POST" action="register">
                    <div class="form-row">
                        <div class="form-title">
                            <h4>Ainda não tem conta? Cadastre-se :)</h4>
                        </div>
                    </div>    
                    <div class="form-row">
                        <div class="col-lg-6">
                            <input class="form-control" type="text" name="reg-name" placeholder="Nome">
                        </div>    
                        <div class="col-lg-6">
                            <input class="form-control" type="text" name="reg-aftername" placeholder="Sobrenome">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-lg-6">
                            <input class="form-control" type="text" name="reg-email" placeholder="E-mail">
                        </div>   
                        <div class="col-lg-6">
                            <input class="form-control" type="password" name="reg-pass" placeholder="Senha">
                        </div>
                    </div>
                    <div class="form-row">               
                        <input type="button" class="btn btn-default" value="Fechar" data-id="hide-modal">  
                        <input type="submit" class="btn btn-primary" value="Cadastrar">                    
                    </div>
                </form>
            </div>
        </div>        
        <jsp:include page="struct/header.jsp" /> 
        <jsp:include page="struct/content.jsp" />
        <jsp:include page="struct/footer.jsp" />
    </div>
</html>
