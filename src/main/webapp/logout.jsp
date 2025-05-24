<%-- 
    Document   : logout
    Created on : 23 de mar. de 2025, 13:42:40
    Author     : kaio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Desabilitar cache no navegador
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session != null && session.getAttribute("usuarioLogado") != null) {
%>

<!-- Link de logout com confirmação em JavaScript <a href="javascript:void(0);" onclick="confirmLogout()">Sair</a> -->

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="img/iconeDaPagina.ico" type="image/x-icon">
    <title>Logout</title>
</head>
<body>
    <div style=" background: #202738" class="main-login">
        <div class="right-login">
            <div class="card-login">
                <h1>Logout</h1>
                <button onclick="confirmLogout()" type="submit" class="btn btn-login">Sair</button>
            </div>  
        </div>
    </div>

    <script src="js/telaCadastro.js"></script>
    <script type="text/javascript">

                    function confirmLogout() {
                        var confirmation = confirm("Você tem certeza que deseja sair?");
                        if (confirmation) {
                            window.location.href = "LogoutUsuarioSrv"; // A URL configurada para o logout
                        }
                    }
    </script>   
</body>
</html>

<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>