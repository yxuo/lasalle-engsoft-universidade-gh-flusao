package com.yxuo.view.cli.gerenciar.professores.enums;

import java.util.ArrayList;
import java.util.List;

import com.yxuo.constant.MessageCte;
import com.yxuo.model.ProfessorAC;
import com.yxuo.model.enums.ProfessorStatusEnum;
import com.yxuo.util.CLI;

/**
 * GerenciarProfessorCliMenuEnum
 */
public enum GProfessorCliMenuEnum {
    CADASTRAR("1"),
    ALOCAR("2"),
    LIBERAR("3"),
    SUSPENDER("4"),
    FINALIZAR("5"),
    CANCELAR("z");

    private String value;

    GProfessorCliMenuEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GProfessorCliMenuEnum getEnum(String value) {
        for (GProfessorCliMenuEnum enumValue : GProfessorCliMenuEnum.values()) {
            if (enumValue.getValue().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }

    public static GProfessorCliMenuEnum getEnum(String value, ProfessorAC professor) {
        ProfessorStatusEnum situacaoProf = ProfessorStatusEnum.getEnum(professor.getSituacao());
        GProfessorCliMenuEnum situacao = GProfessorCliMenuEnum.getEnum(value);
        if (situacao == null) {
            return null;
        } else if (situacao == CANCELAR){
            return situacao;
        }
        if (situacaoProf != null) {
            switch (situacaoProf) {
                case CADASTRADO: {
                    switch (situacao) {
                        case ALOCAR: {
                            return situacao;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case ALOCADO: {
                    switch (situacao) {
                        case LIBERAR: {
                            return situacao;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case DISPONIVEL: {
                    switch (situacao) {
                        case SUSPENDER: {
                            return situacao;
                        }
                        default: {
                            return null;
                        }
                    }
                }
                case SUSPENSO: {
                    switch (situacao) {
                        case LIBERAR:
                        case ALOCAR:
                        case FINALIZAR: {
                            return situacao;
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
        for (GProfessorCliMenuEnum enumValue : GProfessorCliMenuEnum.values()) {
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        return repr;
    }

    public static String toString1(ProfessorAC professor) {
        String repr = "";
        ProfessorStatusEnum situacao = ProfessorStatusEnum.getEnum(professor.getSituacao());
        if (situacao != null) {
            switch (situacao) {
                case CADASTRADO: {
                    GProfessorCliMenuEnum enumValue = GProfessorCliMenuEnum.ALOCAR;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
                case ALOCADO: {
                    GProfessorCliMenuEnum enumValue = GProfessorCliMenuEnum.LIBERAR;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
                case DISPONIVEL: {
                    GProfessorCliMenuEnum enumValue = GProfessorCliMenuEnum.SUSPENDER;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
                case SUSPENSO: {
                    List<GProfessorCliMenuEnum> enumValues = new ArrayList<>();
                    enumValues.add(LIBERAR);
                    enumValues.add(ALOCAR);
                    enumValues.add(FINALIZAR);
                    for (GProfessorCliMenuEnum enumValue : enumValues) {
                        String enumName = enumValue.name().replace("_", " ");
                        repr += enumValue.getValue() + ". " + enumName + "\n";
                    }
                    break;
                }
                case FINALIZADO: {
                    repr += CLI.getItalic(MessageCte.NADA_A_FAZER + "\n");
                    break;
                }
                default: {
                    GProfessorCliMenuEnum enumValue = GProfessorCliMenuEnum.CADASTRAR;
                    String enumName = enumValue.name().replace("_", " ");
                    repr += enumValue.getValue() + ". " + enumName + "\n";
                    break;
                }
            }
        } else {
            GProfessorCliMenuEnum enumValue = GProfessorCliMenuEnum.CADASTRAR;
            String enumName = enumValue.name().replace("_", " ");
            repr += enumValue.getValue() + ". " + enumName + "\n";
        }
        GProfessorCliMenuEnum enumValue = GProfessorCliMenuEnum.CANCELAR;
        String enumName = enumValue.name().replace("_", " ");
        repr += enumValue.getValue() + ". " + enumName + "\n";
        return repr;
    }

}
