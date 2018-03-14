package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

/**
 * Created by cwd on 5/9/2017.
 */

public class BcData implements Serializable {
    //波次
    private String bc;
    //仓位
    private String lgpla;
    //物料号
    private String matnr;
    //全部数量
    private int lgmng;
    //已完成数量
    private int  allLgmng;

    public String getLgpla() {
        return lgpla;
    }

    public void setLgpla(String lgpla) {
        this.lgpla = lgpla;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public int getLgmng() {
        return lgmng;
    }

    public void setLgmng(int lgmng) {
        this.lgmng = lgmng;
    }

    public int getAllLgmng() {
        return allLgmng;
    }

    public void setAllLgmng(int allLgmng) {
        this.allLgmng = allLgmng;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    @Override
    public String toString() {
        return "BcData{" +
                "bc='" + bc + '\'' +
                ", lgpla='" + lgpla + '\'' +
                ", matnr='" + matnr + '\'' +
                ", lgmng=" + lgmng +
                ", allLgmng=" + allLgmng +
                '}';
    }
}
