package com.yxuo.repository.TDE6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.repository.BaseRepository;
import com.yxuo.util.DBConnector;

public class TDE6Repository extends BaseRepository {

    private Connection connection;
    private MatriculadoAC matriculado = new MatriculadoAC();
    private AlunoAC aluno = new AlunoAC();
    private TurmaAC turma = new TurmaAC();
    private DisciplinaAC disciplina = new DisciplinaAC();
    private ProfessorAC professor = new ProfessorAC();

    public TDE6Repository() throws SQLException {
        this.connection = DBConnector.getConnection();
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    // #region Q1

    public List<MatriculadoAC> getQ1() throws SQLException {
        List<MatriculadoAC> matriculados = getQ1Matriculados();
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
    private List<MatriculadoAC> getQ1Matriculados() throws SQLException {
        List<MatriculadoAC> matirculados = new ArrayList<>();
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
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    matirculados.add(construirQ1Matriculados(resultSet));
                }
            }
        }
        return matirculados;
    }

    private MatriculadoAC construirQ1Matriculados(ResultSet resultado) throws SQLException {
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
        MatriculadoAC matirculado = new MatriculadoAC(-1, aluno, turma);
        return matirculado;
    }

    // #endregion Q1

    // #region Q2

    /**
     * 2) Selecione a matriícula e nome do professor, bem como, o código e
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
        DBConnector.parseQuery(query);
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
        DBConnector.parseQuery(query);
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
}
