package com.pluto.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/3
 */
public class LogUtils {

    public static SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");

    public static void log(String content) {
        System.out.println(dataFormat.format(new Date()) + content);
    }

}
