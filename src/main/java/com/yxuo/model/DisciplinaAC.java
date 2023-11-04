package com.yxuo.model;

public class DisciplinaAC extends BaseEntity{
    private int idDis;
    private String codDis;
    private String nome;

    @Override
    public int getId() {
        return getIdDis();
    }
    
    public DisciplinaAC() {
        idDis = -1;
        codDis = "";
        nome = "";
    }
    
    public DisciplinaAC(int idDis, String codDis, String nome) {
        this.idDis = idDis;
        this.codDis = codDis;
        this.nome = nome;
    }
    
    public DisciplinaAC(int idDis) {
        this.idDis = idDis;
        codDis = "";
        nome = "";
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