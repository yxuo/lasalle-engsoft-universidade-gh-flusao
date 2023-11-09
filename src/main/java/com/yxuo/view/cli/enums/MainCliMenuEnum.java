package com.yxuo.view.cli.enums;

public enum MainCliMenuEnum {
    TDE_6("1"),
    VER_BANCO_DE_DADOS("2"),
    SOBRE("3"),
    SAIR("z");

    private String value;

    MainCliMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MainCliMenuEnum getEnum(String value) {
        for (MainCliMenuEnum enumValue : MainCliMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (MainCliMenuEnum enumValue : MainCliMenuEnum.values()) {
            String enumName = enumValue.name();//.replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

}
