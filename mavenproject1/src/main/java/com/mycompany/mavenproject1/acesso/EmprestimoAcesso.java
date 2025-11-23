package com.mycompany.mavenproject1.acesso;

import com.mycompany.mavenproject1.model.Emprestimo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoAcesso {

    // Método auxiliar para conectar
    private Connection getConnection() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Biblioteca", "root", "root");
    }

    // --- 1. REALIZAR EMPRÉSTIMO ---
    public void registrarEmprestimo(int usuarioId, int livroId) throws Exception {
        Connection conn = getConnection();
        
        try {
            // A. Verificar Estoque
            String sqlCheck = "SELECT quantidadedisponivel FROM livros WHERE livroid = ?";
            PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
            stmtCheck.setInt(1, livroId);
            ResultSet rs = stmtCheck.executeQuery();
            
            if(rs.next()) {
                if(rs.getInt("quantidadedisponivel") <= 0) {
                    throw new Exception("Livro sem estoque disponível!");
                }
            }

            // B. Inserir Empréstimo (Data Prevista = Hoje + 7 dias)
            String sqlInsert = "INSERT INTO emprestimos (usuarioid, livroid, dataemprestimo, datadevolucaoprevista, statusemprestimo) "
                             + "VALUES (?, ?, CURRENT_DATE, ?, 'ATIVO')";
            
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, livroId);
            
            // Cálculo da data: Hoje + 7 dias
            java.time.LocalDate hoje = java.time.LocalDate.now();
            stmt.setDate(3, Date.valueOf(hoje.plusDays(7))); 
            
            stmt.executeUpdate();
            
            // C. Baixar Estoque (-1)
            String sqlEstoque = "UPDATE livros SET quantidadedisponivel = quantidadedisponivel - 1 WHERE livroid = ?";
            PreparedStatement stmtEst = conn.prepareStatement(sqlEstoque);
            stmtEst.setInt(1, livroId);
            stmtEst.executeUpdate();
            
        } finally {
            conn.close();
        }
    }

    // --- 2. REALIZAR DEVOLUÇÃO (COM MULTA) ---
    public String realizarDevolucao(int emprestimoId) throws Exception {
        Connection conn = getConnection();
        String mensagem = "Devolução realizada com sucesso.";
        
        try {
            // A. Buscar dados para checar atraso
            String sqlBusca = "SELECT usuarioid, livroid, datadevolucaoprevista FROM emprestimos WHERE emprestimoid = ?";
            PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca);
            stmtBusca.setInt(1, emprestimoId);
            ResultSet rs = stmtBusca.executeQuery();
            
            if(rs.next()) {
                int usuarioId = rs.getInt("usuarioid");
                int livroId = rs.getInt("livroid");
                Date dataPrevista = rs.getDate("datadevolucaoprevista");
                Date dataHoje = new Date(System.currentTimeMillis());
                
                // B. Atualizar Empréstimo (Fechar)
                String sqlUpdate = "UPDATE emprestimos SET datadevolucaoreal = CURRENT_DATE, statusemprestimo = 'FINALIZADO' WHERE emprestimoid = ?";
                PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
                stmtUpdate.setInt(1, emprestimoId);
                stmtUpdate.executeUpdate();
                
                // C. Devolver Estoque (+1)
                String sqlEstoque = "UPDATE livros SET quantidadedisponivel = quantidadedisponivel + 1 WHERE livroid = ?";
                PreparedStatement stmtEst = conn.prepareStatement(sqlEstoque);
                stmtEst.setInt(1, livroId);
                stmtEst.executeUpdate();
                
                // D. Lógica de Penalidade
                if (dataHoje.after(dataPrevista)) {
                    long diff = dataHoje.getTime() - dataPrevista.getTime();
                    long diasAtraso = java.util.concurrent.TimeUnit.DAYS.convert(diff, java.util.concurrent.TimeUnit.MILLISECONDS);
                    
                    // Multa de R$ 2.50 por dia
                    double valorMulta = diasAtraso * 2.50;
                    
                    String sqlMulta = "INSERT INTO penalidades (usuarioid, emprestimoid, tipopenalidade, descricao, valormulta, diasuspensao, statuspenalidade, datapenalidade) "
                                    + "VALUES (?, ?, 'MULTA', ?, ?, 0, 'PENDENTE', CURRENT_DATE)";
                    
                    PreparedStatement stmtMulta = conn.prepareStatement(sqlMulta);
                    stmtMulta.setInt(1, usuarioId);
                    stmtMulta.setInt(2, emprestimoId);
                    stmtMulta.setString(3, "Atraso de " + diasAtraso + " dias.");
                    stmtMulta.setDouble(4, valorMulta); // Use setBigDecimal se mudou o tipo no banco
                    stmtMulta.executeUpdate();
                    
                    mensagem = "Devolução com ATRASO! Multa gerada de R$ " + valorMulta;
                }
            }
        } finally {
            conn.close();
        }
        return mensagem;
    }
    
    // --- 3. LISTAR HISTÓRICO ---
    public List<Emprestimo> listarHistorico() throws Exception {
        List<Emprestimo> lista = new ArrayList<>();
        Connection conn = getConnection();
        
        // JOIN para trazer o Nome do Usuário e Título do Livro
        String sql = "SELECT E.*, U.nome, L.titulo " +
                     "FROM emprestimos E " +
                     "JOIN usuarios U ON E.usuarioid = U.usuarioid " +
                     "JOIN livros L ON E.livroid = L.livroid " +
                     "ORDER BY E.dataemprestimo DESC";
                     
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            Emprestimo emp = new Emprestimo();
            // Dados da tabela Emprestimo
            emp.setEmprestimoId(rs.getInt("emprestimoid"));
            emp.setDataEmprestimo(rs.getDate("dataemprestimo"));
            emp.setDataDevolucaoPrevista(rs.getDate("datadevolucaoprevista"));
            emp.setDataDevolucaoReal(rs.getDate("datadevolucaoreal"));
            emp.setStatusEmprestimo(rs.getString("statusemprestimo"));
            
            // Dados das outras tabelas (JOIN)
            emp.setNomeUsuarioAux(rs.getString("nome"));
            emp.setTituloLivroAux(rs.getString("titulo"));
            
            lista.add(emp);
        }
        conn.close();
        return lista;
    }
}