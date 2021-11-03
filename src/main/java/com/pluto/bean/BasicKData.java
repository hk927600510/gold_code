package com.pluto.bean;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class BasicKData {

    // 交易所行情日期	格式：YYYY-MM-DD
    private String date;
    // 证券代码	格式：sh.600000。sh：上海，sz：深圳
    private String code;
    // 开盘价格	精度：小数点后4位；单位：人民币元
    private String open;
    // 最高价	精度：小数点后4位；单位：人民币元
    private String high;
    // 最低价	精度：小数点后4位；单位：人民币元
    private String low;
    // 收盘价	精度：小数点后4位；单位：人民币元
    private String close;
    // 成交数量	单位：股
    private String volume;
    // 成交金额	精度：小数点后4位；单位：人民币元
    private String amount;
    // 复权状态	不复权、前复权、后复权
    private String adjustFlag;
    // 换手率	精度：小数点后6位；单位：%
    private String turn;
    // 涨跌幅（百分比）
    private String pctChg;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(String adjustFlag) {
        this.adjustFlag = adjustFlag;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getPctChg() {
        return pctChg;
    }

    public void setPctChg(String pctChg) {
        this.pctChg = pctChg;
    }
}
