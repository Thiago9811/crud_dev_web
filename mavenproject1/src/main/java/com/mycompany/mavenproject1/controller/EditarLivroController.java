/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.LivroAcesso;
import com.mycompany.mavenproject1.model.Livro;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Thiago
 */
@WebServlet("/EditarLivro")
public class EditarLivroController extends HttpServlet {

    // Carrega dados e abre o formulário
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        LivroAcesso acesso = new LivroAcesso();
        Livro l = acesso.buscarPorId(id);
        
        request.setAttribute("livro", l);
        request.getRequestDispatcher("editar-livro.jsp").forward(request, response);
    }

    // Salva as alterações
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Livro l = new Livro();
            l.setLivroId(Integer.parseInt(request.getParameter("id")));
            l.setTitulo(request.getParameter("titulo"));
            l.setAutor(request.getParameter("autor"));
            l.setEditora(request.getParameter("editora"));
            l.setAnoPublicacao(Integer.parseInt(request.getParameter("ano")));
            l.setIsbn(request.getParameter("isbn"));
            l.setGenero(request.getParameter("genero"));
            l.setQuantidadeTotal(Integer.parseInt(request.getParameter("qtd")));
            
            LivroAcesso acesso = new LivroAcesso();
            acesso.atualizar(l);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ListarLivros");
    }
}
