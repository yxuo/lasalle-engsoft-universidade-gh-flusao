package com.yxuo.controller.cli;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yxuo.constant.TableCte;
import com.yxuo.controller.cli.struct.SProvasCliCResponse;
import com.yxuo.model.ProvaAC;
import com.yxuo.repository.ProvaRepository;
import com.yxuo.util.CLI;

public class ProvasCLIController {
    private ProvaAC prova = new ProvaAC();
    private ProvaRepository provaRepository;

    public ProvasCLIController() throws SQLException {
        this.provaRepository = new ProvaRepository();
    }

    public ProvasCLIController(Connection con) throws SQLException {
        this.provaRepository = new ProvaRepository(con);
    }

    // #region get

    public SProvasCliCResponse getProvas() throws SQLException {
        String response = "";
        List<ProvaAC> items = provaRepository.listarTodos(false);
        List<String> headers = getHeader();
        List<Integer> maxLength = getItemsMaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getBody(items, maxLength);
        return new SProvasCliCResponse(items, response);
    }

    public SProvasCliCResponse getProvaById(Integer id) throws SQLException {
        String response = "";
        List<ProvaAC> items = new ArrayList<>();
        ProvaAC prova = provaRepository.buscarPorId(id, false);
        if (prova != null) {
            items.add(prova);
        }
        List<String> headers = getHeader();
        List<Integer> maxLength = getItemsMaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getBody(items, maxLength);
        return new SProvasCliCResponse(prova, response);
    }

    public SProvasCliCResponse getProvaByCodigo(String codigo) throws SQLException {
        String response = "";
        List<ProvaAC> items = provaRepository.buscarPorCodigo(codigo, false);
        List<String> headers = getHeader();
        List<Integer> maxLength = getItemsMaxLength(items, headers);
        response += CLI.getTableString(headers, maxLength);
        response += getBody(items, maxLength);
        return new SProvasCliCResponse(items, response);
    }

    // #endregion get

    public void saveProva(ProvaAC prova) throws SQLException {
        ProvaAC provaToSave = new ProvaAC(prova);
        List<ProvaAC> provasDB = provaRepository.buscarPorCodigo(prova.getCodProva(), false);
        if (provasDB.size() == 0) {
            provaRepository.inserir(provaToSave);
        } else {
            provaToSave.setIdProva(provasDB.get(0).getId());
            provaRepository.atualizar(provaToSave);
        }
    }

    // #region utils

    private List<String> getHeader() {
        return Arrays.asList(
                TableCte.LINHA,
                prova.getIdColumn(),
                prova.getCodProvaColumn(),
                prova.getSituacaoColumn(),
                prova.getTurmaColumn());
    }

    private <T> List<Serializable> getBodyItem(T firstItem, ProvaAC obj) {
        return Arrays.asList(
                firstItem.toString(),
                obj.getId(),
                obj.getCodProva(),
                obj.getSituacao(),
                obj.getTurma().getId());
    }

    private String getBody(List<ProvaAC> items, List<Integer> maxLength) throws SQLException {
        String response = "";
        Integer count = 1;
        for (ProvaAC obj : items) {
            response += CLI.getTableString(getBodyItem(count, obj), maxLength);
            count++;
        }
        return response;
    }

    private List<Integer> getItemsMaxLength(List<ProvaAC> items, List<String> headers) {
        List<Integer> maxLength = new ArrayList<>();
        // Header
        maxLength = CLI.getMaxLength(headers, maxLength);
        // Body
        for (ProvaAC obj : items) {
            maxLength = CLI.getMaxLength(getBodyItem("", obj), maxLength);
        }
        return maxLength;
    }

    // #endregion utils
}
