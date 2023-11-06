package com.yxuo.repository.TDE6;

import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.TurmaAC;

public class TDE6Q2DTO {
    public TurmaAC turma;
    public MatriculadoAC matriculado;

    public TDE6Q2DTO(TurmaAC turma, MatriculadoAC matriculado) {
        this.turma = turma;
        this.matriculado = matriculado;
    }

    public TurmaAC getTurma() {
        return this.turma;
    }

    public void setTurma(TurmaAC turma) {
        this.turma = turma;
    }

    public MatriculadoAC getMatriculado() {
        return this.matriculado;
    }

    public void setMatriculado(MatriculadoAC matriculado) {
        this.matriculado = matriculado;
    }

}
