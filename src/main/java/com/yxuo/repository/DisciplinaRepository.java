package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.BaseEntity;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.util.DBConnector;

public class DisciplinaRepository extends BaseRepository {

    public DisciplinaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.disciplina = new DisciplinaAC();
    }

    public DisciplinaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.disciplina = new DisciplinaAC();
    }

    private Connection connection;

    private DisciplinaAC disciplina;

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.disciplina;
        return entity;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private DisciplinaAC construirObjeto(ResultSet resultado) throws SQLException {
        int idDis = resultado.getInt(disciplina.getIdColumn());
        String codDis = resultado.getString(disciplina.getCodDisColumn());
        String nome = resultado.getString(disciplina.getNomeColumn());
        return new DisciplinaAC(idDis, codDis, nome);
    }

    public List<DisciplinaAC> listarTodos() throws SQLException {
        List<DisciplinaAC> disciplinas = new ArrayList<>();
        String query = "SELECT * FROM " + disciplina.getTableName();
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
        String query = "INSERT INTO " + disciplina.getTableName() + " (" + disciplina.getIdColumn() + ", " + disciplina.getCodDisColumn() + ", "
                + disciplina.getNomeColumn() + ") VALUES (?, ?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, disciplina.getId());
            statement.setString(2, disciplina.getCodDis());
            statement.setString(3, disciplina.getNome());
            statement.executeUpdate();
        }
    }

    public void atualizar(DisciplinaAC disciplina) throws SQLException {
        String query = "UPDATE " + disciplina.getTableName() + " SET " + disciplina.getCodDisColumn() + " = ?, " + disciplina.getNomeColumn()
                + " = ? WHERE "
                + disciplina.getIdColumn() + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, disciplina.getCodDis());
            statement.setString(2, disciplina.getNome());
            statement.setInt(3, disciplina.getIdDis());
            statement.executeUpdate();
        }
    }

    public DisciplinaAC buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM " + disciplina.getTableName() + " WHERE " + disciplina.getIdColumn() + " = ?";
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
        String query = "CREATE TABLE IF NOT EXISTS " + disciplina.getTableName() + " (" +
                disciplina.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                disciplina.getCodDisColumn() + " VARCHAR(255), " +
                disciplina.getNomeColumn() + " VARCHAR(255)" +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }
}
