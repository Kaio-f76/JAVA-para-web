<%-- 
    Document   : login
    Created on : 22 de mar. de 2025, 18:37:46
    Author     : kaio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession" %>
<%

    if (session != null && session.getAttribute("usuarioLogado") != null) {
        response.sendRedirect("dashboardGestao.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">

    <%
        String acao = request.getParameter("acao");
        String nomeUsuario = (String) request.getAttribute("nomeUsuario");
        String senhaUsuario = (String) request.getAttribute("senhaUsuario");
        String erroLogin = (String) request.getAttribute("erroLogin");
    %>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="img/iconeDaPagina.ico" type="image/x-icon">
        <title>Login</title>

        <script>
            if (window.history && window.history.pushState) {
                window.history.pushState(null, null, window.location.href);
                window.onpopstate = function () {
                window.location.replace("index.html"); 
                };
            }
        </script>

    </head>
    <body>
        <form action="LoginUsuarioSrv" method="POST">
            <input type="hidden" name="DadosTelaLogin" value="<%=acao != null ? acao : "telaLogin"%>" />


            <div class="main-login">
                <div class="left-login">
                    <h1>Faça login! <br> caso ainda não tenha, faça um cadastro :)</h1>
                    <img class="left-login-image" src="img/financial-data-animate.svg" alt="animacaoFinancas">
                </div>
                <div class="right-login">
                    <div class="card-login">
                        <h1>LOGIN</h1>

                        <!-- Exibindo a mensagem de erro, se houver, mensagem chamadas lá no servlet -->
                        <% if (erroLogin != null) {%>
                        <div style="color: red; background: #131721; padding: 10px; text-align: center; margin-bottom: 15px;">
                            <p><%= erroLogin%></p>
                        </div>
                        <% }%>


                        <!-- Formulário de login -->
                        <div class="textfield">
                            <label for="usuario">Usuário</label>
                            <input type="text" name="usuario" placeholder="Usuário"  value="<%= nomeUsuario != null ? nomeUsuario : ""%>" required>
                        </div>
                        <div class="textfield">
                            <label for="senha">Senha</label>
                            <div class="password-container">
                                <input id="senha" type="password" name="senha" placeholder="Senha" value="<%= senhaUsuario != null ? senhaUsuario : ""%>"  required>
                                <img id="toggle-password" src="img/eye-closed.png" alt="Mostrar senha" class="eye-icon">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-login">Login</button>

                        <!-- Cadastro -->
                        <button onclick="window.location.href = 'cadastro.jsp?acao=telaCadastro'" class="btn btn-realizar-cadastro">Não possuo um cadastro!</button>
                        <button onclick="history.go(-1);" class="btn btn-realizar-cadastro">voltar</button>
                    </div>  
                </div>
            </div>
        </form>
        <script src="js/app.js"></script>

        <script>
                            // Verifica se há o parâmetro de erro na URL e exibe a mensagem
                            window.onload = function () {
                                // Pega a query string da URL
                                const urlParams = new URLSearchParams(window.location.search);
                                const erro = urlParams.get('erro');

                                // Se o erro estiver presente, mostra a mensagem de erro
                                if (erro && erro === 'true') {
                                    document.getElementById('erro-login').style.display = 'block';
                                }
                            };
        </script>
    </body>
</html>
