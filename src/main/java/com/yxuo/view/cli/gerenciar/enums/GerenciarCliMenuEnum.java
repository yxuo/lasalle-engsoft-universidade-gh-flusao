package com.yxuo.view.cli.gerenciar.enums;

public enum GerenciarCliMenuEnum {
    PROFESSORES("1"),
    PROVAS("2"),
    MENU("z");

    private String value;

    GerenciarCliMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GerenciarCliMenuEnum getEnum(String value) {
        for (GerenciarCliMenuEnum enumValue : GerenciarCliMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (GerenciarCliMenuEnum enumValue : GerenciarCliMenuEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

}
