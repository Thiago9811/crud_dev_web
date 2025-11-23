package com.mycompany.mavenproject1; 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MostrarUsuarios")
public class MostrarUsuarios extends HttpServlet {

    /**
     * O método doGet é chamado automaticamente pelo servidor quando 
     * o navegador faz uma requisição GET (link ou formulário method="get").
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Define que a resposta será uma página HTML
        response.setContentType("text/html;charset=UTF-8");

        // O 'out' é como o System.out.println, mas escreve direto na página do navegador
        try (PrintWriter out = response.getWriter()) {
            
            // --- 1. Início do HTML ---
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista de Usuários</title>");
            out.println("<style>"); // Um pouco de CSS para a tabela ficar bonita
            out.println("table { border-collapse: collapse; width: 50%; margin: 20px auto; }");
            out.println("th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.println("th { background-color: #4CAF50; color: white; }");
            out.println("h1 { text-align: center; }");
            out.println("div { text-align: center; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Usuários Cadastrados</h1>");

            // --- 2. Lógica de Conexão e Consulta ao Banco ---
            Connection conn = null;
            Statement stm = null;
            ResultSet executar = null;

            try {
                // A. Carrega o Driver do Derby
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                // B. Dados de Conexão (Verifique se batem com o seu banco 'Biblioteca')
                String url = "jdbc:derby://localhost:1527/Biblioteca";
                String usuario = "root"; 
                String senha = "root";

                // C. Abre a conexão
                conn = DriverManager.getConnection(url, usuario, senha);

                // D. Cria o comando SQL
                stm = conn.createStatement();
                String sql = "SELECT * FROM USUARIOS"; // ⚠️ Tenha certeza que a tabela chama USUARIOS (maiúsculo/minúsculo importa)

                // E. Executa e pega os resultados
                executar = stm.executeQuery(sql);

                // --- 3. Gerando a Tabela HTML com os Dados ---
                out.println("<table>");
                // Cabeçalho da tabela
                out.println("<tr> <th>ID</th> <th>Nome</th> <th>Email</th> </tr>");

                // Loop linha por linha do banco
                while (executar.next()) {
                    // ⚠️ IMPORTANTE: Os nomes "ID", "NOME", "EMAIL" aqui devem ser 
                    // IGUAIS aos nomes das colunas que você criou no banco de dados.
                    int id = executar.getInt("USUARIOID");
                    String nome = executar.getString("NOME");
                    String email = executar.getString("EMAIL"); // Se não tiver email, remova esta linha
                    String telefone = executar.getString("TELEFONE");
                    String tipo = executar.getString("TIPOUSUARIO");
                    String status = executar.getString("STATUSCONTA");
                    
                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + nome + "</td>");
                    out.println("<td>" + email + "</td>");
                    out.println("<td>" + telefone + "</td>");
                    out.println("<td>" + tipo + "</td>");
                    out.println("<td>" + status + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

            } catch (ClassNotFoundException e) {
                out.println("<p style='color:red; text-align:center'>Erro: Driver do banco não encontrado!</p>");
                e.printStackTrace(); // Imprime erro no console do servidor (NetBeans)
            } catch (SQLException e) {
                out.println("<p style='color:red; text-align:center'>Erro SQL: " + e.getMessage() + "</p>");
                e.printStackTrace(); // Imprime erro no console do servidor
            } finally {
                // F. Fecha conexões para não travar o banco
                try {
                    if (executar != null) executar.close();
                    if (stm != null) stm.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    // Ignora erro de fechamento
                }
            }

            // --- 4. Botão de Voltar ---
            out.println("<div>");
            out.println("<a href='index.html'><button>Voltar para Início</button></a>");
            out.println("</div>");
            
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    // Como estamos usando apenas GET no formulário, não precisamos implementar doPost
}