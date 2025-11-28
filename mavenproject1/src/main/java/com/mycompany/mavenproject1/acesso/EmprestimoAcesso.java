package com.mycompany.mavenproject1.acesso;

import com.mycompany.mavenproject1.model.Emprestimo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class EmprestimoAcesso {

    private Connection getConnection() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Biblioteca", "root", "root");
    }

    public int buscarIdUsuarioPorNome(String nome) throws Exception {
        int id = -1;
        try (Connection conn = getConnection()) {
            String sql = "SELECT usuarioid FROM usuarios WHERE LOWER(nome) = LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome.trim());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("usuarioid");
                    }
                }
            }
        }
        return id;
    }

    public int buscarIdLivroPorTitulo(String titulo) throws Exception {
        int id = -1;
        try (Connection conn = getConnection()) {
            String sql = "SELECT livroid FROM livros WHERE LOWER(titulo) = LOWER(?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, titulo.trim());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("livroid");
                    }
                }
            }
        }
        return id;
    }

    public List<String> listarNomesUsuarios() throws Exception {
        List<String> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT nome FROM usuarios ORDER BY nome";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("nome"));
                }
            }
        }
        return lista;
    }

    public List<String> listarTitulosLivrosDisponiveis() throws Exception {
        List<String> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT titulo FROM livros WHERE quantidadedisponivel > 0 ORDER BY titulo";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("titulo"));
                }
            }
        }
        return lista;
    }

    public void registrarEmprestimo(int usuarioId, int livroId) throws Exception {
        try (Connection conn = getConnection()) {
            String sqlCheck = "SELECT quantidadedisponivel FROM livros WHERE livroid = ?";
            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
                stmtCheck.setInt(1, livroId);
                try (ResultSet rs = stmtCheck.executeQuery()) {
                    if (rs.next() && rs.getInt("quantidadedisponivel") <= 0) {
                        throw new Exception("Livro sem estoque disponível!");
                    }
                }
            }

            String sqlInsert = "INSERT INTO emprestimos "
                    + "(usuarioid, livroid, dataemprestimo, datadevolucaoprevista, statusemprestimo) "
                    + "VALUES (?, ?, CURRENT_DATE, ?, 'ativo')";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
                stmt.setInt(1, usuarioId);
                stmt.setInt(2, livroId);
                stmt.setDate(3, Date.valueOf(java.time.LocalDate.now().plusDays(7)));
                stmt.executeUpdate();
            }

            String sqlEstoque = "UPDATE livros SET quantidadedisponivel = quantidadedisponivel - 1 WHERE livroid = ?";
            try (PreparedStatement stmtEst = conn.prepareStatement(sqlEstoque)) {
                stmtEst.setInt(1, livroId);
                stmtEst.executeUpdate();
            }
        }
    }

    public String realizarDevolucao(int emprestimoId) throws Exception {
        String mensagem = "Devolução realizada com sucesso.";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            int usuarioId, livroId;
            Date dataPrevista, dataHoje = new Date(System.currentTimeMillis());

            String sqlBusca = "SELECT usuarioid, livroid, datadevolucaoprevista FROM emprestimos WHERE emprestimoid = ?";
            try (PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {
                stmtBusca.setInt(1, emprestimoId);
                try (ResultSet rs = stmtBusca.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return "Empréstimo não encontrado.";
                    }
                    usuarioId = rs.getInt("usuarioid");
                    livroId = rs.getInt("livroid");
                    dataPrevista = rs.getDate("datadevolucaoprevista");
                }
            }

            boolean houveAtraso = dataHoje.after(dataPrevista);
            BigDecimal valorMulta = BigDecimal.ZERO;
            if (houveAtraso) {
                long diasAtraso = TimeUnit.DAYS.convert(dataHoje.getTime() - dataPrevista.getTime(), TimeUnit.MILLISECONDS);
                valorMulta = BigDecimal.valueOf(diasAtraso);

                String sqlPen = "INSERT INTO penalidades "
                        + "(usuarioid, emprestimoid, tipopenalidade, descricao, valormulta, diassuspensao, statuspenalidade, datapenalidade) "
                        + "VALUES (?, ?, 'atraso', ?, ?, 0, 'pendente', CURRENT_DATE)";

                try (PreparedStatement stmtPen = conn.prepareStatement(sqlPen)) {
                    stmtPen.setInt(1, usuarioId);
                    stmtPen.setInt(2, emprestimoId);
                    stmtPen.setString(3, "Atraso de " + valorMulta + " dias.");
                    stmtPen.setBigDecimal(4, valorMulta);
                    stmtPen.executeUpdate();
                }

                mensagem = "Devolução realizada com ATRASO! Multa gerada de R$ " + valorMulta;
            }

            String status = houveAtraso ? "atrasado" : "devolvido";
            String sqlUpdate = "UPDATE emprestimos SET datadevolucaoreal = ?, statusemprestimo = ? WHERE emprestimoid = ?";
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setDate(1, dataHoje);
                stmtUpdate.setString(2, status);
                stmtUpdate.setInt(3, emprestimoId);
                stmtUpdate.executeUpdate();
            }

            String sqlEstoque = "UPDATE livros SET quantidadedisponivel = quantidadedisponivel + 1 WHERE livroid = ?";
            try (PreparedStatement stmtEst = conn.prepareStatement(sqlEstoque)) {
                stmtEst.setInt(1, livroId);
                stmtEst.executeUpdate();
            }

            conn.commit();
        }
        return mensagem;
    }

    public List<Emprestimo> listarHistorico() throws Exception {
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT E.*, U.nome AS usuario_nome, L.titulo AS livro_titulo "
                    + "FROM emprestimos E "
                    + "JOIN usuarios U ON E.usuarioid = U.usuarioid "
                    + "JOIN livros L ON E.livroid = L.livroid "
                    + "ORDER BY E.dataemprestimo DESC";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Emprestimo emp = new Emprestimo();
                    emp.setEmprestimoId(rs.getInt("emprestimoid"));
                    emp.setUsuarioId(rs.getInt("usuarioid"));
                    emp.setLivroId(rs.getInt("livroid"));
                    emp.setDataEmprestimo(rs.getDate("dataemprestimo"));
                    emp.setDataDevolucaoPrevista(rs.getDate("datadevolucaoprevista"));
                    emp.setDataDevolucaoReal(rs.getDate("datadevolucaoreal"));
                    emp.setStatusEmprestimo(rs.getString("statusemprestimo"));
                    emp.setNomeUsuarioAux(rs.getString("usuario_nome"));
                    emp.setTituloLivroAux(rs.getString("livro_titulo"));
                    lista.add(emp);
                }
            }
        }
        return lista;
    }

    public List<Map<String, Object>> gerarRelatorioEmprestimosPorUsuario() throws Exception {
        List<Map<String, Object>> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT U.NOME AS USUARIO, " +
                         "       COUNT(E.EMPRESTIMOID) AS TOTAL_EMPRESTIMOS, " +
                         "       SUM(CASE WHEN E.STATUSEMPRESTIMO='atrasado' THEN 1 ELSE 0 END) AS ATRASADOS " +
                         "FROM usuarios U " +
                         "LEFT JOIN emprestimos E ON U.USUARIOID = E.USUARIOID " +
                         "GROUP BY U.NOME " +
                         "ORDER BY U.NOME";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> linha = new HashMap<>();
                    linha.put("usuario", rs.getString("USUARIO"));
                    linha.put("totalEmprestimos", rs.getInt("TOTAL_EMPRESTIMOS"));
                    linha.put("atrasados", rs.getInt("ATRASADOS"));
                    lista.add(linha);
                }
            }
        }
        return lista;
    }

    public List<Map<String, Object>> gerarRelatorioPenalidades() throws Exception {
        List<Map<String, Object>> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT P.PENALIDADEID, U.NOME AS USUARIO, E.EMPRESTIMOID, " +
                         "       P.TIPOPENALIDADE, P.VALORMULTA, P.STATUSPENALIDADE, P.DATAPENALIDADE " +
                         "FROM penalidades P " +
                         "LEFT JOIN usuarios U ON P.USUARIOID = U.USUARIOID " +
                         "LEFT JOIN emprestimos E ON P.EMPRESTIMOID = E.EMPRESTIMOID " +
                         "ORDER BY P.DATAPENALIDADE DESC";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> linha = new HashMap<>();
                    linha.put("penalidadeId", rs.getInt("PENALIDADEID"));
                    linha.put("usuario", rs.getString("USUARIO") != null ? rs.getString("USUARIO") : "N/A");
                    linha.put("emprestimoId", rs.getInt("EMPRESTIMOID"));
                    linha.put("tipo", rs.getString("TIPOPENALIDADE"));
                    linha.put("valor", rs.getBigDecimal("VALORMULTA"));
                    linha.put("status", rs.getString("STATUSPENALIDADE"));
                    linha.put("data", rs.getDate("DATAPENALIDADE"));
                    lista.add(linha);
                }
            }
        }
        return lista;
    }

    public List<Map<String, Object>> gerarRelatorioEstoque() throws Exception {
        List<Map<String, Object>> lista = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT LIVROID, TITULO, QUANTIDADETOTAL, QUANTIDADEDISPONIVEL, " +
                         "(QUANTIDADETOTAL - QUANTIDADEDISPONIVEL) AS EMPRESTADOS " +
                         "FROM livros " +
                         "ORDER BY EMPRESTADOS DESC";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> linha = new HashMap<>();
                    linha.put("livroId", rs.getInt("LIVROID"));
                    linha.put("titulo", rs.getString("TITULO"));
                    linha.put("total", rs.getInt("QUANTIDADETOTAL"));
                    linha.put("disponivel", rs.getInt("QUANTIDADEDISPONIVEL"));
                    linha.put("emprestados", rs.getInt("EMPRESTADOS"));
                    lista.add(linha);
                }
            }
        }
        return lista;
    }
}