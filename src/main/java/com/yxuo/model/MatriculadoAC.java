package com.yxuo.model;

public class MatriculadoAC extends BaseEntity {
    private int idMatriculado;
    private AlunoAC aluno;
    private TurmaAC turma;

    @Override
    public int getId() {
        return getIdMatriculado();
    }

    public MatriculadoAC() {
        idMatriculado = -1;
        aluno = new AlunoAC();
        turma = new TurmaAC();
    }

    public MatriculadoAC(int idMatriculado, AlunoAC aluno, TurmaAC turma) {
        this.idMatriculado = idMatriculado;
        this.aluno = aluno;
        this.turma = turma;
    }

    public MatriculadoAC(int idMatriculado) {
        this.idMatriculado = idMatriculado;
        this.aluno = new AlunoAC();
        this.turma = new TurmaAC();
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