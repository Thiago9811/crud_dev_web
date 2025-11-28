package com.mycompany.mavenproject1.acesso;

import com.mycompany.mavenproject1.model.Penalidade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;

public class PenalidadeAcesso {

    private Connection getConnection() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Biblioteca", "root", "root");
    }

    /**
     * Insere uma penalidade (multa) no banco.
     */
    public void criarPenalidade(int usuarioId, int emprestimoId, String tipo, String descricao, BigDecimal valorMulta, int diasSuspensao) throws Exception {
        String sql = "INSERT INTO penalidades (usuarioid, emprestimoid, tipopenalidade, descricao, valormulta, diassuspensao, statuspenalidade, datapenalidade) "
                   + "VALUES (?, ?, ?, ?, ?, ?, 'pendente', CURRENT_DATE)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, emprestimoId);
            ps.setString(3, tipo);
            ps.setString(4, descricao);
            ps.setBigDecimal(5, valorMulta != null ? valorMulta : BigDecimal.ZERO);
            ps.setInt(6, diasSuspensao);
            ps.executeUpdate();
        }
    }

    /**
     * Marca a penalidade como paga.
     */
    public void pagarPenalidade(int penalidadeId) throws Exception {
        String sql = "UPDATE penalidades SET statuspenalidade = 'paga' WHERE penalidadeid = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, penalidadeId);
            ps.executeUpdate();
        }
    }

    /**
     * Lista penalidades pendentes de um usuário.
     */
    public List<Penalidade> listarPendentesPorUsuario(int usuarioId) throws Exception {
        List<Penalidade> lista = new ArrayList<>();
        String sql = "SELECT * FROM penalidades WHERE usuarioid = ? AND statuspenalidade = 'pendente'";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Penalidade p = new Penalidade();
                    p.setPenalidadeId(rs.getInt("penalidadeid"));
                    p.setUsuarioId(rs.getInt("usuarioid"));
                    p.setEmprestimoId(rs.getInt("emprestimoid"));
                    p.setTipoPenalidade(rs.getString("tipopenalidade"));
                    p.setDescricao(rs.getString("descricao"));

                    // Ler valormulta como BigDecimal (trata null)
                    BigDecimal valor = rs.getBigDecimal("valormulta");
                    p.setValorMulta(valor != null ? valor : BigDecimal.ZERO);

                    p.setDiasSuspensao(rs.getInt("diassuspensao"));
                    p.setStatusPenalidade(rs.getString("statuspenalidade"));

                    // Converter java.sql.Date -> java.time.LocalDate (trata null)
                    Date dt = rs.getDate("datapenalidade");
                    if (dt != null) {
                        p.setDataPenalidade(dt.toLocalDate());
                    } else {
                        p.setDataPenalidade(null);
                    }

                    lista.add(p);
                }
            }
        }
        return lista;
    }

    /**
     * Opcional: buscar uma penalidade por emprestimo (ex.: para mostrar no histórico).
     * Retorna a primeira penalidade pendente encontrada para o empréstimo ou null.
     */
    public Penalidade buscarPenalidadePendentePorEmprestimo(int emprestimoId) throws Exception {
        String sql = "SELECT * FROM penalidades WHERE emprestimoid = ? AND statuspenalidade = 'pendente'";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, emprestimoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Penalidade p = new Penalidade();
                    p.setPenalidadeId(rs.getInt("penalidadeid"));
                    p.setUsuarioId(rs.getInt("usuarioid"));
                    p.setEmprestimoId(rs.getInt("emprestimoid"));
                    p.setTipoPenalidade(rs.getString("tipopenalidade"));
                    p.setDescricao(rs.getString("descricao"));

                    BigDecimal valor = rs.getBigDecimal("valormulta");
                    p.setValorMulta(valor != null ? valor : BigDecimal.ZERO);

                    p.setDiasSuspensao(rs.getInt("diassuspensao"));
                    p.setStatusPenalidade(rs.getString("statuspenalidade"));

                    Date dt = rs.getDate("datapenalidade");
                    if (dt != null) p.setDataPenalidade(dt.toLocalDate());

                    return p;
                }
            }
        }
        return null;
    }
}
