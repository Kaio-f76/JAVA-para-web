<%-- 
Document   : dadosFinanceiros
Created on : 23 de mar. de 2025, 16:36:51
Author     : kaio
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%

    if (session == null || session.getAttribute("usuarioLogado") == null) {
        response.sendRedirect("login.jsp");
        return; 
    }

    String id = request.getParameter("id");

 
    String acao = request.getParameter("acao");
    String erroCadastro = (String) request.getAttribute("erroCadastro");
    String mes = request.getParameter("mes");
    String receita = request.getParameter("receita");
    String despesaFixa = request.getParameter("despesaFixa");
    String despesaVariavel = request.getParameter("despesaVariavel");
    String notas = request.getParameter("notas");
    String meta = request.getParameter("meta");
    boolean isEdicao = "edicao".equals(acao); 
%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="img/iconeDaPagina.ico" type="image/x-icon">
        <title>Sprint cadastro do mês</title>
        <link rel="stylesheet" href="css/styleDadosFinanceiros.css">

        <style>
            /* Estilo do botão */
            .btn-dicas {
                background-color: #4CAF50; /* Cor verde */
                color: white;
                padding: 15px 32px;
                font-size: 16px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }

            .btn-dicas:hover {
                background-color: #45a049;
            }

            /* Estilo do menu suspenso */
            .header-right {
                position: relative;
                display: inline-block;
            }

            .dropdown-menu {
                display: none; /* Oculto por padrão */
                position: absolute;
                right: 0;
                background-color: #131721;
                min-width: 160px;
                box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown-menu h3 {
                margin: 0;
                padding: 12px 16px;
                cursor: pointer;
            }

            .dropdown-menu h3:hover {
                background-color: #202738;
            }

            .three-dots-btn {
                background-color: transparent;
                border: none;
                font-size: 24px;
                cursor: pointer;
            }

            .three-dots-btn:hover {
                color: #333;
            }

            /* Estilo da janela modal */
            .modal {
                display: none; /* Inicialmente escondida */
                position: fixed;
                z-index: 1; /* Fica acima do conteúdo da página */
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.4); /* Cor de fundo escura */
                overflow: auto;
            }

            .modal-content {
                background-color: #202738;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%; /* Largura da janela */
                max-width: 400px;
                border-radius: 8px;
            }

            .modal-header {
                font-size: 18px;
                font-weight: bold;
            }

            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }
        </style>


    </head>
    <body>
        <header>
            <div class="header-left">
                <h1>Sprint - finanças pessoais</h1>
            </div>
            <div class="header-right">
                <button class="three-dots-btn">...</button>
                <!-- Menu suspenso com links -->
                <div class="dropdown-menu">
                    <h3><a href="logout.jsp?acao=telaLogout">Logout</a></h3>
                    <h3 id="dicasBtn" >Dicas para o Usuário</h3>
                </div>
            </div>
        </header>


        <!-- A janela modal -->
        <div id="myModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close">&times;</span>
                    <h2>Dicas para o Usuário</h2>
                </div>
                <div class="modal-body">
                    <ul>
                        <li>No campo mês: Indique o mês ao qual deseja registrar.</li>
                        <br>
                        <li>No campo valor receita: Coloque o valor adquirido no mês.</li>
                        <br>
                        <li>No campo Despesa fixa: Coloque o valor total das despesas que são consideradas "obrigatórias". Ex: Conta de luz, conta de água, alimentação, etc.</li>
                        <br>
                        <li>No campo Despesa Variável: Coloque o valor total gasto no mês.</li>
                        <br>
                        <li>No campo Notas: Adicione anotações. Ex: Anotações sobre o mês ou lembretes econômicos.</li>
                        <br>
                        <li>No campo nota: Coloque o valor que deseja economizar nesse mês.</li>
                    </ul>

                </div>
            </div>
        </div>




        <div class="form-container">
            <h1>Registro do Mês</h1>
            <!-- Exibir a mensagem de erro, caso exista -->
            <% if (erroCadastro != null) {%>
            <div style="color: red; background: #2f2841;">
                <%= erroCadastro%>
            </div>
            <% }%>

            <c:if test="${not empty erro}">
                <div class="alert alert-danger">
                    ${erro}
                </div>
            </c:if>
            <form action="tabelaDadosUsuarioSrv" method="POST">
                <input type="hidden" name="acao" value="<%= acao != null ? acao : "DadosFinanceirosInclusao"%>" />
                <input type="hidden" name="id" value="<%= id%>" />

                <div class="form-group">
                    <label for="mes">Mês</label>
                    <!-- Se for edição, o <select> ficará oculto e o valor será enviado pelo <input type="hidden"> -->
                    <% if (isEdicao) {%>
                    <!-- Input oculto para enviar o mês no modo de edição -->
                    <input type="hidden" name="mes" value="<%= mes != null ? mes : ""%>">
                    <div class="disabled-select">
                        <label><%= mes != null ? mes : "Nenhum mês selecionado"%></label> <!-- Mostra o mês selecionado -->
                    </div>
                    <% } else {%>
                    <!-- Caso não seja edição, o <select> estará visível e funcional -->
                    <select name="mes" required>
                        <option value="" disabled <%= mes == null ? "selected" : ""%>>Escolha o mês</option>
                        <option value="janeiro" <%= "janeiro".equals(mes) ? "selected" : ""%>>Janeiro</option>
                        <option value="fevereiro" <%= "fevereiro".equals(mes) ? "selected" : ""%>>Fevereiro</option>
                        <option value="março" <%= "março".equals(mes) ? "selected" : ""%>>Março</option>
                        <option value="abril" <%= "abril".equals(mes) ? "selected" : ""%>>Abril</option>
                        <option value="maio" <%= "maio".equals(mes) ? "selected" : ""%>>Maio</option>
                        <option value="junho" <%= "junho".equals(mes) ? "selected" : ""%>>Junho</option>
                        <option value="julho" <%= "julho".equals(mes) ? "selected" : ""%>>Julho</option>
                        <option value="agosto" <%= "agosto".equals(mes) ? "selected" : ""%>>Agosto</option>
                        <option value="setembro" <%= "setembro".equals(mes) ? "selected" : ""%>>Setembro</option>
                        <option value="outubro" <%= "outubro".equals(mes) ? "selected" : ""%>>Outubro</option>
                        <option value="novembro" <%= "novembro".equals(mes) ? "selected" : ""%>>Novembro</option>
                        <option value="dezembro" <%= "dezembro".equals(mes) ? "selected" : ""%>>Dezembro</option>
                    </select>
                    <% }%>
                </div>

                <div class="form-group">
                    <label for="receita">Valor da Receita (R$):</label>
                    <input type="text" id="receita" name="receita" value="<%= receita != null ? receita : ""%>" required>
                </div>

                <div class="form-group">
                    <label for="despesaFixa">Despesa Fixa (R$):</label>
                    <input type="text" id="despesaFixa" name="despesaFixa" value="<%= despesaFixa != null ? despesaFixa : ""%>" required>
                </div>

                <div class="form-group">
                    <label for="despesaVariavel">Despesa Variável (R$):</label>
                    <input type="text" id="despesaVariavel" name="despesaVariavel" value="<%= despesaVariavel != null ? despesaVariavel : ""%>" required>
                </div>

                <div class="form-group">
                    <label for="notas">Notas (opcional):</label>
                    <textarea id="notas" name="notas" rows="4" placeholder="Adicione aqui suas anotações financeiras..."><%= notas != null ? notas : ""%></textarea>
                </div>

                <div class="form-group">
                    <label for="metaEconomia">Meta de Economia (R$):</label>
                    <input type="text" id="metaEconomia" name="meta" value="<%= meta != null ? meta : ""%>" required>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn btn-submit">Confirmar</button>
                    <button type="button" onclick="history.go(-1);" class="btn btn-voltar">Voltar</button>
                </div>
            </form>
        </div>

        <script>
            // Script para abrir e fechar a modal/tela 3 pontinhos
            var modal = document.getElementById("myModal");
            var dicasBtn = document.getElementById("dicasBtn");
            var span = document.getElementsByClassName("close")[0];

            // Abrir o modal quando o usuário clicar em "Dicas para o Usuário"
            dicasBtn.onclick = function () {
                modal.style.display = "block";
            }

            // Fechar o modal quando o usuário clicar no "X"
            span.onclick = function () {
                modal.style.display = "none";
            }

            // Fechar o modal quando o usuário clicar fora da janela modal
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }

            // Script para abrir o menu suspenso
            var menu = document.querySelector('.header-right');
            var dropdownMenu = document.querySelector('.dropdown-menu');
            var threeDotsBtn = document.querySelector('.three-dots-btn');

            // Mostrar e esconder o menu suspenso
            threeDotsBtn.onclick = function () {
                dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
            }

            // Fechar o menu se o usuário clicar fora dele
            window.onclick = function (event) {
                if (!event.target.matches('.three-dots-btn') && !event.target.matches('.dropdown-menu')) {
                    dropdownMenu.style.display = 'none';
                }
            }
        </script>

    </body>
</html>
