package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            
            //RequestDispatcher index = null;
            //index = request.getRequestDispatcher("index.html");
            //index.forward(request, response);

            String acao = request.getParameter("DadosTelaCadastro");

            String nomeUsuario = request.getParameter("usuario");
            String emailUsuario = request.getParameter("email");
            String senhaUsuario = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarsenha");

            InterfaceDao dao = DaoFactory.novoLoginDAO();
            loginUsuario lu = null;
            RequestDispatcher rd = null;

            System.out.println("\n" + "Antes - Dados coletados em cadastro.jsp " + "\n" + "nome: " + nomeUsuario + "\n"
                    + "email: " + emailUsuario + "\n" + "senha: " + senhaUsuario + "\n"
                    + "confirmar senha: " + confirmarSenha + "\n"
            );

            //nomeUsuario = "josé";
            //emailUsuario = "jose@gmail.com";
            //senhaUsuario = "1234";
            //confirmarSenha = "1234";
            System.out.println(acao);

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

                   
                    rd = request.getRequestDispatcher("index.html");
                    rd.forward(request, response);

              
                    break;
                       

            }

        } catch (Exception ex) {
            RequestDispatcher rd = null;
            rd = request.getRequestDispatcher("index.html");
            rd.forward(request, response);
            System.out.println("ERRO emitido da class CadastroUsuarioSrv.java !");
            Logger.getLogger(CadastroUsuarioSrv.class.getName()).log(Level.SEVERE, null, ex);
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
