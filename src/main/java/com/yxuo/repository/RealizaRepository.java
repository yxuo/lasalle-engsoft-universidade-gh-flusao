package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.BaseEntity;
import com.yxuo.model.MatriculadoAC;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.RealizaAC;
import com.yxuo.util.DBConnector;

public class RealizaRepository extends BaseRepository {
    private Connection connection;
    private final MatriculadoRepository matriculadoRepository;
    private final ProvaRepository provaRepository;
    private final RealizaAC realiza;

    public RealizaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.matriculadoRepository = new MatriculadoRepository();
        this.provaRepository = new ProvaRepository();
        this.realiza = new RealizaAC();
    }

    public RealizaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.matriculadoRepository = new MatriculadoRepository(con);
        this.provaRepository = new ProvaRepository(con);
        this.realiza = new RealizaAC();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.realiza;
        return entity;
    }

    private RealizaAC construirObjeto(ResultSet resultado) throws SQLException {
        int idRealiza = resultado.getInt(realiza.getIdColumn());
        double nota = resultado.getDouble(realiza.getNotaColumn());
        MatriculadoAC matriculado = matriculadoRepository.buscarPorId(resultado.getInt(realiza.getMatriculadoColumn()));
        ProvaAC prova = provaRepository.buscarPorId(resultado.getInt(realiza.getProvaColumn()));
        return new RealizaAC(idRealiza, nota, matriculado, prova);
    }

    public List<RealizaAC> listarTodos() throws SQLException {
        List<RealizaAC> realizacoes = new ArrayList<>();
        String query = "SELECT * FROM " + realiza.getTableName();
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
        String query = "INSERT INTO " + realiza.getTableName() + " (" + realiza.getIdColumn() + ", " + realiza.getNotaColumn() + ", "
                + realiza.getMatriculadoColumn() + ", "
                + realiza.getProvaColumn() + ") VALUES (?, ?, ?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, realiza.getId());
            statement.setDouble(2, realiza.getNota());
            statement.setInt(3, realiza.getMatriculado().getIdMatriculado());
            statement.setInt(4, realiza.getProva().getIdProva());
            statement.executeUpdate();
        }
    }

    public void atualizar(RealizaAC realiza) throws SQLException {
        String query = "UPDATE " + realiza.getTableName() + " SET " + realiza.getNotaColumn() + " = ?, " + realiza.getMatriculadoColumn()
                + " = ?, "
                + realiza.getProvaColumn() + " = ? WHERE " + realiza.getIdColumn() + " = ?";
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
        String query = "SELECT * FROM " + realiza.getTableName() + " WHERE " + realiza.getIdColumn() + " = ?";
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
        ProvaAC prova = realiza.getProva();
        MatriculadoAC matriculado = realiza.getMatriculado();
        String query = "CREATE TABLE IF NOT EXISTS " + realiza.getTableName() + " (" +
                realiza.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                realiza.getNotaColumn() + " DOUBLE, " +
                realiza.getMatriculadoColumn() + " INT, " +
                realiza.getProvaColumn() + " INT, " +
                "FOREIGN KEY (" + realiza.getMatriculadoColumn() + ") REFERENCES " + matriculado.getTableName() + "(" + matriculado.getIdColumn()
                + ") ON DELETE CASCADE, "
                +
                "FOREIGN KEY (" + realiza.getProvaColumn() + ") REFERENCES " + prova.getTableName() + "(" + prova.getIdColumn()
                + ") ON DELETE CASCADE"
                +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
