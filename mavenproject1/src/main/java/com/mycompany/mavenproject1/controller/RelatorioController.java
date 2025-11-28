package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.EmprestimoAcesso;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/RelatorioController")
public class RelatorioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processar(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processar(request, response);
    }

    private void processar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EmprestimoAcesso dao = new EmprestimoAcesso();

        try {
            List<Map<String,Object>> relEmprestimos = dao.gerarRelatorioEmprestimosPorUsuario();
            List<Map<String,Object>> relPenalidades = dao.gerarRelatorioPenalidades();
            List<Map<String,Object>> relEstoque = dao.gerarRelatorioEstoque();

            request.setAttribute("relEmprestimos", relEmprestimos);
            request.setAttribute("relPenalidades", relPenalidades);
            request.setAttribute("relEstoque", relEstoque);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msgSistema", "Erro ao gerar relatórios: " + e.getMessage());
        }

        request.getRequestDispatcher("relatorios.jsp").forward(request, response);
    }
}