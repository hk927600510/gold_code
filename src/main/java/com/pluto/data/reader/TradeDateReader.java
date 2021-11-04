package com.pluto.data.reader;

import com.pluto.bean.TradeDate;
import com.pluto.helper.CodeHelper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class TradeDateReader implements Reader<List<TradeDate>> {

    private List<TradeDate> tradeDateList;

    private final String dataPath;

    public TradeDateReader(String dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public List<TradeDate> getDataAll() {
        if (tradeDateList == null) {
            List<String> fileContents = CodeHelper.readFromFile(dataPath);
            tradeDateList = fileContents.stream().map(this::transferTradeDate).sorted(Comparator.comparing(TradeDate::getCalendarDate).reversed()).collect(Collectors.toList());
        }
        return tradeDateList;
    }

    @Override
    public List<TradeDate> getDataByCondition(String key) {
        return null;
    }

    private TradeDate transferTradeDate(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        String[] array = content.split(",", -1);
        return new TradeDate.Builder().calendarDate(array[0]).isTradingDay(array[1]).build();

    }
}
