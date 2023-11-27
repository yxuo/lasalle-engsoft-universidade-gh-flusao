package com.yxuo.view.cli.gerenciar.professores.enums;

public enum GProfessoresCliMenuEnum {
    VER_TODOS("1"),
    BUSCAR("2"),
    EDITAR("3"),
    VOLTAR("z");

    private String value;

    GProfessoresCliMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GProfessoresCliMenuEnum getEnum(String value) {
        for (GProfessoresCliMenuEnum enumValue : GProfessoresCliMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (GProfessoresCliMenuEnum enumValue : GProfessoresCliMenuEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

    public static String toString1(Boolean editar) {
        String repr = "";
        for (GProfessoresCliMenuEnum enumValue : GProfessoresCliMenuEnum.values()) {
            if (editar == true || (editar == false && enumValue != EDITAR)) {
                String enumName = enumValue.name().replace("_", " ");
                repr += enumValue.getValue() + ". " + enumName + "\n";
            }
        }
        return repr;
    }

}
