package com.dusto.mobile.biz.warehouse.vo.res;

import java.io.Serializable;

/**
 * Created by cwd on 5/9/2017.
 */

public class DtData implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3397951529919031829L;
	//客户编号
    private String werks;
    //客户流水号
    private String zkhls;
    //物料号
    private String matnr;
    //数量
    private int menge;

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getZkhls() {
        return zkhls;
    }

    public void setZkhls(String zkhls) {
        this.zkhls = zkhls;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    @Override
    public String toString() {
        return "DtData{" +
                "werks='" + werks + '\'' +
                ", zkhls='" + zkhls + '\'' +
                ", matnr='" + matnr + '\'' +
                ", menge=" + menge +
                '}';
    }
}
