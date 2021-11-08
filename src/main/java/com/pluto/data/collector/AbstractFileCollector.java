package com.pluto.data.collector;

import com.pluto.helper.LogUtils;

import java.util.Scanner;

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
            LogUtils.log("runCmds log : " + readStringFromShell(process, ""));
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
            LogUtils.log("runCmds log : \n" + readStringFromShell(process, ""));
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
