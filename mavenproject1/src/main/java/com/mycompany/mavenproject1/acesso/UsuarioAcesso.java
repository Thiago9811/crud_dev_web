package com.mycompany.mavenproject1.acesso;

import com.mycompany.mavenproject1.util.Seguranca;
import com.mycompany.mavenproject1.model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAcesso {
    
    private Connection getConnection() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Biblioteca", "root", "root");
    }

    // Método auxiliar para compatibilidade com códigos antigos
    public List<Usuario> listarTodos() {
        return listar("");
    }

    public List<Usuario> listar(String busca) {
        List<Usuario> lista = new ArrayList<>();
        // SQL corrigido para minusculas ('ativo', 'pendente')
        String sql = "SELECT u.*, " +
             "(SELECT COUNT(*) FROM emprestimos e WHERE e.usuarioid = u.usuarioid AND e.statusemprestimo = 'ativo') as qtd_emp, " +
             "(SELECT COUNT(*) FROM penalidades p WHERE p.usuarioid = u.usuarioid AND p.statuspenalidade = 'pendente') as qtd_pen " +
             "FROM usuarios u " +
             "WHERE LOWER(u.nome) LIKE ?"; 

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (busca == null) busca = "";
            stmt.setString(1, "%" + busca.toLowerCase() + "%");
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuarioId(rs.getInt("usuarioid"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setCpf(rs.getString("cpf"));
                u.setTipoUsuario(rs.getString("tipousuario"));
                
                u.setQtdEmprestimosAtivos(rs.getInt("qtd_emp"));
                u.setTemPenalidadePendente(rs.getInt("qtd_pen") > 0);
                
                lista.add(u);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public Usuario autenticar(String email, String senha) {
        String hash = Seguranca.hashMD5(senha);
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senhahash = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, hash);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuarioId(rs.getInt("usuarioid"));
                u.setNome(rs.getString("nome"));
                u.setTipoUsuario(rs.getString("tipousuario")); // Importante para o JSP saber se é admin
                return u;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    
    public void salvar(Usuario u) {
        String sql = "INSERT INTO usuarios (nome, email, telefone, cpf, senhahash, tipousuario, statusconta, datacadastro) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getTelefone());
            stmt.setString(4, u.getCpf());
            stmt.setString(5, Seguranca.hashMD5(u.getSenha()));
            stmt.setString(6, u.getTipoUsuario() != null ? u.getTipoUsuario().toLowerCase() : "leitor");
            stmt.setString(7, "ativo");
            
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM usuarios WHERE usuarioid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void atualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nome=?, email=?, telefone=?, cpf=? WHERE usuarioid=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getTelefone());
            stmt.setString(4, u.getCpf());
            stmt.setInt(5, u.getUsuarioId());
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE usuarioid = ?";
        Usuario u = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setUsuarioId(rs.getInt("usuarioid"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setCpf(rs.getString("cpf"));
                u.setTipoUsuario(rs.getString("tipousuario"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return u;
    }
}