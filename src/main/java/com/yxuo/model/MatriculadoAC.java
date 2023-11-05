package com.yxuo.model;

public class MatriculadoAC extends BaseEntity {
    private int idMatriculado;
    private AlunoAC aluno;
    private TurmaAC turma;

    private static final String TABLE_NAME = "MatriculadoAC";
    private static final String ID_MATIRCULADO_COLUMN = "idMatriculado";
    private static final String ALUNO_COLUMN = "aluno";
    private static final String TURMA_COLUMN = "turma";

    public MatriculadoAC() {
        idMatriculado = -1;
        aluno = new AlunoAC();
        turma = new TurmaAC();
    }

    public MatriculadoAC(int idMatriculado) {
        this.idMatriculado = idMatriculado;
        this.aluno = new AlunoAC();
        this.turma = new TurmaAC();
    }

    public MatriculadoAC(int idMatriculado, AlunoAC aluno, TurmaAC turma) {
        this.idMatriculado = idMatriculado;
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
        return handleNamingStrategy(ID_MATIRCULADO_COLUMN);
    }

    public String getAlunoColumn() {
        return handleNamingStrategy(ALUNO_COLUMN);
    }

    public String getTurmaColumn() {
        return handleNamingStrategy(TURMA_COLUMN);
    }
    
    // Fields
    
        @Override
        public int getId() {
            return getIdMatriculado();
        }

    public int getIdMatriculado() {
        return idMatriculado;
    }

    public void setIdMatriculado(int idMatriculado) {
        this.idMatriculado = idMatriculado;
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
                "idMatriculado=" + idMatriculado +
                ", aluno=" + aluno +
                ", turma=" + turma +
                '}';
    }
}