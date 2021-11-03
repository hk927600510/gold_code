package com.pluto.bean;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class DayKData extends BasicKData {

    // 昨日收盘价	精度：小数点后4位；单位：人民币元
    private String preClose;
    // 交易状态	1：正常交易 0：停牌
    private String tradeStatus;
    // 是否ST	1是，0否
    private String isST;

    public String getPreClose() {
        return preClose;
    }

    public void setPreClose(String preClose) {
        this.preClose = preClose;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getIsST() {
        return isST;
    }

    public void setIsST(String isST) {
        this.isST = isST;
    }
}
