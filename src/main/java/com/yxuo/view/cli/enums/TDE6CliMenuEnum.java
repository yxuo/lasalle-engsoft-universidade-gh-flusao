package com.yxuo.view.cli.enums;

import java.util.HashMap;
import java.util.Map;

import com.yxuo.util.CLI;

public enum TDE6CliMenuEnum {
    QUESTÃO_1("1"),
    QUESTÃO_2("2"),
    QUESTÃO_3("3"),
    QUESTÃO_4("4"),
    QUESTÃO_5("5"),
    QUESTÃO_6("6"),
    QUESTÃO_7("7"),
    QUESTÃO_8("8"),
    QUESTÃO_9("9"),
    MENU("z");

    private String value = "";
    private static final Map<String, String> descriptions = new HashMap<String, String>();

    static {
        descriptions.put("1", "Selecione matrícula e nome do aluno, bem como, código,"
                + " turno, dia, hora início e hora fim das turmas que eles estão matriculados");
        descriptions.put("2", "Selecione a matrícula e nome do professor, bem como, "
                + "o código e nome da disciplina");
        descriptions.put("3", "Selecione  a matrícula e nome dos alunos que não estão "
                + "matriculados em turmas.");
        descriptions.put("4", "Selecione id da turma, nome da disciplina, código da prova, "
                + "situação da prova e média das notas.");
        descriptions.put("5", "Selecione matrícula e nome do professor,id da turma, nome da disciplina"
                + ", código da prova, situação da prova, média das notas, maior nota e menor nota.");
        descriptions.put("6", "Selecionar os nome dos professores e a quantidade de turmas "
                + "que ministram.");
        descriptions.put("7", "Selecionar o nome do professor e o nome da disciplina ministrada.");
        descriptions.put("8", "Selecionar os nomes das disciplinas sem turmas.");
        descriptions.put("9", "Selecionar as provas e suas disciplinas que não foram aplicadas.");
    }

    TDE6CliMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TDE6CliMenuEnum getEnum(String value) {
        for (TDE6CliMenuEnum enumValue : TDE6CliMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (TDE6CliMenuEnum enumValue : TDE6CliMenuEnum.values()) {
            String message = descriptions.get(enumValue.getValue());
            if (message == null) {
                message = "";
            } else {
                message = CLI.ANSI_ITALIC + CLI.ANSI_BLUE + "  " + message + CLI.ANSI_RESET;
            }
            repr += enumValue.getValue().toString() + ". " + enumValue.name().replace("_", " ") + message + "\n";
        }
        return repr;
    }

}
