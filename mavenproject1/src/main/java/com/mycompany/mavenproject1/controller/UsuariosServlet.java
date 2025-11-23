/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1.controller;


import com.mycompany.mavenproject1.acesso.UsuarioAcesso;
import com.mycompany.mavenproject1.model.Usuario;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// A URL aqui DEVE ser igual ao action do form no index.html
@WebServlet("/UsuariosServlet") 
public class UsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Chama o DAO
        UsuarioAcesso acesso = new UsuarioAcesso();
        List<Usuario> lista = acesso.listarTodos();
        
        // 2. Coloca a lista na memória para o JSP
        request.setAttribute("listaDeUsuarios", lista);
        
        // 3. Encaminha para a página JSP (Visualização)
        request.getRequestDispatcher("lista-usuarios.jsp").forward(request, response);
    }
}