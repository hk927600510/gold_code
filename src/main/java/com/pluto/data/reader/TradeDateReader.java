package com.pluto.data.reader;

import com.pluto.bean.TradeDate;
import com.pluto.helper.SqliteDAOHelper;

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

    @Override
    public List<TradeDate> getDataAll() {
        if (tradeDateList == null) {
            List<TradeDate> result = SqliteDAOHelper.queryTradeDate("");
            tradeDateList = result.stream().sorted(Comparator.comparing(TradeDate::getCalendarDate).reversed()).collect(Collectors.toList());
        }
        return tradeDateList;
    }

    @Override
    public List<TradeDate> getDataByCondition(String key) {
        return null;
    }

}
