package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.UsuarioAcesso;
import com.mycompany.mavenproject1.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BuscarUsuario", urlPatterns = {"/buscar-usuario"})
public class BuscarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome"); // veio do input do JSP

        UsuarioAcesso dao = new UsuarioAcesso();
        List<Usuario> usuarios = dao.listar(nome); // AQUI busca pelo nome!

        request.setAttribute("usuarios", usuarios);

        // manda para o JSP de empréstimo
        request.getRequestDispatcher("emprestimo.jsp").forward(request, response);
    }
}
