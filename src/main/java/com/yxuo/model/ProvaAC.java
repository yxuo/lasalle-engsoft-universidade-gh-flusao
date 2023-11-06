package com.yxuo.model;

public class ProvaAC extends BaseEntity {
    private int idProva;
    private String codProva;
    private String situacao;
    private TurmaAC turma;

    private static final String TABLE_NAME = "ProvaAC";
    private static final String ID_PROVA_COLUMN = "idProva";
    private static final String COD_PROVA_COLUMN = "codProva";
    private static final String SITUACAO_COLUMN = "situacao";
    private static final String TURMA_COLUMN = "turma";

    public ProvaAC() {
        idProva = -1;
        codProva = "";
        situacao = "";
        turma = new TurmaAC();
    }

    public ProvaAC(int idProva) {
        this.idProva = idProva;
        this.codProva = "";
        this.situacao = "";
        this.turma = new TurmaAC();
    }

    public ProvaAC(int idProva, String codProva, String situacao, TurmaAC turma) {
        this.idProva = idProva;
        this.codProva = codProva;
        this.situacao = situacao;
        this.turma = turma;
    }

    // Names

    @Override
    public String getTableName() {
        return handleNamingStrategy(TABLE_NAME);
    }

    @Override
    public String getIdColumn() {
        return handleNamingStrategy(ID_PROVA_COLUMN);
    }

    public String getCodProvaColumn() {
        return handleNamingStrategy(COD_PROVA_COLUMN);
    }

    public String getSituacaoColumn() {
        return handleNamingStrategy(SITUACAO_COLUMN);
    }

    public String getTurmaColumn() {
        return handleNamingStrategy(TURMA_COLUMN);
    }

    // Fields

    @Override
    public Integer getId() {
        return getIdProva();
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