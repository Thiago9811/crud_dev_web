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
import com.mycompany.mavenproject1.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditarUsuario")
public class EditarUsuarioController extends HttpServlet {

    // CARREGA O USUÁRIO PARA EDIÇÃO
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        UsuarioAcesso dao = new UsuarioAcesso();
        Usuario u = dao.buscarPorId(id);
        
        // Manda o objeto 'usuario' para o JSP preencher os campos
        request.setAttribute("usuario", u);
        
        request.getRequestDispatcher("editar-usuario.jsp").forward(request, response);
    }


    // SALVA A EDIÇÃO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        try {
            Usuario u = new Usuario();

            // 1. ESTA É A LINHA CRÍTICA: Você está pegando o ID?
            // Se o seu input hidden no JSP chama "id", aqui tem que ser "id".
            String idStr = request.getParameter("id");

            // Debug: Imprima para ver se está chegando
            System.out.println("ID recebido para atualizar: " + idStr); 

            u.setUsuarioId(Integer.parseInt(idStr));

            u.setNome(request.getParameter("nome"));
            u.setEmail(request.getParameter("email"));
            u.setTelefone(request.getParameter("telefone"));
            u.setCpf(request.getParameter("cpf"));

            // Se você não está editando tipo/senha, não precisa setar nada aqui
            // O DAO deve ser inteligente para não zerar esses campos (veremos abaixo)

            UsuarioAcesso dao = new UsuarioAcesso();
            dao.atualizar(u);

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("UsuariosServlet");
    }
}
