package com.yxuo.model;

/**
 * Também chamado de "Cursa", mas na implementação se chama RealizaAC.
 */
public class RealizaProvaAC extends BaseEntity {
    private int idRealiza = -1;
    private double nota = -1;
    private CursaAC matriculado = new CursaAC();
    private ProvaAC prova = new ProvaAC();

    private static final String TABLE_NAME = "RealizaProvaAC";
    private static final String ID_REALIZA_COLUMN = "idRealiza";
    private static final String NOTA_COLUMN = "nota";
    private static final String MATIRCULADO_COLUMN = "matriculado";
    private static final String PROVA_COLUMN = "prova";

    public RealizaProvaAC() {
    }

    public RealizaProvaAC(int idRealiza) {
        this.idRealiza = idRealiza;
    }

    public RealizaProvaAC(int idRealiza, double nota, int idMatriculado, int idProva) {
        this.idRealiza = idRealiza;
        this.nota = nota;
        this.matriculado = new CursaAC(idMatriculado);
        this.prova = new ProvaAC(idProva);
    }

    public RealizaProvaAC(int idRealiza, double nota, CursaAC matriculado, ProvaAC prova) {
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

    public CursaAC getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(CursaAC matriculado) {
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
