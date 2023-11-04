package com.yxuo.repository;

import com.yxuo.model.AlunoAC;
import com.yxuo.util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository extends BaseRepository {
    private Connection connection;
    private final String TABLE_NAME = "Aluno";
    private final String COLUMN_ID = "idAluno";
    private final String COLUMN_MAT = "mat";
    private final String COLUMN_NOME = "nome";

    public AlunoRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
    }

    public AlunoRepository(Connection con) throws SQLException {
        this.connection = con;
    }

    @Override
    public String getCOLUMN_ID() {
        return COLUMN_ID;
    }

    @Override
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private AlunoAC construirObjeto(ResultSet resultado) throws SQLException {
        int idAluno = resultado.getInt(COLUMN_ID);
        String mat = resultado.getString(COLUMN_MAT);
        String nome = resultado.getString(COLUMN_NOME);
        return new AlunoAC(idAluno, mat, nome);
    }

    public List<AlunoAC> listarTodos() throws SQLException {
        List<AlunoAC> alunos = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                AlunoAC aluno = construirObjeto(resultSet);
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    public void inserir(AlunoAC aluno) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_MAT + ", " + COLUMN_NOME + ") VALUES (?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, aluno.getMat());
            statement.setString(2, aluno.getNome());
            statement.executeUpdate();
        }
    }

    public void atualizar(AlunoAC aluno) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_MAT + " = ?, " + COLUMN_NOME + " = ? WHERE "
                + COLUMN_ID + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, aluno.getMat());
            statement.setString(2, aluno.getNome());
            statement.setInt(3, aluno.getIdAluno());
            statement.executeUpdate();
        }
    }

    public AlunoAC buscarPorId(int id) throws SQLException {
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
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INT AUTO_INCREMENT PRIMARY KEY, " +
                COLUMN_NOME + " VARCHAR(255), " +
                COLUMN_MAT + " VARCHAR(255) " +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }
}
