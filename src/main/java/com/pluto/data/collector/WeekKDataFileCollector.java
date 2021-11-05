package com.pluto.data.collector;

import com.pluto.bean.BasicKData;
import com.pluto.data.reader.Reader;
import com.pluto.data.reader.WeekKDataReader;
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
public class WeekKDataFileCollector extends AbstractFileCollector<Map<String, BasicKData>> {

    private String bsn_date;

    public WeekKDataFileCollector(String bsn_date) {
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
            runCmds("python", "pythonJob/week_k_data.py", bsn_date, collectStartDate, collectEndDate);
        }
    }

    @Override
    public boolean hasFinish() {
        File file = new File(getDataPath());
        if (file.exists()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(CodeHelper.transToDate(bsn_date));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            LogUtils.log("week k lastModified=" + file.lastModified() + " calendar=" + calendar.getTimeInMillis());
            return file.lastModified() > calendar.getTimeInMillis();
        }
        return false;
    }

    @Override
    public Reader<Map<String, BasicKData>> getReader() {
        return new WeekKDataReader(getDataPath());
    }

    @Override
    String getDataPath() {
        return CodeHelper.getCodeDataHomePath() + File.separator + "k_data" + File.separator + "week";
    }
}
