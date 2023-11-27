package com.yxuo.repository.TDE6.structs;

import com.yxuo.model.ProfessorAC;

public class Tde6Q6Obj {
    private ProfessorAC professor = new ProfessorAC();
    private Integer countProfessor = 0;

    public Tde6Q6Obj() {
    }

    public Tde6Q6Obj(ProfessorAC professor, Integer countProfessor) {
        this.professor = professor;
        this.countProfessor = countProfessor;
    }

    public ProfessorAC getProfessor() {
        return this.professor;
    }

    public void setProfessor(ProfessorAC professor) {
        this.professor = professor;
    }

    public Integer getProfessorCount() {
        return this.countProfessor;
    }

    public void setCountProfessor(Integer countProfessor) {
        this.countProfessor = countProfessor;
    }

    @Override
    public String toString() {
        return "{" +
                " professor='" + getProfessor() + "'" +
                ", countProfessor='" + getProfessorCount() + "'" +
                "}";
    }

}
