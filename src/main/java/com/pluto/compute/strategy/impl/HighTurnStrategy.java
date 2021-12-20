package com.pluto.compute.strategy.impl;

import com.pluto.bean.CodeBasic;
import com.pluto.compute.condition.CodeIpoDateCondition;
import com.pluto.compute.condition.Condition;
import com.pluto.compute.condition.DayKMaCondition;
import com.pluto.compute.condition.DayKRedCountCondition;
import com.pluto.compute.condition.DayKTurnCondition;
import com.pluto.data.reader.Reader;
import com.pluto.data.reader.ReaderManager;
import com.pluto.helper.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class HighTurnStrategy extends AbstractStrategy {

    private String bsn_date;

    private List<Condition> conditionList;

    public HighTurnStrategy(String bsn_date) {
        this.bsn_date = bsn_date;
        this.conditionList = new ArrayList<>();
        conditionList.add(new CodeIpoDateCondition(bsn_date));
        conditionList.add(new DayKMaCondition(bsn_date, 1, 0));
        conditionList.add(new DayKTurnCondition(bsn_date, 10, 2));
        conditionList.add(new DayKRedCountCondition(bsn_date, 1, 1));
    }

    @Override
    public String getName() {
        return "放量策略";
    }

    @Override
    public List<CodeBasic> strategyMatch() {
        List<CodeBasic> result = new ArrayList<>();
        Reader<Map<String, CodeBasic>> reader = ReaderManager.getCodeBasicReader();
        Map<String, CodeBasic> allCodeBasic = reader.getDataAll();
        int count = 0;
        for (String key : allCodeBasic.keySet()) {
            if (count % 500 == 0) {
                LogUtils.log(getName() + " begin  count=" + count);
            }
            count++;
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

}
