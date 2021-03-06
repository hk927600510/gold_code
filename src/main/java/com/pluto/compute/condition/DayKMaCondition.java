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
public class DayKMaCondition extends AbstractCondition {

    private String bsnDate;

    private int dayNum;

    private int ignoreNum;

    public DayKMaCondition(String bsnDate, int dayNum, int ignoreNum) {
        this.bsnDate = bsnDate;
        this.dayNum = dayNum;
        this.ignoreNum = ignoreNum;
    }

    @Override
    public String getName() {
        return "日k" + dayNum + "天站上均线,容错" + ignoreNum + "天";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndDayKMap.keySet()).stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        if (dateList.size() < 100) {
            return false;
        }

        int falseCount = 0;
        for (int i = 0; i < dayNum; i++) {
            if (!checkDayKMa(dateAndDayKMap, dateList)) {
                falseCount++;
            }
            dateList.remove(0);
        }
        return falseCount <= ignoreNum;
    }

    private boolean checkDayKMa(Map<String, DayKData> dateAndDayKMap, List<String> dateList) {
        double ma5 = dateList.subList(0, 5).stream().map(dateAndDayKMap::get).mapToDouble(p -> Double.parseDouble(p.getClose())).sum() / 5;
        double ma10 = dateList.subList(0, 10).stream().map(dateAndDayKMap::get).mapToDouble(p -> Double.parseDouble(p.getClose())).sum() / 10;
        double ma20 = dateList.subList(0, 20).stream().map(dateAndDayKMap::get).mapToDouble(p -> Double.parseDouble(p.getClose())).sum() / 20;
        double ma60 = dateList.subList(0, 60).stream().map(dateAndDayKMap::get).mapToDouble(p -> Double.parseDouble(p.getClose())).sum() / 60;
        String todayClose = dateAndDayKMap.get(dateList.get(0)).getClose();
        double todayCloseDouble = Double.parseDouble(todayClose);
        return todayCloseDouble >= ma5 && todayCloseDouble >= ma10 && todayCloseDouble >= ma20 && todayCloseDouble >= ma60;
    }
}
