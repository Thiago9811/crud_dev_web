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
import java.sql.SQLException;

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
    
    // ... seus outros métodos de verificar disponibilidade ...
}