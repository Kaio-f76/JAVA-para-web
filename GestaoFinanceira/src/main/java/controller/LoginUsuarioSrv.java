/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dao.InterfaceDao;
import model.dao.UsuarioDaojpa;
import model.loginUsuario;

/**
 *
 * @author kaio
 */
public class LoginUsuarioSrv extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {

            String acao = request.getParameter("DadosTelaLogin");
            String nomeUsuario = request.getParameter("usuario");
            String senhaUsuario = request.getParameter("senha");

            System.out.println("dado campo acao LoginUsuarioSrv = " + acao);

            System.out.println("\n" + "Antes - Dados coletados em login.jsp" + "\n" + "nome: " + nomeUsuario + "\n"
                    + "Senha: " + senhaUsuario + "\n"
            );

            switch (acao) {

                case "telaLogin":

                    if (nomeUsuario == null || nomeUsuario.trim().isEmpty() || senhaUsuario == null || senhaUsuario.trim().isEmpty()) {
                        request.setAttribute("erroLogin", "Por favor, preencha ambos os campos.");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    InterfaceDao<loginUsuario> dao = new UsuarioDaojpa();

                    List<loginUsuario> usuarios = dao.filtrarPorNomeESenha(nomeUsuario,senhaUsuario);

                    if (usuarios.isEmpty()) {
                        request.setAttribute("erroLogin", "Nome de usuário ou senha inválidos.");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    loginUsuario usuarioEncontrado = usuarios.get(0);

                    if (!usuarioEncontrado.getSenha().equals(senhaUsuario)) {
                        request.setAttribute("erroLogin", "Nome de usuário ou senha inválidos.");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogado", usuarioEncontrado);

                    System.out.println("\n" + "Usuario autorizado!" + "\n");

                    RequestDispatcher rd = request.getRequestDispatcher("exibirResultados.jsp");
                    rd.forward(request, response);

                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erroLogin", "Ocorreu um erro ao processar sua solicitação. Tente novamente.");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
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
