/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.LivroAcesso;
import com.mycompany.mavenproject1.model.Livro;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Thiago
 */

@WebServlet("/ListarLivros")
public class ListarLivrosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Busca os dados
            LivroAcesso acesso = new LivroAcesso();
            List<Livro> lista = acesso.listarTodos();
            
            // 2. Coloca na memória da requisição
            request.setAttribute("listaDeLivros", lista);
            
            // 3. Envia para o JSP
            request.getRequestDispatcher("listar-livros.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace(); // Erro vai para o log do GlassFish
        }
    }
}