/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.model;

/**
 *
 * @author Thiago
 */

import java.sql.Date;

public class Usuario {
    private int usuarioId;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String senha;
    private String tipoUsuario;
    private int qtdEmprestimosAtivos;
    private boolean temPenalidadePendente;
    // ... adicione os outros campos se quiser mostrar na lista (tipo, status, etc)

    // Getters e Setters
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getSenha(){return senha;}
    public void setSenha(String senha){this.senha = senha;}
    
    public void setTipoUsuario(String tipoUsuario){this.tipoUsuario = tipoUsuario;}
    public String getTipoUsuario(){return tipoUsuario;}
    // Getters e Setters normais...
    public int getQtdEmprestimosAtivos() { return qtdEmprestimosAtivos; }
    public void setQtdEmprestimosAtivos(int qtd) { this.qtdEmprestimosAtivos = qtd; }
    public boolean isTemPenalidadePendente() { return temPenalidadePendente; }
    public void setTemPenalidadePendente(boolean tem) { this.temPenalidadePendente = tem; }

    // --- FEATURE 7: Formatação de CPF (Método Inteligente) ---
    // O JSP vai chamar isso usando ${usuario.cpfFormatado}
    public String getCpfFormatado() {
        if (this.cpf != null && this.cpf.length() == 11) {
            return this.cpf.substring(0, 3) + "." +
                   this.cpf.substring(3, 6) + "." +
                   this.cpf.substring(6, 9) + "-" +
                   this.cpf.substring(9, 11);
        }
        return this.cpf; // Retorna normal se não tiver 11 dígitos
    }

    
    
   
}