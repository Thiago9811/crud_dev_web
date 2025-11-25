/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.acesso;

/**
 *
 * @author Thiago
 */

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

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        // SQL ajustado para o nome correto da sua tabela
        String sql = "SELECT * FROM usuarios"; 
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                // Ajustado para os nomes exatos das suas colunas
                u.setUsuarioId(rs.getInt("usuarioid"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setCpf(rs.getString("cpf"));
                
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    public void excluir(int id) {
        String sql = "DELETE FROM usuarios WHERE usuarioid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. MÉTODO ATUALIZAR
    public void atualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nome=?, email=?, telefone=?, cpf=? WHERE usuarioid=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getTelefone());
            stmt.setString(4, u.getCpf());
            // Adicione getTipoUsuario() na sua classe Usuario se ainda não tiver
            // Se não tiver, remova essa linha ou use um valor fixo por enquanto
            //stmt.setString(5, "LEITOR"); 
            
            stmt.setInt(5, u.getUsuarioId());
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. MÉTODO BUSCAR POR ID (Para preencher o formulário de edição)
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
                // u.setTipoUsuario(rs.getString("tipousuario"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
}
    // ... seu método de salvar/cadastrar também fica aqui ...
