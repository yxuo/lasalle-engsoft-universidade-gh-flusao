package com.yxuo.repository;

import com.yxuo.model.ProfessorAC;
import com.yxuo.util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository extends BaseRepository {
    private Connection connection;
    private final String TABLE_NAME = "Professor";
    private final String COLUMN_ID = "idProf";
    private final String COLUMN_MAT_PROF = "matProf";
    private final String COLUMN_NOME = "nome";

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

    public ProfessorRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
    }

    public ProfessorRepository(Connection con) throws SQLException {
        this.connection = con;
    }

    private ProfessorAC construirObjeto(ResultSet resultado) throws SQLException {
        int idProf = resultado.getInt(COLUMN_ID);
        String matProf = resultado.getString(COLUMN_MAT_PROF);
        String nome = resultado.getString(COLUMN_NOME);
        return new ProfessorAC(idProf, matProf, nome);
    }

    public List<ProfessorAC> listarTodos() throws SQLException {
        List<ProfessorAC> professores = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProfessorAC professor = construirObjeto(resultSet);
                professores.add(professor);
            }
        }
        return professores;
    }

    public void inserir(ProfessorAC professor) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_MAT_PROF + ", " + COLUMN_NOME + ") VALUES (?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, professor.getMatProf());
            statement.setString(2, professor.getNome());
            statement.executeUpdate();
        }
    }

    public void atualizar(ProfessorAC professor) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_MAT_PROF + " = ?, " + COLUMN_NOME + " = ? WHERE "
                + COLUMN_ID + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, professor.getMatProf());
            statement.setString(2, professor.getNome());
            statement.setInt(3, professor.getIdProf());
            statement.executeUpdate();
        }
    }

    public ProfessorAC buscarPorId(int id) throws SQLException {
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
                COLUMN_MAT_PROF + " VARCHAR(255), " +
                COLUMN_NOME + " VARCHAR(255) " +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
