package com.yxuo.model;

public class DisciplinaAC extends BaseEntity {
    private int idDis;
    private String codDis;
    private String nome;

    private static final String TABLE_NAME = "DisciplinaAC";
    private static final String ID_DIS_COLUMN = "idDis";
    private static final String COD_DIS_COLUMN = "codDis";
    private static final String NOME_COLUMN = "nome";

    public DisciplinaAC() {
        idDis = -1;
        codDis = "";
        nome = "";
    }

    public DisciplinaAC(int idDis) {
        this.idDis = idDis;
        codDis = "";
        nome = "";
    }

    public DisciplinaAC(int idDis, String codDis, String nome) {
        this.idDis = idDis;
        this.codDis = codDis;
        this.nome = nome;
    }

    // Names

    @Override
    public String getTableName() {
        return handleNamingStrategy(TABLE_NAME);
    }

    @Override
    public String getIdColumn() {
        return handleNamingStrategy(ID_DIS_COLUMN);
    }

    public String getCodDisColumn() {
        return handleNamingStrategy(COD_DIS_COLUMN);
    }

    public String getNomeColumn() {
        return handleNamingStrategy(NOME_COLUMN);
    }

    // Fields

    @Override
    public int getId() {
        return getIdDis();
    }

    public int getIdDis() {
        return idDis;
    }

    public void setIdDis(int idDis) {
        this.idDis = idDis;
    }

    public String getCodDis() {
        return codDis;
    }

    public void setCodDis(String codDis) {
        this.codDis = codDis;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "DisciplinaAC{" +
                "idDis=" + idDis +
                ", codDis='" + codDis + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}