package com.yxuo.view.cli.gerenciar.provas;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.yxuo.constant.MessageCte;
import com.yxuo.controller.cli.ProvasCLIController;
import com.yxuo.controller.cli.struct.SProvasCliCResponse;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.enums.ProvaStatusEnum;
import com.yxuo.util.CLI;
import com.yxuo.view.cli.gerenciar.provas.enums.GProvaCliMenuEnum;
import com.yxuo.view.cli.gerenciar.provas.enums.GProvasCliBuscarMenuEnum;
import com.yxuo.view.cli.gerenciar.provas.enums.GProvasCliMenuEnum;
import com.yxuo.view.cli.gerenciar.provas.enums.GProvasCliStatusEnum;

public class GerenciarProvasCliView {

    private GProvasCliStatusEnum status = GProvasCliStatusEnum.PROVAS;
    private ProvasCLIController provasCLIController;
    private Scanner scanner;
    private SProvasCliCResponse lastResult = new SProvasCliCResponse();

    public GerenciarProvasCliView() throws SQLException {
        provasCLIController = new ProvasCLIController();
        scanner = new Scanner(System.in);
    }

    public void display() throws SQLException {
        clearCli();
        try {
            while (status != GProvasCliStatusEnum.MENU) {
                System.out.println("Gerenciar dados > provas:");
                System.out.println(GProvasCliMenuEnum.toString1(lastResult.getProvas().size() > 0));
                System.out.println(MessageCte.ESCOLHA_OPCAO);

                String choice = scanner.nextLine();
                GProvasCliMenuEnum selectedOption = GProvasCliMenuEnum.getEnum(choice);
                if (selectedOption != null) {
                    performAction(selectedOption);
                } else {
                    clearCli();
                    clearCli();
                    System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                }
            }
        } catch (NoSuchElementException ex) {
            System.out.println("\nAborting 3...");
            scanner.close();
            System.exit(3);
        }
        clearCli();
    }

    public void clearCli() {
        CLI.clear();
    }

    public void printLastResponse() {
        if (lastResult.getResponse().length() > 0) {
            System.out.println("\n" + "-".repeat(20) + "\n");
            System.out.println(lastResult.getResponse());
            System.out.println("-".repeat(20) + "\n");
        }
    }

    public void performAction(GProvasCliMenuEnum selectedOption) throws SQLException {
        clearCli();
        switch (selectedOption) {
            case BUSCAR: {
                performBuscarAction();
                break;
            }
            case VER_TODOS: {
                System.out.println("\n" + "-".repeat(20) + "\n");
                SProvasCliCResponse response = provasCLIController.getProvas();
                lastResult = response;
                System.out.println("\n" + response.getResponse());
                System.out.println("-".repeat(20) + "\n");
                break;
            }
            case EDITAR: {
                performEditarAction();
                break;
            }
            case VOLTAR: {
                status = GProvasCliStatusEnum.MENU;
                break;
            }
            default: {
                clearCli();
                clearCli();
                System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                break;
            }
        }
    }

    public void performEditarAction() throws SQLException {
        status = GProvasCliStatusEnum.EDITAR;
        clearCli();
        while (status == GProvasCliStatusEnum.EDITAR) {
            System.out.println("Gerenciar dados > provas > editar\n");
            System.out.println(lastResult.getResponse());
            System.out.println("z. CANCELAR");
            System.out.println(MessageCte.ESCOLLHA_LINHA);
            String choice = scanner.nextLine();
            if (choice.equals("z")) {
                status = GProvasCliStatusEnum.PROVAS;
                clearCli();
                return;
            }
            try {
                Integer linha = Integer.parseInt(choice);
                if (linha < 1 || linha > lastResult.getProvas().size()) {
                    clearCli();
                    clearCli();
                    System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                } else {
                    performProvaAction(linha);
                }
            } catch (Exception e) {
                clearCli();
                clearCli();
                System.out.println(CLI.getError(MessageCte.FORMATO_INVALIDO) + "\n");
            }
        }
    }

    public void performProvaAction(Integer linha) throws SQLException {
        status = GProvasCliStatusEnum.PROVA;
        ProvaAC prova = new ProvaAC(lastResult.getProvas().get(linha - 1));
        clearCli();
        while (status == GProvasCliStatusEnum.PROVA) {
            System.out.println("Gerenciar dados > provas > editar > #" + prova.getId());
            System.out.println(prova.toString(true) + "\n");
            System.out.println(GProvaCliMenuEnum.toString1(prova));
            System.out.println(MessageCte.ESCOLHA_OPCAO);
            String choice = scanner.nextLine();
            GProvaCliMenuEnum choiceEnum = GProvaCliMenuEnum.getEnum(choice, prova);
            ProvaStatusEnum newStatus = ProvaStatusEnum.getEnum(choice, prova);
            if (choiceEnum == null) {
                clearCli();
                clearCli();
                System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");

            } else if (choiceEnum == GProvaCliMenuEnum.CANCELAR) {
                printLastResponse();
                status = GProvasCliStatusEnum.PROVAS;
            } else {
                prova.setSituacao(newStatus);
                System.out.println("\n" + "-".repeat(20) + "\n");
                provasCLIController.saveProva(prova);
                SProvasCliCResponse response = provasCLIController
                        .getProvaByCodigo(prova.getCodProva());
                lastResult = response;
                System.out.println("\n" + lastResult.getResponse());
                System.out.println("-".repeat(20) + "\n");
                status = GProvasCliStatusEnum.PROVAS;
            }
        }
    }

    public void performBuscarAction() throws SQLException {
        status = GProvasCliStatusEnum.BUSCAR;
        clearCli();
        while (status == GProvasCliStatusEnum.BUSCAR) {
            System.out.println("Gerenciar dados > provas > buscar");
            System.out.println(GProvasCliBuscarMenuEnum.toString1());
            System.out.println(MessageCte.FACA_BUSCA);
            String choice = scanner.nextLine();
            String[] args = choice.split(" ");
            GProvasCliBuscarMenuEnum selectedOption = GProvasCliBuscarMenuEnum
                    .getEnum(args[0]);

            if (selectedOption != null) {
                switch (selectedOption) {
                    case CANCELAR:
                        clearCli();
                        status = GProvasCliStatusEnum.PROVAS;
                        break;
                    default:
                        if (args.length >= 2) {
                            switch (selectedOption) {
                                case ID: {
                                    try {
                                        Integer id = Integer.parseInt(args[1]);
                                        System.out.println("\n" + "-".repeat(20) + "\n");
                                        SProvasCliCResponse response = provasCLIController
                                                .getProvaById(id);
                                        lastResult = response;
                                        System.out.println("\n" + response.getResponse());
                                        System.out.println("-".repeat(20) + "\n");
                                        status = GProvasCliStatusEnum.PROVAS;
                                    } catch (NumberFormatException e) {
                                        clearCli();
                                        clearCli();
                                        System.out.println(CLI.getError(MessageCte.FORMATO_INVALIDO) + "\n");
                                    }
                                    break;
                                }
                                case CODIGO: {
                                    String codigo = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                                    System.out.println("\n" + "-".repeat(20) + "\n");
                                    SProvasCliCResponse response = provasCLIController
                                            .getProvaByCodigo(codigo);
                                    lastResult = response;
                                    System.out.println("\n" + response.getResponse());
                                    System.out.println("-".repeat(20) + "\n");
                                    status = GProvasCliStatusEnum.PROVAS;
                                    break;
                                }
                                default: {
                                    clearCli();
                                    clearCli();
                                    System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                                    break;
                                }
                            }
                        } else {
                            clearCli();
                            clearCli();
                            System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                        }
                        break;
                }
            } else {
                clearCli();
                clearCli();
                System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
            }
        }
    }

}
