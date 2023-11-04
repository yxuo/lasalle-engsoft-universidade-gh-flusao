package com.yxuo.repository;

import com.yxuo.model.TurmaAC;
import com.yxuo.model.DisciplinaAC;
import com.yxuo.model.ProfessorAC;
import com.yxuo.util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurmaRepository extends BaseRepository {
    private Connection connection;
    private final String TABLE_NAME = "Turma";
    private final String COLUMN_ID = "idTurma";
    private final String COLUMN_TURNO = "turno";
    private final String COLUMN_DIA = "dia";
    private final String COLUMN_HORA_INICIO = "horaInicio";
    private final String COLUMN_HORA_FIM = "horaFim";
    private final String COLUMN_DISCIPLINA_ID = "disciplina_id";
    private final String COLUMN_PROFESSOR_ID = "professor_id";
    private final DisciplinaRepository disciplinaRepository; // Repositório de DisciplinaAC
    private final ProfessorRepository professorRepository; // Repositório de ProfessorAC

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

    public TurmaRepository() throws SQLException {
        this.connection = DBConnector.getConnection();
        this.disciplinaRepository = new DisciplinaRepository();
        this.professorRepository = new ProfessorRepository();
    }

    public TurmaRepository(Connection con) throws SQLException {
        this.connection = con;
        this.disciplinaRepository = new DisciplinaRepository(con);
        this.professorRepository = new ProfessorRepository(con);
    }

    private TurmaAC construirObjeto(ResultSet resultado) throws SQLException {
        int idTurma = resultado.getInt(COLUMN_ID);
        String turno = resultado.getString(COLUMN_TURNO);
        String dia = resultado.getString(COLUMN_DIA);
        String horaInicio = resultado.getString(COLUMN_HORA_INICIO);
        String horaFim = resultado.getString(COLUMN_HORA_FIM);

        DisciplinaAC disciplina = disciplinaRepository.buscarPorId(resultado.getInt(COLUMN_DISCIPLINA_ID));
        ProfessorAC professor = professorRepository.buscarPorId(resultado.getInt(COLUMN_PROFESSOR_ID));

        return new TurmaAC(idTurma, turno, dia, horaInicio, horaFim, disciplina, professor);
    }

    public List<TurmaAC> listarTodos() throws SQLException {
        List<TurmaAC> turmas = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                TurmaAC turma = construirObjeto(resultSet);
                turmas.add(turma);
            }
        }
        return turmas;
    }

    public void inserir(TurmaAC turma) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TURNO + ", " + COLUMN_DIA + ", " + COLUMN_HORA_INICIO
                + ", " + COLUMN_HORA_FIM + ", " + COLUMN_DISCIPLINA_ID + ", " + COLUMN_PROFESSOR_ID
                + ") VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, turma.getTurno());
            statement.setString(2, turma.getDia());
            statement.setString(3, turma.getHoraInicio());
            statement.setString(4, turma.getHoraFim());
            statement.setInt(5, turma.getDisciplina().getIdDis());
            statement.setInt(6, turma.getProfessor().getIdProf());
            statement.executeUpdate();
        }
    }

    public void atualizar(TurmaAC turma) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_TURNO + " = ?, " + COLUMN_DIA + " = ?, "
                + COLUMN_HORA_INICIO + " = ?, " + COLUMN_HORA_FIM + " = ?, " + COLUMN_DISCIPLINA_ID + " = ?, "
                + COLUMN_PROFESSOR_ID + " = ? WHERE " + COLUMN_ID + " = ?";
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
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
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
        String disciplinaTable = disciplinaRepository.getTABLE_NAME();
        String disciplinaId = disciplinaRepository.getCOLUMN_ID();
        String professorTable = professorRepository.getTABLE_NAME();
        String professorId = professorRepository.getCOLUMN_ID();

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INT AUTO_INCREMENT PRIMARY KEY, " +
                COLUMN_TURNO + " VARCHAR(255), " +
                COLUMN_DIA + " VARCHAR(255), " +
                COLUMN_HORA_INICIO + " VARCHAR(255), " +
                COLUMN_HORA_FIM + " VARCHAR(255), " +
                COLUMN_DISCIPLINA_ID + " INT, " +
                COLUMN_PROFESSOR_ID + " INT, " +
                "FOREIGN KEY (" + COLUMN_DISCIPLINA_ID + ") REFERENCES " + disciplinaTable + "(" + disciplinaId
                + ") ON DELETE CASCADE, " +
                "FOREIGN KEY (" + COLUMN_PROFESSOR_ID + ") REFERENCES " + professorTable + "(" + professorId
                + ") ON DELETE CASCADE" +
                ")";
        DBConnector.parseQuery(query);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

}
