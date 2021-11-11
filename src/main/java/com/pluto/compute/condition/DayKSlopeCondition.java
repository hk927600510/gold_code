package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.reader.ReaderManager;
import com.pluto.helper.CodeHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/11
 */
public class DayKSlopeCondition extends AbstractCondition {

    private String dataBegin;

    private String dataEnd;

    public DayKSlopeCondition(String dataBegin, String dataEnd) {
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
    }

    @Override
    public String getName() {
        return "日k拟合斜率大于0";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
        List<DayKData> filterData = dateAndDayKMap.values().stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).collect(Collectors.toList());
        return CodeHelper.getKDataSlope(filterData) > 0;
    }
}
