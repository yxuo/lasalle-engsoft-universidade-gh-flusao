package com.yxuo.model;

public class RealizaAC extends BaseEntity {
    private int idRealiza;
    private double nota;
    private MatriculadoAC matriculado;
    private ProvaAC prova;

    private static final String TABLE_NAME = "RealizaAC";
    private static final String ID_REALIZA_COLUMN = "idRealiza";
    private static final String NOTA_COLUMN = "nota";
    private static final String MATIRCULADO_COLUMN = "matriculado";
    private static final String PROVA_COLUMN = "prova";

    public RealizaAC() {
        idRealiza = -1;
        nota = 0;
        matriculado = new MatriculadoAC();
        prova = new ProvaAC();
    }

    public RealizaAC(int idRealiza) {
        this.idRealiza = idRealiza;
        this.nota = 0;
        this.matriculado = new MatriculadoAC();
        this.prova = new ProvaAC();
    }

    public RealizaAC(int idRealiza, double nota, int idMatriculado, int idProva) {
        this.idRealiza = idRealiza;
        this.nota = nota;
        this.matriculado = new MatriculadoAC(idMatriculado);
        this.prova = new ProvaAC(idProva);
    }

    public RealizaAC(int idRealiza, double nota, MatriculadoAC matriculado, ProvaAC prova) {
        this.idRealiza = idRealiza;
        this.nota = nota;
        this.matriculado = matriculado;
        this.prova = prova;
    }

    // Names

    @Override
    public String getTableName() {
        return handleNamingStrategy(TABLE_NAME);
    }

    @Override
    public String getIdColumn() {
        return handleNamingStrategy(ID_REALIZA_COLUMN);
    }

    public String getNotaColumn() {
        return handleNamingStrategy(NOTA_COLUMN);
    }

    public String getMatriculadoColumn() {
        return handleNamingStrategy(MATIRCULADO_COLUMN);
    }

    public String getProvaColumn() {
        return handleNamingStrategy(PROVA_COLUMN);
    }

    // Fields

    @Override
    public Integer getId() {
        return getIdRealiza();
    }

    public int getIdRealiza() {
        return idRealiza;
    }

    public void setIdRealiza(int idRealiza) {
        this.idRealiza = idRealiza;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public MatriculadoAC getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(MatriculadoAC matriculado) {
        this.matriculado = matriculado;
    }

    public ProvaAC getProva() {
        return prova;
    }

    public void setProva(ProvaAC prova) {
        this.prova = prova;
    }

    @Override
    public String toString() {
        return "RealizaAC{" +
                "idRealiza=" + idRealiza +
                ", nota=" + nota +
                ", matriculado=" + matriculado +
                ", prova=" + prova +
                '}';
    }
}
