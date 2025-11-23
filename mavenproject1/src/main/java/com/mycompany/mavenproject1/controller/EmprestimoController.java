package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.EmprestimoAcesso;
import com.mycompany.mavenproject1.model.Emprestimo;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmprestimoController")
public class EmprestimoController extends HttpServlet {

    // doPost: Usado para realizar AÇÕES (Emprestar / Devolver)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        EmprestimoAcesso dao = new EmprestimoAcesso();
        String mensagem = "";
        
        try {
            if ("emprestar".equals(acao)) {
                int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));
                int livroId = Integer.parseInt(request.getParameter("livroId"));
                
                dao.registrarEmprestimo(usuarioId, livroId);
                mensagem = "Empréstimo realizado com sucesso!";
                
            } else if ("devolver".equals(acao)) {
                int emprestimoId = Integer.parseInt(request.getParameter("emprestimoId"));
                
                // O método devolve uma String avisando se teve multa
                mensagem = dao.realizarDevolucao(emprestimoId);
            }
            
        } catch (Exception e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }
        
        // Passa a mensagem para o JSP e recarrega a lista
        request.setAttribute("msgSistema", mensagem);
        doGet(request, response); 
    }

    // doGet: Usado para CARREGAR a página e a tabela
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            EmprestimoAcesso dao = new EmprestimoAcesso();
            // Busca a lista atualizada
            List<Emprestimo> lista = dao.listarHistorico();
            
            // Coloca a lista na "mochila" (request) para o JSP ler
            request.setAttribute("listaEmprestimos", lista);
            
            // Abre o JSP
            request.getRequestDispatcher("gerenciar-emprestimos.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}