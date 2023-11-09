package com.yxuo.controller.cli.TDE6;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yxuo.constant.TableCte;
import com.yxuo.model.AlunoAC;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.RealizaAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.repository.TDE6.TDE6Repository;
import com.yxuo.repository.TDE6.transfer.Tde6Q5Obj;
import com.yxuo.repository.TDE6.transfer.Tde6Q6Obj;
import com.yxuo.util.CLI;

public class TDE6CLIController {

    private final TDE6Repository tde6Repository;
    private final TurmaAC turma = new TurmaAC();
    private final DisciplinaAC disciplina = turma.getDisciplina();
    private final ProfessorAC professor = turma.getProfessor();
    private final AlunoAC aluno = new AlunoAC();
    private final RealizaAC realiza = new RealizaAC();
    private final ProvaAC prova = new ProvaAC();

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

    // #region Q4

    /**
     * Query: {@code Disciplina <--- Turma <--- Prova <--- REALIZA}
     * 
     * @return {turma.id, disc.nome, prova{codigo, situacao}, notas }
     * 
     * @throws SQLException
     */
    public String getQ4String() throws SQLException {
        String response = "";
        List<RealizaAC> q4 = tde6Repository.getQ4();
        List<String> headers = getQ4Header(q4);
        List<Integer> maxLength = getQ4MaxLength(q4, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ4Body(q4, maxLength);
        return response;
    }

    private List<String> getQ4Header(List<RealizaAC> q3) {
        return Arrays.asList(
                TableCte.LINHA,
                turma.getIdColumn(),
                disciplina.getNomeColumn(),
                prova.getCodProvaColumn(),
                prova.getSituacaoColumn(),
                realiza.getNotaColumn());
    }

    private <T> List<Serializable> getQ4BodyItem(T firstItem, RealizaAC realiza) {
        return Arrays.asList(
                firstItem.toString(),
                realiza.getProva().getTurma().getId(),
                realiza.getProva().getTurma().getDisciplina().getNome(),
                realiza.getProva().getCodProva(),
                realiza.getProva().getSituacao(),
                realiza.getNota());
    }

    private String getQ4Body(List<RealizaAC> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (RealizaAC realiza : items) {
            response += CLI.getTableString(getQ4BodyItem(count, realiza), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ4MaxLength(List<RealizaAC> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (RealizaAC realiza : items) {
            maxLength = CLI.getMaxLength(getQ4BodyItem("", realiza), maxLength);
        }
        return maxLength;
    }

    // #endregion Q4

    // #region Q5

    /**
     * Query: {@code Disciplina <--- Turma <--- Prova <--- REALIZA}
     * 
     * @return {turma.id, disc.nome, prova{codigo, situacao}, notas }
     * 
     * @throws SQLException
     */
    public String getQ5String() throws SQLException {
        String response = "";
        List<Tde6Q5Obj> items = tde6Repository.getQ5();
        List<String> headers = getQ5Header();
        List<Integer> maxLength = getQ5MaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ5Body(items, maxLength);
        return response;
    }

    private List<String> getQ5Header() {
        return Arrays.asList(
                TableCte.LINHA,
                professor.getMatProfColumn(),
                professor.getNomeColumn() + " prof.",
                turma.getIdColumn(),
                disciplina.getNomeColumn() + " disc.",
                prova.getCodProvaColumn(),
                prova.getSituacaoColumn(),
                "MEDIA " + realiza.getNotaColumn(),
                "MAX " + realiza.getNotaColumn(),
                "MIN " + realiza.getNotaColumn());
    }

    private <T> List<Serializable> getQ5BodyItem(T firstItem, Tde6Q5Obj obj) {
        ProvaAC prova = obj.getRealiza().getProva();
        TurmaAC turma = prova.getTurma();
        ProfessorAC professor = turma.getProfessor();
        DisciplinaAC disciplina = turma.getDisciplina();
        return Arrays.asList(
                firstItem.toString(),
                professor.getMatProf(),
                professor.getNome(),
                turma.getId(),
                disciplina.getNome(),
                prova.getCodProva(),
                prova.getSituacao(),
                obj.getRealizaNotaAvg(),
                obj.getRealizaNotaMax(),
                obj.getRealizaNotaMin());
    }

    private String getQ5Body(List<Tde6Q5Obj> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (Tde6Q5Obj obj : items) {
            response += CLI.getTableString(getQ5BodyItem(count, obj), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ5MaxLength(List<Tde6Q5Obj> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (Tde6Q5Obj obj : items) {
            maxLength = CLI.getMaxLength(getQ5BodyItem("", obj), maxLength);
        }
        return maxLength;
    }

    // #endregion Q5

    // #region Q6

    /**
     * Query: {@code Professor <--- TURMA }
     * 
     * @return { prof.nome, COUNT(prof.id) }
     * 
     * @throws SQLException
     */
    public String getQ6String() throws SQLException {
        String response = "";
        List<Tde6Q6Obj> items = tde6Repository.getQ6();
        List<String> headers = getQ6Header();
        List<Integer> maxLength = getQ6MaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ6Body(items, maxLength);
        return response;
    }

    private List<String> getQ6Header() {
        return Arrays.asList(
                TableCte.LINHA,
                professor.getNomeColumn(),
                "CONTAGEM " + professor.getIdColumn());
    }

    private <T> List<Serializable> getQ6BodyItem(T firstItem, Tde6Q6Obj obj) {
        return Arrays.asList(
                firstItem.toString(),
                obj.getProfessor().getNome(),
                obj.getProfessorCount());
    }

    private String getQ6Body(List<Tde6Q6Obj> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (Tde6Q6Obj obj : items) {
            response += CLI.getTableString(getQ6BodyItem(count, obj), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ6MaxLength(List<Tde6Q6Obj> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (Tde6Q6Obj obj : items) {
            maxLength = CLI.getMaxLength(getQ6BodyItem("", obj), maxLength);
        }
        return maxLength;
    }

    // #endregion Q6

    // #region Q7

    /**
     * Selecionar o nome do professor e o nome da disciplina ministrada
     * 
     * Query: {@code Professor <--- TURMA ---> Disciplina }
     * 
     * @return { prof.nome, COUNT(prof.id) }
     * 
     * @throws SQLException
     */
    public String getQ7String() throws SQLException {
        String response = "";
        List<TurmaAC> items = tde6Repository.getQ7();
        List<String> headers = getQ7Header();
        List<Integer> maxLength = getQ7MaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ7Body(items, maxLength);
        return response;
    }

    private List<String> getQ7Header() {
        return Arrays.asList(
                TableCte.LINHA,
                professor.getNomeColumn() + " PROF.",
                disciplina.getNomeColumn() + " DISC."
                );
    }

    private <T> List<Serializable> getQ7BodyItem(T firstItem, TurmaAC obj) {
        return Arrays.asList(
                firstItem.toString(),
                obj.getProfessor().getNome(),
                obj.getDisciplina().getNome());
    }

    private String getQ7Body(List<TurmaAC> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (TurmaAC obj : items) {
            response += CLI.getTableString(getQ7BodyItem(count, obj), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ7MaxLength(List<TurmaAC> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (TurmaAC obj : items) {
            maxLength = CLI.getMaxLength(getQ7BodyItem("", obj), maxLength);
        }
        return maxLength;
    }

    // #endregion Q7

    // #region Q8

    /**
     * Selecionar o nome do professor e o nome da disciplina ministrada
     * 
     * Query: {@code Professor <--- TURMA ---> Disciplina }
     * 
     * @return { prof.nome, COUNT(prof.id) }
     * 
     * @throws SQLException
     */
    public String getQ8String() throws SQLException {
        String response = "";
        List<DisciplinaAC> items = tde6Repository.getQ8();
        List<String> headers = getQ8Header();
        List<Integer> maxLength = getQ8MaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ8Body(items, maxLength);
        return response;
    }

    private List<String> getQ8Header() {
        return Arrays.asList(
                TableCte.LINHA,
                disciplina.getNomeColumn() + " DISC."
                );
    }

    private <T> List<Serializable> getQ8BodyItem(T firstItem, DisciplinaAC obj) {
        return Arrays.asList(
                firstItem.toString(),
                obj.getNome());
    }

    private String getQ8Body(List<DisciplinaAC> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (DisciplinaAC obj : items) {
            response += CLI.getTableString(getQ8BodyItem(count, obj), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ8MaxLength(List<DisciplinaAC> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (DisciplinaAC obj : items) {
            maxLength = CLI.getMaxLength(getQ8BodyItem("", obj), maxLength);
        }
        return maxLength;
    }

    // #endregion Q8

    // #region Q9

    /**
     * Q) Selecionar as provas e suas disciplinas que não foram aplicadas
     * 
     * Query: {@code Disciplina <--- Turma <--- PROVA }
     * 
     * @throws SQLException
     */
    public String getQ9String() throws SQLException {
        String response = "";
        List<ProvaAC> items = tde6Repository.getQ9();
        List<String> headers = getQ9Header();
        List<Integer> maxLength = getQ9MaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getQ9Body(items, maxLength);
        return response;
    }

    private List<String> getQ9Header() {
        return Arrays.asList(
                TableCte.LINHA,
                prova.getCodProvaColumn(),
                disciplina.getNomeColumn() + " DISC."
                );
    }

    private <T> List<Serializable> getQ9BodyItem(T firstItem, ProvaAC obj) {
        return Arrays.asList(
                firstItem.toString(),
                obj.getCodProva(),
                obj.getTurma().getDisciplina().getNome()
                );
    }

    private String getQ9Body(List<ProvaAC> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (ProvaAC obj : items) {
            response += CLI.getTableString(getQ9BodyItem(count, obj), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getQ9MaxLength(List<ProvaAC> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (ProvaAC obj : items) {
            maxLength = CLI.getMaxLength(getQ9BodyItem("", obj), maxLength);
        }
        return maxLength;
    }

    // #endregion Q9
}
