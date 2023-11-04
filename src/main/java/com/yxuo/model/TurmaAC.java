package com.yxuo.model;

public class TurmaAC extends BaseEntity{
    private int idTurma;
    private String turno;
    private String dia;
    private String horaInicio;
    private String horaFim;
    private DisciplinaAC disciplina;
    private ProfessorAC professor;

    @Override
    public int getId() {
        return getIdTurma();
    }
    
    public TurmaAC() {
        this.idTurma = -1;
        this.turno = "";
        this.dia = "";
        this.horaInicio = "";
        this.horaFim = "";
        this.disciplina = new DisciplinaAC();
        this.professor = new ProfessorAC();
    }

    public TurmaAC(int idTurma, String turno, String dia, String horaInicio, String horaFim,
            DisciplinaAC disciplina, ProfessorAC professor) {
        this.idTurma = idTurma;
        this.turno = turno;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    public TurmaAC(int idTurma, int idDis, int idProfessor) {
        this.idTurma = idTurma;
        this.turno = "";
        this.dia = "";
        this.horaInicio = "";
        this.horaFim = "";
        this.disciplina = new DisciplinaAC(idDis);
        this.professor = new ProfessorAC(idProfessor);
    }

    public TurmaAC(int idTurma) {
        this.idTurma = idTurma;
        this.turno = "";
        this.dia = "";
        this.horaInicio = "";
        this.horaFim = "";
        this.disciplina = new DisciplinaAC();
        this.professor = new ProfessorAC();
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public DisciplinaAC getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaAC disciplina) {
        this.disciplina = disciplina;
    }

    public ProfessorAC getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorAC professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "TurmaAC{" +
                "idTurma=" + idTurma +
                ", turno='" + turno + '\'' +
                ", dia='" + dia + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFim='" + horaFim + '\'' +
                ", disciplina=" + disciplina +
                ", professor=" + professor +
                '}';
    }
}