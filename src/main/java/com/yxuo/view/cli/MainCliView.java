package com.yxuo.view.cli;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.yxuo.constant.MessageCte;
import com.yxuo.util.CLI;
import com.yxuo.view.cli.enums.MainCliMenuEnum;

public class MainCliView {
    private String beforeClear = "";

    public void display() throws SQLException {
        clearCli();
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Menu principal:");
                System.out.println(MainCliMenuEnum.toString1());

                System.out.println(MessageCte.ESCOLHA_OPCAO);
                String choice = scanner.nextLine();

                MainCliMenuEnum selectedOption = MainCliMenuEnum.getEnum(choice);
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
    }

    public void clearCli() {
        CLI.clear();
        if (this.beforeClear.isEmpty() == false) {
            System.out.println(this.beforeClear + "\n");
            this.beforeClear = "";
        }
    }

    public void performAction(MainCliMenuEnum selectedOption) throws SQLException {
        if (selectedOption != MainCliMenuEnum.SAIR) {
            clearCli();
        }
        switch (selectedOption) {
            case TDE6:
                TDE6CliView tde6 = new TDE6CliView();
                tde6.display();
                break;
            case SAIR:
                System.out.println("Saindo...");
                System.exit(0);
                break;
            case SOBRE:
                System.out.println("-".repeat(20) + "\n");
                System.out.println(MessageCte.SOBRE + "\n");
                System.out.println("-".repeat(20) + "\n");
                break;
            default:
                break;
        }
    }

}
