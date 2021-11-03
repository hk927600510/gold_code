package com.pluto.compute.condition;

import com.pluto.bean.BasicKData;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class WeekKRedCountCondition implements Condition {

    private Map<String, Collector> collectorMap;

    public WeekKRedCountCondition(Map<String, Collector> collectorMap) {
        this.collectorMap = collectorMap;
    }

    @Override
    public String getName() {
        return "3个周k开盘价趋势向上";
    }

    @Override
    public boolean check(String code) {
        Reader<Map<String, BasicKData>> dayKReader = (Reader<Map<String, BasicKData>>) collectorMap.get("WeekKData").getReader();
        Map<String, BasicKData> dateAndWeekKMap = dayKReader.getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndWeekKMap.keySet()).stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        int compareCount = Math.min(dateList.size(), 2);
        for (int i = 0; i < compareCount; i++) {
            String firstOpen = dateAndWeekKMap.get(dateList.get(i)).getOpen();
            String secondOpen = dateAndWeekKMap.get(dateList.get(i + 1)).getOpen();
            if (Double.parseDouble(firstOpen) > Double.parseDouble(secondOpen)) {
                return false;
            }
        }
        return true;
    }
}
