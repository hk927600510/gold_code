package com.pluto.compute.condition;

import com.pluto.bean.CodeBasic;
import com.pluto.bean.DayKData;
import com.pluto.data.reader.ReaderManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKMaxPctChgCondition extends AbstractCondition {

    private String bsnDate;

    public DayKMaxPctChgCondition(String bsnDate) {
        this.bsnDate = bsnDate;
    }

    @Override
    public String getName() {
        return bsnDate + " 日k-涨停后新高";
    }

    @Override
    public boolean check(String code) {
        Map<String, CodeBasic> codeBasicMap = ReaderManager.getCodeBasicReader().getDataByCondition(code);
        Map<String, DayKData> dateAndDayKMap = ReaderManager.getDayKDataReader().getDataByCondition(code);
        List<String> dateList = new ArrayList<>(dateAndDayKMap.keySet()).stream().sorted(Comparator.comparing(String::toString).reversed()).collect(Collectors.toList());
        if (dateList.isEmpty() || dateList.size() < 60) {
            return false;
        }
        DayKData today = dateAndDayKMap.get(dateList.get(0));
        if (isTradeSuspend(today)) {
            return false;
        }
        double close = Double.parseDouble(today.getClose());
        double closeMaxPctCgh = close * getCodeMaxPercent(codeBasicMap.get(code));
        List<String> dateBefore60List = dateList.subList(0, 60);
        OptionalDouble max = dateBefore60List.stream().map(dateAndDayKMap::get).filter(Objects::nonNull).mapToDouble((p -> Double.parseDouble(p.getClose()))).max();
        if (max.isPresent()) {
            return max.getAsDouble() <= closeMaxPctCgh;
        }
        return false;
    }

    private double getCodeMaxPercent(CodeBasic codeBasic) {
        if (codeBasic == null) {
            return 1.1;
        }
        String code = codeBasic.getCode();
        if (code.contains(".300")) {
            return 1.2;
        }
        if (code.contains(".688")) {
            return 1.2;
        }
        if (codeBasic.getCodeName().startsWith("ST")) {
            return 1.05;
        }
        return 1.1;
    }
}
