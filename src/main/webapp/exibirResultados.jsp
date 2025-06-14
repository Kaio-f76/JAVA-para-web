<%-- 
    Document   : ExibirResultados
    Created on : 19 de mar. de 2025, 18:40:55
    Author     : kaio
    
    ATENÇÃO ESSA TELA NÃO FAZ PARTE DO PROJETO PRINCIPAL!
    ESTÁ PARA FINALIZADES FUTURAS, talvez...

--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%


    if (session == null || session.getAttribute("usuarioLogado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }


    String listaHTML = (String) request.getAttribute("resultados");
    String listaHTMLEdicao = (String) request.getAttribute("resultadosEdicao");
    String listaHTMLEdicaoExclusao = (String) request.getAttribute("resultadosExclusao");

%>

<!DOCTYPE html>
<html lang="pt-br">    
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/exibirResultados.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
        <link rel="shortcut icon" href="img/iconeDaPagina.ico" type="image/x-icon">
        <title>Gestão Financeira Pessoal</title>
        <script>
 
            if (window.history && window.history.pushState) {

                window.history.pushState(null, null, window.location.href);
                window.onpopstate = function () {
                window.location.replace("dashboardGestao.jsp"); 
                };
            }
        </script>
        <style>
            /* Estilo para os botões de editar e excluir */
            .botao-editar, .botao-excluir {
                width: auto; /* Ajuste a largura para se adaptar ao conteúdo */
                padding: 6px 0;
                margin: 5px 0;
                border: none;
                border-radius: 6px;
                outline: none;
                text-transform: uppercase;
                font-weight: 800;
                letter-spacing: 2px;
                color: #2b134b;
                background: #00ff88;
                cursor: pointer;
                box-shadow: 0px 4px 10px -5px #00ff8052; /* Efeito de sombra mais sutil */
                transition: all 0.3s ease;
            }

            /* Efeito para o botão de editar ao passar o mouse */
            .botao-editar:hover {
                background-color: #0056b3;
                transform: scale(1); /* Removido o efeito de aumento */
                box-shadow: 0px 6px 20px -10px rgba(0, 255, 136, 0.6);
            }

            /* Estilo para o botão de excluir */
            .botao-excluir {
                background: #ff0044;
            }

            /* Efeito para o botão de excluir ao passar o mouse */
            .botao-excluir:hover {
                background-color: #b30000;
                transform: scale(1); /* Removido o efeito de aumento */
                box-shadow: 0px 6px 20px -10px rgba(255, 0, 68, 0.6);
            }

            /* Efeito nos ícones quando passar o mouse nos botões */
            .botao-editar i, .botao-excluir i {
                transition: transform 0.3s ease, color 0.3s ease;
            }

            /* Aumentar o tamanho do ícone e mudar a cor ao passar o mouse */
            .botao-editar:hover i, .botao-excluir:hover i {
                color: #ffffff;
                transform: scale(1.2);
            }

            /* Ajustar a largura das células da tabela */
            td {
                width: 120px; /* Ajuste a largura da célula que contém os botões */
            }


        </style>


    </head>
    <body>
        <a></a>
        <header>
            <h1>Gerencie suas finanças de forma simples e eficaz</h1>
        </header>
        <form action="tabelaDadosUsuarioSrv" method="POST">           
            <div class="container">
                <section class="summary-section">
                    <h2>Resumo Financeiro</h2>
                    <div class="summary-item">
                        <span>Saldo Atual:</span>
                        <span class="positive">R$ </span>
                    </div>
                    <div class="summary-item">
                        <span>Receitas (Mês Atual):</span>
                        <span class="positive">R$</span>
                    </div>
                    <div class="summary-item">
                        <span>Despesas (Mês Atual):</span>
                        <span class="negative">R$</span>
                    </div>
                </section>

                <section class="chart-section">
                    <h2>Gráfico de Gastos</h2>
                    <div class="chart">
                        <p>Aqui estará o gráfico interativo de comparação de gastos entre categorias</p>
                    </div>
                </section>

                <section class="recent-transactions">
                    <h2>Transações Recentes</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Nome Do Gasto</th>
                                <th>Despesa</th>
                                <th>Breve Descrição</th>
                                <th>Fonte de renda</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%= listaHTML%>
                        </tbody>
                        <%= listaHTMLEdicao%>
                        <%= listaHTMLEdicaoExclusao%>                            
                    </table>
                </section>
                <section class="goals-section">
                    <h2>Metas Financeiras</h2>
                    <p>Economizar R$ até dezembro de 2025</p>
                    <div class="goal-progress">
                        <div class="goal-progress-inner"></div>
                    </div>
                </section>
            </div>
        </form>
        <footer>
            <p>© 2025 Gestão Financeira Pessoal. Todos os direitos reservados.</p>
        </footer>
    </body> 



</html>