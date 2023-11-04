package com.yxuo.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.RealizaAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.repository.AlunoRepository;
import com.yxuo.repository.DisciplinaRepository;
import com.yxuo.repository.MatriculadoRepository;
import com.yxuo.repository.ProfessorRepository;
import com.yxuo.repository.ProvaRepository;
import com.yxuo.repository.RealizaRepository;
import com.yxuo.repository.TurmaRepository;

public class Migration {
    private Connection connection;
    private AlunoRepository alunoRepository;
    private MatriculadoRepository matriculadoRepository;
    private ProvaRepository provaRepository;
    private RealizaRepository realizaRepository;
    private ProfessorRepository professorRepository;
    private DisciplinaRepository disciplinaRepository;
    private TurmaRepository turmaRepository;

    public Migration() throws SQLException {
        connection = DBConnector.getConnection();
        alunoRepository = new AlunoRepository(connection);
        matriculadoRepository = new MatriculadoRepository(connection);
        provaRepository = new ProvaRepository(connection);
        realizaRepository = new RealizaRepository(connection);
        professorRepository = new ProfessorRepository(connection);
        disciplinaRepository = new DisciplinaRepository(connection);
        turmaRepository = new TurmaRepository(connection);
    }

    public Migration(Connection con) throws SQLException {
        connection = con;
        alunoRepository = new AlunoRepository(connection);
        matriculadoRepository = new MatriculadoRepository(connection);
        provaRepository = new ProvaRepository(connection);
        realizaRepository = new RealizaRepository(connection);
        professorRepository = new ProfessorRepository(connection);
        disciplinaRepository = new DisciplinaRepository(connection);
        turmaRepository = new TurmaRepository(connection);
    }

    public void apagarTabelas() throws SQLException {
        alunoRepository.apagarTabela();
        matriculadoRepository.apagarTabela();
        provaRepository.apagarTabela();
        realizaRepository.apagarTabela();
        professorRepository.apagarTabela();
        disciplinaRepository.apagarTabela();
        turmaRepository.apagarTabela();
    }

    public void criarTabelas() throws SQLException {
        alunoRepository.criarTabela();
        matriculadoRepository.criarTabela();
        provaRepository.criarTabela();
        realizaRepository.criarTabela();
        professorRepository.criarTabela();
        disciplinaRepository.criarTabela();
        turmaRepository.criarTabela();
    }

    public void migrar() throws SQLException {
        List<AlunoAC> Alunos = migrarAluno();
        List<DisciplinaAC> Disciplinas = migrarDisciplina();
        List<ProfessorAC> Professores = migrarProfessor();
        List<TurmaAC> Turmas = migrarTurma(Disciplinas, Professores);
        List<ProvaAC> Provas = migrarProva(Turmas);
        List<MatriculadoAC> Matriculados = migrarMatriculado(Alunos, Turmas);
        migrarRealiza(Matriculados, Provas);
    }

    private List<AlunoAC> migrarAluno() throws SQLException {
        List<AlunoAC> Alunos = new ArrayList<>();
        Alunos.add(new AlunoAC(1, "456", "MARTA"));
        Alunos.add(new AlunoAC(2, "789", "CARLOS"));
        Alunos.add(new AlunoAC(3, "987", "PEDRO"));
        Alunos.add(new AlunoAC(4, "356", "MARTASA"));
        Alunos.add(new AlunoAC(5, "777", "CAOSRO"));
        Alunos.add(new AlunoAC(6, "889", "PEDRORLO"));
        Alunos.add(new AlunoAC(7, "376", "MARVA"));
        Alunos.add(new AlunoAC(8, "787", "CARLOS PEDRO 2"));
        Alunos.add(new AlunoAC(10, "156", "MARIA RITA"));
        Alunos.add(new AlunoAC(11, "177", "CARLOS PEDRO"));
        Alunos.add(new AlunoAC(12, "588", "PEDW CARLOS"));

        for (AlunoAC aluno : Alunos) {
            alunoRepository.inserir(aluno);
        }
        return Alunos;
    }

    private List<ProvaAC> migrarProva(List<TurmaAC> Turmas) throws SQLException {
        List<ProvaAC> Provas = new ArrayList<>();
        Provas.add(new ProvaAC(1, "AV1", "APLICADA", EntityUtil.getFromList(1, Turmas)));
        Provas.add(new ProvaAC(2, "AV1", "APLICADA", EntityUtil.getFromList(2, Turmas)));
        for (ProvaAC prova : Provas) {
            provaRepository.inserir(prova);
        }
        return Provas;
    }

    private List<RealizaAC> migrarRealiza(List<MatriculadoAC> Matriculados, List<ProvaAC> Provas) throws SQLException {
        List<RealizaAC> Realizas = new ArrayList<>();
        Realizas.add(new RealizaAC(realizaRepository.obterProximoID(), 10, EntityUtil.getFromList(1, Matriculados), EntityUtil.getFromList(1, Provas)));
        Realizas.add(new RealizaAC(realizaRepository.obterProximoID(), 1, EntityUtil.getFromList(24, Matriculados), EntityUtil.getFromList(1, Provas)));
        Realizas.add(new RealizaAC(realizaRepository.obterProximoID(), 8, EntityUtil.getFromList(31, Matriculados), EntityUtil.getFromList(1, Provas)));
        for (RealizaAC realiza : Realizas) {
            realizaRepository.inserir(realiza);
        }
        return Realizas;
    }

    private List<ProfessorAC> migrarProfessor() throws SQLException {
        List<ProfessorAC> Professores = new ArrayList<>();
        Professores.add(new ProfessorAC(21, "P01", "GEORGE"));
        Professores.add(new ProfessorAC(22, "P02", "HAMILTON"));
        Professores.add(new ProfessorAC(23, "P03", "COSTA"));
        Professores.add(new ProfessorAC(24, "P04", "CARLOS"));
        Professores.add(new ProfessorAC(25, "P05", "JEAN"));
        for (ProfessorAC professor : Professores) {
            professorRepository.inserir(professor);
        }
        return Professores;
    }

    private List<DisciplinaAC> migrarDisciplina() throws SQLException {
        List<DisciplinaAC> Disciplinas = new ArrayList<>();
        Disciplinas.add(new DisciplinaAC(1, "D01", "BANCO DE DADOS"));
        Disciplinas.add(new DisciplinaAC(2, "D02", "ANALISE OO"));
        Disciplinas.add(new DisciplinaAC(3, "D03", "ENGENHARIA DE SOFTWARE"));
        Disciplinas.add(new DisciplinaAC(4, "D04", "GESTÃO DE PROJETOS"));
        Disciplinas.add(new DisciplinaAC(5, "D05", "CALCULO"));
        Disciplinas.add(new DisciplinaAC(6, "D06", "FISICA"));
        for (DisciplinaAC disciplina : Disciplinas) {
            disciplinaRepository.inserir(disciplina);
        }
        return Disciplinas;
    }

    private List<TurmaAC> migrarTurma(List<DisciplinaAC> Disciplinas, List<ProfessorAC> Professores)
            throws SQLException {
        List<TurmaAC> Turmas = new ArrayList<>();
        Turmas.add(new TurmaAC(1, "MANHA", "TERCA", "18:20", "22:00", EntityUtil.getFromList(1, Disciplinas),
                EntityUtil.getFromList(21, Professores)));
        Turmas.add(new TurmaAC(2, "MANHA", "QUARTA", "08:20", "12:00", EntityUtil.getFromList(3, Disciplinas),
                EntityUtil.getFromList(21, Professores)));
        Turmas.add(new TurmaAC(3, "MANHA", "TERCA", "08:20", "12:00", EntityUtil.getFromList(2, Disciplinas),
                EntityUtil.getFromList(22, Professores)));
        Turmas.add(new TurmaAC(4, "NOITE", "QUINTA", "18:20", "22:00", EntityUtil.getFromList(4, Disciplinas),
                EntityUtil.getFromList(23, Professores)));
        Turmas.add(new TurmaAC(5, "MANHA", "QUINTA", "08:20", "12:00", EntityUtil.getFromList(4, Disciplinas),
                EntityUtil.getFromList(21, Professores)));
        for (TurmaAC turma : Turmas) {
            turmaRepository.inserir(turma);
        }
        return Turmas;
    }

    private List<MatriculadoAC> migrarMatriculado(List<AlunoAC> Alunos, List<TurmaAC> Turmas) throws SQLException {
        List<MatriculadoAC> Matriculados = new ArrayList<>();
        Matriculados.add(new MatriculadoAC(1, EntityUtil.getFromList(1, Alunos), EntityUtil.getFromList(1, Turmas)));
        Matriculados.add(new MatriculadoAC(21, EntityUtil.getFromList(1, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(22, EntityUtil.getFromList(1, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(23, EntityUtil.getFromList(1, Alunos), EntityUtil.getFromList(4, Turmas)));
        Matriculados.add(new MatriculadoAC(24, EntityUtil.getFromList(2, Alunos), EntityUtil.getFromList(1, Turmas)));
        Matriculados.add(new MatriculadoAC(25, EntityUtil.getFromList(2, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(26, EntityUtil.getFromList(2, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(27, EntityUtil.getFromList(2, Alunos), EntityUtil.getFromList(5, Turmas)));
        Matriculados.add(new MatriculadoAC(28, EntityUtil.getFromList(3, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(29, EntityUtil.getFromList(3, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(30, EntityUtil.getFromList(3, Alunos), EntityUtil.getFromList(4, Turmas)));
        Matriculados.add(new MatriculadoAC(31, EntityUtil.getFromList(4, Alunos), EntityUtil.getFromList(1, Turmas)));
        Matriculados.add(new MatriculadoAC(32, EntityUtil.getFromList(4, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(33, EntityUtil.getFromList(5, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(34, EntityUtil.getFromList(5, Alunos), EntityUtil.getFromList(5, Turmas)));
        Matriculados.add(new MatriculadoAC(35, EntityUtil.getFromList(6, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(36, EntityUtil.getFromList(6, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(37, EntityUtil.getFromList(7, Alunos), EntityUtil.getFromList(4, Turmas)));
        Matriculados.add(new MatriculadoAC(38, EntityUtil.getFromList(7, Alunos), EntityUtil.getFromList(1, Turmas)));
        Matriculados.add(new MatriculadoAC(39, EntityUtil.getFromList(7, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(40, EntityUtil.getFromList(8, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(41, EntityUtil.getFromList(8, Alunos), EntityUtil.getFromList(5, Turmas)));
        Matriculados.add(new MatriculadoAC(42, EntityUtil.getFromList(11, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(43, EntityUtil.getFromList(12, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(44, EntityUtil.getFromList(11, Alunos), EntityUtil.getFromList(4, Turmas)));
        Matriculados.add(new MatriculadoAC(45, EntityUtil.getFromList(12, Alunos), EntityUtil.getFromList(1, Turmas)));
        Matriculados.add(new MatriculadoAC(46, EntityUtil.getFromList(12, Alunos), EntityUtil.getFromList(2, Turmas)));
        Matriculados.add(new MatriculadoAC(47, EntityUtil.getFromList(11, Alunos), EntityUtil.getFromList(3, Turmas)));
        Matriculados.add(new MatriculadoAC(48, EntityUtil.getFromList(10, Alunos), EntityUtil.getFromList(5, Turmas)));

        for (MatriculadoAC matriculado : Matriculados) {
            matriculadoRepository.inserir(matriculado);
        }
        return Matriculados;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("--- Migrations ---");
        String cmd = "run";
        if (args.length > 0) {
            cmd = args[0];
        }
        System.out.println("Cmd: " + cmd);

        Migration migration = new Migration();
        if (cmd.equals("run")) {
            System.out.println("Apagando tabelas...\n");
            migration.apagarTabelas();
            System.out.println("Criando tabelas...\n");
            migration.criarTabelas();
            System.out.println("\nAplicando migrações...\n");
            migration.migrar();
        } else if (cmd.equals("clean")) {
            System.out.println("Apagando tabelas...");
            migration.apagarTabelas();
        }
        System.out.println("Feito!");

    }
}
