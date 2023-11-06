package com.yxuo.view.cli.enums;

public enum MainCliMenuEnum {
    TDE6("1"),
    SOBRE("2"),
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
            repr += enumValue.getValue() + ". " + enumValue.name() + "\n";
        }
        return repr;
    }

}
