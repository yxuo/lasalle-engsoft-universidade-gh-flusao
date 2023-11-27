package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.BaseEntity;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.util.DBConnector;

public class ProvaRepository extends BaseRepository {

    public ProvaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.turmaRepository = new TurmaRepository();
        this.prova = new ProvaAC();
    }

    public ProvaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.turmaRepository = new TurmaRepository(con);
        this.prova = new ProvaAC();
    }

    private Connection connection;
    private final TurmaRepository turmaRepository;
    private final ProvaAC prova;

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.prova;
        return entity;
    }

    private ProvaAC construirObjeto(ResultSet resultado, Boolean subItens) throws SQLException {
        int idProva = resultado.getInt(prova.getIdColumn());
        String codProva = resultado.getString(prova.getCodProvaColumn());
        String situacao = resultado.getString(prova.getSituacaoColumn());
        TurmaAC turma = new TurmaAC(resultado.getInt(prova.getTurmaColumn()));
        if (subItens) {
            turma = turmaRepository.buscarPorId(resultado.getInt(prova.getTurmaColumn()));
        }
        return new ProvaAC(idProva, codProva, situacao, turma);
    }

    public List<ProvaAC> listarTodos() throws SQLException {
        return listarTodos(true);
    }

    public List<ProvaAC> listarTodos(Boolean subItens) throws SQLException {
        List<ProvaAC> provas = new ArrayList<>();
        String query = "SELECT * FROM " + prova.getTableName();
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProvaAC prova = construirObjeto(resultSet, subItens);
                provas.add(prova);
            }
        }
        return provas;
    }

    public void inserir(ProvaAC prova) throws SQLException {
        String query = "INSERT INTO " + prova.getTableName() + " (" + prova.getIdColumn() + ", "
                + prova.getCodProvaColumn() + ", "
                + prova.getSituacaoColumn()
                + ", " + prova.getTurmaColumn() + ") VALUES (?, ?, ?, ?)";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, prova.getId());
            statement.setString(2, prova.getCodProva());
            statement.setString(3, prova.getSituacao());
            statement.setInt(4, prova.getTurma().getIdTurma());
            statement.executeUpdate();
        }
    }

    public void atualizar(ProvaAC prova) throws SQLException {
        String query = "UPDATE " + prova.getTableName() + " SET " + prova.getCodProvaColumn() + " = ?, "
                + prova.getSituacaoColumn()
                + " = ?, " + prova.getTurmaColumn() + " = ? WHERE " + prova.getIdColumn() + " = ?";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prova.getCodProva());
            statement.setString(2, prova.getSituacao());
            statement.setInt(3, prova.getTurma().getIdTurma());
            statement.setInt(4, prova.getIdProva());
            statement.executeUpdate();
        }
    }

    public ProvaAC buscarPorId(int id) throws SQLException {
        return buscarPorId(id, true);
    }

    public ProvaAC buscarPorId(int id, Boolean subItens) throws SQLException {
        String query = "SELECT * FROM " + prova.getTableName() + " WHERE " + prova.getIdColumn() + " = ?";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return construirObjeto(resultSet, subItens);
                }
            }
        }
        return null;
    }

    public List<ProvaAC> buscarPorCodigo(String codProva) throws SQLException {
        return buscarPorCodigo(codProva, true);
    }

    public List<ProvaAC> buscarPorCodigo(String codProva, Boolean subItens) throws SQLException {
        String query = "SELECT * FROM " + prova.getTableName()
                + " WHERE " + prova.getCodProvaColumn() + " LIKE ? COLLATE NOCASE";
        DBConnector.printQuery(query);
        List<ProvaAC> provas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + codProva + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProvaAC prova = construirObjeto(resultSet, subItens);
                    provas.add(prova);
                }
            }
        }
        return provas;
    }

    public void criarTabela() throws SQLException {
        TurmaAC t = prova.getTurma();

        String query = "CREATE TABLE IF NOT EXISTS " + prova.getTableName() + " (" +
                prova.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                prova.getCodProvaColumn() + " VARCHAR(255), " +
                prova.getSituacaoColumn() + " VARCHAR(255), " +
                prova.getTurmaColumn() + " INT, " +
                "FOREIGN KEY (" + prova.getTurmaColumn() + ") REFERENCES " + t.getTableName() + "(" + t.getIdColumn()
                + ") ON DELETE CASCADE"
                +
                ")";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
