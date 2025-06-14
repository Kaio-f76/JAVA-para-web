package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dadosFinanceirosUsuario;
import model.dao.DadosFinanceirosDaoJpa;
import model.dao.DaoFactory;
import model.dao.InterfaceDao;
import model.loginUsuario;

/**
 *
 * @author kaio
 */
public class tabelaDadosUsuarioSrv extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {

            String id = request.getParameter("id");

            String acao = request.getParameter("acao");

            String mes = request.getParameter("mes");
            String receita = request.getParameter("receita");
            String despesaFixa = request.getParameter("despesaFixa");
            String despesaVariavel = request.getParameter("despesaVariavel");
            String notas = request.getParameter("notas");
            String meta = request.getParameter("meta");

            String nomePesquisado = request.getParameter("nomePesquisado");

            // Recuperar o usuário logado da sessão
            HttpSession session = request.getSession();
            loginUsuario usuarioLogado = (loginUsuario) session.getAttribute("usuarioLogado");

            if (usuarioLogado == null) {
                // Caso não tenha usuário logado, redireciona para o login
                response.sendRedirect("login.jsp");
                return;
            }

            // Cria o objeto de dados financeiros
            dadosFinanceirosUsuario dados = new dadosFinanceirosUsuario();

            /*
            // Associa o usuário logado aos dados financeiros
            dados.setFonteDeRenda(fonteDeRenda);
            dados.setDespesa(despesa);
            dados.setDescricaoDosGastos(descricaoDosGastos);
            dados.setNomeDoGasto(nomeDoGasto);
            dados.setLoginUsuario(usuarioLogado); // Associando o usuário logado
             */
            System.out.println("Acao de tabelaDadosUsuarioSrv = " + acao);

            System.out.println("mes = " + mes);
            System.out.println("receita = " + receita);
            System.out.println("despesaFixa = " + despesaFixa);
            System.out.println("despesaVariavel = " + despesaVariavel);
            System.out.println("notas = " + notas);
            System.out.println("meta = " + despesaVariavel);
            System.out.println("Valor da pesquisa chegando em tabelaDadosUsuarioSrv " + nomePesquisado);

            System.out.println();

            System.out.println("usuariologado = " + usuarioLogado);

            System.out.println();

            System.out.println("USUARIO LOGADO NO MOMENTO = " + usuarioLogado.getId());

            DadosFinanceirosDaoJpa dao = new DadosFinanceirosDaoJpa();
            RequestDispatcher rd = null;

            //dadosFinanceirosUsuario dfu = null;
            switch (acao) {

                //inclusao
                case "telaDadosFinanceiros":
                    dados = new dadosFinanceirosUsuario(mes, receita, despesaFixa, despesaVariavel, notas, meta, usuarioLogado);

                    // Verificar se o mês já existe para o usuário
                    if (verificarMesDuplicado(mes, usuarioLogado)) {
                        // Caso o mês já exista, redireciona de volta com uma mensagem de erro
                        request.setAttribute("erro", "Mês já cadastrado. Não é possível adicionar dados financeiros para o mesmo mês.\n");
                        rd = request.getRequestDispatcher("dadosFinanceiros.jsp?acao=telaDadosFinanceiros"); // Redireciona para a página de cadastro
                        rd.forward(request, response);
                        return; 
                    }

                    try {
                        dao.incluir(dados);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                    response.sendRedirect("tabelaDadosUsuarioSrv?acao=telaResultados");

                    break;

                case "pre-edicao":
                    dados = dao.pesquisarPorId(Integer.parseInt(id));

                    rd = request.getRequestDispatcher("dadosFinanceiros.jsp?acao=edicao"
                            + "&id=" + dados.getId()
                            + "&mes=" + dados.getMes()
                            + "&receita=" + dados.getReceita()
                            + "&despesaFixa=" + dados.getDespesaFixa()
                            + "&despesaVariavel=" + dados.getDespesaVariavel()
                            + "&notas=" + dados.getNotas()
                            + "&meta=" + dados.getMeta());
                    rd.forward(request, response);
                    break;

                case "edicao":
                    dados = new dadosFinanceirosUsuario(mes, receita, despesaFixa, despesaVariavel, notas, meta, usuarioLogado);

                    dados.setId(Integer.parseInt(id));

                    try {
                        dao.editar(dados);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    // Alteração: Buscar dados financeiros do usuário logado
                    List<dadosFinanceirosUsuario> listaDadosFinanceiroUsuarioEditar = dao.listarPorUsuarioId(usuarioLogado.getId());
                    String listaHTMLEdicao = gerarTabelaResultados(listaDadosFinanceiroUsuarioEditar);
                    
                    //limpar cache
                    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);

 
                    request.setAttribute("resultadosEdicao", listaHTMLEdicao);
                    response.sendRedirect("tabelaDadosUsuarioSrv?acao=telaResultados");

                    break;

                case "exclusao":
                    try {
                        dados = new dadosFinanceirosUsuario();
                        dados.setId(Integer.parseInt(id));
                        dao.excluir(dados);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                    // Alteração: Buscar dados financeiros do usuário logado
                    List<dadosFinanceirosUsuario> listaDadosFinanceiroUsuarioExcluir = dao.listarPorUsuarioId(usuarioLogado.getId());
                    String listaHTMLEdicaoExclusao = gerarTabelaResultados(listaDadosFinanceiroUsuarioExcluir);


                    request.setAttribute("resultadosExclusao", listaHTMLEdicaoExclusao);
                    response.sendRedirect("tabelaDadosUsuarioSrv?acao=telaResultados");

                    break;

                case "telaResultados":
                    //System.out.println("ID do loginUsuario associado: " + dados.getLoginUsuario().getId());

                    // Alteração: Buscar dados financeiros do usuário logado
                    List<dadosFinanceirosUsuario> listaDadosFinanceiroUsuario = dao.listarPorUsuarioId(usuarioLogado.getId());
                    String listaHTML = gerarTabelaResultados(listaDadosFinanceiroUsuario);

                    // Criar listas para armazenar os dados para o gráfico
                    List<Integer> receitas = new ArrayList<>();
                    List<Integer> despesas = new ArrayList<>();
                    Set<String> mesesCadastrados = new HashSet<>();

                    // Populando as listas com os valores de receita e despesa
                    for (dadosFinanceirosUsuario dadosFinanceiro : listaDadosFinanceiroUsuario) {
                        // Convertendo as strings para inteiros e adicionando nas listas
                        try {
                            int receitaInt = Integer.parseInt(dadosFinanceiro.getReceita());
                            int despesaFixaInt = Integer.parseInt(dadosFinanceiro.getDespesaFixa());
                            int despesaVariavelInt = Integer.parseInt(dadosFinanceiro.getDespesaVariavel());
                            mesesCadastrados.add(dadosFinanceiro.getMes());

                            // Calculando as despesas totais (fixas + variáveis)
                            int despesaTotal = despesaFixaInt + despesaVariavelInt;

                            // Adicionando à lista de receitas e despesas
                            receitas.add(receitaInt);
                            despesas.add(despesaTotal);
                        } catch (NumberFormatException e) {
                            // Trate a exceção caso algum valor não seja um número válido
                            System.out.println("Erro ao converter valor para inteiro: " + e.getMessage());
                            // Adiciona valores padrão ou faz outra ação necessária
                            receitas.add(0);
                            despesas.add(0);
                        }
                    }

                    List<String> mesesList = new ArrayList<>(mesesCadastrados);

                    // Lista que representa os meses em ordem cronológica
                    List<String> mesesOrdenados = Arrays.asList(
                            "janeiro", "fevereiro", "março", "abril", "maio", "junho",
                            "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
                    );

                    // Ordenar os meses cadastrados com base na ordem cronológica
                    mesesList.sort((m1, m2) -> {
                        int pos1 = mesesOrdenados.indexOf(m1);
                        int pos2 = mesesOrdenados.indexOf(m2);
                        return Integer.compare(pos1, pos2);
                    });

                    // envia os metas para serem pegos pelo js
                    List<String> metas = new ArrayList<>();
                    for (dadosFinanceirosUsuario dadosMeta : listaDadosFinanceiroUsuario) {
                        metas.add(dadosMeta.getMeta()); 
                    }

                    request.setAttribute("metas", metas);
                    request.setAttribute("meses", mesesList);
                    request.setAttribute("receitas", receitas);
                    request.setAttribute("despesas", despesas);
                    request.setAttribute("resultados", listaHTML);
                    
                    rd = request.getRequestDispatcher("dashboardGestao.jsp?acao= ");
                    rd.forward(request, response);
                    break;

                case "pesquisarPorNome":

                    // Alteração: Buscar dados financeiros do usuário logado
                    List<dadosFinanceirosUsuario> ListaDadosFinanceiroUsuario = dao.listarPorUsuarioId(usuarioLogado.getId());
                    String listaHTML2 = gerarTabelaResultados(ListaDadosFinanceiroUsuario);

                    // Criar listas para armazenar os dados para o gráfico
                    List<Integer> Receitas = new ArrayList<>();
                    List<Integer> Despesas = new ArrayList<>();
                    Set<String> MesesCadastrados = new HashSet<>();

                    // Populando as listas com os valores de receita e despesa
                    for (dadosFinanceirosUsuario dadosFinanceiro : ListaDadosFinanceiroUsuario) {
                        // Convertendo as strings para inteiros e adicionando nas listas
                        try {
                            int receitaInt = Integer.parseInt(dadosFinanceiro.getReceita());
                            int despesaFixaInt = Integer.parseInt(dadosFinanceiro.getDespesaFixa());
                            int despesaVariavelInt = Integer.parseInt(dadosFinanceiro.getDespesaVariavel());
                            MesesCadastrados.add(dadosFinanceiro.getMes());

                            // Calculando as despesas totais (fixas + variáveis)
                            int despesaTotal = despesaFixaInt + despesaVariavelInt;

                            // Adicionando à lista de receitas e despesas
                            Receitas.add(receitaInt);
                            Despesas.add(despesaTotal);
                        } catch (NumberFormatException e) {
                            // Trate a exceção caso algum valor não seja um número válido
                            System.out.println("Erro ao converter valor para inteiro: " + e.getMessage());
                            // Adiciona valores padrão ou faz outra ação necessária
                            Receitas.add(0);
                            Despesas.add(0);
                        }
                    }

                    List<String> MesesList = new ArrayList<>(MesesCadastrados);

                    // Lista que representa os meses em ordem cronológica
                    List<String> MesesOrdenados = Arrays.asList(
                            "janeiro", "fevereiro", "março", "abril", "maio", "junho",
                            "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
                    );

                    // Ordenar os meses cadastrados com base na ordem cronológica
                    MesesList.sort((m1, m2) -> {
                        int pos1 = MesesOrdenados.indexOf(m1);
                        int pos2 = MesesOrdenados.indexOf(m2);
                        return Integer.compare(pos1, pos2);
                    });
                    
                    

                    List<String> Metas = new ArrayList<>();
                    for (dadosFinanceirosUsuario dadosMeta : ListaDadosFinanceiroUsuario) {
                        Metas.add(dadosMeta.getMeta()); 
                    }


                    request.setAttribute("metas", Metas);                                                        
                    request.setAttribute("meses", MesesList);
                    request.setAttribute("receitas", Receitas);
                    request.setAttribute("despesas", Despesas);


                    String Mes = request.getParameter("mes");

                    HttpSession sessionFiltrada = request.getSession();
                    loginUsuario usuarioLogadoFiltragem = (loginUsuario) session.getAttribute("usuarioLogado");

                    /*
                    if (usuarioLogadoFiltragem != null) {
                        int usuarioId = usuarioLogadoFiltragem.getId();
                        request.setAttribute("resultadosFiltrada", listagemFiltrada(mes, usuarioId));  // Passando o ID do usuário
                        rd = request.getRequestDispatcher("dashboardGestao.jsp?acao=");
                        rd.forward(request, response);
                    } else {
                        // Caso o usuário não esteja logado
                        response.sendRedirect("login.jsp");
                    }
                     */
                    if (usuarioLogadoFiltragem != null) {
                        int loginUsuarioId = usuarioLogadoFiltragem.getId();  // Obtendo o ID do usuário logado
                        try {
                            System.out.println("id do usuario lista pesquisa: " + loginUsuarioId);
                            // Chama o método passando o mês e o ID do usuário
                            List<dadosFinanceirosUsuario> resultados = dao.filtrarPorNome(mes, loginUsuarioId);

                            String resultadosFiltrada = listagemFiltrada(resultados);

                            request.setAttribute("resultadosFiltrada", resultadosFiltrada); 
                        } catch (Exception ex) {
                            System.out.println("Erro ao buscar dados financeiros: " + ex.getMessage());
                        }

                        rd = request.getRequestDispatcher("dashboardGestao.jsp");
                        rd.forward(request, response);
                    } else {
                        // Caso o usuário não esteja logado
                        response.sendRedirect("login.jsp");
                    }

                    break;

            }
        } catch (Exception ex) {
            Logger.getLogger(tabelaDadosUsuarioSrv.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Gera a tabela de resultados em HTML para exibir os dados financeiros.
     *
     * @param listaDadosFinanceiroUsuario Lista de dados financeiros do usuário
     * @return String com a tabela gerada em HTML
     */
    private String gerarTabelaResultados(List<dadosFinanceirosUsuario> listaDadosFinanceiroUsuario) {

        // Lista que representa a ordem dos meses no ano
        List<String> meses = Arrays.asList(
                "janeiro", "fevereiro", "março", "abril", "maio", "junho",
                "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
        );

        // Ordenar a lista de dados financeiros pela coluna mês (como string)
        listaDadosFinanceiroUsuario.sort((d1, d2) -> {
            int mes1 = meses.indexOf(d1.getMes());  // Obter o índice do mês 1
            int mes2 = meses.indexOf(d2.getMes());  // Obter o índice do mês 2

            // Se algum mês for inválido (não encontrado na lista), coloca no final
            if (mes1 == -1 || mes2 == -1) {
                return Integer.compare(mes1, mes2);
            }

            // Compara as posições dos meses na lista
            return Integer.compare(mes1, mes2);
        });
        StringBuilder listaHTML = new StringBuilder();

        // Cor do cabeçalho (também aplicada ao contorno da tabela)
        String corCabecalho = "#2c3e50";

        // Início da tabela
        listaHTML.append("<table style='border-collapse: collapse; width: 100%; border: 1px solid ").append(corCabecalho).append(";'>")
                .append("<thead style='background-color: ").append(corCabecalho).append(";'>")
                .append("<tr>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Mês</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Receita</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Despesa Fixa</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Despesa Variável</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Notas</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Meta</th>")
                .append("<th colspan='2' style='padding: 8px; text-align: center; border: 1px solid ").append(corCabecalho).append(";'>Ações</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        for (dadosFinanceirosUsuario dadosFinanceirosUsuario : listaDadosFinanceiroUsuario) {
            listaHTML.append("<tr>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append(dadosFinanceirosUsuario.getMes()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getReceita()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getDespesaFixa()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getDespesaVariavel()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append(dadosFinanceirosUsuario.getNotas()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getMeta()).append("</td>")
                    .append("<td colspan='2' style='padding: 8px; text-align: center; border: 1px solid ").append(corCabecalho).append(";'>")
                    .append("<form action='tabelaDadosUsuarioSrv?acao=pre-edicao' method='POST' style='display: inline;'>")
                    .append("<input type='hidden' name='id' value='").append(dadosFinanceirosUsuario.getId()).append("'>")
                    .append("<button type='submit' class='botao-editar' style='padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; margin-right: 10px;'><i class='fa fa-edit'></i> Editar</button>")
                    .append("</form>")
                    .append("<form action='tabelaDadosUsuarioSrv?acao=exclusao' method='POST' style='display: inline;'>")
                    .append("<input type='hidden' name='id' value='").append(dadosFinanceirosUsuario.getId()).append("'>")
                    .append("<button type='submit' class='botao-excluir' style='padding: 5px 10px; background-color: #f44336; color: white; border: none; cursor: pointer;'><i class='fa fa-trash'></i> Excluir</button>")
                    .append("</form>")
                    .append("</td>")
                    .append("</tr>");
        }
        // Fechando a tabela
        listaHTML.append("</tbody>")
                .append("</table>");
        return listaHTML.toString();
    }

    // Método para verificar se o mês já existe para o usuário logado
    private boolean verificarMesDuplicado(String mes, loginUsuario usuarioLogado) throws Exception {
        DadosFinanceirosDaoJpa dao = new DadosFinanceirosDaoJpa();

        // Buscar os dados financeiros do usuário logado para o mês fornecido
        List<dadosFinanceirosUsuario> listaDados = dao.listarPorUsuarioId(usuarioLogado.getId());

        for (dadosFinanceirosUsuario dados : listaDados) {
            if (dados.getMes().equals(mes)) {
                return true; // Mês já existe
            }
        }
        return false; // Mês não existe
    }

    /* codigo testado em outras condições
    
    private String listagemFiltrada(String nome, int id) {
        StringBuilder listaHTMLFiltrada = new StringBuilder();
        List<dadosFinanceirosUsuario> listaDadosFinanceiroUsuarioFiltrada = null;
        DadosFinanceirosDaoJpa dao = new DadosFinanceirosDaoJpa();

        try {
            // Filtra por nome
            listaDadosFinanceiroUsuarioFiltrada = dao.filtrarPorNome(nome, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // Cor do cabeçalho (também aplicada ao contorno da tabela)
        String corCabecalho = "#2c3e50";

        // Início da tabela
        listaHTMLFiltrada.append("<table style='border-collapse: collapse; width: 100%; border: 1px solid ").append(corCabecalho).append(";'>")
                .append("<thead style='background-color: ").append(corCabecalho).append(";'>")
                .append("<tr>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Mês</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Receita</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Despesa Fixa</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Despesa Variável</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Notas</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Meta</th>")
                .append("<th colspan='2' style='padding: 8px; text-align: center; border: 1px solid ").append(corCabecalho).append(";'>Ações</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        for (dadosFinanceirosUsuario dadosFinanceirosUsuario : listaDadosFinanceiroUsuarioFiltrada) {
            listaHTMLFiltrada.append("<tr>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append(dadosFinanceirosUsuario.getMes()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getReceita()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getDespesaFixa()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getDespesaVariavel()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append(dadosFinanceirosUsuario.getNotas()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getMeta()).append("</td>")
                    .append("<td colspan='2' style='padding: 8px; text-align: center; border: 1px solid ").append(corCabecalho).append(";'>")
                    .append("<form action='tabelaDadosUsuarioSrv?acao=pre-edicao' method='POST' style='display: inline;'>")
                    .append("<input type='hidden' name='id' value='").append(dadosFinanceirosUsuario.getId()).append("'>")
                    .append("<button type='submit' class='botao-editar' style='padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; margin-right: 10px;'><i class='fa fa-edit'></i> Editar</button>")
                    .append("</form>")
                    .append("<form action='tabelaDadosUsuarioSrv?acao=exclusao' method='POST' style='display: inline;'>")
                    .append("<input type='hidden' name='id' value='").append(dadosFinanceirosUsuario.getId()).append("'>")
                    .append("<button type='submit' class='botao-excluir' style='padding: 5px 10px; background-color: #f44336; color: white; border: none; cursor: pointer;'><i class='fa fa-trash'></i> Excluir</button>")
                    .append("</form>")
                    .append("</td>")
                    .append("</tr>");
        }
        // Fechando a tabela
        listaHTMLFiltrada.append("</tbody>")
                .append("</table>");
        return listaHTMLFiltrada.toString();
    }
     */
    private String listagemFiltrada(List<dadosFinanceirosUsuario> listaDadosFinanceiroUsuarioFiltrada) {
        StringBuilder listaHTMLFiltrada = new StringBuilder();

        // Cor do cabeçalho (também aplicada ao contorno da tabela)
        String corCabecalho = "#2c3e50";

        // Início da tabela
        listaHTMLFiltrada.append("<table style='border-collapse: collapse; width: 100%; border: 1px solid ").append(corCabecalho).append(";'>")
                .append("<thead style='background-color: ").append(corCabecalho).append(";'>")
                .append("<tr>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Mês</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Receita</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Despesa Fixa</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Despesa Variável</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Notas</th>")
                .append("<th style='padding: 8px; text-align: left; border: 1px solid ").append(corCabecalho).append(";'>Meta</th>")
                .append("<th colspan='2' style='padding: 8px; text-align: center; border: 1px solid ").append(corCabecalho).append(";'>Ações</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        // Gerar as linhas da tabela com os dados filtrados
        for (dadosFinanceirosUsuario dadosFinanceirosUsuario : listaDadosFinanceiroUsuarioFiltrada) {
            listaHTMLFiltrada.append("<tr>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append(dadosFinanceirosUsuario.getMes()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getReceita()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getDespesaFixa()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getDespesaVariavel()).append("</a>").append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append(dadosFinanceirosUsuario.getNotas()).append("</td>")
                    .append("<td style='padding: 8px; border: 1px solid ").append(corCabecalho).append(";'>").append("<a>R$:").append(dadosFinanceirosUsuario.getMeta()).append("</td>")
                    .append("<td colspan='2' style='padding: 8px; text-align: center; border: 1px solid ").append(corCabecalho).append(";'>")
                    .append("<form action='tabelaDadosUsuarioSrv?acao=pre-edicao' method='POST' style='display: inline;'>")
                    .append("<input type='hidden' name='id' value='").append(dadosFinanceirosUsuario.getId()).append("'>")
                    .append("<button type='submit' class='botao-editar' style='padding: 5px 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; margin-right: 10px;'><i class='fa fa-edit'></i> Editar</button>")
                    .append("</form>")
                    .append("<form action='tabelaDadosUsuarioSrv?acao=exclusao' method='POST' style='display: inline;'>")
                    .append("<input type='hidden' name='id' value='").append(dadosFinanceirosUsuario.getId()).append("'>")
                    .append("<button type='submit' class='botao-excluir' style='padding: 5px 10px; background-color: #f44336; color: white; border: none; cursor: pointer;'><i class='fa fa-trash'></i> Excluir</button>")
                    .append("</form>")
                    .append("</td>")
                    .append("</tr>");
        }
        // Fechando a tabela
        listaHTMLFiltrada.append("</tbody>")
                .append("</table>");
        return listaHTMLFiltrada.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
