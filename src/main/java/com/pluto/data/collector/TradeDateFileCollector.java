package com.pluto.data.collector;

import com.pluto.bean.TradeDate;
import com.pluto.data.reader.Reader;
import com.pluto.data.reader.TradeDateReader;
import com.pluto.helper.CodeHelper;

import java.io.File;
import java.util.List;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class TradeDateFileCollector extends AbstractFileCollector<List<TradeDate>> {

    private String date;

    public TradeDateFileCollector(String inputDate) {
        this.date = inputDate;
    }

    @Override
    public void collect() {
        //if (!finish()) {
        if (false) {
            runCmds("python", "trade_date.py", date);
        }
    }

    @Override
    public boolean finish() {
        File dataFile = new File(getDataPath());
        return dataFile.exists();
    }

    @Override
    public Reader<List<TradeDate>> getReader() {
        return new TradeDateReader(getDataPath());
    }

    @Override
    public String getDataPath() {
        return CodeHelper.getCodeDataHomePath() + File.separator + "trade_date" + File.separator + "trade_dates_" + date;
    }
}