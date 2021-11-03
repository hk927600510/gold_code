package com.pluto.compute.strategy.impl;

import com.pluto.bean.CodeBasic;
import com.pluto.compute.condition.Condition;
import com.pluto.compute.condition.DayKRedCountCondition;
import com.pluto.compute.condition.PctChgLimitCondition;
import com.pluto.compute.condition.WeekKRedCountCondition;
import com.pluto.compute.strategy.Strategy;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class FirstStrategy implements Strategy {

    private Map<String, Collector> collectorMap;

    private List<Condition> conditionList;

    public FirstStrategy(Map<String, Collector> collectorMap) {
        this.collectorMap = collectorMap;
        this.conditionList = new ArrayList<>();
        conditionList.add(new DayKRedCountCondition(collectorMap));
        conditionList.add(new PctChgLimitCondition(collectorMap));
        conditionList.add(new WeekKRedCountCondition(collectorMap));
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
            if (checkCondition(key)) {
                result.add(allCodeBasic.get(key));
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
