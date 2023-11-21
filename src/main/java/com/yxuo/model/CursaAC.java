package com.yxuo.model;

/**
 * Também chamado de "Matriculado".
 */
public class CursaAC extends BaseEntity {
    private int idCursa;
    private AlunoAC aluno;
    private TurmaAC turma;

    private static final String TABLE_NAME = "CursaAC";
    private static final String ID_CURSA_COLUMN = "idCursa";
    private static final String ALUNO_COLUMN = "aluno";
    private static final String TURMA_COLUMN = "turma";

    public CursaAC() {
        idCursa = -1;
        aluno = new AlunoAC();
        turma = new TurmaAC();
    }

    public CursaAC(int idMatriculado) {
        this.idCursa = idMatriculado;
        this.aluno = new AlunoAC();
        this.turma = new TurmaAC();
    }

    public CursaAC(int idMatriculado, AlunoAC aluno, TurmaAC turma) {
        this.idCursa = idMatriculado;
        this.aluno = aluno;
        this.turma = turma;
    }

    // Names

    @Override
    public String getTableName() {
        return handleNamingStrategy(TABLE_NAME);
    }
    
    @Override
    public String getIdColumn() {
        return handleNamingStrategy(ID_CURSA_COLUMN);
    }

    public String getAlunoColumn() {
        return handleNamingStrategy(ALUNO_COLUMN);
    }

    public String getTurmaColumn() {
        return handleNamingStrategy(TURMA_COLUMN);
    }
    
    // Fields
    
        @Override
        public Integer getId() {
            return getIdCursa();
        }

    public int getIdCursa() {
        return idCursa;
    }

    public void setIdCursa(int idMatriculado) {
        this.idCursa = idMatriculado;
    }

    public AlunoAC getAluno() {
        return aluno;
    }

    public void setAluno(AlunoAC aluno) {
        this.aluno = aluno;
    }

    public TurmaAC getTurma() {
        return turma;
    }

    public void setTurma(TurmaAC turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        return "MatriculadoAC{" +
                "idMatriculado=" + idCursa +
                ", aluno=" + aluno +
                ", turma=" + turma +
                '}';
    }
}