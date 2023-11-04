package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.RealizaAC;
import com.yxuo.util.DBConnector;

public class RealizaRepository extends BaseRepository {
    private Connection connection;
    private final String TABLE_NAME = "Realiza";
    private final String COLUMN_ID = "idRealiza";
    private final String COLUMN_NOTA = "nota";
    private final String COLUMN_MATRICULADO_ID = "matriculado_id";
    private final String COLUMN_PROVA_ID = "prova_id";
    private final MatriculadoRepository matriculadoRepository;
    private final ProvaRepository provaRepository;

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

    public RealizaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.matriculadoRepository = new MatriculadoRepository();
        this.provaRepository = new ProvaRepository();
    }

    public RealizaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.matriculadoRepository = new MatriculadoRepository(con);
        this.provaRepository = new ProvaRepository(con);
    }

    private RealizaAC construirObjeto(ResultSet resultado) throws SQLException {
        int idRealiza = resultado.getInt(COLUMN_ID);
        double nota = resultado.getDouble(COLUMN_NOTA);
        MatriculadoAC matriculado = matriculadoRepository.buscarPorId(resultado.getInt(COLUMN_MATRICULADO_ID));
        ProvaAC prova = provaRepository.buscarPorId(resultado.getInt(COLUMN_PROVA_ID));
        return new RealizaAC(idRealiza, nota, matriculado, prova);
    }

    public List<RealizaAC> listarTodos() throws SQLException {
        List<RealizaAC> realizacoes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RealizaAC realiza = construirObjeto(resultSet);
                realizacoes.add(realiza);
            }
        }
        return realizacoes;
    }

    public void inserir(RealizaAC realiza) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NOTA + ", " + COLUMN_MATRICULADO_ID + ", "
                + COLUMN_PROVA_ID + ") VALUES (?, ?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, realiza.getNota());
            statement.setInt(2, realiza.getMatriculado().getIdMatriculado());
            statement.setInt(3, realiza.getProva().getIdProva());
            statement.executeUpdate();
        }
    }

    public void atualizar(RealizaAC realiza) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NOTA + " = ?, " + COLUMN_MATRICULADO_ID + " = ?, "
                + COLUMN_PROVA_ID + " = ? WHERE " + COLUMN_ID + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, realiza.getNota());
            statement.setInt(2, realiza.getMatriculado().getIdMatriculado());
            statement.setInt(3, realiza.getProva().getIdProva());
            statement.setInt(4, realiza.getIdRealiza());
            statement.executeUpdate();
        }
    }

    public RealizaAC buscarPorId(int id) throws SQLException {
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
        String provaTable = provaRepository.getTABLE_NAME();
        String provaId = provaRepository.getCOLUMN_ID();
        String matriculadoTable = matriculadoRepository.getTABLE_NAME();
        String matriculadoId = matriculadoRepository.getCOLUMN_ID();
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INT AUTO_INCREMENT PRIMARY KEY, " +
                COLUMN_NOTA + " DOUBLE, " +
                COLUMN_MATRICULADO_ID + " INT, " +
                COLUMN_PROVA_ID + " INT, " +
                "FOREIGN KEY (" + COLUMN_MATRICULADO_ID + ") REFERENCES " + matriculadoTable + "(" + matriculadoId
                + ") ON DELETE CASCADE, "
                +
                "FOREIGN KEY (" + COLUMN_PROVA_ID + ") REFERENCES " + provaTable + "(" + provaId + ") ON DELETE CASCADE"
                +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
