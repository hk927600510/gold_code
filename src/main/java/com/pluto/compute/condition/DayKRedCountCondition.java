package com.pluto.compute.condition;

import com.pluto.bean.DayKData;
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
public class DayKRedCountCondition extends AbstractCondition {

    private String bsnDate;

    private int dayNum;

    private int redDayNum;


    public DayKRedCountCondition(String bsnDate, int dayNum, int redDayNum) {
        this.bsnDate = bsnDate;
        this.dayNum = dayNum;
        this.redDayNum = redDayNum;
    }

    @Override
    public String getName() {
        return "日k-" + dayNum + "天K线涨幅>=0至少有" + redDayNum + "天";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndDayKMap.keySet()).stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        if (dateList.size() < 100) {
            return false;
        }
        long redCount = dateList.subList(0, dayNum).stream().map(dateAndDayKMap::get).filter(this::filterRedDayKData).count();
        return redCount >= redDayNum;
    }

    private boolean filterRedDayKData(DayKData data) {
        if (isTradeSuspend(data)) {
            return true;
        }
        return Double.parseDouble(data.getPctChg()) >= 0;
    }
}
