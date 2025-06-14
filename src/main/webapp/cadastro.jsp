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
        String erroCadastro = (String) request.getAttribute("erroCadastro"); // Verifica se há algum erro de cadastro
        String nomeUsuario = (String) request.getAttribute("nomeUsuario"); // Pega o nome preenchido anteriormente
        String emailUsuario = (String) request.getAttribute("emailUsuario"); // Pega o email preenchido anteriormente
        String senhaUsuario = (String) request.getAttribute("senhaUsuario"); // Pega a senha preenchida anteriormente
        String confirmarSenha = (String) request.getAttribute("confirmarSenha"); // Pega a confirmação da senha
    %>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="img/iconeDaPagina.ico" type="image/x-icon">
        <title>Novo Cadastro</title>
    </head>
    <body>
        <!-- Exibir a mensagem de erro, caso exista -->
        <% if (erroCadastro != null) { %>
            <div style="color: red; background: #2f2841;">
                <%= erroCadastro %>
            </div>
        <% } %>

        <form action="CadastroUsuarioSrv" method="POST">
           <input type="hidden" name="DadosTelaCadastro" value="<%=acao != null ? acao : "telaCadastro" %>" />

            
            <div class="main-login">
                <div class="right-login">
                    <div class="card-login">
                        <h1>Novo Cadastro</h1>

                        <!-- Usuário -->
                        <div class="textfield">
                            <label for="usuario">Usuário</label>
                            <input type="text" name="usuario" placeholder="Usuário" value="<%= nomeUsuario != null ? nomeUsuario : "" %>" required>
                        </div>

                        <!-- Email -->
                        <div class="textfield">
                            <label for="email">Email</label>
                            <input type="text" name="email" placeholder="Email" value="<%= emailUsuario != null ? emailUsuario : "" %>" required>
                        </div>

                        <!-- Senha -->
                        <div class="textfield">
                            <label for="senha">Senha</label>
                            <div class="password-container">
                                <input type="password" name="senha" id="senha" placeholder="Senha" value="<%= senhaUsuario != null ? senhaUsuario : "" %>" required>
                                <img id="toggle-senha" src="img/eye-closed.png" alt="Mostrar senha" class="eye-icon">
                            </div>
                        </div>

                        <!-- Confirmar Senha -->
                        <div class="textfield">
                            <label for="confirmarsenha">Confirmar Senha</label>
                            <div class="password-container">
                                <input type="password" name="confirmarsenha" id="confirmarsenha" placeholder="Confirmar Senha" value="<%= confirmarSenha != null ? confirmarSenha : "" %>" required>
                                <img id="toggle-confirmarsenha" src="img/eye-closed.png" alt="Mostrar senha" class="eye-icon">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-login">Confirmar</button>
                         
                        <button type="button" onclick="history.go(-1);" class="btn btn-realizar-cadastro">Voltar</button>
                    </div>  
                </div>
            </div>
        </form>

        <script src="js/telaCadastro.js"></script>
    </body>
</html>



