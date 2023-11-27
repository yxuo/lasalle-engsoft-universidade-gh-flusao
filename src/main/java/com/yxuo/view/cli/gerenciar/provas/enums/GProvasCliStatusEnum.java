package com.yxuo.view.cli.gerenciar.provas.enums;

public enum GProvasCliStatusEnum {
    PROVAS("PROVAS"),
    BUSCAR("BUSCAR"),
    EDITAR("EDITAR"),
    PROVA("PROVA"),
    MENU("MENU");

    private String value;

    GProvasCliStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GProvasCliStatusEnum getEnum(String value) {
        for (GProvasCliStatusEnum enumValue : GProvasCliStatusEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (GProvasCliStatusEnum enumValue : GProvasCliStatusEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

}
