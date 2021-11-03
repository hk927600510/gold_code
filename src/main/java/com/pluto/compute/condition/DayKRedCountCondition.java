package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;

import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKRedCountCondition implements Condition {

    private Map<String, Collector> collectorMap;

    public DayKRedCountCondition(Map<String, Collector> collectorMap) {
        this.collectorMap = collectorMap;
    }

    @Override
    public String getName() {
        return "红k数量过半";
    }

    @Override
    public boolean check(String code) {
        Reader<Map<String, DayKData>> dayKReader = (Reader<Map<String, DayKData>>) collectorMap.get("DayKData").getReader();
        Map<String, DayKData> dateAndDayKMap = dayKReader.getDataByCondition(code);
        long count = dateAndDayKMap.values().stream().filter(this::filterRedDayKData).count();
        return count > dateAndDayKMap.size() / 2;
    }

    private boolean filterRedDayKData(DayKData data) {
        if (data == null) {
            return false;
        }
        if ("0" .equals(data.getTradeStatus())) {
            return true;
        }
        return Double.parseDouble(data.getPctChg()) >= 0;
    }
}
