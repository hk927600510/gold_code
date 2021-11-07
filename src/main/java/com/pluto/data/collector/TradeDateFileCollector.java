package com.pluto.data.collector;

import com.pluto.bean.TradeDate;
import com.pluto.data.reader.Reader;
import com.pluto.data.reader.TradeDateReader;
import com.pluto.helper.CodeHelper;
import com.pluto.helper.LogUtils;

import java.io.File;
import java.util.List;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class TradeDateFileCollector extends AbstractFileCollector<List<TradeDate>> {

    private String date;

    private Reader<List<TradeDate>> reader;

    public TradeDateFileCollector(String inputDate) {
        this.date = inputDate;
    }

    @Override
    public void collect() {
        if (!finish()) {
            //if (false) {
            runCmds("python", "pythonJob/trade_date.py", date);
        }
    }

    @Override
    public boolean finish() {
        return true;
    }

    @Override
    public Reader<List<TradeDate>> getReader() {
        if (reader == null) {
            reader = new TradeDateReader();
            LogUtils.log(getClass().getSimpleName() + "getReader: dataPath=" + getDataPath());
        }
        return reader;
    }

    @Override
    public String getDataPath() {
        return CodeHelper.getCodeDataHomePath() + File.separator + "trade_date" + File.separator + "trade_dates_" + date;
    }
}
