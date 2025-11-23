/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Thiago
 */

@WebServlet("/CadastroUsuario")
public class CadastroUsuario extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Novo Cadastro</title>");
            out.println("<style>");
            out.println("body { font-family: sans-serif; text-align: center; margin-top: 50px; }");
            out.println("form { display: inline-block; border: 1px solid #ccc; padding: 20px; border-radius: 10px; }");
            out.println("input { display: block; margin: 10px auto; padding: 5px; width: 200px; }");
            out.println("button { background-color: #28a745; color: white; padding: 10px 20px; border: none; cursor: pointer; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Cadastro de Usuário</h1>");
            
            // --- O FORMULÁRIO ---
            // action="CadastroUsuario": Envia os dados para este mesmo Servlet
            // method="post": Importante! Para enviar dados, usamos POST.
            out.println("<form action='CadastroUsuario' method='post'>");
            
            out.println("<label>Nome:</label>");
            out.println("<input type='text' name='nome' required>");
            
            out.println("<label>Email:</label>");
            out.println("<input type='email' name='email' required>");
            
            out.println("<label>Telefone:</label>");
            out.println("<input type='text' name='telefone' required>");
            
            // Se sua tabela tiver SENHA, descomente as linhas abaixo:
            out.println("<label>Senha:</label>");
            out.println("<input type='password' name='senha' required>");
             
            out.println("<label>CPF:</label>");
            out.println("<input type='text' name='cpf' required>");
            
            out.println("<div class='radio-group'>");
            out.println("<input type='radio' id='membro' name='tipoUsuario' value='membro' checked>");
            out.println("<label for='membro'>Usuário Comum</label>");
            out.println("&nbsp;&nbsp;"); // Espaço
            
            // Opção 2: Admin
            out.println("<input type='radio' id='admin' name='tipoUsuario' value='admin'>");
            out.println("<label for='admin'>Administrador</label>");
            out.println("</div>");
                        
            out.println("<button type='submit'>Salvar no Banco</button>");
            out.println("</form>");
            
            out.println("<br><br>");
            out.println("<a href='index.html'>Voltar</a>");
            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Pegar os dados que vieram do formulário
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String cpf = request.getParameter("cpf");
        String senhaUSR = request.getParameter("senha");
        String tipoUSR = request.getParameter("tipoUsuario");
        String statusConta = "ativo";
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            Connection conn = null;
            PreparedStatement stmt = null;
            
            try {
                // 2. Conectar ao Banco
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                String url = "jdbc:derby://localhost:1527/Biblioteca";
                String usuario = "root";
                String senha = "root";
                conn = DriverManager.getConnection(url, usuario, senha);
                
                // 3. O Comando SQL (INSERT)
                // Usamos '?' como placeholders para segurança
                // ATENÇÃO: Estou assumindo que o ID é gerado automaticamente pelo banco.
                String sql = "INSERT INTO USUARIOS (NOME, EMAIL, TELEFONE, SENHAHASH, TIPOUSUARIO, STATUSCONTA, DATACADASTRO, CPF) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
                
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);  // Substitui o primeiro ? pelo nome
                stmt.setString(2, email); // Substitui o segundo ? pelo email
                stmt.setString(3, telefone); // Substitui o segundo ? pelo email
                stmt.setString(4, senhaUSR); // Substitui o segundo ? pelo email
                stmt.setString(5, tipoUSR); // Substitui o segundo ? pelo email
                stmt.setString(6, statusConta); // Substitui o segundo ? pelo email
                stmt.setString(7, cpf); // Substitui o segundo ? pelo email
                
                // 4. Executar
                int linhasAfetadas = stmt.executeUpdate();
                
                // 5. Dar feedback ao usuário
                out.println("<h1>Sucesso!</h1>");
                if(linhasAfetadas > 0) {
                    out.println("<p>O usuário <b>" + nome + "</b> foi cadastrado com sucesso.</p>");
                }
                out.println("<a href='UsuariosServlet'>Ver Lista de Usuários</a><br>");
                out.println("<a href='CadastroUsuario'>Cadastrar Outro</a>");
                
            } catch (Exception e) {
                out.println("<h1>Erro ao Salvar</h1>");
                out.println("<p>" + e.getMessage() + "</p>");
                e.printStackTrace();
            } finally {
                try { if(stmt != null) stmt.close(); if(conn != null) conn.close(); } catch(Exception e){}
            }
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
    