package com.pluto.compute.condition;

import com.pluto.bean.BasicKData;
import com.pluto.bean.DayKData;
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
 * Created by Kevin.H on 2021/11/2
 */
public class WeekKRedCountCondition extends AbstractCondition {

    private String dataBegin;

    private String dataEnd;

    public WeekKRedCountCondition(String dataBegin, String dataEnd) {
        this.dataBegin = dataBegin;
        this.dataEnd = dataEnd;
    }

    @Override
    public String getName() {
        return "3个周k开盘价趋势向上";
    }

    @Override
    public boolean check(String code) {
        Map<String, BasicKData> dateAndWeekKMap = ReaderManager.getWeekKDataReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndWeekKMap.keySet()).stream().filter(p -> DataFilter.dateFilterByBeginAndEndDate(p, dataBegin, dataEnd)).sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        if (dateList.size() < 3) {
            return false;
        }
        String firstWeekKDate = dateList.get(0);
        if (dataEnd.equals(firstWeekKDate)) {
            List<String> threeWeekDates = dateList.subList(0, 3);
            String firstOpen = dateAndWeekKMap.get(threeWeekDates.get(0)).getOpen();
            String secondOpen = dateAndWeekKMap.get(threeWeekDates.get(1)).getOpen();
            String thirdOpen = dateAndWeekKMap.get(threeWeekDates.get(2)).getOpen();
            if (Double.parseDouble(firstOpen) < Double.parseDouble(secondOpen) || Double.parseDouble(secondOpen) < Double.parseDouble(thirdOpen)) {
                return false;
            }
        } else {
            List<String> twoWeekDates = dateList.subList(0, 2);
            String firstOpen = dateAndWeekKMap.get(twoWeekDates.get(0)).getOpen();
            String secondOpen = dateAndWeekKMap.get(twoWeekDates.get(1)).getOpen();
            if (Double.parseDouble(firstOpen) < Double.parseDouble(secondOpen)) {
                return false;
            }
            Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
            List<BasicKData> thisWeekDayKData = dateAndDayKMap.values().stream().filter(p -> p.getDate().compareTo(twoWeekDates.get(0)) > 0).sorted(Comparator.comparing(BasicKData::getDate).reversed()).collect(Collectors.toList());
            String mondayOpen = thisWeekDayKData.get(0).getOpen();
            if ((Double.parseDouble(mondayOpen) < Double.parseDouble(firstOpen))) {
                return false;
            }
        }

        return true;
    }
}
