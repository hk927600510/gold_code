package com.pluto.data.collector;

import com.pluto.bean.CodeBasic;
import com.pluto.data.reader.CodeBasicReader;
import com.pluto.data.reader.Reader;
import com.pluto.helper.CodeHelper;
import com.pluto.helper.LogUtils;

import java.io.File;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class CodeBasicFileCollector extends AbstractFileCollector<Map<String, CodeBasic>> {

    private String bsn_date;

    private Reader<Map<String, CodeBasic>> reader;

    public CodeBasicFileCollector(String bsn_date) {
        this.bsn_date = bsn_date;
    }

    @Override
    public void collect() {
        if (!finish()) {
            // if (false) {
            runCmds("python", "pythonJob/all_code.py", bsn_date);
        }
    }

    @Override
    public boolean hasFinish() {
        File dataFile = new File(getDataPath());
        return dataFile.exists();
    }

    @Override
    public Reader<Map<String, CodeBasic>> getReader() {
        if (reader == null) {
            reader = new CodeBasicReader(getDataPath());
            LogUtils.log(getClass().getSimpleName() + "getReader: dataPath=" + getDataPath());
        }
        return reader;
    }

    @Override
    public String getDataPath() {
        return CodeHelper.getCodeDataHomePath() + File.separator + "all_code" + File.separator + "all_code_" + bsn_date;
    }
}
