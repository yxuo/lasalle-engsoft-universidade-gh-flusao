package com.yxuo.view.cli.gerenciar.professores.enums;

public enum GProfessoresCliBuscarMenuEnum {
    NOME("nome", "<nome>"),
    ID("id", "<número>"),
    MATIRCULA("matricula", "<código de matrícula>"),
    CANCELAR("z", "");

    private String value;
    private String description;

    GProfessoresCliBuscarMenuEnum(String command, String description) {
        this.value = command;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static GProfessoresCliBuscarMenuEnum getEnum(String value) {
        for (GProfessoresCliBuscarMenuEnum enumValue : GProfessoresCliBuscarMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (GProfessoresCliBuscarMenuEnum enumValue : GProfessoresCliBuscarMenuEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            String description = enumValue.getDescription();
            if (description.length() > 0){
                description = " " + description;
            }
            repr += "[" + enumValue.getValue() + description + "] " + enumName + "\n";
        }
        return repr;
    }

}
