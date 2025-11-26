package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.LivroAcesso;
import com.mycompany.mavenproject1.model.Livro;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CadastroLivroController")
public class CadastroLivroController extends HttpServlet {

    // --- 1. MOSTRAR O FORMULÁRIO (AGORA USANDO JSP) ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Apenas abre a página visual que criamos acima
        request.getRequestDispatcher("cadastro-livro.jsp").forward(request, response);
    }

    // --- 2. RECEBER DADOS E SALVAR ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // A. Pegar dados do form
            Livro l = new Livro();
            l.setTitulo(request.getParameter("titulo"));
            l.setAutor(request.getParameter("autor"));
            l.setEditora(request.getParameter("editora"));
            
            // Conversão segura para inteiros (evita erro se vier vazio)
            String anoStr = request.getParameter("ano");
            l.setAnoPublicacao(anoStr != null && !anoStr.isEmpty() ? Integer.parseInt(anoStr) : 0);
            
            l.setIsbn(request.getParameter("isbn"));
            l.setGenero(request.getParameter("genero"));
            
            String qtdStr = request.getParameter("qtd");
            l.setQuantidadeTotal(qtdStr != null && !qtdStr.isEmpty() ? Integer.parseInt(qtdStr) : 1);
            
            // B. Chamar o DAO
            LivroAcesso acesso = new LivroAcesso();
            acesso.cadastrar(l); // Certifique-se que LivroAcesso tem o método 'cadastrar'
            
            // C. Redirecionar para a lista (Feedback Visual)
            // Em vez de imprimir HTML de sucesso aqui, mandamos o usuário de volta para a lista
            // E podemos adicionar um parâmetro na URL para mostrar uma mensagem lá
            response.sendRedirect("ListarLivros"); // Redireciona para o Servlet de listagem
            
        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro, fica na mesma página ou mostra erro
            response.getWriter().println("Erro crítico: " + e.getMessage());
        }
    }
}