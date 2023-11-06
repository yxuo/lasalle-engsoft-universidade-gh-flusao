package com.yxuo.repository.TDE6;

import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.MatriculadoAC;

public class TDE6Q1ResponseDTO {
    private List<MatriculadoAC> matriculados = new ArrayList<>();

    public List<MatriculadoAC> getMatriculados() {
        return this.matriculados;
    }

    public void setMatriculados(List<MatriculadoAC> matriculados) {
        this.matriculados = matriculados;
    }


}
