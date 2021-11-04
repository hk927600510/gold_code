package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;
import com.pluto.helper.LogUtils;

import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKPctChgLimitCondition implements Condition {

    private Map<String, Collector> collectorMap;

    private String dataBegin;

    private String dataEnd;

    public DayKPctChgLimitCondition(Map<String, Collector> collectorMap, String dataBegin, String dataEnd) {
        this.collectorMap = collectorMap;
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
        LogUtils.log(getClass().getSimpleName() + ": condition=" + getName() + " dataBegin=" + this.dataBegin + " dataEnd=" + this.dataEnd);
    }

    @Override
    public String getName() {
        return "涨跌幅限制,跌幅不能超过4%";
    }

    @Override
    public boolean check(String code) {
        Reader<Map<String, DayKData>> dayKReader = (Reader<Map<String, DayKData>>) collectorMap.get("DayKData").getReader();
        Map<String, DayKData> dateAndDayKMap = dayKReader.getDataByCondition(code);
        long count = dateAndDayKMap.values().stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).filter(this::filterPctChgLimit).count();
        return count == 0;
    }

    private boolean filterPctChgLimit(DayKData data) {
        if (data == null) {
            return false;
        }
        if ("0" .equals(data.getTradeStatus())) {
            return false;
        }
        if (data.getPctChg().isEmpty()){
            // 涨幅为空判定为停牌，有些停牌标志不准
            return false;
        }
        return Double.parseDouble(data.getPctChg()) < -4;
    }
}
