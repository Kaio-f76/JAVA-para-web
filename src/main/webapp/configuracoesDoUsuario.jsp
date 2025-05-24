<%-- 
    Document   : configuracoesDoUsuario
    Created on : 31 de mar. de 2025, 15:46:10
    Author     : kaio
--%>

<%@page import="model.loginUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        loginUsuario usuarioLogado = (loginUsuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String nomeLogado = usuarioLogado.getNome();
        String emailLogado = usuarioLogado.getEmail();
        String senhaLogado = usuarioLogado.getSenha();

        String id = request.getParameter("id");
        String acao = request.getParameter("acao");
        String erroCadastro = (String) request.getAttribute("erroCadastro"); // Verifica se há algum erro de cadastro
        String nomeUsuario = (String) request.getAttribute("nomeUsuario"); // Pega o nome preenchido anteriormente
        String emailUsuario = (String) request.getAttribute("emailUsuario"); // Pega o email preenchido anteriormente
        String senhaUsuario = (String) request.getAttribute("senhaUsuario"); // Pega a senha preenchida anteriormente
        String confirmarSenha = (String) request.getAttribute("confirmarSenha"); // Pega a confirmação da senha
    %>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/styleUsuario.css">
        <link rel="shortcut icon" href="img/iconeDaPagina.ico" type="image/x-icon">
        <title>Editar Cadastro</title>
    </head>
    <body>
        <!-- Exibir a mensagem de erro, caso exista -->
        <% if (erroCadastro != null) {%>
        <div style="color: red; background: #2f2841;">
            <%= erroCadastro%>
        </div>
        <% }%>

        <form action="CadastroUsuarioSrv" method="POST">
            <input type="hidden" name="DadosTelaCadastro" value="<%=acao != null ? acao : "telaCadastroEdicao"%>" />
            <input type="hidden" name="id" value="<%= id%>" />

            <div class="main-login">
                <div class="right-login">
                    <div class="card-login">
                        <h1>Editar Cadastro</h1>

                        <!-- Usuário -->
                        <div class="textfield">
                            <label for="usuario">Usuário</label>
                            <input type="text" name="usuario" placeholder="Usuário" value="<%= nomeUsuario != null ? nomeUsuario : nomeLogado%>" required>
                        </div>

                        <!-- Email -->
                        <div class="textfield">
                            <label for="email">Email</label>
                            <input type="text" name="email" placeholder="Email" value="<%= emailUsuario != null ? emailUsuario : emailLogado%>" required>
                        </div>

                        <!-- Senha -->
                        <div class="textfield">
                            <label for="senha">Senha</label>
                            <div class="password-container">
                                <input type="password" name="senha" id="senha" placeholder="Senha" value="<%= senhaUsuario != null ? senhaUsuario : senhaLogado%>" required>
                                <img id="toggle-senha" src="img/eye-closed.png" alt="Mostrar senha" class="eye-icon">
                            </div>
                        </div>

                        <!-- Confirmar Senha -->
                        <div class="textfield">
                            <label for="confirmarsenha">Confirmar Senha</label>
                            <div class="password-container">
                                <input type="password" name="confirmarsenha" id="confirmarsenha" placeholder="Confirmar Senha" value="<%= confirmarSenha != null ? confirmarSenha : senhaLogado%>" required>
                                <img id="toggle-confirmarsenha" src="img/eye-closed.png" alt="Mostrar senha" class="eye-icon">
                            </div>
                        </div>

                        <button type="submit" class="btn btn-login">Alterar Cadastro</button>

                        <button type="button" onclick="history.go(-1);" class="btn btn-realizar-cadastro">Voltar</button>

                       <button type="submit" class="btn btn-excluir-dados" onclick="alterarValorHidden()">EXCLUIR DADOS !</button>


                    </div>  
                </div>
            </div>
        </form>
        <script> function alterarValorHidden() {
                // Alterando o valor do campo hidden para 'telaCadastroExclusao'
                document.querySelector('input[name="DadosTelaCadastro"]').value = "telaCadastroExclusao";
            }
        </script> 
        <script src="js/telaCadastro.js"></script>
    </body>
</html>
