package com.yxuo.repository.TDE6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.CursaAC;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.RealizaProvaAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.repository.BaseRepository;
import com.yxuo.repository.TDE6.structs.Tde6Q5Obj;
import com.yxuo.repository.TDE6.structs.Tde6Q6Obj;
import com.yxuo.util.DBConnector;
import com.yxuo.util.Query;

public class TDE6Repository extends BaseRepository {

    private final Connection connection;
    private final CursaAC matriculado = new CursaAC();
    private final AlunoAC aluno = new AlunoAC();
    private final TurmaAC turma = new TurmaAC();
    private final DisciplinaAC disciplina = new DisciplinaAC();
    private final ProfessorAC professor = new ProfessorAC();
    private final ProvaAC prova = new ProvaAC();
    private final RealizaProvaAC realiza = new RealizaProvaAC();

    public TDE6Repository() throws SQLException {
        this.connection = DBConnector.getConnection();
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    // #region Q1

    public List<CursaAC> getQ1() throws SQLException {
        List<CursaAC> matriculados = getQ1Matriculados();
        return matriculados;
    }

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
    private List<CursaAC> getQ1Matriculados() throws SQLException {
        List<CursaAC> matirculados = new ArrayList<>();
        String query = "SELECT "
                // aluno
                + aluno.getNomeColumn() + ", " + aluno.getMatColumn() + ", "
                // turma
                + turma.getIdColumn() + ", " + turma.getTurnoColumn() + ", " + turma.getDiaColumn() + ", "
                + turma.getHoraInicioColumn() + ", " + turma.getHoraFimColumn() + "\n"
                + "FROM " + aluno.getTableName() + " a \n"
                + "LEFT JOIN " + matriculado.getTableName() + " m \n"
                + "ON m." + matriculado.getAlunoColumn() + " = a." + aluno.getIdColumn() + "\n"
                + "LEFT JOIN " + turma.getTableName() + " t \n"
                + "ON m." + matriculado.getTurmaColumn() + " = t." + turma.getIdColumn() + "\n"
                + "WHERE " + turma.getIdColumn() + " IS NOT NULL";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    matirculados.add(construirQ1Matriculados(resultSet));
                }
            }
        }
        return matirculados;
    }

    private CursaAC construirQ1Matriculados(ResultSet resultado) throws SQLException {
        AlunoAC aluno = new AlunoAC(
                -1,
                resultado.getString(this.aluno.getMatColumn()),
                resultado.getString(this.aluno.getNomeColumn()));
        TurmaAC turma = new TurmaAC(
                resultado.getInt(this.turma.getIdColumn()),
                resultado.getString(this.turma.getTurnoColumn()),
                resultado.getString(this.turma.getDiaColumn()),
                resultado.getString(this.turma.getHoraInicioColumn()),
                resultado.getString(this.turma.getHoraFimColumn()));
        CursaAC matirculado = new CursaAC(-1, aluno, turma);
        return matirculado;
    }

    // #endregion Q1

    // #region Q2

    /**
     * 2) Selecione a matrícula e nome do professor, bem como, o código e
     * nome da disciplina
     * 
     * <ul>
     * <li>
     * <b>Query:</b>
     * <dl>
     * <li>
     * <ul>
     * 
     * <pre>{@code Disciplina -- TURMA -- Professor }</pre>
     * </ul>
     * </li>
     * </dl>
     * </li>
     * </ul>
     * 
     * @return
     * 
     *         <pre>
     *         {@code
     * List<Turma>{
     *     Professor{ matricula, nome }
     *     Disciplina{ codigo, nome }
     * }
     *         </pre>
     * 
     *         }
     * 
     * @throws SQLException
     */
    public List<TurmaAC> getQ2() throws SQLException {
        List<TurmaAC> turmas = new ArrayList<>();
        String query = "SELECT "
                + professor.getMatProfColumn() + ", p." + professor.getNomeColumn() + " AS P_NOME, "
                + disciplina.getIdColumn() + ", d." + disciplina.getNomeColumn() + " AS D_NOME "
                + "FROM " + turma.getTableName() + " t \n"
                + "LEFT JOIN " + professor.getTableName() + " p \n"
                + "ON t." + turma.getProfessorColumn() + " = p." + professor.getIdColumn() + "\n"
                + "LEFT JOIN " + disciplina.getTableName() + " d \n"
                + "ON t." + turma.getDisciplinaColumn() + " = d." + disciplina.getIdColumn();
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    turmas.add(construirQ2(resultSet));
                }
            }
        }
        return turmas;
    }

    private TurmaAC construirQ2(ResultSet resultado) throws SQLException {
        ProfessorAC professor = new ProfessorAC(
                -1,
                resultado.getString(this.professor.getMatProfColumn()),
                resultado.getString("P_" + this.professor.getNomeColumn()));
        DisciplinaAC disciplina = new DisciplinaAC(
                -1,
                resultado.getString(this.disciplina.getIdColumn()),
                resultado.getString("D_" + this.disciplina.getNomeColumn()));
        TurmaAC turma = new TurmaAC(-1, disciplina, professor);
        return turma;
    }

    // endregion Q2

    // #region Q3

    /**
     * 3) Selecione a matrícula e nome dos alunos que não estão matriculados em
     * turmas.
     * </br>
     * </br>
     * 
     * Query: {@code ALUNO --- matriculado(cursa) --- turma}
     * 
     * @return
     * 
     *         <pre>
     * List<Aluno>{ matricula, nome }
     *         </pre>
     * 
     * 
     * @throws SQLException
     */
    public List<AlunoAC> getQ3() throws SQLException {
        List<AlunoAC> alunos = new ArrayList<>();
        String query = "SELECT "
                + "a." + aluno.getMatColumn() + ", a." + aluno.getNomeColumn() + " "
                + "FROM " + aluno.getTableName() + " a \n"
                + "LEFT JOIN " + matriculado.getTableName() + " m \n"
                + "ON a." + aluno.getIdColumn() + " = m." + matriculado.getAlunoColumn() + "\n"
                + "WHERE m." + matriculado.getAlunoColumn() + " IS NULL";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    alunos.add(construirQ3(resultSet));
                }
            }
        }
        return alunos;
    }

    private AlunoAC construirQ3(ResultSet resultado) throws SQLException {
        AlunoAC aluno = new AlunoAC(
                -1,
                resultado.getString(this.aluno.getMatColumn()),
                resultado.getString(this.aluno.getNomeColumn()));
        return aluno;
    }

    // endregion Q3

    // #region Q4

    /**
     * 4) Selecione id da turma, nome da disciplina, código da prova,
     * situação da prova e média das notas.
     * </br>
     * </br>
     * 
     * Query: {@code Disciplina <--- Turma <--- Prova <--- REALIZA}
     * 
     * @return
     * 
     *         <pre>
     * {@code
     * List<Realiza>{
     *     nota, // (média)
     *     Prova {
     *     codigo,
     *         Turma {
     *             id,
     *             Disciplina { nome }
     *         }
     * }
     *         </pre>
     * 
     * 
     * @throws SQLException
     */
    public List<RealizaProvaAC> getQ4() throws SQLException {
        List<RealizaProvaAC> realizas = new ArrayList<>();
        String query =
            Query.select(
                turma.getIdColumn(), "d." + disciplina.getNomeColumn(),
                prova.getCodProvaColumn(), prova.getSituacaoColumn(),
                Query.avg(realiza.getNotaColumn(), "r_AVG_")
                )
            + "FROM " + turma.getTableName() + " t" + "\n"
            + "INNER JOIN " + disciplina.getTableName() + " d ON " + turma.getDisciplinaColumn() + " = " + disciplina.getIdColumn() + "\n"
            + "INNER JOIN " + prova.getTableName() + " p ON " + turma.getIdColumn() + " = " + prova.getTurmaColumn() + "\n"
            + "LEFT JOIN " + realiza.getTableName() + " r ON " + realiza.getProvaColumn() + " = " + prova.getIdColumn() + "\n"
            + Query.groupBy(turma.getIdColumn(), disciplina.getNomeColumn(), prova.getCodProvaColumn(), prova.getSituacaoColumn())
            ;
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    realizas.add(construirQ4(resultSet));
                }
            }
        }
        return realizas;
    }

    private RealizaProvaAC construirQ4(ResultSet resultado) throws SQLException {
        DisciplinaAC disciplina = new DisciplinaAC();
        disciplina.setNome(resultado.getString(disciplina.getNomeColumn()));
        TurmaAC turma = new TurmaAC();
        turma.setIdTurma(resultado.getInt(turma.getIdColumn()));
        turma.setDisciplina(disciplina);
        ProvaAC prova = new ProvaAC();
        prova.setCodProva(resultado.getString(prova.getCodProvaColumn()));
        prova.setSituacao(resultado.getString(prova.getSituacaoColumn()));
        prova.setTurma(turma);
        RealizaProvaAC realiza = new RealizaProvaAC();
        realiza.setNota(resultado.getInt("r_AVG_" + realiza.getNotaColumn()));
        realiza.setProva(prova);
        return realiza;
    }

    // endregion Q4

    // #region Q5

    /**
     * 5) Selecione matrícula e nome do professor,id da turma, nome da disciplina,
     * código da prova, situação da prova, média das notas, maior nota e menor nota.
     * </br>
     * </br>
     * 
     * Query: {@code
     * Disciplina <--- Turma <--- Prova <--- REALIZA
     * Professor  <---/
     * }
     * 
     * @throws SQLException
     */
    public List<Tde6Q5Obj> getQ5() throws SQLException {
        List<Tde6Q5Obj> objects = new ArrayList<>();
        String query = Query.select("\n" +
                professor.getMatProfColumn(),
                "pr." + Query.as(professor.getNomeColumn(), "pr_"), "\n" + 
                turma.getIdColumn(),
                "d." + Query.as(disciplina.getNomeColumn(), "d_"), "\n" + 
                prova.getCodProvaColumn(),
                prova.getSituacaoColumn(), "\n" + 
                Query.fn2("AVG", realiza.getNotaColumn(), "r_"),
                Query.fn2("MAX", realiza.getNotaColumn(), "r_"),
                Query.fn2("MIN", realiza.getNotaColumn(), "r_")
            )
            + "FROM " + turma.getTableName() + " t" + "\n"
            + "LEFT JOIN " + disciplina.getTableName() + " d ON " + turma.getDisciplinaColumn() + " = " + disciplina.getIdColumn() + "\n"
            + "LEFT JOIN " + prova.getTableName() + " p ON " + turma.getIdColumn() + " = " + prova.getTurmaColumn() + "\n"
            + "LEFT JOIN " + realiza.getTableName() + " r ON " + realiza.getProvaColumn() + " = " + prova.getIdColumn() + "\n"
            + "LEFT JOIN " + professor.getTableName() + " pr ON " + professor.getIdColumn() + " = " + turma.getProfessorColumn() + "\n"
            + Query.groupBy(turma.getIdColumn(), "d_" + disciplina.getNomeColumn(), prova.getCodProvaColumn(), prova.getSituacaoColumn())
            ;
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    objects.add(construirQ5(resultSet));
                }
            }
        }
        return objects;
    }

    private Tde6Q5Obj construirQ5(ResultSet result) throws SQLException {
        ProfessorAC professor = new ProfessorAC();
        professor.setMatProf(result.getString(professor.getMatProfColumn()));
        professor.setNome(result.getString("pr_" + professor.getNomeColumn()));
        DisciplinaAC disciplina = new DisciplinaAC();
        disciplina.setNome(result.getString("d_" + disciplina.getNomeColumn()));
        TurmaAC turma = new TurmaAC();
        turma.setIdTurma(result.getInt(turma.getIdColumn()));
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
        ProvaAC prova = new ProvaAC();
        prova.setCodProva(result.getString(prova.getCodProvaColumn()));
        prova.setSituacao(result.getString(prova.getSituacaoColumn()));
        prova.setTurma(turma);
        RealizaProvaAC realiza = new RealizaProvaAC();
        realiza.setProva(prova);
        Tde6Q5Obj obj = new Tde6Q5Obj();
        obj.setRealiza(realiza);
        obj.setRealizaNotaAvg(result.getInt("r_AVG_" + realiza.getNotaColumn()));
        obj.setRealizaNotaMax(result.getInt("r_MAX_" + realiza.getNotaColumn()));
        obj.setRealizaNotaMin(result.getInt("r_MIN_" + realiza.getNotaColumn()));
        return obj;
    }

    // endregion Q5

    // #region Q6

    /**
     * 6) Selecionar os nome dos professores e a quantidade de turmas que ministram.
     * </br>
     * </br>
     * 
     * Query: {@code
     * Professor <--- TURMA
     * }
     * 
     * @throws SQLException
     */
    public List<Tde6Q6Obj> getQ6() throws SQLException {
        List<Tde6Q6Obj> objects = new ArrayList<>();
        String query = Query.select(
                professor.getNomeColumn(),
                Query.fn("COUNT", professor.getIdColumn())
            )
            + "FROM " + professor.getTableName() + " p" + "\n"
            + "LEFT JOIN " + turma.getTableName() + " t ON " + professor.getIdColumn() + " = " + turma.getProfessorColumn() + "\n"
            + Query.groupBy(professor.getIdColumn())
            ;
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    objects.add(construirQ6(resultSet));
                }
            }
        }
        return objects;
    }

    private Tde6Q6Obj construirQ6(ResultSet result) throws SQLException {
        ProfessorAC professor = new ProfessorAC();
        professor.setNome(result.getString(professor.getNomeColumn()));
        Tde6Q6Obj obj = new Tde6Q6Obj();
        obj.setProfessor(professor);
        obj.setCountProfessor(result.getInt("COUNT_" + professor.getIdColumn()));
        return obj;
    }

    // endregion Q6
  
    // #region Q7

    /**
     * Q) Selecionar o nome do professor e o nome da disciplina ministrada
     * </br>
     * </br>
     * 
     * Query: {@code
     * Professor <--- TURMA ---> Disciplina
     * }
     * 
     * @throws SQLException
     */
    public List<TurmaAC> getQ7() throws SQLException {
        List<TurmaAC> objects = new ArrayList<>();
        String query = Query.select(
                "p." + Query.as(professor.getNomeColumn(), "p_"),
                "d." + Query.as(disciplina.getNomeColumn(), "d_")
            )
            + "FROM " + professor.getTableName() + " p" + "\n"
            + "LEFT JOIN " + turma.getTableName() + " t ON p." + professor.getIdColumn() + " = " + turma.getProfessorColumn() + "\n"
            + "LEFT JOIN " + disciplina.getTableName() + " d ON d." + disciplina.getIdColumn() + " = " + turma.getDisciplinaColumn() + "\n"
            + "WHERE " + disciplina.getIdColumn() + " IS NOT NULL"
            ;
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    objects.add(construirQ7(resultSet));
                }
            }
        }
        return objects;
    }

    private TurmaAC construirQ7(ResultSet result) throws SQLException {
        ProfessorAC professor = new ProfessorAC();
        professor.setNome(result.getString("p_" + professor.getNomeColumn()));
        DisciplinaAC disciplina = new DisciplinaAC();
        disciplina.setNome(result.getString("d_" + disciplina.getNomeColumn()));
        TurmaAC turma = new TurmaAC();
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
        return turma;
    }

    // endregion Q7
  
    // #region Q8

    /**
     * Q) Selecionar os nomes das disciplinas sem turmas
     * </br>
     * </br>
     * 
     * Query: {@code
     * TURMA ---> Disciplina
     * }
     * 
     * @throws SQLException
     */
    public List<DisciplinaAC> getQ8() throws SQLException {
        List<DisciplinaAC> objects = new ArrayList<>();
        String query = Query.select(
               disciplina.getNomeColumn()
            )
            + "FROM " + disciplina.getTableName() + "\n"
            + "LEFT JOIN " + turma.getTableName() + " t ON " + turma.getDisciplinaColumn() + " = " + disciplina.getIdColumn() + "\n"
            + "WHERE " + turma.getIdColumn() + " IS NULL"
            ;
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    objects.add(construirQ8(resultSet));
                }
            }
        }
        return objects;
    }

    private DisciplinaAC construirQ8(ResultSet result) throws SQLException {
        DisciplinaAC disciplina = new DisciplinaAC();
        disciplina.setNome(result.getString(disciplina.getNomeColumn()));
        return disciplina;
    }

    // endregion Q8
  
    // #region Q9

    /**
     * Q) Selecionar as provas e suas disciplinas que não foram aplicadas
     * </br>
     * </br>
     * 
     * Query: {@code Disciplina <--- Turma <--- PROVA }
     * 
     * @throws SQLException
     */
    public List<ProvaAC> getQ9() throws SQLException {
        List<ProvaAC> objects = new ArrayList<>();
        String query = Query.select(
               prova.getCodProvaColumn(),
               "d." + disciplina.getNomeColumn()
            )
            + "FROM " + prova.getTableName() + " p \n"
            + "LEFT JOIN " + turma.getTableName() + " t ON p." + prova.getTurmaColumn() + " = t." + turma.getIdColumn() + "\n"
            + "RIGHT JOIN " + disciplina.getTableName() + " d ON d." + disciplina.getIdColumn() + " = t." + turma.getDisciplinaColumn() + "\n"
            + "WHERE " + prova.getIdColumn() + " IS NULL"
            ;
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    objects.add(construirQ9(resultSet));
                }
            }
        }
        return objects;
    }

    private ProvaAC construirQ9(ResultSet result) throws SQLException {
        DisciplinaAC disciplina = new DisciplinaAC();
        disciplina.setNome(result.getString(disciplina.getNomeColumn()));
        TurmaAC turma = new TurmaAC();
        turma.setDisciplina(disciplina);
        ProvaAC prova = new ProvaAC();
        prova.setCodProva(result.getString(prova.getCodProvaColumn()));
        prova.setTurma(turma);
        return prova;
    }

    // endregion Q9
}
