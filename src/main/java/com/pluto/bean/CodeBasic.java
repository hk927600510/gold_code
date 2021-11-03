package com.pluto.bean;

import com.pluto.constant.CodeConstant;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class CodeBasic {

    // 证券代码
    private String code;

    // 交易状态(1：正常交易 0：停牌)
    private String tradeStatus;

    // 证券名称
    private String codeName;

    // 上市日期
    private String ipoDate;

    // 退市日期
    private String outDate;

    // 证券类型，其中1：股票，2：指数,3：其它
    private String type;

    // 上市状态，其中1：上市，0：退市
    private String status;

    // 行业更新日期
    private String industryUpdateDate;

    // 行业名称
    private String industry;

    // 行业类别
    private String industryClassification;

    @Override
    public String toString() {
        return code + CodeConstant.SEPARATOR +
                codeName + CodeConstant.SEPARATOR +
                industry + CodeConstant.SEPARATOR;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getIpoDate() {
        return ipoDate;
    }

    public void setIpoDate(String ipoDate) {
        this.ipoDate = ipoDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndustryUpdateDate() {
        return industryUpdateDate;
    }

    public void setIndustryUpdateDate(String industryUpdateDate) {
        this.industryUpdateDate = industryUpdateDate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryClassification() {
        return industryClassification;
    }

    public void setIndustryClassification(String industryClassification) {
        this.industryClassification = industryClassification;
    }
}
