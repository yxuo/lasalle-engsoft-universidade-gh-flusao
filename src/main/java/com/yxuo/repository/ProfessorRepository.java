package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.BaseEntity;
import com.yxuo.model.ProfessorAC;
import com.yxuo.util.DBConnector;

public class ProfessorRepository extends BaseRepository {

    public ProfessorRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.professor = new ProfessorAC();
    }

    public ProfessorRepository(Connection con) throws SQLException {
        this.connection = con;
        this.professor = new ProfessorAC();
    }

    private Connection connection;
    private ProfessorAC professor;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.professor;
        return entity;
    }

    private ProfessorAC construirObjeto(ResultSet resultado) throws SQLException {
        int idProf = resultado.getInt(professor.getIdColumn());
        String matProf = resultado.getString(professor.getMatProfColumn());
        String nome = resultado.getString(professor.getNomeColumn());
        ProfessorAC professor = new ProfessorAC(idProf, matProf, nome);
        try {
            String situacao = resultado.getString(professor.getSituacaoColumn());
            professor.setSituacao(situacao);
        } catch (Exception e) {}
        return professor;
    }

    public List<ProfessorAC> listarTodos() throws SQLException {
        List<ProfessorAC> professores = new ArrayList<>();
        String query = "SELECT * FROM " + professor.getTableName();
        DBConnector.printQuery(query);
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
        String query = "INSERT INTO " + professor.getTableName() + " (" + professor.getIdColumn() + ", "
                + professor.getMatProfColumn() + ", "
                + professor.getNomeColumn() + ") VALUES (?, ?, ?)";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, professor.getId());
            statement.setString(2, professor.getMatProf());
            statement.setString(3, professor.getNome());
            statement.executeUpdate();
        }
    }

    public void atualizar(ProfessorAC professor) throws SQLException {
        String query = "UPDATE " + professor.getTableName()
                + " SET " + professor.getMatProfColumn() + " = ?, "
                + professor.getNomeColumn() + " = ?, "
                + professor.getSituacaoColumn() + " = ? "
                + "WHERE " + professor.getIdColumn() + " = ?";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, professor.getMatProf());
            statement.setString(2, professor.getNome());
            statement.setString(3, professor.getSituacao());
            statement.setInt(4, professor.getId());
            statement.executeUpdate();
        }
    }

    public ProfessorAC buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM " + professor.getTableName()
                + " WHERE " + professor.getIdColumn() + " = ?";
        DBConnector.printQuery(query);
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

    public ProfessorAC buscarPorMatricula(String matricula) throws SQLException {
        String query = "SELECT * FROM " + professor.getTableName()
                + " WHERE " + professor.getMatProfColumn() + " = ?";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, matricula);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return construirObjeto(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Busca se o nome contém substring
     */
    public List<ProfessorAC> buscarPorNome(String nome) throws SQLException {
        List<ProfessorAC> professores = new ArrayList<>();
        String query = "SELECT * FROM " + professor.getTableName()
                + " WHERE " + professor.getNomeColumn() + " LIKE ? COLLATE NOCASE";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + nome + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProfessorAC professor = construirObjeto(resultSet);
                    professores.add(professor);
                }
            }
        }
        return professores;
    }

    public void criarTabela() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + professor.getTableName() + " (" +
                professor.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                professor.getMatProfColumn() + " VARCHAR(255), " +
                professor.getNomeColumn() + " VARCHAR(255), " +
                professor.getSituacaoColumn() + " VARCHAR(255) " +
                ")";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
