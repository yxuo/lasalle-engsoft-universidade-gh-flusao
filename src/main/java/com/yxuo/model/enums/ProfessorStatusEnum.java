package com.yxuo.model.enums;

import com.yxuo.model.ProfessorAC;
import com.yxuo.view.cli.gerenciar.professores.enums.GProfessorCliMenuEnum;

public enum ProfessorStatusEnum {
    CADASTRADO("CADASTRADO"),
    ALOCADO("ALOCADO"),
    DISPONIVEL("DISPONIVEL"),
    SUSPENSO("SUSPENSO"),
    FINALIZADO("FINALIZADO");

    private String value;

    ProfessorStatusEnum(String value) {
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

    public static ProfessorStatusEnum getEnum(String value, ProfessorAC professor) {
        ProfessorStatusEnum situacaoProf = ProfessorStatusEnum.getEnum(professor.getSituacao());
        GProfessorCliMenuEnum situacao = GProfessorCliMenuEnum.getEnum(value);
        if (situacao == null) {
            return null;
        }
        if (situacaoProf != null) {
            switch (situacaoProf) {
                case CADASTRADO: {
                    switch (situacao) {
                        case ALOCAR: {
                            return ProfessorStatusEnum.ALOCADO;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case ALOCADO: {
                    switch (situacao) {
                        case LIBERAR: {
                            return ProfessorStatusEnum.DISPONIVEL;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case DISPONIVEL: {
                    switch (situacao) {
                        case SUSPENDER: {
                            return ProfessorStatusEnum.SUSPENSO;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case SUSPENSO: {
                    switch (situacao) {
                        case LIBERAR: {
                            return ProfessorStatusEnum.DISPONIVEL;
                        }
                        case ALOCAR: {
                            return ProfessorStatusEnum.ALOCADO;
                        }
                        case FINALIZAR: {
                            return ProfessorStatusEnum.FINALIZADO;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case FINALIZADO: {
                    return null;
                }
                default: {
                    switch (situacao) {
                        case CADASTRAR: {
                            return ProfessorStatusEnum.CADASTRADO;
                        }
                        default: {
                            return null;
                        }
                    }
                }
            }
        } else {
            switch (situacao) {
                case CADASTRAR: {
                    return ProfessorStatusEnum.CADASTRADO;
                }
                default: {
                    return null;
                }
            }
        }
    }

    public static String toString1() {
        String repr = "";
        for (ProfessorStatusEnum enumValue : ProfessorStatusEnum.values()) {
            String enumName = enumValue.name();// .replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }
}
