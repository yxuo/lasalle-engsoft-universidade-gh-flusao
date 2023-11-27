package com.yxuo.model.enums;

public enum ProfessorColumnsEnum {
    ID_PROF("ID_PROF"),
    MAT_PROF("MAT_PROF"),
    NOME("NOME"),
    SITUACAO("SITUACAO");

    private String value;

    ProfessorColumnsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProfessorStatusEnum getEnum(String value) {
        for (ProfessorStatusEnum enumValue : ProfessorStatusEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static String toString1() {
        String repr = "";
        for (ProfessorStatusEnum enumValue : ProfessorStatusEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }
}
