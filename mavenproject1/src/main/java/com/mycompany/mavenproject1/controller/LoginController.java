package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.UsuarioAcesso;
import com.mycompany.mavenproject1.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        UsuarioAcesso dao = new UsuarioAcesso();
        Usuario u = dao.autenticar(email, senha);
        
        if (u != null) {
            // SUCESSO: Cria sessão
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", u);
            response.sendRedirect("dashboard.jsp"); // Vai para a nova home
        } else {
            // ERRO
            request.setAttribute("erro", "Dados inválidos!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    // Logout
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
    }
}