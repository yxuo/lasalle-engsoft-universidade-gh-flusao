package com.yxuo.view.cli.gerenciar.provas.enums;

public enum GProvasCliBuscarMenuEnum {
    CODIGO("codigo", "<nome>"),
    ID("id", "<número>"),
    CANCELAR("z", "");

    private String value;
    private String description;

    GProvasCliBuscarMenuEnum(String command, String description) {
        this.value = command;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static GProvasCliBuscarMenuEnum getEnum(String value) {
        for (GProvasCliBuscarMenuEnum enumValue : GProvasCliBuscarMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (GProvasCliBuscarMenuEnum enumValue : GProvasCliBuscarMenuEnum.values()) {
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
