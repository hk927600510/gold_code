package com.pluto.compute.condition;

import com.pluto.bean.CodeBasic;
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
public class DayKKeepRedCondition extends AbstractCondition {

    private String dataEnd;

    public DayKKeepRedCondition(String dataEnd) {
        this.dataEnd = dataEnd;
    }

    @Override
    public String getName() {
        return "日k-红突突突";
    }

    @Override
    public boolean check(String code) {
        Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
        Map<String, CodeBasic> codeBasicMap = ReaderManager.getCodeBasicReader().getDataByCondition(code);
        CodeBasic codeBasic = codeBasicMap.get(code);
        List<String> filterDateList = new ArrayList<>(dateAndDayKMap.keySet()).stream().filter(p -> p.compareTo(dataEnd) <= 0).sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        if (filterDateList.size() < 10) {
            return false;
        }
        List<String> needDateList = filterDateList.subList(0, 5);
        boolean hasMax = false;
        int bigGreedCount = 0;
        int smallGreedCount = 0;
        for (String date : needDateList) {
            DayKData data = dateAndDayKMap.get(date);
            if (!isTradeSuspend(data)) {
                double pctChg = Double.parseDouble(data.getPctChg());
                if (pctChg >= getCodeMax(codeBasic)) {
                    hasMax = true;
                }
                if (pctChg < -(getCodeMax(codeBasic) / 2)) {
                    bigGreedCount++;
                }
                if (pctChg > -(getCodeMax(codeBasic) / 2) && pctChg < 0) {
                    smallGreedCount++;
                }
            }
        }
        return hasMax && bigGreedCount == 0 && smallGreedCount <= 1;
    }

    private double getCodeMax(CodeBasic codeBasic) {
        if (codeBasic == null) {
            return 9.9;
        }
        String code = codeBasic.getCode();
        if (code.contains(".300")) {
            return 19.9;
        }
        if (code.contains(".688")) {
            return 19.9;
        }
        if (codeBasic.getCodeName().startsWith("ST")) {
            return 4.9;
        }
        return 9.9;
    }
}
