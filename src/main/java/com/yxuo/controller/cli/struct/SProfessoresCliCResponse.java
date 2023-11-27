package com.yxuo.controller.cli.struct;

import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.ProfessorAC;

public class SProfessoresCliCResponse {
    private List<ProfessorAC> professores = new ArrayList<>();
    private String response = "";

    public SProfessoresCliCResponse() {
    }

    public SProfessoresCliCResponse(List<ProfessorAC> professores, String response) {
        this.professores = professores;
        this.response = response;
    }

    public SProfessoresCliCResponse(SProfessorCliCResponse response) {
        this.professores = new ArrayList<>();
        this.professores.add(response.getProfessor());
        this.response = response.getResponse();
    }

    public List<ProfessorAC> getProfessores() {
        return this.professores;
    }

    public void setProfessores(List<ProfessorAC> professores) {
        this.professores = professores;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
