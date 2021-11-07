package com.pluto.data.collector;

import com.pluto.bean.DayKData;
import com.pluto.data.reader.DayKDataReader;
import com.pluto.data.reader.Reader;
import com.pluto.helper.CodeHelper;
import com.pluto.helper.LogUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class DayKDataFileCollector extends AbstractFileCollector<Map<String, DayKData>> {

    private String bsn_date;

    private Reader<Map<String, DayKData>> reader;

    public DayKDataFileCollector(String bsn_date) {
        this.bsn_date = bsn_date;
    }

    @Override
    public void collect() {
        if (!finish()) {
            //if (false) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(CodeHelper.transToDate(bsn_date));
            String collectEndDate = CodeHelper.formatDate(calendar.getTime());
            calendar.add(Calendar.YEAR, -1);
            String collectStartDate = CodeHelper.formatDate(calendar.getTime());
            runCmds("python", "pythonJob/day_k_data.py", bsn_date, collectStartDate, collectEndDate);
        }
    }

    @Override
    public boolean finish() {
        return true;
    }

    @Override
    public Reader<Map<String, DayKData>> getReader() {
        if (reader == null) {
            reader = new DayKDataReader();
        }
        return reader;
    }

    @Override
    public String getDataPath() {
        return CodeHelper.getCodeDataHomePath() + File.separator + "k_data" + File.separator + "day";
    }
}
