package com.mycompany.mavenproject1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Penalidade {

    private int penalidadeId;
    private int usuarioId;
    private int emprestimoId;
    private String tipoPenalidade;     // exemplo: "multa"
    private String descricao;          // "atraso de 3 dias"
    private BigDecimal valorMulta;     // valor financeiro
    private int diasSuspensao;         // caso queira usar no futuro
    private String statusPenalidade;   // "pendente", "paga"
    private LocalDate dataPenalidade;

    // =============================
    // Getters e Setters
    // =============================

    public int getPenalidadeId() {
        return penalidadeId;
    }

    public void setPenalidadeId(int penalidadeId) {
        this.penalidadeId = penalidadeId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(int emprestimoId) {
        this.emprestimoId = emprestimoId;
    }

    public String getTipoPenalidade() {
        return tipoPenalidade;
    }

    public void setTipoPenalidade(String tipoPenalidade) {
        this.tipoPenalidade = tipoPenalidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(BigDecimal valorMulta) {
        this.valorMulta = valorMulta;
    }

    public int getDiasSuspensao() {
        return diasSuspensao;
    }

    public void setDiasSuspensao(int diasSuspensao) {
        this.diasSuspensao = diasSuspensao;
    }

    public String getStatusPenalidade() {
        return statusPenalidade;
    }

    public void setStatusPenalidade(String statusPenalidade) {
        this.statusPenalidade = statusPenalidade;
    }

    public LocalDate getDataPenalidade() {
        return dataPenalidade;
    }

    public void setDataPenalidade(LocalDate dataPenalidade) {
        this.dataPenalidade = dataPenalidade;
    }
}
