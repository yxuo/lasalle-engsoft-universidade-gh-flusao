package com.yxuo.view.cli.gerenciar.professores.enums;

public enum GProfessoresCliStatusEnum {
    PROFESSORES("PROFESSORES"),
    BUSCAR("BUSCAR"),
    EDITAR("EDITAR"),
    PROFESSOR("PROFESSOR"),
    MENU("MENU");

    private String value;

    GProfessoresCliStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GProfessoresCliStatusEnum getEnum(String value) {
        for (GProfessoresCliStatusEnum enumValue : GProfessoresCliStatusEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (GProfessoresCliStatusEnum enumValue : GProfessoresCliStatusEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

}
