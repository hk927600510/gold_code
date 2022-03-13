package com.pluto.helper;

import com.pluto.bean.DayKData;
import com.pluto.bean.SlopeBean;
import com.pluto.bean.TradeDate;
import com.pluto.data.reader.ReaderManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class CodeHelper {

    public static List<String> readFromFile(String fileName) {
        File targetFile = new File(fileName);
        if (!targetFile.exists()) {
            return Collections.emptyList();
        }
        List<String> contents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
        } catch (Exception ignored) {
        }
        return contents;
    }

    public static void writeToFile(String fileName, List<String> contents) {
        File targetFile = new File(fileName);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException ignored) {
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)))) {
            for (String content : contents) {
                writer.write(content);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getCodeDataHomePath() {
        // return "/Users/kevin/Works/github/project/gold_code/codeData";
        return "./codeData";
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Date transToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                deleteFile(subFile);
            }
        }
        file.delete();
    }

    public static String getBsnDateWithInterval(String date, int interval) {
        List<TradeDate> tradeDateList = ReaderManager.getTradeDateReader().getDataAll();
        List<TradeDate> filterTradeDateList = tradeDateList.stream().filter(p -> p.getCalendarDate().compareTo(date) <= 0 && "1".equals(p.getIsTradingDay())).collect(Collectors.toList());
        return filterTradeDateList.get(Math.abs(interval)).getCalendarDate();
    }

    public static String getCommonDateWithInterval(String bsnDate, int yearInterval, int monthInterval, int dayInterval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transToDate(bsnDate));
        calendar.add(Calendar.YEAR, yearInterval);
        calendar.add(Calendar.MONTH, monthInterval);
        calendar.add(Calendar.DAY_OF_YEAR, dayInterval);
        return formatDate(calendar.getTime());
    }

    // 获取k先拟合斜率
    public static double getKDataSlope(List<DayKData> kDataList) {
        List<DayKData> sortedData = kDataList.stream().sorted(Comparator.comparing(DayKData::getDate)).collect(Collectors.toList());
        List<SlopeBean> slopeBeanList = new ArrayList<>();
        for (int i = 0; i < sortedData.size(); i++) {
            DayKData kData = sortedData.get(i);
            if (kData.getPctChg() == null || kData.getPctChg().isEmpty()) {
                continue;
            }
            SlopeBean bean = new SlopeBean();
            bean.setX(i);
            bean.setY(Double.parseDouble(kData.getClose()));
            slopeBeanList.add(bean);
        }

        if (slopeBeanList.isEmpty() || slopeBeanList.size() < 2) {
            return 0;
        }

        int n = slopeBeanList.size();
        double xySum = slopeBeanList.stream().mapToDouble(p -> p.getX() * p.getY()).sum();
        double xAvg = slopeBeanList.stream().mapToDouble(SlopeBean::getX).average().getAsDouble();
        double yAvg = slopeBeanList.stream().mapToDouble(SlopeBean::getY).average().getAsDouble();
        double x2Sum = slopeBeanList.stream().mapToDouble(p -> p.getX() * p.getX()).sum();
        return (xySum - n * xAvg * yAvg) / (x2Sum - n * xAvg * xAvg);

    }

}
