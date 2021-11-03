package com.pluto.data.collector;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public abstract class AbstractFileCollector<T> implements Collector<T> {

    protected int runCmd(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            return process.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    protected int runCmds(String... cmds) {
        try {
            Process process = Runtime.getRuntime().exec(cmds);
            process.waitFor();
            return process.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取数据文件path
     *
     * @return
     */
    abstract String getDataPath();
}
