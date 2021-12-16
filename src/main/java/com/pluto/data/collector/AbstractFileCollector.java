package com.pluto.data.collector;

import com.pluto.helper.LogUtils;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public abstract class AbstractFileCollector implements Collector {

    protected void runCmd(String cmd) throws Exception {
        LogUtils.log(getClass().getSimpleName() + ": " + cmd);
        Process process = Runtime.getRuntime().exec(cmd);
        LogUtils.log("runCmd log : " + readStringFromShell(process, ""));
        process.waitFor();
        int result = process.exitValue();
        if (result != 0) {
            throw new Exception("runCmds error");
        }
    }

    protected void runCmds(String... cmds) throws Exception {
        int tryTime = 1;
        while (tryTime <= 3) {
            LogUtils.log(getClass().getSimpleName() + ": " + String.join(" ", cmds) + " tryTime=" + tryTime);
            Process process = Runtime.getRuntime().exec(cmds);
            LogUtils.log("runCmds log : \n" + readStringFromShell(process, ""));
            process.waitFor(30, TimeUnit.MINUTES);
            int result = process.exitValue();
            if (result == 0) {
                LogUtils.log("runCmds success");
                break;
            }
            LogUtils.log("runCmds error");
            process.destroy();
            tryTime++;
        }
        if (tryTime == 4) {
            LogUtils.log("runCmds failed");
            throw new Exception("runCmds failed!!");
        }
    }


    @Override
    public boolean finish() {
        return false;
    }

    private String readStringFromShell(Process process, String charsetName) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner sc;
        if (charsetName == null || charsetName.isEmpty()) {
            sc = new Scanner(process.getInputStream());
        } else {
            sc = new Scanner(process.getInputStream(), charsetName);
        }
        while (sc.hasNextLine()) {
            stringBuilder.append(sc.nextLine())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
