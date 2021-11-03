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
 * Created by Kevin.H on 2021/11/3
 */
public class WeekKOpenAndCloseCondition implements Condition {

    private Map<String, Collector> collectorMap;

    public WeekKOpenAndCloseCondition(Map<String, Collector> collectorMap) {
        this.collectorMap = collectorMap;
    }


    @Override
    public String getName() {
        return "最近的一个周k收盘价大于开盘价";
    }

    @Override
    public boolean check(String code) {
        Reader<Map<String, BasicKData>> dayKReader = (Reader<Map<String, BasicKData>>) collectorMap.get("WeekKData").getReader();
        Map<String, BasicKData> dateAndWeekKMap = dayKReader.getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndWeekKMap.keySet()).stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        BasicKData lastedWeekKData = dateAndWeekKMap.get(dateList.get(0));
        return Double.parseDouble(lastedWeekKData.getClose()) > Double.parseDouble(lastedWeekKData.getOpen());
    }
}
