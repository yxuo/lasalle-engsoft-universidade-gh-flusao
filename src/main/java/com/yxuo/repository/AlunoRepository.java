package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.BaseEntity;
import com.yxuo.util.DBConnector;

public class AlunoRepository extends BaseRepository {

    public AlunoRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.aluno = new AlunoAC();
    }

    public AlunoRepository(Connection con) throws SQLException {
        this.connection = con;
        this.aluno = new AlunoAC();
    }

    private Connection connection;
    private AlunoAC aluno;

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.aluno;
        return entity;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    private AlunoAC construirObjeto(ResultSet resultado) throws SQLException {
        int idAluno = resultado.getInt(aluno.getIdColumn());
        String mat = resultado.getString(aluno.getMatColumn());
        String nome = resultado.getString(aluno.getNomeColumn());
        return new AlunoAC(idAluno, mat, nome);
    }

    public List<AlunoAC> listarTodos() throws SQLException {
        List<AlunoAC> alunos = new ArrayList<>();
        String query = "SELECT * FROM " + aluno.getTableName();
        DBConnector.printQuery(query);
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
        String query = "INSERT INTO " + aluno.getTableName() + " (" + aluno.getIdColumn() + ", "
                + aluno.getMatColumn() + ", " + aluno.getNomeColumn() + ") VALUES (?, ?, ?)";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, aluno.getId());
            statement.setString(2, aluno.getMat());
            statement.setString(3, aluno.getNome());
            statement.executeUpdate();
        }
    }

    public void atualizar(AlunoAC aluno) throws SQLException {
        String query = "UPDATE " + aluno.getTableName() + " SET " + aluno.getMatColumn() + " = ?, "
                + aluno.getNomeColumn()
                + " = ? WHERE "
                + aluno.getIdColumn() + " = ?";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, aluno.getMat());
            statement.setString(2, aluno.getNome());
            statement.setInt(3, aluno.getIdAluno());
            statement.executeUpdate();
        }
    }

    public AlunoAC buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM " + aluno.getTableName() + " WHERE " + aluno.getIdColumn() + " = ?";
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

    public void criarTabela() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + aluno.getTableName() + " (" +
                aluno.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                aluno.getNomeColumn() + " VARCHAR(255), " +
                aluno.getMatColumn() + " VARCHAR(255) " +
                ")";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }
}
