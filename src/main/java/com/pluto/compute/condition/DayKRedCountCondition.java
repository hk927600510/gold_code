package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;
import com.pluto.helper.LogUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKRedCountCondition implements Condition {

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
        Reader<Map<String, DayKData>> dayKReader = (Reader<Map<String, DayKData>>) collectorMap.get("DayKData").getReader();
        Map<String, DayKData> dateAndDayKMap = dayKReader.getDataByCondition(code);
        List<DayKData> filterData = dateAndDayKMap.values().stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).collect(Collectors.toList());
        long count = filterData.stream().filter(this::filterRedDayKData).count();
        return count > filterData.size() / 2;
    }

    private boolean filterRedDayKData(DayKData data) {
        if (data == null) {
            return false;
        }
        if ("0" .equals(data.getTradeStatus())) {
            return true;
        }
        if (data.getPctChg().isEmpty()) {
            // 涨幅为空判定为停牌，有些停牌标志不准
            return true;
        }
        return Double.parseDouble(data.getPctChg()) >= 0;
    }
}
