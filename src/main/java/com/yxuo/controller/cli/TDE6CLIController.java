package com.yxuo.controller.cli;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yxuo.constants.TableCte;
import com.yxuo.model.AlunoAC;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.repository.TDE6.TDE6Repository;
import com.yxuo.util.CLI;

public class TDE6CLIController {

    private TDE6Repository tde6Repository;
    private TurmaAC turma = new TurmaAC();
    private DisciplinaAC disciplina = turma.getDisciplina();
    private ProfessorAC professor = turma.getProfessor();
    private AlunoAC aluno = new AlunoAC();

    public TDE6CLIController() throws SQLException {
        tde6Repository = new TDE6Repository();
    }

    // #region Q1

    /**
     * @return
     * 
     *         <pre>
     * matriculado {
     *     aluno{ matricula, nome},
     *     turma{ codigo(id?), turno, dia, horaInicio, horaFim}
     * }
     *         </pre>
     * 
     * @throws SQLException
     */
    public String getQ1String() throws SQLException {
        String response = "";
        List<MatriculadoAC> q1 = tde6Repository.getQ1();
        List<Integer> maxLength = getQ1MaxLength(q1);
        response += getQ1Header(maxLength);
        response += getQ1Body(q1, maxLength);
        return response;
    }

    private String getQ1Header(List<Integer> maxLength) {
        String response = "";
        MatriculadoAC matriculado = new MatriculadoAC();
        AlunoAC aluno = matriculado.getAluno();
        TurmaAC turma = matriculado.getTurma();
        response += CLI.getTableString(Arrays.asList(
                "linha",
                aluno.getMatColumn(),
                aluno.getNomeColumn(),
                turma.getIdColumn(),
                turma.getTurnoColumn(),
                turma.getDiaColumn(),
                turma.getHoraInicioColumn(),
                turma.getHoraFimColumn()), maxLength);
        return response;
    }

    private String getQ1Body(List<MatriculadoAC> q1, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (MatriculadoAC matriculado : q1) {
            AlunoAC aluno = matriculado.getAluno();
            TurmaAC turma = matriculado.getTurma();
            response += CLI.getTableString(Arrays.asList(
                    count,
                    aluno.getMat(),
                    aluno.getNome(),
                    turma.getId(),
                    turma.getTurno(),
                    turma.getDia(),
                    turma.getHoraInicio(),
                    turma.getHoraFim()), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ1MaxLength(List<MatriculadoAC> q1) {
        List<Integer> maxLength = new ArrayList<>();

        AlunoAC aluno = new AlunoAC();
        TurmaAC turma = new TurmaAC();
        maxLength = CLI.getMaxLength(
                Arrays.asList(
                        TableCte.LINHA,
                        aluno.getMatColumn(),
                        aluno.getNomeColumn(),
                        turma.getIdColumn(),
                        turma.getTurnoColumn(),
                        turma.getDiaColumn(),
                        turma.getHoraInicioColumn(),
                        turma.getHoraFimColumn()),
                maxLength);

        for (MatriculadoAC matriculado : q1) {
            AlunoAC alunoI = matriculado.getAluno();
            TurmaAC turmaI = matriculado.getTurma();
            maxLength = CLI.getMaxLength(
                    Arrays.asList(
                            "",
                            alunoI.getMat(),
                            alunoI.getNome(),
                            turmaI.getId(),
                            turmaI.getTurno(),
                            turmaI.getDia(),
                            turmaI.getHoraInicio(),
                            turmaI.getHoraFim()),
                    maxLength);
        }
        return maxLength;
    }

    // #endregion Q1

    // #region Q2

    /**
     * @return
     * 
     *         <pre>
     * matriculado {
     *     aluno{ matricula, nome},
     *     turma{ codigo(id?), turno, dia, horaInicio, horaFim}
     * }
     *         </pre>
     * 
     * @throws SQLException
     */
    public String getQ2String() throws SQLException {
        String response = "";
        List<TurmaAC> q2 = tde6Repository.getQ2();
        List<String> headers = getQ2Headers(q2);
        List<Integer> maxLength = getQ2MaxLength(q2, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ2Body(q2, maxLength);
        return response;
    }

    private List<String> getQ2Headers(List<TurmaAC> q2) {
        return Arrays.asList(
                "linha",
                professor.getMatProfColumn(),
                professor.getNomeColumn(),
                disciplina.getCodDisColumn(),
                disciplina.getNomeColumn());
    }

    private String getQ2Body(List<TurmaAC> q2, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (TurmaAC turma : q2) {
            DisciplinaAC disciplina = turma.getDisciplina();
            ProfessorAC professor = turma.getProfessor();
            response += CLI.getTableString(
                    Arrays.asList(
                            count,
                            professor.getMatProf(),
                            professor.getNome(),
                            disciplina.getCodDis(),
                            disciplina.getNome()),
                    maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ2MaxLength(List<TurmaAC> q2, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();

        // header
        maxLength = CLI.getMaxLength(headers, maxLength);

        for (TurmaAC turma : q2) {
            ProfessorAC professor = turma.getProfessor();
            DisciplinaAC disciplina = turma.getDisciplina();
            maxLength = CLI.getMaxLength(
                    Arrays.asList(
                            "",
                            professor.getMatProf(),
                            professor.getNome(),
                            disciplina.getCodDis(),
                            disciplina.getNome()),
                    maxLength);
        }
        return maxLength;
    }

    // #endregion Q2

    // #region Q3

    /**
     * @return aluno{ matricula, nome }
     * 
     * @throws SQLException
     */
    public String getQ3String() throws SQLException {
        String response = "";
        List<AlunoAC> q3 = tde6Repository.getQ3();
        List<String> headers = getQ3Headers(q3);
        List<Integer> maxLength = getQ3MaxLength(q3, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ3Body(q3, maxLength);
        return response;
    }

    private List<String> getQ3Headers(List<AlunoAC> q3) {
        return Arrays.asList(
                TableCte.LINHA,
                aluno.getMatColumn(),
                aluno.getNomeColumn());
    }

    private String getQ3Body(List<AlunoAC> q3, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (AlunoAC aluno : q3) {
            response += CLI.getTableString(
                    Arrays.asList(
                            count,
                            aluno.getMat(),
                            aluno.getNome()),
                    maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ3MaxLength(List<AlunoAC> q3, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();

        // header
        maxLength = CLI.getMaxLength(headers, maxLength);

        for (AlunoAC aluno : q3) {
            maxLength = CLI.getMaxLength(
                    Arrays.asList(
                            "",
                            aluno.getMat(),
                            aluno.getNome()),
                    maxLength);
        }
        return maxLength;
    }

    // #endregion Q3
}
