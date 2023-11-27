package com.yxuo.controller.cli;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yxuo.constant.TableCte;
import com.yxuo.controller.cli.struct.SProfessorCliCResponse;
import com.yxuo.controller.cli.struct.SProfessoresCliCResponse;
import com.yxuo.model.ProfessorAC;
import com.yxuo.repository.ProfessorRepository;
import com.yxuo.util.CLI;

public class ProfessoresCLIController {
    private ProfessorAC professor = new ProfessorAC();
    private ProfessorRepository professorRepository;

    public ProfessoresCLIController() throws SQLException {
        this.professorRepository = new ProfessorRepository();
    }

    public ProfessoresCLIController(Connection con) throws SQLException {
        this.professorRepository = new ProfessorRepository(con);
    }

    // #region get

    public SProfessoresCliCResponse getProfessores() throws SQLException {
        String response = "";
        List<ProfessorAC> items = professorRepository.listarTodos();
        List<String> headers = getHeader();
        List<Integer> maxLength = getItemsMaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getBody(items, maxLength);
        return new SProfessoresCliCResponse(items, response);
    }

    public SProfessorCliCResponse getProfessorByMatricula(String matricula) throws SQLException {
        String response = "";
        List<ProfessorAC> items = new ArrayList<>();
        ProfessorAC professor = professorRepository.buscarPorMatricula(matricula);
        if (professor != null) {
            items.add(professor);
        }
        List<String> headers = getHeader();
        List<Integer> maxLength = getItemsMaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getBody(items, maxLength);
        return new SProfessorCliCResponse(professor, response);
    }

    public SProfessorCliCResponse getProfessorByIdString(Integer id) throws SQLException {
        String response = "";
        List<ProfessorAC> items = new ArrayList<>();
        ProfessorAC professor = professorRepository.buscarPorId(id);
        if (professor != null) {
            items.add(professor);
        }
        List<String> headers = getHeader();
        List<Integer> maxLength = getItemsMaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getBody(items, maxLength);
        return new SProfessorCliCResponse(professor, response);
    }

    public SProfessoresCliCResponse getProfessoresByNome(String nome) throws SQLException {
        String response = "";
        List<ProfessorAC> items = professorRepository.buscarPorNome(nome);
        List<String> headers = getHeader();
        List<Integer> maxLength = getItemsMaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getBody(items, maxLength);
        return new SProfessoresCliCResponse(items, response);
    }

    // #endregion get

    public void saveProfessor(ProfessorAC professor) throws SQLException {
        ProfessorAC professorToSave = new ProfessorAC(professor);
        ProfessorAC professorDB = professorRepository.buscarPorMatricula(professor.getMatProf());
        if (professorDB == null) {
            professorRepository.inserir(professorToSave);
        } else {
            professorToSave.setIdProf(professorDB.getIdProf());
            professorRepository.atualizar(professorToSave);
        }
    }

    // #region utils

    private List<String> getHeader() {
        return Arrays.asList(
                TableCte.LINHA,
                professor.getIdColumn(),
                professor.getMatProfColumn(),
                professor.getNomeColumn(),
                professor.getSituacaoColumn());
    }

    private <T> List<Serializable> getBodyItem(T firstItem, ProfessorAC obj) {
        return Arrays.asList(
                firstItem.toString(),
                obj.getId(),
                obj.getMatProf(),
                obj.getNome(),
                obj.getSituacao());
    }

    private String getBody(List<ProfessorAC> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (ProfessorAC obj : items) {
            response += CLI.getTableString(getBodyItem(count, obj), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getItemsMaxLength(List<ProfessorAC> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (ProfessorAC obj : items) {
            maxLength = CLI.getMaxLength(getBodyItem("", obj), maxLength);
        }
        return maxLength;
    }

    // #endregion utils
}
