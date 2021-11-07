package com.pluto.compute.condition;

import com.pluto.bean.BasicKData;
import com.pluto.bean.DayKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.collector.Collector;
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
    }

    @Override
    public String getName() {
        return "3个周k涨幅大于0";
    }

    @Override
    public boolean check(String code) {
        Map<String, BasicKData> dateAndWeekKMap = getWeekKReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndWeekKMap.keySet()).stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        List<String> twoWeekDates = dateList.size() < 2 ? dateList : dateList.subList(0, 2);
        if (!twoWeekDates.isEmpty()) {
            for (String date : twoWeekDates) {
                BasicKData data = dateAndWeekKMap.get(date);
                if (data != null && !data.getPctChg().isEmpty() && Double.parseDouble(data.getPctChg()) < 0) {
                    return false;
                }
            }

            Map<String, DayKData> dateAndDayKMap = getDayKReader().getDataByCondition(code);
            List<BasicKData> thisWeekDayKData = dateAndDayKMap.values().stream().filter(p -> p.getDate().compareTo(twoWeekDates.get(0)) > 0).collect(Collectors.toList());
            double thisWeekDayKPctChg = thisWeekDayKData.stream().filter(p -> !p.getPctChg().isEmpty()).map(p -> Double.parseDouble(p.getPctChg())).mapToDouble(p -> p).sum();
            if (thisWeekDayKPctChg < 0) {
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
