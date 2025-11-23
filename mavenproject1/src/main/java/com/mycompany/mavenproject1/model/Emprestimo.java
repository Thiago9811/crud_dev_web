package com.mycompany.mavenproject1.model;

import java.sql.Date;

public class Emprestimo {
    // Colunas exatas da tabela
    private int emprestimoId;
    private int usuarioId;
    private int livroId;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private String statusEmprestimo;
    
    // Campos Auxiliares (Não existem na tabela emprestimos, mas vêm do JOIN)
    private String nomeUsuarioAux;
    private String tituloLivroAux;

    // --- GETTERS E SETTERS (Gere via Alt+Insert no NetBeans) ---
    public int getEmprestimoId() { return emprestimoId; }
    public void setEmprestimoId(int emprestimoId) { this.emprestimoId = emprestimoId; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public int getLivroId() { return livroId; }
    public void setLivroId(int livroId) { this.livroId = livroId; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }
    public Date getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(Date dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }
    public String getStatusEmprestimo() { return statusEmprestimo; }
    public void setStatusEmprestimo(String statusEmprestimo) { this.statusEmprestimo = statusEmprestimo; }
    
    public String getNomeUsuarioAux() { return nomeUsuarioAux; }
    public void setNomeUsuarioAux(String nomeUsuarioAux) { this.nomeUsuarioAux = nomeUsuarioAux; }
    public String getTituloLivroAux() { return tituloLivroAux; }
    public void setTituloLivroAux(String tituloLivroAux) { this.tituloLivroAux = tituloLivroAux; }
}