package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.collector.Collector;
import com.pluto.helper.LogUtils;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKNewHighRecentCondition extends AbstractCondition {

    private Map<String, Collector> collectorMap;

    private String dataBegin;

    private String dataEnd;

    public DayKNewHighRecentCondition(Map<String, Collector> collectorMap, String dataBegin, String dataEnd) {
        this.collectorMap = collectorMap;
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
    }

    @Override
    public String getName() {
        return "日K-60日新高";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = getDayKReader().getDataByCondition(code);
        List<DayKData> filterData = dateAndDayKMap.values().stream().filter(p -> !isTradeSuspend(p)).filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).collect(Collectors.toList());
        OptionalDouble max = filterData.stream().mapToDouble(p -> Double.parseDouble(p.getClose())).max();
        DayKData today = dateAndDayKMap.get(dataEnd);
        if (max.isPresent() && today != null) {
            return max.getAsDouble() == Double.parseDouble(today.getClose());
        }
        return false;

    }

    @Override
    Map<String, Collector> getCollectorMap() {
        return collectorMap;
    }
}
