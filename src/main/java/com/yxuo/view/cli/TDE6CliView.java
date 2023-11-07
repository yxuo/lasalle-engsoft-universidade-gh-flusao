package com.yxuo.view.cli;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.yxuo.constant.MessageCte;
import com.yxuo.controller.cli.TDE6CLIController;
import com.yxuo.util.CLI;
import com.yxuo.view.cli.enums.TDE6CliMenuEnum;

public class TDE6CliView {

    private Boolean exitView = false;
    private String message = "";
    private TDE6CLIController tde6cliController;

    public TDE6CliView() throws SQLException {
        tde6cliController = new TDE6CLIController();
    }

    public void display() throws SQLException {
        clearCli();
        Scanner scanner = new Scanner(System.in);
        try {
            while (exitView == false) {
                System.out.println("TDE6:");
                System.out.println(TDE6CliMenuEnum.toString1());

                System.out.println(MessageCte.ESCOLHA_OPCAO);
                String choice = scanner.nextLine();

                TDE6CliMenuEnum selectedOption = TDE6CliMenuEnum.getEnum(choice);
                if (selectedOption != null) {
                    performAction(selectedOption);
                } else {
                    clearCli();
                    clearCli();
                    System.out.println(CLI.getError(MessageCte.OPCAO_INVALIDA) + "\n");
                }
            }
        } catch (NoSuchElementException ex) {
            System.out.println("\nAborting...");
            scanner.close();
        }
        clearCli();
    }

    public void clearCli() {
        CLI.clear();
        if (this.message.isEmpty() == false) {
            System.out.println(this.message + "\n");
            this.message = "";
        }
    }

    public void performAction(TDE6CliMenuEnum selectedOption) throws SQLException {
        clearCli();
        System.out.println("\n" + "-".repeat(20) + "\n");
        switch (selectedOption) {
            case QUESTÃO_1:
                System.out.println("\n" + tde6cliController.getQ1String());
                break;
            case QUESTÃO_2:
                System.out.println("\n" + tde6cliController.getQ2String());
                break;
            case QUESTÃO_3:
                System.out.println("\n" + tde6cliController.getQ3String());
                break;
            case QUESTÃO_4:
                System.out.println("\n" + tde6cliController.getQ4String());
                break;
            case QUESTÃO_5:
                System.out.println("\n" + tde6cliController.getQ5String());
                break;
            case QUESTÃO_6:
                System.out.println("\n" + tde6cliController.getQ6String());
                break;
            case MENU:
                exitView = true;
                break;
            default:
                break;
        }
        System.out.println("-".repeat(20) + "\n");
    }

}
