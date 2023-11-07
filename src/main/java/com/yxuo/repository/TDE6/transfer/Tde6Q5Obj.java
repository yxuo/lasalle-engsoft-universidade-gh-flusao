package com.yxuo.repository.TDE6.transfer;

import com.yxuo.model.RealizaAC;

public class Tde6Q5Obj {
    private RealizaAC realiza = new RealizaAC();
    private Integer realizaNotaMax = 0;
    private Integer realizaNotaMin = 0;
    private Integer realizaNotaAvg = 0;

    public Tde6Q5Obj() {
    }

    public Tde6Q5Obj(RealizaAC realiza, Integer realizaNotaMax, Integer realizaNotaMin, Integer realizaNotaAvg) {
        this.realiza = realiza;
        this.realizaNotaMax = realizaNotaMax;
        this.realizaNotaMin = realizaNotaMin;
        this.realizaNotaAvg = realizaNotaAvg;
    }

    public RealizaAC getRealiza() {
        return this.realiza;
    }

    public void setRealiza(RealizaAC realiza) {
        this.realiza = realiza;
    }

    public Integer getRealizaNotaMax() {
        return this.realizaNotaMax;
    }

    public void setRealizaNotaMax(Integer realizaNotaMax) {
        this.realizaNotaMax = realizaNotaMax;
    }

    public Integer getRealizaNotaMin() {
        return this.realizaNotaMin;
    }

    public void setRealizaNotaMin(Integer realizaNotaMin) {
        this.realizaNotaMin = realizaNotaMin;
    }

    public Integer getRealizaNotaAvg() {
        return this.realizaNotaAvg;
    }

    public void setRealizaNotaAvg(Integer realizaNotaAvg) {
        this.realizaNotaAvg = realizaNotaAvg;
    }


    @Override
    public String toString() {
        return "{" +
            " realiza='" + getRealiza() + "'" +
            ", realizaNotaMax='" + getRealizaNotaMax() + "'" +
            ", realizaNotaMin='" + getRealizaNotaMin() + "'" +
            ", realizaNotaAvg='" + getRealizaNotaAvg() + "'" +
            "}";
    }

}
