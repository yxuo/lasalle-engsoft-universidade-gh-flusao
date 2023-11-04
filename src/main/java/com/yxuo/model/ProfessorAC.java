package com.yxuo.model;

public class ProfessorAC extends BaseEntity {
    private int idProf;
    private String matProf;
    private String nome;

    @Override
    public int getId() {
        return getIdProf();
    }

    public ProfessorAC() {
        idProf = -1;
        matProf = "";
        nome = "";
    }

    public ProfessorAC(int idProf, String matProf, String nome) {
        this.idProf = idProf;
        this.matProf = matProf;
        this.nome = nome;
    }

    public ProfessorAC(int idProf) {
        this.idProf = idProf;
        this.matProf = "";
        this.nome = "";
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
