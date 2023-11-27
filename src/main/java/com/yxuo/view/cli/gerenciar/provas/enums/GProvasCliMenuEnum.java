package com.yxuo.view.cli.gerenciar.provas.enums;

public enum GProvasCliMenuEnum {
    VER_TODOS("1"),
    BUSCAR("2"),
    EDITAR("3"),
    VOLTAR("z");

    private String value;

    GProvasCliMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GProvasCliMenuEnum getEnum(String value) {
        for (GProvasCliMenuEnum enumValue : GProvasCliMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (GProvasCliMenuEnum enumValue : GProvasCliMenuEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

    public static String toString1(Boolean editar) {
        String repr = "";
        for (GProvasCliMenuEnum enumValue : GProvasCliMenuEnum.values()) {
            if (editar == true || (editar == false && enumValue != EDITAR)) {
                String enumName = enumValue.name().replace("_", " ");
                repr += enumValue.getValue() + ". " + enumName + "\n";
            }
        }
        return repr;
    }

}
