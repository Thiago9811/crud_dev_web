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
import jakarta.servlet.http.HttpSession;

@WebServlet("/EmprestimoController")
public class EmprestimoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processarAcao(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao == null || acao.isEmpty() || "listar".equals(acao)) {
            carregarListas(request, response);
        } else {
            processarAcao(request, response); 
        }
    }

    private void processarAcao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        EmprestimoAcesso dao = new EmprestimoAcesso();
        HttpSession session = request.getSession(); 
        
        try {
            if ("emprestar".equals(acao)) {
                String nomeUsuario = request.getParameter("nomeUsuario");
                String tituloLivro = request.getParameter("tituloLivro");

                int usuarioId = dao.buscarIdUsuarioPorNome(nomeUsuario);
                int livroId = dao.buscarIdLivroPorTitulo(tituloLivro);

                if (usuarioId == -1) {
                    throw new Exception("Usuário '" + nomeUsuario + "' não encontrado.");
                }
                if (livroId == -1) {
                    throw new Exception("Livro '" + tituloLivro + "' não encontrado.");
                }

                dao.registrarEmprestimo(usuarioId, livroId);
                
                session.setAttribute("msgSistema", "Empréstimo registrado com sucesso para " + nomeUsuario + "!");
                session.setAttribute("tipoMsg", "success");

            } else if ("devolver".equals(acao)) {
                int emprestimoId = Integer.parseInt(request.getParameter("emprestimoId"));
                String resultado = dao.realizarDevolucao(emprestimoId);
                
                session.setAttribute("msgSistema", resultado);
                if(resultado.contains("ATRASO")) {
                    session.setAttribute("tipoMsg", "warning");
                } else {
                    session.setAttribute("tipoMsg", "success");
                }
            }

        } catch (Exception e) {
            session.setAttribute("msgSistema", "Erro: " + e.getMessage());
            session.setAttribute("tipoMsg", "danger");
            e.printStackTrace();
        }

        response.sendRedirect("EmprestimoController");
    }

    private void carregarListas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EmprestimoAcesso dao = new EmprestimoAcesso();
        HttpSession session = request.getSession();
        
        if (session.getAttribute("msgSistema") != null) {
            request.setAttribute("msgSistema", session.getAttribute("msgSistema"));
            request.setAttribute("tipoMsg", session.getAttribute("tipoMsg"));
            session.removeAttribute("msgSistema");
            session.removeAttribute("tipoMsg");
        }

        try {
            List<Emprestimo> lista = dao.listarHistorico();
            request.setAttribute("listaEmprestimos", lista);

            List<String> sugestaoUsuarios = dao.listarNomesUsuarios();
            List<String> sugestaoLivros = dao.listarTitulosLivrosDisponiveis();
            
            request.setAttribute("sugestaoUsuarios", sugestaoUsuarios);
            request.setAttribute("sugestaoLivros", sugestaoLivros);

            request.getRequestDispatcher("gerenciar-emprestimos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msgSistema", "Erro fatal ao carregar dados: " + e.getMessage());
            request.getRequestDispatcher("gerenciar-emprestimos.jsp").forward(request, response);
        }
    }
}