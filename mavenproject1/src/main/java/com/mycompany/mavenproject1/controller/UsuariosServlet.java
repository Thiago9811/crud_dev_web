package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.acesso.UsuarioAcesso;
import com.mycompany.mavenproject1.model.Usuario;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UsuariosServlet") 
public class UsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // 1. Pegar o termo de busca (se houver)
            String busca = request.getParameter("busca");
            
            // 2. Instanciar o DAO
            UsuarioAcesso acesso = new UsuarioAcesso();
            
            // 3. Chamar o método inteligente 'listar' 
            // (Ele já trata: se busca for null, traz todos. Se tiver texto, filtra)
            List<Usuario> lista = acesso.listar(busca); 
            
            // 4. Coloca a lista na memória
            request.setAttribute("listaDeUsuarios", lista);
            
            // 5. Encaminha para a tela (UMA ÚNICA VEZ)
            request.getRequestDispatcher("listar-usuarios.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}