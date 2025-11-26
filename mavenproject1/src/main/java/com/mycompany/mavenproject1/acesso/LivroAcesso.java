/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.acesso;

/**
 *
 * @author Thiago
 */

import com.mycompany.mavenproject1.model.Livro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroAcesso {
    
    private Connection getConnection() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Biblioteca", "root", "root");
    }

    public void cadastrar(Livro livro) throws Exception {
        Connection conn = getConnection();
        String sql = "INSERT INTO LIVROS (TITULO, AUTOR, EDITORA, ANOPUBLICACAO, ISBN, GENERO, QUANTIDADETOTAL, QUANTIDADEDISPONIVEL) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getIsbn());
            stmt.setString(6, livro.getGenero());
            stmt.setInt(7, livro.getQuantidadeTotal());
            
            // Ao cadastrar, a quantidade disponível é igual à total
            stmt.setInt(8, livro.getQuantidadeTotal()); 
            
            stmt.executeUpdate();
        } finally {
            conn.close();
        }
    }
    // ... imports (List, ArrayList, ResultSet, PreparedStatement, etc) ...

    public List<Livro> listarTodos() {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM LIVROS";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro l = new Livro();

                // Mapeando as colunas do banco para o objeto
                l.setLivroId(rs.getInt("livroid"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setEditora(rs.getString("editora"));
                l.setAnoPublicacao(rs.getInt("anopublicacao"));
                l.setIsbn(rs.getString("isbn"));
                l.setGenero(rs.getString("genero"));
                l.setQuantidadeTotal(rs.getInt("quantidadetotal"));
                l.setQuantidadeDisponivel(rs.getInt("quantidadedisponivel"));

                lista.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // 1. EXCLUIR
    public void excluir(int id) {
        String sql = "DELETE FROM livros WHERE livroid = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. BUSCAR POR ID (Para preencher o formulário de edição)
    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE livroid = ?";
        Livro l = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                l = new Livro();
                l.setLivroId(rs.getInt("livroid"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setEditora(rs.getString("editora"));
                l.setAnoPublicacao(rs.getInt("anopublicacao"));
                l.setIsbn(rs.getString("isbn"));
                l.setGenero(rs.getString("genero"));
                l.setQuantidadeTotal(rs.getInt("quantidadetotal"));
                l.setQuantidadeDisponivel(rs.getInt("quantidadedisponivel"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    // 3. ATUALIZAR
    
    public void atualizar(Livro l) {
        try {
            Connection conn = getConnection();
            
            // 1. Antes de atualizar, buscamos como está no banco AGORA
            // Isso é necessário para saber quantos estão disponíveis e calcular a diferença
            Livro livroNoBanco = buscarPorId(l.getLivroId());
            
            // Exemplo: Tinha 10, Usuário digitou 15. Diferença = +5.
            // Exemplo: Tinha 10, Usuário digitou 8. Diferença = -2.
            int diferencaEstoque = l.getQuantidadeTotal() - livroNoBanco.getQuantidadeTotal();
            
            // A nova disponibilidade é a antiga + a diferença
            int novaDisponibilidade = livroNoBanco.getQuantidadeDisponivel() + diferencaEstoque;
            
            // Validação simples para não ficar negativo
            if (novaDisponibilidade < 0) novaDisponibilidade = 0;

            // 2. Agora fazemos o UPDATE atualizando AMBOS
            String sql = "UPDATE livros SET titulo=?, autor=?, editora=?, anopublicacao=?, isbn=?, genero=?, quantidadetotal=?, quantidadedisponivel=? WHERE livroid=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, l.getTitulo());
            stmt.setString(2, l.getAutor());
            stmt.setString(3, l.getEditora());
            stmt.setInt(4, l.getAnoPublicacao());
            stmt.setString(5, l.getIsbn());
            stmt.setString(6, l.getGenero());
            stmt.setInt(7, l.getQuantidadeTotal());
            
            // Aqui entra o valor calculado automaticamente
            stmt.setInt(8, novaDisponibilidade);
            
            stmt.setInt(9, l.getLivroId()); // WHERE (agora é o índice 9)
            
            stmt.executeUpdate();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ... seus outros métodos de verificar disponibilidade ...
}