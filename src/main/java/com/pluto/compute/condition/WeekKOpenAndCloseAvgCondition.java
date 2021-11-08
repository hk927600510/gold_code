package com.pluto.compute.condition;

import com.pluto.bean.BasicKData;
import com.pluto.compute.filter.DataFilter;
import com.pluto.data.reader.ReaderManager;

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
public class WeekKOpenAndCloseAvgCondition extends AbstractCondition {

    private String dataBegin;

    private String dataEnd;

    public WeekKOpenAndCloseAvgCondition(String dataBegin, String dataEnd) {
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
    }


    @Override
    public String getName() {
        return "周k的开盘与收盘价中点趋势向上";
    }

    @Override
    public boolean check(String code) {
        Map<String, BasicKData> dateAndWeekKMap = ReaderManager.getWeekKDataReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndWeekKMap.keySet()).stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        int compareCount = Math.min(dateList.size() - 1, 2);
        for (int i = 0; i < compareCount; i++) {
            String firstOpen = dateAndWeekKMap.get(dateList.get(i)).getOpen();
            String firstClose = dateAndWeekKMap.get(dateList.get(i)).getClose();
            double firstAvg = (Double.parseDouble(firstOpen) + Double.parseDouble(firstClose)) / 2;
            String secondOpen = dateAndWeekKMap.get(dateList.get(i + 1)).getOpen();
            String secondClose = dateAndWeekKMap.get(dateList.get(i + 1)).getClose();
            double secondAvg = (Double.parseDouble(secondOpen) + Double.parseDouble(secondClose)) / 2;
            if (firstAvg < secondAvg) {
                return false;
            }
        }
        return true;

    }
}
