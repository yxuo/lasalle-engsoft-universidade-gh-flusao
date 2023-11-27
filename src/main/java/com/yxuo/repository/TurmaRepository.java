package com.yxuo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yxuo.model.BaseEntity;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.TurmaAC;
import com.yxuo.util.DBConnector;

public class TurmaRepository extends BaseRepository {
    private Connection connection;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaAC turma;

    public TurmaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.disciplinaRepository = new DisciplinaRepository();
        this.professorRepository = new ProfessorRepository();
        this.turma = new TurmaAC();
    }

    public TurmaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.disciplinaRepository = new DisciplinaRepository(con);
        this.professorRepository = new ProfessorRepository(con);
        this.turma = new TurmaAC();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends BaseEntity> T getEntity() {
        @SuppressWarnings("unchecked")
        T entity = (T) this.turma;
        return entity;
    }

    private TurmaAC construirObjeto(ResultSet resultado, Boolean subItens) throws SQLException {
        int idTurma = resultado.getInt(turma.getIdColumn());
        String turno = resultado.getString(turma.getTurnoColumn());
        String dia = resultado.getString(turma.getDiaColumn());
        String horaInicio = resultado.getString(turma.getHoraInicioColumn());
        String horaFim = resultado.getString(turma.getHoraFimColumn());

        DisciplinaAC disciplina = disciplinaRepository.buscarPorId(resultado.getInt(turma.getDisciplina().getId()));
        ProfessorAC professor = professorRepository.buscarPorId(resultado.getInt(turma.getProfessor().getId()));

        return new TurmaAC(idTurma, turno, dia, horaInicio, horaFim, disciplina, professor);
    }

    public List<TurmaAC> listarTodos() throws SQLException {
        return listarTodos(true);
    }

    public List<TurmaAC> listarTodos(Boolean subItens) throws SQLException {
        List<TurmaAC> turmas = new ArrayList<>();
        String query = "SELECT * FROM " + turma.getTableName();
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                TurmaAC turma = construirObjeto(resultSet, subItens);
                turmas.add(turma);
            }
        }
        return turmas;
    }

    public void inserir(TurmaAC turma) throws SQLException {
        String query = "INSERT INTO " + turma.getTableName() + " ("
                + turma.getIdColumn() + ", "
                + turma.getTurnoColumn() + ", "
                + turma.getDiaColumn() + ", "
                + turma.getHoraInicioColumn() + ", "
                + turma.getHoraFimColumn() + ", "
                + turma.getDisciplinaColumn() + ", "
                + turma.getProfessorColumn()
                + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, turma.getId());
            statement.setString(2, turma.getTurno());
            statement.setString(3, turma.getDia());
            statement.setString(4, turma.getHoraInicio());
            statement.setString(5, turma.getHoraFim());
            statement.setInt(6, turma.getDisciplina().getIdDis());
            statement.setInt(7, turma.getProfessor().getIdProf());
            statement.executeUpdate();
        }
    }

    public void atualizar(TurmaAC turma) throws SQLException {
        String query = "UPDATE " + turma.getTableName() + " SET " + turma.getTurnoColumn() + " = ?, "
                + turma.getDiaColumn() + " = ?, "
                + turma.getHoraInicioColumn() + " = ?, " + turma.getHoraFimColumn() + " = ?, " + turma.getIdColumn()
                + " = ?, "
                + turma.getProfessorColumn() + " = ? WHERE " + turma.getIdColumn() + " = ?";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, turma.getTurno());
            statement.setString(2, turma.getDia());
            statement.setString(3, turma.getHoraInicio());
            statement.setString(4, turma.getHoraFim());
            statement.setInt(5, turma.getDisciplina().getIdDis());
            statement.setInt(6, turma.getProfessor().getIdProf());
            statement.setInt(7, turma.getIdTurma());
            statement.executeUpdate();
        }
    }

    public TurmaAC buscarPorId(int id) throws SQLException {
        return buscarPorId(id, true);
    }

    public TurmaAC buscarPorId(int id, Boolean subItens) throws SQLException {
        String query = "SELECT * FROM " + turma.getTableName() + " WHERE " + turma.getIdColumn() + " = ?";
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

    public void criarTabela() throws SQLException {
        DisciplinaAC disciplina = turma.getDisciplina();
        ProfessorAC professor = turma.getProfessor();

        String query = "CREATE TABLE IF NOT EXISTS " + turma.getTableName() + " (" +
                turma.getIdColumn() + " INT AUTO_INCREMENT PRIMARY KEY, " +
                turma.getTurnoColumn() + " VARCHAR(255), " +
                turma.getDiaColumn() + " VARCHAR(255), " +
                turma.getHoraInicioColumn() + " VARCHAR(255), " +
                turma.getHoraFimColumn() + " VARCHAR(255), " +
                turma.getDisciplinaColumn() + " INT, " +
                turma.getProfessorColumn() + " INT, " +
                "FOREIGN KEY (" + turma.getIdColumn() + ") REFERENCES " + disciplina.getTableName() + "("
                + disciplina.getIdColumn()
                + ") ON DELETE CASCADE, " +
                "FOREIGN KEY (" + turma.getProfessorColumn() + ") REFERENCES " + professor.getTableName() + "("
                + professor.getIdColumn()
                + ") ON DELETE CASCADE" +
                ")";
        DBConnector.printQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
