package com.pluto.compute.condition;

import com.pluto.bean.CodeBasic;
import com.pluto.data.collector.Collector;
import com.pluto.helper.CodeHelper;
import com.pluto.helper.LogUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/4
 */
public class CodeIpoDateCondition extends AbstractCondition {

    private Map<String, Collector> collectorMap;

    private String bsnDate;

    public CodeIpoDateCondition(Map<String, Collector> collectorMap, String bsnDate) {
        this.collectorMap = collectorMap;
        this.bsnDate = bsnDate;
        LogUtils.log(getClass().getSimpleName() + ": condition=" + getName() + " bsnDate=" + this.bsnDate);
    }

    @Override
    Map<String, Collector> getCollectorMap() {
        return collectorMap;
    }

    @Override
    public String getName() {
        return "上市时间在1.5年以上";
    }

    @Override
    public boolean check(String code) {
        Map<String, CodeBasic> codeBasicMap = getCodeBasicReader().getDataByCondition(code);
        CodeBasic codeBasic = codeBasicMap.get(code);
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
