package com.pluto.data.collector;

import com.pluto.bean.CodeBasic;
import com.pluto.data.reader.CodeBasicReader;
import com.pluto.data.reader.Reader;
import com.pluto.helper.CodeHelper;

import java.io.File;
import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class CodeBasicFileCollector extends AbstractFileCollector<Map<String, CodeBasic>> {

    private String bsn_date;

    public CodeBasicFileCollector(String bsn_date) {
        this.bsn_date = bsn_date;
    }

    @Override
    public void collect() {
        //if (!finish()) {
        if (false) {
            runCmds("python", "all_code.py", bsn_date);
        }
    }

    @Override
    public boolean finish() {
        File dataFile = new File(getDataPath());
        return dataFile.exists();
    }

    @Override
    public Reader<Map<String, CodeBasic>> getReader() {
        return new CodeBasicReader(getDataPath());
    }

    @Override
    public String getDataPath() {
        return CodeHelper.getCodeDataHomePath() + File.separator + "all_code" + File.separator + "all_code_" + bsn_date;
    }
}