package com.yxuo.controller;

import com.yxuo.model.AlunoAC;
import com.yxuo.repository.AlunoRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoController {
    private List<AlunoAC> alunos;
    private AlunoRepository alunoRepository;

    public AlunoController() throws SQLException {
        this.alunos = new ArrayList<>();
        this.alunoRepository = new AlunoRepository();
    }

    public List<AlunoAC> getAlunos() {
        return alunos;
    }

    public void adicionarAluno(AlunoAC aluno) {
        alunos.add(aluno);
    }

    public void removerAluno(AlunoAC aluno) {
        alunos.remove(aluno);
    }

}
