package com.pluto.data.reader;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/8
 */
public class ReaderManager {

    public static CodeBasicReader codeBasicReader;
    public static TradeDateReader tradeDateReader;
    public static DayKDataReader dayKDataReader;
    public static WeekKDataReader weekKDataReader;

    public static CodeBasicReader getCodeBasicReader() {
        if (codeBasicReader == null) {
            codeBasicReader = new CodeBasicReader();
        }
        return codeBasicReader;
    }

    public static TradeDateReader getTradeDateReader() {
        if (tradeDateReader == null) {
            tradeDateReader = new TradeDateReader();
        }
        return tradeDateReader;
    }

    public static DayKDataReader getDayKDataReader() {
        if (dayKDataReader == null) {
            dayKDataReader = new DayKDataReader();
        }
        return dayKDataReader;
    }

    public static WeekKDataReader getWeekKDataReader() {
        if (weekKDataReader == null) {
            weekKDataReader = new WeekKDataReader();
        }
        return weekKDataReader;
    }

}
