package com.pluto.compute.condition;

import com.pluto.bean.CodeBasic;
import com.pluto.data.reader.ReaderManager;
import com.pluto.helper.CodeHelper;

import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/4
 */
public class CodeIpoDateCondition extends AbstractCondition {

    private String bsnDate;

    private String conditionDate;

    public CodeIpoDateCondition(String bsnDate, int yearInterval, int monthInterval, int dayInterval) {
        this.bsnDate = bsnDate;
        this.conditionDate = CodeHelper.getCommonDateWithInterval(bsnDate, yearInterval, monthInterval, dayInterval);
    }


    @Override
    public String getName() {
        return "上市时间在" + conditionDate + "之前";
    }

    @Override
    public boolean check(String code) {
        Map<String, CodeBasic> codeBasicMap = ReaderManager.getCodeBasicReader().getDataByCondition(code);
        CodeBasic codeBasic = codeBasicMap.get(code);
        // 没有上市时间就相当于满足条件吧
        if (codeBasic.getIpoDate() == null) {
            return true;
        }
        return codeBasic.getIpoDate().compareTo(conditionDate) <= 0;
    }

}
