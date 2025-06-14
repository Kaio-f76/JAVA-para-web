package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.DaoFactory;
import model.dao.InterfaceDao;
import model.loginUsuario;

/**
 *
 * @author kaio
 */
public class CadastroUsuarioSrv extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String acao = request.getParameter("DadosTelaCadastro");
            String id = request.getParameter("id");

            String nomeUsuario = request.getParameter("usuario");
            String emailUsuario = request.getParameter("email");
            String senhaUsuario = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarsenha");

            InterfaceDao dao = DaoFactory.novoLoginDAO();
            loginUsuario lu = null;
            RequestDispatcher rd = null;

            // Recuperar o usuário logado da sessão
            HttpSession session = request.getSession();
            loginUsuario usuarioLogado = (loginUsuario) session.getAttribute("usuarioLogado");
            /*if (usuarioLogado == null) {
                response.sendRedirect("login.jsp"); // Se não houver usuário logado, redireciona para a tela de login.
                return;
            }*/ 
            
            System.out.println("Acao chegando em CadastroUsuarioSrv: " + acao);
            
            switch (acao) {

                case "telaCadastro":

                    if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
                        request.setAttribute("erroCadastro", "O nome de usuário não pode ser vazio.");
                        request.setAttribute("nomeUsuario", nomeUsuario);
                        request.setAttribute("emailUsuario", emailUsuario);
                        request.setAttribute("senhaUsuario", senhaUsuario);
                        request.setAttribute("confirmarSenha", confirmarSenha);
                        rd = request.getRequestDispatcher("cadastro.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    if (emailUsuario == null || emailUsuario.trim().isEmpty()
                            || !validarEmail.validarEmail(emailUsuario)) {
                        request.setAttribute("erroCadastro", "O email informado é inválido.");
                        request.setAttribute("nomeUsuario", nomeUsuario);
                        request.setAttribute("emailUsuario", emailUsuario);
                        request.setAttribute("senhaUsuario", senhaUsuario);
                        request.setAttribute("confirmarSenha", confirmarSenha);
                        rd = request.getRequestDispatcher("cadastro.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    if (senhaUsuario == null || senhaUsuario.trim().isEmpty()) {
                        request.setAttribute("erroCadastro", "A senha não pode ser vazia.");
                        request.setAttribute("nomeUsuario", nomeUsuario);
                        request.setAttribute("emailUsuario", emailUsuario);
                        request.setAttribute("senhaUsuario", senhaUsuario);
                        request.setAttribute("confirmarSenha", confirmarSenha);
                        rd = request.getRequestDispatcher("cadastro.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    if (confirmarSenha == null || confirmarSenha.trim().isEmpty()) {
                        request.setAttribute("erroCadastro", "A confirmação de senha não pode ser vazia.");
                        request.setAttribute("nomeUsuario", nomeUsuario);
                        request.setAttribute("emailUsuario", emailUsuario);
                        request.setAttribute("senhaUsuario", senhaUsuario);
                        request.setAttribute("confirmarSenha", confirmarSenha);
                        rd = request.getRequestDispatcher("cadastro.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    if (!senhaUsuario.equals(confirmarSenha)) {
                        request.setAttribute("erroCadastro", "As senhas não coincidem.");
                        request.setAttribute("nomeUsuario", nomeUsuario);
                        request.setAttribute("emailUsuario", emailUsuario);
                        request.setAttribute("senhaUsuario", senhaUsuario);
                        request.setAttribute("confirmarSenha", confirmarSenha);
                        rd = request.getRequestDispatcher("cadastro.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    lu = new loginUsuario(nomeUsuario, emailUsuario, senhaUsuario);
                    try {
                        dao.incluir(lu);
                    } catch (Exception ex) {
                        System.out.println("Erro na inclusão do usuário -> emitido da class CadastroUsuarioSrv.java !"
                                + ex.getMessage());
                        Logger.getLogger(CadastroUsuarioSrv.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("erroCadastro", "Erro ao cadastrar o usuário.");
                        rd = request.getRequestDispatcher("cadastro.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    response.sendRedirect("index.html");
                    //rd = request.getRequestDispatcher("index.html");
                    //rd.forward(request, response);              
                    break;

                case "telaCadastroPre-Edicao":
                    // Passa os dados do usuário logado para a tela de edição
                    if (usuarioLogado != null) {
                        rd = request.getRequestDispatcher("configuracoesDoUsuario.jsp?DadosTelaCadastro=telaCadastroEdicao"
                                + "&id=" + usuarioLogado.getId()
                                + "&nomeUsuario=" + usuarioLogado.getNome()
                                + "&emailUsuario=" + usuarioLogado.getEmail()
                                + "&senhaUsuario=" + usuarioLogado.getSenha());
                    }
                    rd.forward(request, response);
                    break;

                case "telaCadastroEdicao":
                    // Atualizar os dados do usuário logado
                    if (usuarioLogado != null) {
                        usuarioLogado.setNome(nomeUsuario);
                        usuarioLogado.setEmail(emailUsuario);
                        usuarioLogado.setSenha(senhaUsuario);

                        try {
                            dao.editar(usuarioLogado);
                        } catch (Exception ex) {
                            request.setAttribute("erroCadastro", "Erro ao editar o usuário.");
                            rd = request.getRequestDispatcher("configuracoesDoUsuario.jsp");
                            rd.forward(request, response);
                            return;
                        }

                        // Realizar o logout após a edição
                        session.invalidate();

                        // Redireciona para uma tela informando que o login precisa ser feito novamente
                        request.setAttribute("msgLogout", "Seu perfil foi atualizado. Por favor, faça login novamente.");
                        rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                    }
                    break;

                case "telaCadastroExclusao":
                    // Excluir o usuário logado
                    if (usuarioLogado != null) {
                        try {
                            dao.excluir(usuarioLogado);
                            session.invalidate(); // Finaliza a sessão

                            // Redireciona para a página de logout
                            request.setAttribute("msgLogout", "Sua conta foi excluída. Faça login novamente.");
                            rd = request.getRequestDispatcher("login.jsp");
                            rd.forward(request, response);
                        } catch (Exception ex) {
                            request.setAttribute("erroCadastro", "Erro ao excluir o usuário.");
                            rd = request.getRequestDispatcher("configuracoesDoUsuario.jsp");
                            rd.forward(request, response);
                            return;
                        }
                    }
                    break;

                case "telaCadastroListagem":
                    //não faz sentido o usuario poder listar os usuarios é um cadastro pra cada.
                    //ps: mas depois coloco pra listar no terminal!
                    break;

                default:
                    response.sendRedirect("index.html");
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("index.html");
        }
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
