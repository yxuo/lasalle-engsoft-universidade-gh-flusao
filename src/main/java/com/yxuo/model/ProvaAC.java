package com.yxuo.model;

public class ProvaAC extends BaseEntity {
    private int idProva;
    private String codProva;
    private String situacao;
    private TurmaAC turma;

    @Override
    public int getId() {
        return getIdProva();
    }

    public ProvaAC() {
        idProva = -1;
        codProva = "";
        situacao = "";
        turma = new TurmaAC();
    }

    public ProvaAC(int idProva, String codProva, String situacao, TurmaAC turma) {
        this.idProva = idProva;
        this.codProva = codProva;
        this.situacao = situacao;
        this.turma = turma;
    }

    public ProvaAC(int idProva) {
        this.idProva = idProva;
        this.codProva = "";
        this.situacao = "";
        this.turma = new TurmaAC();
    }

    public int getIdProva() {
        return idProva;
    }

    public void setIdProva(int idProva) {
        this.idProva = idProva;
    }

    public String getCodProva() {
        return codProva;
    }

    public void setCodProva(String codProva) {
        this.codProva = codProva;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public TurmaAC getTurma() {
        return turma;
    }

    public void setTurma(TurmaAC turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        return "ProvaAC{" +
                "idProva=" + idProva +
                ", codProva='" + codProva + '\'' +
                ", situacao='" + situacao + '\'' +
                ", turma=" + turma +
                '}';
    }
}