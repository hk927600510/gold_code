package com.pluto.compute.condition;

import com.pluto.bean.CodeBasic;
import com.pluto.data.reader.ReaderManager;
import com.pluto.helper.CodeHelper;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/4
 */
public class CodeIpoDateCondition extends AbstractCondition {

    private String bsnDate;

    public CodeIpoDateCondition(String bsnDate) {
        this.bsnDate = bsnDate;
    }


    @Override
    public String getName() {
        return "上市时间在1.5年以上";
    }

    @Override
    public boolean check(String code) {
        Map<String, CodeBasic> codeBasicMap = ReaderManager.getCodeBasicReader().getDataByCondition(code);
        CodeBasic codeBasic = codeBasicMap.get(code);
        // 没有上市时间就相当于满足条件吧
        if (codeBasic.getIpoDate() == null) {
            return true;
        }
        return codeBasic.getIpoDate().compareTo(getConditionDate()) <= 0;
    }

    private String getConditionDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(CodeHelper.transToDate(bsnDate));
        calendar.add(Calendar.MONTH, -6);
        calendar.add(Calendar.YEAR, -1);
        return CodeHelper.formatDate(calendar.getTime());
    }
}
