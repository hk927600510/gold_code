package com.pluto.compute.strategy.impl;

import com.pluto.bean.CodeBasic;
import com.pluto.compute.condition.CodeIpoDateCondition;
import com.pluto.compute.condition.Condition;
import com.pluto.compute.condition.DayKPctChgLimitCondition;
import com.pluto.compute.condition.DayKRedCountCondition;
import com.pluto.compute.condition.WeekKOpenAndCloseAvgCondition;
import com.pluto.compute.condition.WeekKOpenAndCloseCondition;
import com.pluto.compute.condition.WeekKRedCountCondition;
import com.pluto.compute.strategy.Strategy;
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
public class FirstStrategy implements Strategy {

    private String bsn_date;

    private Map<String, Collector> collectorMap;

    private Map<String, Collector> beforeCollectorMap;

    private List<Condition> conditionList;

    public FirstStrategy(String bsn_date, Map<String, Collector> beforeCollectorMap, Map<String, Collector> collectorMap) {
        this.bsn_date = bsn_date;
        this.collectorMap = collectorMap;
        this.beforeCollectorMap = beforeCollectorMap;
        this.conditionList = new ArrayList<>();
        String dataBeginDate = CodeHelper.getBsnDateWithInterval(beforeCollectorMap, bsn_date, -15);
        conditionList.add(new CodeIpoDateCondition(collectorMap, bsn_date));
        conditionList.add(new DayKRedCountCondition(collectorMap, dataBeginDate, bsn_date));
        conditionList.add(new DayKPctChgLimitCondition(collectorMap, dataBeginDate, bsn_date));
        conditionList.add(new WeekKRedCountCondition(collectorMap, dataBeginDate, bsn_date));
        conditionList.add(new WeekKOpenAndCloseAvgCondition(collectorMap, dataBeginDate, bsn_date));
        conditionList.add(new WeekKOpenAndCloseCondition(collectorMap, dataBeginDate, bsn_date));
    }

    @Override
    public String getName() {
        return "1号策略";
    }

    @Override
    public List<CodeBasic> match() {
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
