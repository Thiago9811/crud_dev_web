/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.UsuarioAcesso;
import com.mycompany.mavenproject1.model.Usuario;
import com.mycompany.mavenproject1.util.Seguranca; // Importante para o MD5
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
@WebServlet("/CadastroUsuario")
public class CadastroUsuarioController extends HttpServlet {

    // --- 1. VIEW: Mostra o formulário JSP ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Apenas encaminha para a página JSP bonita
        request.getRequestDispatcher("cadastro-usuario.jsp").forward(request, response);
    }

    // --- 2. CONTROLLER: Recebe dados e chama o DAO ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Usuario u = new Usuario();
            
            // Preenche o objeto com dados do formulário
            u.setNome(request.getParameter("nome"));
            u.setEmail(request.getParameter("email"));
            u.setTelefone(request.getParameter("telefone"));
            u.setCpf(request.getParameter("cpf"));
            
            // Senha (crua, o DAO ou o Setter pode criptografar)
            // No seu caso, vamos criptografar antes de mandar pro DAO se o DAO esperar hash
            // Ou mandar crua se o DAO fizer o hash. Vamos assumir que o DAO faz o hash.
            u.setSenha(request.getParameter("senha")); 
            
            // Tipo de Usuário (Agora com Radio Buttons)
            u.setTipoUsuario(request.getParameter("tipoUsuario"));
            
            // Chama o DAO para salvar
            UsuarioAcesso dao = new UsuarioAcesso();
            dao.salvar(u); // Certifique-se que este método existe no UsuarioAcesso
            
            // Sucesso: Redireciona para a lista
            response.sendRedirect("UsuariosServlet");
            
        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro, volta pro formulário (pode adicionar msg de erro aqui)
            response.sendRedirect("CadastroUsuario");
        }
    }
}
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    