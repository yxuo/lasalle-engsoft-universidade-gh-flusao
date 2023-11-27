package com.yxuo.controller.cli.struct;

import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.ProvaAC;

public class SProvasCliCResponse {
    private List<ProvaAC> provas = new ArrayList<>();
    private String response = "";

    public SProvasCliCResponse() {
    }

    public SProvasCliCResponse(List<ProvaAC> provas, String response) {
        this.provas = provas;
        this.response = response;
    }

    public SProvasCliCResponse(ProvaAC prova, String response) {
        this.provas.clear();
        this.provas.add(prova);
        this.response = response;
    }

    public List<ProvaAC> getProvas() {
        return this.provas;
    }

    public void setProvas(List<ProvaAC> provas) {
        this.provas = provas;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
