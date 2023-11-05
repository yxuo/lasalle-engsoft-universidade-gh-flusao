package com.yxuo.model;

public class AlunoAC extends BaseEntity {
    private Integer idAluno;
    private String mat;
    private String nome;

    private static final String TABLE_NAME = "AlunoAC";
    private static final String ID_ALUNO_COLUMN = "idAluno";
    private static final String MAT_COLUMN = "mat";
    private static final String NOME_COLUMN = "nome";

    public AlunoAC() {
        this.idAluno = -1;
        this.mat = "";
        this.nome = "";
    }

    public AlunoAC(int idAluno) {
        this.idAluno = idAluno;
        this.mat = "";
        this.nome = "";
    }

    public AlunoAC(int idAluno, String mat, String nome) {
        this.idAluno = idAluno;
        this.mat = mat;
        this.nome = nome;
    }

    // Names

    @Override
    public String getTableName() {
        return handleNamingStrategy(TABLE_NAME);
    }

    @Override
    public String getIdColumn() {
        return handleNamingStrategy(ID_ALUNO_COLUMN);
    }

    public final String getMatColumn() {
        return handleNamingStrategy(MAT_COLUMN);
    }

    public final String getNomeColumn() {
        return handleNamingStrategy(NOME_COLUMN);
    }

    // Fields

    @Override
    public int getId() {
        return getIdAluno();
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {

        this.mat = mat;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{" +
                "idAluno=" + idAluno +
                ", mat='" + mat + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

}