package com.yxuo.view.cli.enums;

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

    private String value;

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
            repr += enumValue.getValue().toString() + ". " + enumValue.name().replace("_", " ") + "\n";
        }
        return repr;
    }

}
