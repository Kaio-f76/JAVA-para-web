<%-- 
    Document   : ExibirResultados
    Created on : 19 de mar. de 2025, 18:40:55
    Author     : kaio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

    <%
        System.out.println("Entrou na página exibirResultados.jsp");
    %>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestão Financeira Pessoal</title>
        <style>
            body {
                margin: 0;
                font-family: 'Noto Sans', sans-serif;
                background-color: #201b2c;
                color: #77ffc0;
            }

            body * {
                box-sizing: border-box;
            }

            header {
                background-color: #2f2841;
                color: #77ffc0;
                padding: 20px;
                text-align: center;
            }

            .container {
                max-width: 1200px;
                margin: 20px auto;
                padding: 20px;
                background-color: #2f2841;
                border-radius: 20px;
                box-shadow: 0px 10px 40px #00000056;
            }

            h1, h2 {
                color: #77ffc0;
            }

            .summary-section, .chart-section, .recent-transactions, .goals-section {
                margin-bottom: 20px;
            }

            .summary-item {
                display: flex;
                justify-content: space-between;
                padding: 10px;
                border: 1px solid #514869;
                border-radius: 10px;
                background-color: #514869;
                margin-bottom: 10px;
                color: #f0ffffde;
            }

            .summary-item span {
                font-weight: bold;
            }

            .positive {
                color: #00ff88;
            }

            .negative {
                color: #E74C3C;
            }

            table {
                width: 100%;
                border-collapse: collapse;
            }

            th, td {
                border: 1px solid #514869;
                padding: 8px;
                text-align: left;
            }

            th {
                background-color: #4B0082;
                color: white;
            }

            td {
                color: #f0ffff94;
            }

            .chart {
                text-align: center;
                margin-bottom: 20px;
            }

            .goal-progress {
                background-color: #514869;
                height: 20px;
                width: 100%;
                border-radius: 10px;
                overflow: hidden;
                margin-top: 5px;
            }

            .goal-progress-inner {
                height: 100%;
                background-color: #00ff88;
                width: 70%;
            }

            footer {
                text-align: center;
                margin-top: 20px;
                padding: 10px;
                background-color: #2f2841;
                color: #77ffc0;
            }

        </style>
    </head>
    <body>
        <header>
            <h1>Gerencie suas finanças de forma simples e eficaz</h1>
        </header>

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
                            <th>Data</th>
                            <th>Descrição</th>
                            <th>Categoria</th>
                            <th>Valor</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td></td> 
                            <td></td>  
                            <td>Alimentação</td>
                            <td></td> 
                        </tr>
                        <tr>
                            <td></td> 
                            <td></td> 
                            <td>Receita</td>
                            <td></td> 
                        </tr>
                        <tr>
                            <td></td> 
                            <td></td> 
                            <td>Vestuário</td>
                            <td></td> 
                        </tr>
                        <tr>
                            <td></td> 
                            <td></td> 
                            <td>Tarifas</td>
                            <td></td> 
                        </tr>
                        <tr>
                            <td></td> 
                            <td></td> 
                            <td>Transporte</td>
                            <td></td> 
                        </tr>
                        <tr>
                            <td></td> 
                            <td></td> 
                            <td>Lazer</td>
                            <td></td> 
                        </tr>
                        <tr>
                            <td></td> 
                            <td></td> 
                            <td>Higiene</td>
                            <td></td> 
                        </tr>
                    </tbody>
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

        <footer>
            <p>© 2025 Gestão Financeira Pessoal. Todos os direitos reservados.</p>
        </footer>
    </body>    
</html>