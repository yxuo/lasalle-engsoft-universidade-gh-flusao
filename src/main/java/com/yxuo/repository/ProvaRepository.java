package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.ProvaAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.util.DBConnector;

public class ProvaRepository extends BaseRepository {
    private Connection connection;
    private final String TABLE_NAME = "Prova";
    private final String COLUMN_ID = "idProva";
    private final String COLUMN_COD_PROVA = "codProva";
    private final String COLUMN_SITUACAO = "situacao";
    private final String COLUMN_TURMA_ID = "turma";
    private final TurmaRepository turmaRepository;

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

    public ProvaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.turmaRepository = new TurmaRepository();
    }

    public ProvaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.turmaRepository = new TurmaRepository(con);
    }

    private ProvaAC construirObjeto(ResultSet resultado) throws SQLException {
        int idProva = resultado.getInt(COLUMN_ID);
        String codProva = resultado.getString(COLUMN_COD_PROVA);
        String situacao = resultado.getString(COLUMN_SITUACAO);
        TurmaAC turma = turmaRepository.buscarPorId(resultado.getInt(COLUMN_TURMA_ID));
        return new ProvaAC(idProva, codProva, situacao, turma);
    }

    public List<ProvaAC> listarTodos() throws SQLException {
        List<ProvaAC> provas = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProvaAC prova = construirObjeto(resultSet);
                provas.add(prova);
            }
        }
        return provas;
    }

    public void inserir(ProvaAC prova) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_COD_PROVA + ", " + COLUMN_SITUACAO
                + ", " + COLUMN_TURMA_ID + ") VALUES (?, ?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prova.getCodProva());
            statement.setString(2, prova.getSituacao());
            statement.setInt(3, prova.getTurma().getIdTurma());
            statement.executeUpdate();
        }
    }

    public void atualizar(ProvaAC prova) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_COD_PROVA + " = ?, " + COLUMN_SITUACAO
                + " = ?, " + COLUMN_TURMA_ID + " = ? WHERE " + COLUMN_ID + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prova.getCodProva());
            statement.setString(2, prova.getSituacao());
            statement.setInt(3, prova.getTurma().getIdTurma());
            statement.setInt(4, prova.getIdProva());
            statement.executeUpdate();
        }
    }

    public ProvaAC buscarPorId(int id) throws SQLException {
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
        String turmaTable = turmaRepository.getTABLE_NAME();
        String turmaId = turmaRepository.getCOLUMN_ID();

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INT AUTO_INCREMENT PRIMARY KEY, " +
                COLUMN_COD_PROVA + " VARCHAR(255), " +
                COLUMN_SITUACAO + " VARCHAR(255), " +
                COLUMN_TURMA_ID + " INT, " +
                "FOREIGN KEY (" + COLUMN_TURMA_ID + ") REFERENCES " + turmaTable + "(" + turmaId + ") ON DELETE CASCADE"
                +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
