package com.yxuo.view.cli.gerenciar.professores;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.yxuo.constant.MessageCte;
import com.yxuo.controller.cli.ProfessoresCLIController;
import com.yxuo.controller.cli.struct.SProfessorCliCResponse;
import com.yxuo.controller.cli.struct.SProfessoresCliCResponse;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.enums.ProfessorStatusEnum;
import com.yxuo.util.CLI;
import com.yxuo.view.cli.gerenciar.professores.enums.GProfessorCliMenuEnum;
import com.yxuo.view.cli.gerenciar.professores.enums.GProfessoresCliBuscarMenuEnum;
import com.yxuo.view.cli.gerenciar.professores.enums.GProfessoresCliMenuEnum;
import com.yxuo.view.cli.gerenciar.professores.enums.GProfessoresCliStatusEnum;

public class GerenciarProfessoresCliView {

    private GProfessoresCliStatusEnum status = GProfessoresCliStatusEnum.PROFESSORES;
    private ProfessoresCLIController professoresCLIController;
    private Scanner scanner;
    private SProfessoresCliCResponse lastResult = new SProfessoresCliCResponse();

    public GerenciarProfessoresCliView() throws SQLException {
        professoresCLIController = new ProfessoresCLIController();
        scanner = new Scanner(System.in);
    }

    public void display() throws SQLException {
        clearCli();
        try {
            while (status != GProfessoresCliStatusEnum.MENU) {
                System.out.println("Gerenciar dados > professores:");
                System.out.println(GProfessoresCliMenuEnum.toString1(lastResult.getProfessores().size() > 0));
                System.out.println(MessageCte.ESCOLHA_OPCAO);

                String choice = scanner.nextLine();
                GProfessoresCliMenuEnum selectedOption = GProfessoresCliMenuEnum.getEnum(choice);
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

    public void performAction(GProfessoresCliMenuEnum selectedOption) throws SQLException {
        clearCli();
        switch (selectedOption) {
            case BUSCAR: {
                performBuscarAction();
                break;
            }
            case VER_TODOS: {
                System.out.println("\n" + "-".repeat(20) + "\n");
                SProfessoresCliCResponse response = professoresCLIController.getProfessores();
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
                status = GProfessoresCliStatusEnum.MENU;
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
        status = GProfessoresCliStatusEnum.EDITAR;
        clearCli();
        while (status == GProfessoresCliStatusEnum.EDITAR) {
            System.out.println("Gerenciar dados > professores > editar\n");
            System.out.println(lastResult.getResponse());
            System.out.println("z. CANCELAR");
            System.out.println(MessageCte.ESCOLLHA_LINHA);
            String choice = scanner.nextLine();
            if (choice.equals("z")) {
                status = GProfessoresCliStatusEnum.PROFESSORES;
                clearCli();
                return;
            }
            try {
                Integer linha = Integer.parseInt(choice);
                if (linha < 1 || linha > lastResult.getProfessores().size()) {
                    clearCli();
                    clearCli();
                    System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                } else {
                    performProfessorAction(linha);
                }
            } catch (Exception e) {
                clearCli();
                clearCli();
                System.out.println(CLI.getError(MessageCte.FORMATO_INVALIDO) + "\n");
            }
        }
    }

    public void performProfessorAction(Integer linha) throws SQLException {
        status = GProfessoresCliStatusEnum.PROFESSOR;
        ProfessorAC professor = new ProfessorAC(lastResult.getProfessores().get(linha - 1));
        clearCli();
        while (status == GProfessoresCliStatusEnum.PROFESSOR) {
            System.out.println("Gerenciar dados > professores > editar > #" + professor.getId());
            System.out.println(professor.toString() + "\n");
            System.out.println(GProfessorCliMenuEnum.toString1(professor));
            System.out.println(MessageCte.ESCOLHA_OPCAO);
            String choice = scanner.nextLine();
            GProfessorCliMenuEnum choiceEnum = GProfessorCliMenuEnum.getEnum(choice, professor);
            ProfessorStatusEnum newStatus = ProfessorStatusEnum.getEnum(choice, professor);
            if (choiceEnum == null) {
                clearCli();
                clearCli();
                System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");

            } else if (choiceEnum == GProfessorCliMenuEnum.CANCELAR) {
                printLastResponse();
                status = GProfessoresCliStatusEnum.PROFESSORES;
            } else {
                professor.setSituacao(newStatus);
                System.out.println("\n" + "-".repeat(20) + "\n");
                professoresCLIController.saveProfessor(professor);
                SProfessorCliCResponse response = professoresCLIController
                        .getProfessorByMatricula(professor.getMatProf());
                lastResult = new SProfessoresCliCResponse(response);
                System.out.println("\n" + lastResult.getResponse());
                System.out.println("-".repeat(20) + "\n");
                status = GProfessoresCliStatusEnum.PROFESSORES;
            }
        }
    }

    public void performBuscarAction() throws SQLException {
        status = GProfessoresCliStatusEnum.BUSCAR;
        clearCli();
        while (status == GProfessoresCliStatusEnum.BUSCAR) {
            System.out.println("Gerenciar dados > professores > buscar");
            System.out.println(GProfessoresCliBuscarMenuEnum.toString1());
            System.out.println(MessageCte.FACA_BUSCA);
            String choice = scanner.nextLine();
            String[] args = choice.split(" ");
            GProfessoresCliBuscarMenuEnum selectedOption = GProfessoresCliBuscarMenuEnum
                    .getEnum(args[0]);

            if (selectedOption != null) {
                switch (selectedOption) {
                    case CANCELAR:
                        clearCli();
                        status = GProfessoresCliStatusEnum.PROFESSORES;
                        break;
                    default:
                        if (args.length >= 2) {
                            switch (selectedOption) {
                                case ID:
                                    try {
                                        Integer id = Integer.parseInt(args[1]);
                                        System.out.println("\n" + "-".repeat(20) + "\n");
                                        SProfessorCliCResponse response = professoresCLIController
                                                .getProfessorByIdString(id);
                                        lastResult = new SProfessoresCliCResponse(response);
                                        System.out.println("\n" + response.getResponse());
                                        System.out.println("-".repeat(20) + "\n");
                                        status = GProfessoresCliStatusEnum.PROFESSORES;
                                    } catch (NumberFormatException e) {
                                        clearCli();
                                        clearCli();
                                        System.out.println(CLI.getError(MessageCte.FORMATO_INVALIDO) + "\n");
                                    }
                                    break;
                                case MATIRCULA: {
                                    String matricula = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                                    System.out.println("\n" + "-".repeat(20) + "\n");
                                    SProfessorCliCResponse response = professoresCLIController
                                            .getProfessorByMatricula(matricula);
                                    lastResult = new SProfessoresCliCResponse(response);
                                    System.out.println("\n" + response.getResponse());
                                    System.out.println("-".repeat(20) + "\n");
                                    status = GProfessoresCliStatusEnum.PROFESSORES;
                                }
                                    break;
                                case NOME: {
                                    String nome = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                                    System.out.println("\n" + "-".repeat(20) + "\n");
                                    SProfessoresCliCResponse response = professoresCLIController
                                            .getProfessoresByNome(nome);
                                    lastResult = response;
                                    System.out.println("\n" + response.getResponse());
                                    System.out.println("-".repeat(20) + "\n");
                                    status = GProfessoresCliStatusEnum.PROFESSORES;
                                }
                                    break;

                                default:
                                    clearCli();
                                    clearCli();
                                    System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                                    break;
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
