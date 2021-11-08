package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.reader.ReaderManager;

import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKPctChgLimitCondition extends AbstractCondition {

    private String dataBegin;

    private String dataEnd;

    public DayKPctChgLimitCondition(String dataBegin, String dataEnd) {
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
    }

    @Override
    public String getName() {
        return "涨跌幅限制,跌幅不能超过4%";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
        long count = dateAndDayKMap.values().stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).filter(this::filterPctChgLimit).count();
        return count == 0;
    }

    private boolean filterPctChgLimit(DayKData data) {
        if (isTradeSuspend(data)) {
            return false;
        }
        return Double.parseDouble(data.getPctChg()) < -4;
    }

}
