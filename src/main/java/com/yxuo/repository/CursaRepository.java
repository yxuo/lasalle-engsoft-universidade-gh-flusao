package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.AlunoAC;
import com.yxuo.model.BaseEntity;
import com.yxuo.model.CursaAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.util.DBConnector;

public class CursaRepository extends BaseRepository {

    public CursaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.alunoRepository = new AlunoRepository();
        this.turmaRepository = new TurmaRepository();
        cursa = new CursaAC();
    }

    public CursaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.alunoRepository = new AlunoRepository(con);
        this.turmaRepository = new TurmaRepository(con);
        cursa = new CursaAC();
    }

    private Connection connection;
    private final CursaAC cursa;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.cursa;
        return entity;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private CursaAC construirObjeto(ResultSet resultado) throws SQLException {
        int idCursa = resultado.getInt(cursa.getIdColumn());
        AlunoAC aluno = alunoRepository.buscarPorId(resultado.getInt(cursa.getAlunoColumn()));
        TurmaAC turma = turmaRepository.buscarPorId(resultado.getInt(cursa.getTurmaColumn()));
        return new CursaAC(idCursa, aluno, turma);
    }

    public List<CursaAC> listarTodos() throws SQLException {
        List<CursaAC> cursandos = new ArrayList<>();
        String query = "SELECT * FROM " + cursa.getTableName();
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CursaAC cursa = construirObjeto(resultSet);
                cursandos.add(cursa);
            }
        }
        return cursandos;
    }

    public void inserir(CursaAC cursa) throws SQLException {
        String query = "INSERT INTO " + cursa.getTableName() + " (" + cursa.getIdColumn() + ", " + cursa.getAlunoColumn() + ", "
                + cursa.getTurmaColumn()
                + ") VALUES (?, ?, ?)";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cursa.getId());
            statement.setInt(2, cursa.getAluno().getIdAluno());
            statement.setInt(3, cursa.getTurma().getIdTurma());
            statement.executeUpdate();
        }
    }

    public void atualizar(CursaAC cursa) throws SQLException {
        String query = "UPDATE " + cursa.getTableName() + " SET " + cursa.getAlunoColumn() + " = ?, " + cursa.getTurmaColumn()
                + " = ? WHERE "
                + cursa.getIdColumn() + " = ?";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cursa.getAluno().getIdAluno());
            statement.setInt(2, cursa.getTurma().getIdTurma());
            statement.setInt(3, cursa.getIdCursa());
            statement.executeUpdate();
        }
    }

    public CursaAC buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM " + cursa.getTableName() + " WHERE " + cursa.getIdColumn() + " = ?";
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
        AlunoAC aluno = cursa.getAluno();
        TurmaAC turma = cursa.getTurma();

        String query = "CREATE TABLE IF NOT EXISTS " + cursa.getTableName() + " (" +
                cursa.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                cursa.getAlunoColumn() + " INT, " +
                cursa.getTurmaColumn() + " INT, " +
                "FOREIGN KEY (" + cursa.getAlunoColumn() + ") REFERENCES " + aluno.getTableName() + "(" + aluno.getIdColumn()
                + ") ON DELETE CASCADE, " +
                "FOREIGN KEY (" + cursa.getTurmaColumn() + ") REFERENCES " + turma.getTableName() + "(" + turma.getIdColumn()
                + ") ON DELETE CASCADE"
                +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }
}
