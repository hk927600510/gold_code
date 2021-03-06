package com.pluto.data.collector;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class CodeBasicFileCollector extends AbstractFileCollector {

    private String bsn_date;

    public CodeBasicFileCollector(String bsn_date) {
        this.bsn_date = bsn_date;
    }

    @Override
    public void collect() throws Exception{
        if (!finish()) {
            runCmds("python", "pythonJob/sqlite_py/all_code_sqlite_d.py", bsn_date);
        }
    }

}
