package com.pluto.data.collector;

import com.pluto.helper.LogUtils;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public abstract class AbstractFileCollector<T> implements Collector<T> {

    protected int runCmd(String cmd) {
        try {
            LogUtils.log(getClass().getSimpleName() + ": " + cmd);
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
            LogUtils.log(getClass().getSimpleName() + ": " + String.join(" ", cmds));
            Process process = Runtime.getRuntime().exec(cmds);
            process.waitFor();
            return process.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean finish() {
        boolean flag = hasFinish();
        LogUtils.log(getClass().getSimpleName() + " finish result=" + flag);
        return flag;
    }

    /**
     * 获取数据文件path
     *
     * @return
     */
    abstract String getDataPath();

    /**
     * 判断是否已经获取数据
     *
     * @return
     */
    abstract boolean hasFinish();
}
