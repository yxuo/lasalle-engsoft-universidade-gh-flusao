package com.yxuo.view.cli.gerenciar.provas.enums;

import java.util.ArrayList;
import java.util.List;

import com.yxuo.constant.MessageCte;
import com.yxuo.model.ProvaAC;
import com.yxuo.model.enums.ProvaStatusEnum;
import com.yxuo.util.CLI;

/**
 * GerenciarProvaCliMenuEnum
 */
public enum GProvaCliMenuEnum {
    CADASTRAR("1"),
    APLICAR("2"),
    CORRIGIR("3"),
    REVISAR("5"),
    FINALIZAR("4"),
    CANCELAR("z");

    private String value;

    GProvaCliMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GProvaCliMenuEnum getEnum(String value) {
        for (GProvaCliMenuEnum enumValue : GProvaCliMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static GProvaCliMenuEnum getEnum(String value, ProvaAC prova) {
        ProvaStatusEnum situacaoProva = ProvaStatusEnum.getEnum(prova.getSituacao());
        GProvaCliMenuEnum situacao = GProvaCliMenuEnum.getEnum(value);
        if (situacao == null) {
            return null;
        } else if (situacao == CANCELAR) {
            return situacao;
        }
        if (situacaoProva != null) {
            switch (situacaoProva) {
                case DISPONIVEL: {
                    switch (situacao) {
                        case APLICAR: {
                            return situacao;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case APLICADA: {
                    switch (situacao) {
                        case CORRIGIR: {
                            return situacao;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case CORRIGIDA: {
                    switch (situacao) {
                        case REVISAR:
                        case FINALIZAR: {
                            return situacao;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case REVISADA: {
                    switch (situacao) {
                        case FINALIZAR: {
                            return situacao;
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
                            return situacao;
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
                    return situacao;
                }
                default: {
                    return null;
                }
            }
        }
    }

    public static String toString1() {
        String repr = "";
        for (GProvaCliMenuEnum enumValue : GProvaCliMenuEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

    public static String toString1(ProvaAC prova) {
        String repr = "";
        ProvaStatusEnum situacao = ProvaStatusEnum.getEnum(prova.getSituacao());
        if (situacao != null) {
            switch (situacao) {
                case DISPONIVEL: {
                    GProvaCliMenuEnum enumValue = GProvaCliMenuEnum.APLICAR;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
                case APLICADA: {
                    GProvaCliMenuEnum enumValue = GProvaCliMenuEnum.CORRIGIR;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
                case CORRIGIDA: {
                    List<GProvaCliMenuEnum> enumValues = new ArrayList<>();
                    enumValues.add(REVISAR);
                    enumValues.add(FINALIZAR);
                    for (GProvaCliMenuEnum enumValue : enumValues) {
                        String enumName = enumValue.name().replace("_", " ");
                        repr += enumValue.getValue() + ". " + enumName + "\n";
                    }
                    break;
                }
                case REVISADA: {
                    GProvaCliMenuEnum enumValue = GProvaCliMenuEnum.FINALIZAR;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
                case FINALIZADA: {
                    repr += CLI.getItalic(MessageCte.NADA_A_FAZER + "\n");
                    break;
                }
                default: {
                    GProvaCliMenuEnum enumValue = GProvaCliMenuEnum.CADASTRAR;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
            }
        } else {
            GProvaCliMenuEnum enumValue = GProvaCliMenuEnum.CADASTRAR;
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        GProvaCliMenuEnum enumValue = GProvaCliMenuEnum.CANCELAR;
        String enumName = enumValue.name().replace("_", " ");
        repr += enumValue.getValue() + ". " + enumName + "\n";
        return repr;
    }

}
