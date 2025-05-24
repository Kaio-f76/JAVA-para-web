<%-- 
    Document   : dashboardGestao
    Created on : 23 de mar. de 2025, 13:32:02
    Author     : kaio
--%>

<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession" %>
<%

    if (session == null || session.getAttribute("usuarioLogado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String listaHTML = (String) request.getAttribute("resultados");
    String listaHTMLEdicao = (String) request.getAttribute("resultadosEdicao");
    String listaHTMLEdicaoExclusao = (String) request.getAttribute("resultadosExclusao");
    String resultadosFiltrada = (String) request.getAttribute("resultadosFiltrada");

    List<Integer> receitas = (List<Integer>) request.getAttribute("receitas");
    List<Integer> despesas = (List<Integer>) request.getAttribute("despesas");
    List<String> meses = (List<String>) request.getAttribute("meses");
    System.out.println(meses);

    String nomePesquisado = request.getParameter("nomePesquisado");


%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="img/iconeDaPagina.ico" type="image/x-icon">
        <title>Sprint - finanças pessoais</title>
        <link href="https://cdn.jsdelivr.net/npm/chart.js" rel="stylesheet">
        <link rel="stylesheet" href="css/styles.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <script>

            if (window.history && window.history.pushState) {

                window.history.pushState(null, null, window.location.href);
                window.onpopstate = function () {
                window.location.replace("tabelaDadosUsuarioSrv?acao=telaResultados");
                
                };
            }
        </script>
    </head>
    <body>
        <div class="container">
            <!-- Cabeçalho -->
            <header>
                <div class="header-left">
                    <h1>Sprint - finanças pessoais</h1>
                </div>
                <div class="header-right">
                    <button class="three-dots-btn">...</button>
                    <!-- Menu suspenso com links <h3><a href="pesquisarPorInformacoes.jsp?acao=Pesquisar">Pesquisar informações</a></h3>-->
                    <div class="dropdown-menu">
                        <h3><a href="logout.jsp?acao=telaLogout">Logout</a></h3>
                        <h3><a href="dadosFinanceiros.jsp?acao=telaDadosFinanceiros">Cadastrar Novo Dado Financeiro</a></h3>
                        <h3><a href="configuracoesDoUsuario.jsp?acao=telaCadastroEdicao">Editar Configurações</a></h3>
                    </div>
                </div>
            </header>

            <!-- Gráfico altere o tamanho aqui -->
            <section class="chart-section">
                <canvas id="financeChart" width="600" height="200"></canvas>
            </section>

            <form action="tabelaDadosUsuarioSrv?acao=pesquisarPorNome" method="POST" class="search-form">
                <label for="mes">
                    <i class="fas fa-search"></i>  Mês:
                </label>
                <input type="text" name="mes" value="<%= nomePesquisado != null ? nomePesquisado : ""%>" required>
                <input type="submit" value="Pesquisar" />
                <button type="button" class="voltar-btn" onclick="window.history.back();">Voltar</button>
            </form>



            <!-- Tabela de Dados -->
            <section class="table-section">
                <table>
                    <thead>

                    </thead>
                    <!-- Verifica se a listaHTML não é null antes de exibir -->
                    <% if (listaHTML != null) {%>
                    <%= listaHTML%>
                    <% } %>

                    <!-- Verifica se a listaHTMLEdicao não é null antes de exibir -->
                    <% if (listaHTMLEdicao != null) {%>
                    <%= listaHTMLEdicao%>
                    <% } %>

                    <!-- Verifica se a listaHTMLEdicaoExclusao não é null antes de exibir -->
                    <% if (listaHTMLEdicaoExclusao != null) {%>
                    <%= listaHTMLEdicaoExclusao%>
                    <% }%>

                    <!-- Verifica se a listaHTMLEdicaoExclusao não é null antes de exibir -->
                    <% if (resultadosFiltrada != null) {%>
                    <%= resultadosFiltrada%>
                    <% }%>
                </table>

            </section>


            <script src="js/financias.js"></script>
            <!-- Barra de Progresso -->
            <section class="progress-section">
                <label for="progress-bar"><p id="saldo">Saldo: R$ 0.00</p></label>

                <progress id="progress-bar" value="50" max="100"></progress>             
                <p id="progress-text"></p>
                <p id="economia-em-relacao-meta">Economia em relação à meta: R$ 0,00</p>


            </section>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <!-- Barra de Progresso 
        <section class="progress-section">
            <label for="progress-bar">Economia</label>
            <progress id="progress-bar" value="0" max="100"></progress>
            <p id="progress-text">0% economizado</p>
        </section> -->



        <script>
        // Pega as listas de receitas e despesas passadas pelo Servlet
        const receitas = <%= new Gson().toJson(receitas)%>;
        const despesas = <%= new Gson().toJson(despesas)%>;
        const meses = <%= new Gson().toJson(meses)%>;
        

        const metas = <%= new Gson().toJson(request.getAttribute("metas")) %>;
        
        
        console.log('Metas:', metas); 
        console.log('Receitas:', receitas);
        console.log('Despesas:', despesas);
        console.log('Meses:', meses);
        

        initChart(receitas, despesas, meses, metas);
        </script>

        <script>
            document.querySelector('.three-dots-btn').addEventListener('click', function () {
                const dropdownMenu = document.querySelector('.dropdown-menu');
                // Alterna a visibilidade do menu
                dropdownMenu.style.display = (dropdownMenu.style.display === 'block') ? 'none' : 'block';
            });

            // Fecha o menu se o usuário clicar em qualquer outra parte da tela
            window.addEventListener('click', function (event) {
                const dropdownMenu = document.querySelector('.dropdown-menu');
                const threeDotsButton = document.querySelector('.three-dots-btn');
                if (!threeDotsButton.contains(event.target) && !dropdownMenu.contains(event.target)) {
                    dropdownMenu.style.display = 'none';
                }
            });
        </script> 
    </body>
</html>
