package com.yxuo.repository;

import com.yxuo.model.DisciplinaAC;
import com.yxuo.util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaRepository extends BaseRepository {
    private Connection connection;
    private final String TABLE_NAME = "Disciplina";
    private final String COLUMN_ID = "idDis";
    private final String COLUMN_COD_DIS = "codDis";
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

    public DisciplinaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
    }

    public DisciplinaRepository(Connection con) throws SQLException {
        this.connection = con;
    }

    private DisciplinaAC construirObjeto(ResultSet resultado) throws SQLException {
        int idDis = resultado.getInt(COLUMN_ID);
        String codDis = resultado.getString(COLUMN_COD_DIS);
        String nome = resultado.getString(COLUMN_NOME);
        return new DisciplinaAC(idDis, codDis, nome);
    }

    public List<DisciplinaAC> listarTodos() throws SQLException {
        List<DisciplinaAC> disciplinas = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DisciplinaAC disciplina = construirObjeto(resultSet);
                disciplinas.add(disciplina);
            }
        }
        return disciplinas;
    }

    public void inserir(DisciplinaAC disciplina) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_COD_DIS + ", " + COLUMN_NOME + ") VALUES (?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, disciplina.getCodDis());
            statement.setString(2, disciplina.getNome());
            statement.executeUpdate();
        }
    }

    public void atualizar(DisciplinaAC disciplina) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_COD_DIS + " = ?, " + COLUMN_NOME + " = ? WHERE "
                + COLUMN_ID + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, disciplina.getCodDis());
            statement.setString(2, disciplina.getNome());
            statement.setInt(3, disciplina.getIdDis());
            statement.executeUpdate();
        }
    }

    public DisciplinaAC buscarPorId(int id) throws SQLException {
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
                COLUMN_COD_DIS + " VARCHAR(255), " +
                COLUMN_NOME + " VARCHAR(255)" +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }
}
