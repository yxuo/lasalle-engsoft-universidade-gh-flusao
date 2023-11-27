package com.yxuo.model.enums;

import com.yxuo.model.ProvaAC;
import com.yxuo.view.cli.gerenciar.provas.enums.GProvaCliMenuEnum;

public enum ProvaStatusEnum {
    DISPONIVEL("DISPONIVEL"),
    APLICADA("APLICADA"),
    CORRIGIDA("CORRIGIDA"),
    REVISADA("REVISADA"),
    FINALIZADA("FINALIZADA");

    private String value;

    ProvaStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProvaStatusEnum getEnum(String value) {
        for (ProvaStatusEnum enumValue : ProvaStatusEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static ProvaStatusEnum getEnum(String value, ProvaAC professor) {
        ProvaStatusEnum situacaoProva = ProvaStatusEnum.getEnum(professor.getSituacao());
        GProvaCliMenuEnum situacao = GProvaCliMenuEnum.getEnum(value);
        if (situacao == null) {
            return null;
        }
        if (situacaoProva != null) {
            switch (situacaoProva) {
                case DISPONIVEL: {
                    switch (situacao) {
                        case APLICAR: {
                            return ProvaStatusEnum.APLICADA;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case APLICADA: {
                    switch (situacao) {
                        case CORRIGIR: {
                            return ProvaStatusEnum.CORRIGIDA;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case CORRIGIDA: {
                    switch (situacao) {
                        case REVISAR: {
                            return ProvaStatusEnum.REVISADA;
                        }
                        case FINALIZAR: {
                            return ProvaStatusEnum.FINALIZADA;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case REVISADA: {
                    switch (situacao) {
                        case FINALIZAR: {
                            return ProvaStatusEnum.FINALIZADA;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case FINALIZADA: {
                    return null;
                }
                default: {
                    switch (situacao) {
                        case CADASTRAR: {
                            return ProvaStatusEnum.DISPONIVEL;
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
                    return ProvaStatusEnum.DISPONIVEL;
                }
                default: {
                    return null;
                }
            }
        }
    }

    public static String toString1() {
        String repr = "";
        for (ProvaStatusEnum enumValue : ProvaStatusEnum.values()) {
            String enumName = enumValue.name();// .replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }
}
