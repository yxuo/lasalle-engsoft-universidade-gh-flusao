package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.util.DBConnector;

public class MatriculadoRepository extends BaseRepository {
    private Connection connection;
    private final String TABLE_NAME = "Matriculado";
    private final String COLUMN_ID = "idMatriculado";
    private final String COLUMN_ALUNO_ID = "aluno";
    private final String COLUMN_TURMA_ID = "turma";
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    @Override
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    @Override
    public String getCOLUMN_ID() {
        return COLUMN_ID;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    public MatriculadoRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.alunoRepository = new AlunoRepository();
        this.turmaRepository = new TurmaRepository();
    }

    public MatriculadoRepository(Connection con) throws SQLException {
        this.connection = con;
        this.alunoRepository = new AlunoRepository(con);
        this.turmaRepository = new TurmaRepository(con);
    }

    private MatriculadoAC construirObjeto(ResultSet resultado) throws SQLException {
        int idMatriculado = resultado.getInt(COLUMN_ID);
        AlunoAC aluno = alunoRepository.buscarPorId(resultado.getInt(COLUMN_ALUNO_ID));
        TurmaAC turma = turmaRepository.buscarPorId(resultado.getInt(COLUMN_TURMA_ID));
        return new MatriculadoAC(idMatriculado, aluno, turma);
    }

    public List<MatriculadoAC> listarTodos() throws SQLException {
        List<MatriculadoAC> matriculados = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
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
        String query = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_ALUNO_ID + ", " + COLUMN_TURMA_ID
                + ") VALUES (?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matriculado.getAluno().getIdAluno());
            statement.setInt(2, matriculado.getTurma().getIdTurma());
            statement.executeUpdate();
        }
    }

    public void atualizar(MatriculadoAC matriculado) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_ALUNO_ID + " = ?, " + COLUMN_TURMA_ID + " = ? WHERE "
                + COLUMN_ID + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, matriculado.getAluno().getIdAluno());
            statement.setInt(2, matriculado.getTurma().getIdTurma());
            statement.setInt(3, matriculado.getIdMatriculado());
            statement.executeUpdate();
        }
    }

    public MatriculadoAC buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
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
        String alunoTable = alunoRepository.getTABLE_NAME();
        String alunoId = alunoRepository.getCOLUMN_ID();
        String turmaTable = turmaRepository.getTABLE_NAME();
        String turmaId = turmaRepository.getCOLUMN_ID();

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INT AUTO_INCREMENT PRIMARY KEY, " +
                COLUMN_ALUNO_ID + " INT, " +
                COLUMN_TURMA_ID + " INT, " +
                "FOREIGN KEY (" + COLUMN_ALUNO_ID + ") REFERENCES " + alunoTable + "(" + alunoId
                + ") ON DELETE CASCADE, " +
                "FOREIGN KEY (" + COLUMN_TURMA_ID + ") REFERENCES " + turmaTable + "(" + turmaId + ") ON DELETE CASCADE"
                +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }
}
