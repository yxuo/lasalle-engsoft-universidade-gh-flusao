package com.yxuo.controller.cli.struct;

import com.yxuo.model.ProfessorAC;

public class SProfessorCliCResponse {
    private ProfessorAC professor;
    private String response = "";

    public SProfessorCliCResponse(ProfessorAC professor, String response) {
        this.professor = professor;
        this.response = response;
    }

    public ProfessorAC getProfessor() {
        return this.professor;
    }

    public void setProfessor(ProfessorAC professor) {
        this.professor = professor;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
