/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1.controller;
/**
 *
 * @author Thiago
 */

import com.mycompany.mavenproject1.acesso.UsuarioAcesso;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ExcluirUsuario")
public class ExcluirUsuarioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Pega o ID da URL
            int id = Integer.parseInt(request.getParameter("id"));
            
            // 2. Chama o DAO para fazer o serviço sujo
            UsuarioAcesso dao = new UsuarioAcesso();
            dao.excluir(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Redireciona de volta para a lista
        response.sendRedirect("UsuariosServlet");
    }
}