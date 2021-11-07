package com.pluto.compute.strategy.impl;

import com.pluto.bean.CodeBasic;
import com.pluto.compute.condition.CodeIpoDateCondition;
import com.pluto.compute.condition.Condition;
import com.pluto.compute.condition.DayKNewHighRecentCondition;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;
import com.pluto.helper.CodeHelper;
import com.pluto.helper.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class NewHighStrategy extends AbstractStrategy {

    private String bsn_date;

    private Map<String, Collector> collectorMap;

    private Map<String, Collector> beforeCollectorMap;

    private List<Condition> conditionList;

    public NewHighStrategy(String bsn_date, Map<String, Collector> beforeCollectorMap, Map<String, Collector> collectorMap) {
        this.bsn_date = bsn_date;
        this.collectorMap = collectorMap;
        this.beforeCollectorMap = beforeCollectorMap;
        this.conditionList = new ArrayList<>();
        conditionList.add(new CodeIpoDateCondition(collectorMap, bsn_date));
        String dateBefore60 = CodeHelper.getBsnDateWithInterval(beforeCollectorMap, bsn_date, -60);
        conditionList.add(new DayKNewHighRecentCondition(collectorMap, dateBefore60, bsn_date));
    }

    @Override
    public String getName() {
        return "新高策略";
    }

    @Override
    public List<CodeBasic> strategyMatch() {
        List<CodeBasic> result = new ArrayList<>();
        Reader<Map<String, CodeBasic>> reader = (Reader<Map<String, CodeBasic>>) collectorMap.get("CodeBasic").getReader();
        Map<String, CodeBasic> allCodeBasic = reader.getDataAll();
        for (String key : allCodeBasic.keySet()) {
            try {
                if (checkCondition(key)) {
                    result.add(allCodeBasic.get(key));
                }
            } catch (Exception e) {
                LogUtils.log("execute error , code=" + key);
                e.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public List<Condition> getConditions() {
        return conditionList;
    }

    private boolean checkCondition(String key) {
        for (Condition condition : getConditions()) {
            if (!condition.check(key)) {
                return false;
            }
        }
        return true;
    }
}
