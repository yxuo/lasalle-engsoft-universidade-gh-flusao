package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.BaseEntity;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.util.DBConnector;

public class MatriculadoRepository extends BaseRepository {

    public MatriculadoRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.alunoRepository = new AlunoRepository();
        this.turmaRepository = new TurmaRepository();
        matricula = new MatriculadoAC();
    }

    public MatriculadoRepository(Connection con) throws SQLException {
        this.connection = con;
        this.alunoRepository = new AlunoRepository(con);
        this.turmaRepository = new TurmaRepository(con);
        matricula = new MatriculadoAC();
    }

    private Connection connection;
    private final MatriculadoAC matricula;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.matricula;
        return entity;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private MatriculadoAC construirObjeto(ResultSet resultado) throws SQLException {
        int idMatriculado = resultado.getInt(matricula.getIdColumn());
        AlunoAC aluno = alunoRepository.buscarPorId(resultado.getInt(matricula.getAlunoColumn()));
        TurmaAC turma = turmaRepository.buscarPorId(resultado.getInt(matricula.getTurmaColumn()));
        return new MatriculadoAC(idMatriculado, aluno, turma);
    }

    public List<MatriculadoAC> listarTodos() throws SQLException {
        List<MatriculadoAC> matriculados = new ArrayList<>();
        String query = "SELECT * FROM " + matricula.getTableName();
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                MatriculadoAC matriculado = construirObjeto(resultSet);
                matriculados.add(matriculado);
            }
        }
        return matriculados;
    }

    public void inserir(MatriculadoAC matriculado) throws SQLException {
        String query = "INSERT INTO " + matricula.getTableName() + " (" + matricula.getIdColumn() + ", " + matricula.getAlunoColumn() + ", "
                + matricula.getTurmaColumn()
                + ") VALUES (?, ?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matriculado.getId());
            statement.setInt(2, matriculado.getAluno().getIdAluno());
            statement.setInt(3, matriculado.getTurma().getIdTurma());
            statement.executeUpdate();
        }
    }

    public void atualizar(MatriculadoAC matriculado) throws SQLException {
        String query = "UPDATE " + matricula.getTableName() + " SET " + matricula.getAlunoColumn() + " = ?, " + matricula.getTurmaColumn()
                + " = ? WHERE "
                + matricula.getIdColumn() + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matriculado.getAluno().getIdAluno());
            statement.setInt(2, matriculado.getTurma().getIdTurma());
            statement.setInt(3, matriculado.getIdMatriculado());
            statement.executeUpdate();
        }
    }

    public MatriculadoAC buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM " + matricula.getTableName() + " WHERE " + matricula.getIdColumn() + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return construirObjeto(resultSet);
                }
            }
        }
        return null;
    }

    public void criarTabela() throws SQLException {
        AlunoAC aluno = matricula.getAluno();
        TurmaAC turma = matricula.getTurma();

        String query = "CREATE TABLE IF NOT EXISTS " + matricula.getTableName() + " (" +
                matricula.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                matricula.getAlunoColumn() + " INT, " +
                matricula.getTurmaColumn() + " INT, " +
                "FOREIGN KEY (" + matricula.getAlunoColumn() + ") REFERENCES " + aluno.getTableName() + "(" + aluno.getIdColumn()
                + ") ON DELETE CASCADE, " +
                "FOREIGN KEY (" + matricula.getTurmaColumn() + ") REFERENCES " + turma.getTableName() + "(" + turma.getIdColumn()
                + ") ON DELETE CASCADE"
                +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }
}
