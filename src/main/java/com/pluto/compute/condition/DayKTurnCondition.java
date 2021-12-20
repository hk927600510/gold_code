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
public class DayKTurnCondition extends AbstractCondition {

    private String bsnDate;

    private int dayNum;

    private int times;

    public DayKTurnCondition(String bsnDate, int dayNum, int times) {
        this.bsnDate = bsnDate;
        this.dayNum = dayNum;
        this.times = times;
    }

    @Override
    public String getName() {
        return "日k放量-" + times + "倍" + dayNum + "日平均";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndDayKMap.keySet()).stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        if (dateList.size() < 100) {
            return false;
        }
        DayKData today = dateAndDayKMap.get(dateList.get(0));
        if (isTradeSuspend(today)) {
            return false;
        }
        double todayTurn = Double.parseDouble(today.getTurn());
        for (int i = 1; i < 30; i++) {
            String date = dateList.get(i);
            DayKData kData = dateAndDayKMap.get(date);
            if (kData.getTurn() == null || kData.getTurn().isEmpty()) {
                continue;
            }
            double turn = Double.parseDouble(kData.getTurn());
            if (todayTurn < turn * times) {
                return false;
            }
        }
        return true;
    }
}
