/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.controller;

/**
 *
 * @author Thiago
 */

import com.mycompany.mavenproject1.acesso.LivroAcesso;
import com.mycompany.mavenproject1.model.Livro;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CadastroLivroController")
public class CadastroLivroController extends HttpServlet {

    // --- 1. MOSTRAR O FORMULÁRIO (VIEW) ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Cadastrar Livro</title>");
            out.println("<style>body{font-family:sans-serif; max-width:600px; margin:20px auto;} label{display:block; margin-top:10px;} input{width:100%; padding:5px;} button{margin-top:20px; padding:10px; width:100%; background-color:blue; color:white; cursor:pointer;}</style>");
            out.println("</head><body>");
            
            out.println("<h1>Novo Livro</h1>");
            out.println("<form action='CadastroLivroController' method='post'>");
            
            out.println("<label>Título:</label> <input type='text' name='titulo' required>");
            out.println("<label>Autor:</label> <input type='text' name='autor' required>");
            out.println("<label>Editora:</label> <input type='text' name='editora'>");
            out.println("<label>Ano Publicação:</label> <input type='number' name='ano' required>");
            out.println("<label>ISBN:</label> <input type='text' name='isbn'>");
            out.println("<label>Gênero:</label> <input type='text' name='genero'>");
            out.println("<label>Quantidade Total:</label> <input type='number' name='qtd' value='1' required>");
            
            out.println("<button type='submit'>Salvar Livro</button>");
            out.println("</form>");
            
            out.println("<br><a href='index.html'>Voltar ao Menu</a>");
            out.println("</body></html>");
        }
    }

    // --- 2. RECEBER DADOS E SALVAR (CONTROLLER) ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // A. Pegar dados do form
            Livro l = new Livro();
            l.setTitulo(request.getParameter("titulo"));
            l.setAutor(request.getParameter("autor"));
            l.setEditora(request.getParameter("editora"));
            l.setAnoPublicacao(Integer.parseInt(request.getParameter("ano")));
            l.setIsbn(request.getParameter("isbn"));
            l.setGenero(request.getParameter("genero"));
            l.setQuantidadeTotal(Integer.parseInt(request.getParameter("qtd")));
            
            // B. Chamar o DAO
            LivroAcesso acesso = new LivroAcesso();
            acesso.cadastrar(l);
            
            // C. Feedback
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<h1>Livro Cadastrado com Sucesso!</h1>");
                out.println("<p>" + l.getTitulo() + "</p>");
                out.println("<a href='CadastroLivroController'>Cadastrar Outro</a><br><br>");
                out.println("<a href='index.html'>Voltar ao Menu</a>");
            }
            
        } catch (Exception e) {
            response.getWriter().println("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}