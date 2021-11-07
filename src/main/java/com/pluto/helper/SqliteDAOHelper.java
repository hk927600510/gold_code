package com.pluto.helper;

import com.pluto.bean.BasicKData;
import com.pluto.bean.CodeBasic;
import com.pluto.bean.DayKData;
import com.pluto.bean.TradeDate;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/7
 */
public class SqliteDAOHelper {

    //private static String DB_URL = "jdbc:sqlite:" + CodeHelper.getCodeDataHomePath() + File.separator + "gold_code.db";
    private static String DB_URL = "jdbc:sqlite:/Users/kevin/Works/github/project/gold_code/codeData/sqlite" + File.separator + "gold_code.db";

    private static String CLASS_NAME = "org.sqlite.JDBC";

    public static void main(String[] args) throws Exception {
        queryWeekKData("where code = 'sh.000001'");
    }

    public static List<CodeBasic> queryCodeBasic(String condition) throws Exception {
        Connection conn = createConnection();
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from all_code " + condition);
        List<CodeBasic> codeBasicList = new ArrayList<>();
        while (rs.next()) {
            CodeBasic codeBasic = new CodeBasic();
            codeBasic.setCode(rs.getString("code"));
            codeBasic.setTradeStatus(rs.getString("tradeStatus"));
            codeBasic.setCodeName(rs.getString("code_name"));
            codeBasic.setIpoDate(rs.getString("ipoDate"));
            codeBasic.setOutDate(rs.getString("outDate"));
            codeBasic.setType(rs.getString("type"));
            codeBasic.setStatus(rs.getString("status"));
            codeBasic.setIndustryUpdateDate(rs.getString("updateDate"));
            codeBasic.setIndustry(rs.getString("industry"));
            codeBasic.setIndustryClassification(rs.getString("industryClassification"));
            codeBasicList.add(codeBasic);
        }
        rs.close();
        conn.close();
        return codeBasicList;
    }

    public static List<TradeDate> queryTradeDate(String condition) throws Exception {
        Connection conn = createConnection();
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from trade_date " + condition);
        List<TradeDate> tradeDateList = new ArrayList<>();
        while (rs.next()) {
            TradeDate tradeDate = new TradeDate.Builder().calendarDate(rs.getString("calendar_date")).isTradingDay(rs.getString("is_trading_day")).build();
            tradeDateList.add(tradeDate);
        }
        rs.close();
        conn.close();
        return tradeDateList;
    }

    public static List<DayKData> queryDayKData(String condition) throws Exception {
        if (condition == null || condition.isEmpty()) {
            throw new UnsupportedOperationException("queryDayKData condition is empty");
        }
        Connection conn = createConnection();
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from day_k_data " + condition);
        List<DayKData> dayKDataList = new ArrayList<>();
        while (rs.next()) {
            DayKData dayKData = new DayKData();
            dayKData.setDate(rs.getString("date"));
            dayKData.setCode(rs.getString("code"));
            dayKData.setOpen(rs.getString("open"));
            dayKData.setHigh(rs.getString("high"));
            dayKData.setLow(rs.getString("low"));
            dayKData.setClose(rs.getString("close"));
            dayKData.setPreClose(rs.getString("preclose"));
            dayKData.setVolume(rs.getString("volume"));
            dayKData.setAmount(rs.getString("amount"));
            dayKData.setAdjustFlag(rs.getString("adjustflag"));
            dayKData.setTurn(rs.getString("turn"));
            dayKData.setTradeStatus(rs.getString("tradestatus"));
            dayKData.setPctChg(rs.getString("pctChg"));
            dayKData.setIsST(rs.getString("isST"));
            dayKDataList.add(dayKData);
        }
        rs.close();
        conn.close();
        return dayKDataList;
    }

    public static List<BasicKData> queryWeekKData(String condition) throws Exception {
        if (condition == null || condition.isEmpty()) {
            throw new UnsupportedOperationException("queryWeekKData condition is empty");
        }
        Connection conn = createConnection();
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from week_k_data " + condition);
        List<BasicKData> weekKDataList = new ArrayList<>();
        while (rs.next()) {
            BasicKData basicKData = new BasicKData();
            basicKData.setDate(rs.getString("date"));
            basicKData.setCode(rs.getString("code"));
            basicKData.setOpen(rs.getString("open"));
            basicKData.setHigh(rs.getString("high"));
            basicKData.setLow(rs.getString("low"));
            basicKData.setClose(rs.getString("close"));
            basicKData.setVolume(rs.getString("volume"));
            basicKData.setAmount(rs.getString("amount"));
            basicKData.setAdjustFlag(rs.getString("adjustflag"));
            basicKData.setTurn(rs.getString("turn"));

            basicKData.setPctChg(rs.getString("pctChg"));
            weekKDataList.add(basicKData);
        }
        rs.close();
        conn.close();
        return weekKDataList;
    }

    // 创建Sqlite数据库连接
    public static Connection createConnection() throws Exception {
        Class.forName(CLASS_NAME);
        return DriverManager.getConnection(DB_URL);
    }
}
