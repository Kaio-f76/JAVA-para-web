<%-- 
    Document   : pesquisarPorInformacoes
    Created on : 3 de abr. de 2025, 15:01:46
    Author     : kaio
    
    MESMO CASO DA TELA exibirDadosFinanceiros.jsp
    VER DETALHES depois..

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pesquisa e Exibição de Dados</title>
    <link rel="stylesheet" href="css/stylePesquisa.css"> <!-- Seu arquivo de estilo -->
</head>

<body>
    <div class="container">
        <!-- Barra de pesquisa -->
        <header>
            <div class="header-left">
                <h1>O que busca ?</h1>
            </div>
            <div class="header-right">
                <input type="text" id="searchInput" class="search-input" placeholder="Pesquisar..." onkeyup="searchData()">
            </div>
        </header>

        <!-- Seção de resultados da pesquisa -->
        <div class="results-section">
            <!-- Aqui os resultados serão inseridos dinamicamente -->
            <div id="cardsContainer" class="cards-container">
                <!-- Cards de resultados da pesquisa serão gerados aqui -->
                 <%= request.getAttribute("resultados") %>
            </div>
        </div>
    </div>

    <script src="script.js"></script>
</body>

</html>
