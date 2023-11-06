package com.yxuo.model;

public class ProfessorAC extends BaseEntity {
    private int idProf;
    private String matProf;
    private String nome;

    private static final String TABLE_NAME = "ProfessorAC";
    private static final String ID_PROF_COLUMN = "idProf";
    private static final String MAT_PROF_COLUMN = "matProf";
    private static final String NOME_COLUMN = "nome";

    public ProfessorAC() {
        idProf = -1;
        matProf = "";
        nome = "";
    }

    public ProfessorAC(int idProf) {
        this.idProf = idProf;
        this.matProf = "";
        this.nome = "";
    }

    public ProfessorAC(int idProf, String matProf, String nome) {
        this.idProf = idProf;
        this.matProf = matProf;
        this.nome = nome;
    }

    // Names

    @Override
    public String getTableName() {
        return handleNamingStrategy(TABLE_NAME);
    }

    @Override
    public String getIdColumn() {
        return handleNamingStrategy(ID_PROF_COLUMN);
    }

    public String getMatProfColumn() {
        return handleNamingStrategy(MAT_PROF_COLUMN);
    }

    public String getNomeColumn() {
        return handleNamingStrategy(NOME_COLUMN);
    }

    // Fields

    @Override
    public Integer getId() {
        return getIdProf();
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public String getMatProf() {
        return matProf;
    }

    public void setMatProf(String matProf) {
        this.matProf = matProf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ProfessorAC{" +
                "idProf=" + idProf +
                ", matProf='" + matProf + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
