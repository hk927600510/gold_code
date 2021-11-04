package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.collector.Collector;
import com.pluto.helper.LogUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKRedCountCondition extends AbstractCondition {

    private Map<String, Collector> collectorMap;

    private String dataBegin;

    private String dataEnd;

    public DayKRedCountCondition(Map<String, Collector> collectorMap, String dataBegin, String dataEnd) {
        this.collectorMap = collectorMap;
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
        LogUtils.log(getClass().getSimpleName() + ": condition=" + getName() + " dataBegin=" + this.dataBegin + " dataEnd=" + this.dataEnd);
    }

    @Override
    public String getName() {
        return "红k数量过半";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = getDayKReader().getDataByCondition(code);
        List<DayKData> filterData = dateAndDayKMap.values().stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).collect(Collectors.toList());
        long count = filterData.stream().filter(this::filterRedDayKData).count();
        return count > filterData.size() / 2;
    }

    private boolean filterRedDayKData(DayKData data) {
        if (isTradeSuspend(data)) {
            return true;
        }
        return Double.parseDouble(data.getPctChg()) >= 0;
    }

    @Override
    Map<String, Collector> getCollectorMap() {
        return collectorMap;
    }
}
