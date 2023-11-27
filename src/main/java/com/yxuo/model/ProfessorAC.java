package com.yxuo.model;

import com.yxuo.model.enums.ProfessorColumnsEnum;
import com.yxuo.model.enums.ProfessorStatusEnum;

public class ProfessorAC extends BaseEntity {
    private int idProf;
    private String matProf;
    private String nome;
    private String situacao = null;

    private static final String TABLE_NAME = "ProfessorAC";
    private static final String ID_PROF_COLUMN = "idProf";
    private static final String MAT_PROF_COLUMN = "matProf";
    private static final String NOME_COLUMN = "nome";
    private static final String SITUACAO_COLUMN = "situacao";

    public ProfessorAC() {
        this.idProf = -1;
        this.matProf = "";
        this.nome = "";
    }

    public ProfessorAC(ProfessorAC professor) {
        this.idProf = professor.getIdProf();
        this.matProf = professor.getMatProf();
        this.nome = professor.getNome();
        this.situacao = professor.getSituacao();
    }

    public ProfessorAC(int idProf) {
        this.idProf = idProf;
        this.matProf = "";
        this.nome = "";
    }

    public ProfessorAC(int idProf, String matProf, String nome) {
        this.idProf = idProf;
        this.matProf = matProf;
        this.nome = nome;
    }

    public ProfessorAC(int idProf, String matProf, String nome, String situacao) {
        this.idProf = idProf;
        this.matProf = matProf;
        this.nome = nome;
        this.situacao = situacao;
    }

    // Names

    @Override
    public String getTableName() {
        return handleNamingStrategy(TABLE_NAME);
    }

    @Override
    public String getIdColumn() {
        return handleNamingStrategy(ID_PROF_COLUMN);
    }

    public String getMatProfColumn() {
        return handleNamingStrategy(MAT_PROF_COLUMN);
    }

    public String getNomeColumn() {
        return handleNamingStrategy(NOME_COLUMN);
    }

    public String getSituacaoColumn() {
        return handleNamingStrategy(SITUACAO_COLUMN);
    }

    public String getColumnName(ProfessorColumnsEnum columnEnum) {
        switch (columnEnum) {
            case ID_PROF:
                return getIdColumn();
            case MAT_PROF:
                return getMatProfColumn();
            case NOME:
                return getNomeColumn();
            case SITUACAO:
                return getSituacaoColumn();
            default:
                return null;
        }
    }

    // Fields

    @Override
    public Integer getId() {
        return getIdProf();
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public String getMatProf() {
        return matProf;
    }

    public void setMatProf(String matProf) {
        this.matProf = matProf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setSituacao(ProfessorStatusEnum situacao) {
        this.situacao = situacao.getValue();
    }

    public String getColumnValue(ProfessorColumnsEnum columnEnum) {
        switch (columnEnum) {
            case ID_PROF:
                return getId().toString();
            case MAT_PROF:
                return getMatProf().toString();
            case NOME:
                return getNome().toString();
            case SITUACAO:
                return getSituacao().toString();
            default:
                return null;
        }
    }

    public String getColumnType(ProfessorColumnsEnum columnEnum) {
        switch (columnEnum) {
            case ID_PROF:
                return "int";
            case MAT_PROF:
                return "String";
            case NOME:
                return "String";
            case SITUACAO:
                return "String";
            default:
                return null;
        }
    }

    // Actions

    public void cadastrar() {
        this.situacao = ProfessorStatusEnum.DISPONIVEL.toString();
    }

    public void alocar() {
        this.situacao = ProfessorStatusEnum.ALOCADO.toString();
    }

    public void desalocar() {
        this.situacao = ProfessorStatusEnum.DISPONIVEL.toString();
    }

    public void suspender() {
        this.situacao = ProfessorStatusEnum.SUSPENSO.toString();
    }

    public void finalizar() {
        this.situacao = ProfessorStatusEnum.FINALIZADO.toString();
    }

    // Etc

    @Override
    public String toString() {
        return "{ " +
            " idProf=" + getIdProf() +
            ", matProf='" + getMatProf() + "'" +
            ", nome='" + getNome() + "'" +
            ", situacao='" + getSituacao() + "'" +
            " }";
    }
    
}
