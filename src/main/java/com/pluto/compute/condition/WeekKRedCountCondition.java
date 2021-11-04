package com.pluto.compute.condition;

import com.pluto.bean.BasicKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;
import com.pluto.helper.LogUtils;

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
public class WeekKRedCountCondition extends AbstractCondition {

    private Map<String, Collector> collectorMap;

    private String dataBegin;

    private String dataEnd;

    public WeekKRedCountCondition(Map<String, Collector> collectorMap, String dataBegin, String dataEnd) {
        this.collectorMap = collectorMap;
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
        LogUtils.log(getClass().getSimpleName() + ": condition=" + getName() + " dataBegin=" + this.dataBegin + " dataEnd=" + this.dataEnd);
    }

    @Override
    public String getName() {
        return "3个周k开盘价趋势向上";
    }

    @Override
    public boolean check(String code) {
        Map<String, BasicKData> dateAndWeekKMap = getWeekKReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndWeekKMap.keySet()).stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        int compareCount = Math.min(dateList.size() - 1, 2);
        for (int i = 0; i < compareCount; i++) {
            String firstOpen = dateAndWeekKMap.get(dateList.get(i)).getOpen();
            String secondOpen = dateAndWeekKMap.get(dateList.get(i + 1)).getOpen();
            if (Double.parseDouble(firstOpen) < Double.parseDouble(secondOpen)) {
                return false;
            }
        }
        return true;
    }

    @Override
    Map<String, Collector> getCollectorMap() {
        return collectorMap;
    }
}
