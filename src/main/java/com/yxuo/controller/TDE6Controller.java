package com.yxuo.controller;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.repository.AlunoRepository;
import com.yxuo.repository.MatriculadoRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TDE6Controller {
    private List<AlunoAC> alunos;
    private AlunoRepository alunoRepository;
    private MatriculadoRepository matriculadoRepository;

    public TDE6Controller() throws SQLException {
        this.alunos = new ArrayList<>();
        this.alunoRepository = new AlunoRepository();
        this.matriculadoRepository = new MatriculadoRepository();
    }

    /**
     * Selecione matrícula e nome do aluno, bem como, código, turno, dia, hora
     * início e hora fim das turmas que eles estão matriculados
     */
    // public List<MatriculadoAC> Q1GetAlunosMatriculados() {
        
    // }

}
