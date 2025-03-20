<%-- 
    Document   : cadastro
    Created on : 17 de mar. de 2025, 00:03:49
    Author     : kaio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <%

        String acao = request.getParameter("acao");

    %>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Novo Cadastro</title>
    </head>
    <body>
        <form action="CadastroUsuarioSrv" method="POST">
            <input type="hidden" name="DadosTelaCadastro" value="<%=acao%>" />
            <div class="main-login">
                <div class="right-login">
                    <div class="card-login">
                        <h1>Novo Cadastro</h1>
                        <div class="textfield">
                            <label for="usuario">Usuário</label>
                            <input type="text" name="usuario" placeholder="Usuário" value="">
                        </div>
                        <div class="textfield">
                            <label for="email">Email</label>
                            <input type="text" name="email" placeholder="Email" value="" >
                        </div>
                        
                        <!-- Senha -->
                        <div class="textfield">
                            <label for="senha">Senha</label>
                            <div class="password-container">
                                <input type="password" name="senha" id="senha" placeholder="Senha" value="" >
                                <img id="toggle-senha" src="img/eye-closed.png" alt="Mostrar senha" class="eye-icon">
                            </div>
                        </div>
                        
                        <!-- Confirmar Senha -->
                        <div class="textfield">
                            <label for="confirmarsenha">Confirmar Senha</label>
                            <div class="password-container">
                                <input type="password" name="confirmarsenha" id="confirmarsenha" placeholder="Confirmar Senha" value="" >
                                <img id="toggle-confirmarsenha" src="img/eye-closed.png" alt="Mostrar senha" class="eye-icon">
                            </div>
                        </div>
                        
                        <button type="submit" class="btn btn-login">Confirmar</button>
                         
                         
                        <button onclick="history.go(-1);" class="btn btn-realizar-cadastro" >Voltar</button>
                    </div>  
                </div>
            </div>
        </form>

        <!-- Referência ao arquivo JavaScript // onclick="window.location.href='CadastroUsuarioSrv?acao=telaLogin'" 
        <a href="CadastroUsuarioSrv?acao=telaLogin"></a> -->
        <script src="js/telaCadastro.js"></script>
    </body>
</html>



