package com.pluto.data.collector;

import com.pluto.helper.LogUtils;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public abstract class AbstractFileCollector implements Collector {

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
        return false;
    }

}
