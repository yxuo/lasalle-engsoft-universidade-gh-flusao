package com.yxuo.view.cli.gerenciar;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.yxuo.constant.MessageCte;
import com.yxuo.util.CLI;
import com.yxuo.view.cli.gerenciar.enums.GerenciarCliMenuEnum;
import com.yxuo.view.cli.gerenciar.professores.GerenciarProfessoresCliView;
import com.yxuo.view.cli.gerenciar.provas.GerenciarProvasCliView;

public class GerenciarCliView {

    private Boolean exitView = false;
    private String message = "";

    public void display() throws SQLException {
        clearCli();
        Scanner scanner = new Scanner(System.in);
        try {
            while (exitView == false) {
                System.out.println("Gerenciar dados:");
                System.out.println(GerenciarCliMenuEnum.toString1());

                System.out.println(MessageCte.ESCOLHA_OPCAO);
                String choice = scanner.nextLine();

                GerenciarCliMenuEnum selectedOption = GerenciarCliMenuEnum.getEnum(choice);
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

    public void performAction(GerenciarCliMenuEnum selectedOption) throws SQLException {
        clearCli();
        switch (selectedOption) {

            case PROFESSORES: {
                GerenciarProfessoresCliView gerenciarProfessoresView = new GerenciarProfessoresCliView();
                gerenciarProfessoresView.display();
                break;
            }
            case PROVAS: {
                GerenciarProvasCliView gerenciarProvasView = new GerenciarProvasCliView();
                gerenciarProvasView.display();
                break;
            }
            case MENU: {
                exitView = true;
                break;
            }
            default: {
                break;
            }
        }
    }

}
