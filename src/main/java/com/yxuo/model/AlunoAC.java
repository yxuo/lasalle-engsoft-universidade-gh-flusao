package com.yxuo.model;

public class AlunoAC extends BaseEntity{
    private int idAluno;
    private String mat;
    private String nome;

    @Override
    public int getId() {
        return getIdAluno();
    }
    
    public AlunoAC() {
        this.idAluno = -1;
        this.mat = "";
        this.nome = "";
    }

    public AlunoAC(int idAluno, String mat, String nome) {
        this.idAluno = idAluno;
        this.mat = mat;
        this.nome = nome;
    }

    public AlunoAC(int idAluno) {
        this.idAluno = idAluno;
        this.mat = "";
        this.nome = "";
    }

    public int getIdAluno() {
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

    // Métodos get e set para o campo &#39;nome&#39;
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "AlunoAC{" +
                "idAluno=" + idAluno +
                ", mat='" + mat + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

}