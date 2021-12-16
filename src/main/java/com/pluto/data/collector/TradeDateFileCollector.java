package com.pluto.data.collector;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class TradeDateFileCollector extends AbstractFileCollector {

    private String date;

    public TradeDateFileCollector(String inputDate) {
        this.date = inputDate;
    }

    @Override
    public void collect() throws Exception {
        if (!finish()) {
            runCmds("python", "pythonJob/sqlite_py/trade_date_sqlit_d.py", date);
        }
    }

}
