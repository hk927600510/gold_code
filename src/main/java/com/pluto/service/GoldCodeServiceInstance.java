package com.pluto.service;

import com.pluto.bean.CodeBasic;
import com.pluto.bean.TradeDate;
import com.pluto.compute.strategy.Strategy;
import com.pluto.compute.strategy.impl.FirstStrategy;
import com.pluto.constant.CodeConstant;
import com.pluto.data.collector.CodeBasicFileCollector;
import com.pluto.data.collector.Collector;
import com.pluto.data.collector.DayKDataFileCollector;
import com.pluto.data.collector.TradeDateFileCollector;
import com.pluto.data.collector.WeekKDataFileCollector;
import com.pluto.data.reader.Reader;
import com.pluto.helper.CodeHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public enum GoldCodeServiceInstance {

    INSTANCE;

    // 输入日期参数
    private String inputDate;

    // 跑数开盘业务日期
    private String bsn_date;

    // bsn_date往前数n个开盘日
    private String bsn_begin_date;

    private String outputPath;

    private final Map<String, Collector> beforeCollectorMap = new HashMap<>();

    private final Map<String, Collector> collectorMap = new HashMap<>();

    private final List<Strategy> strategyList = Arrays.asList(
            new FirstStrategy(collectorMap)
    );

    private final List<String> resultPathList = new ArrayList<>();

    /**
     * 开始任务
     */
    public void start(String date) {
        inputDate = date;

        clear();
        // 提前批处理、获取交易时间
        beforeDataCollect();
        // 交易时间初始化
        dateInit(date);
        // 数据收集
        dataCollect();
        // 分析
        codeAnalysis();
        // 发送邮件
        sendEmail();
    }

    private void sendEmail() {
    }

    private void clear() {
        outputPath = CodeHelper.getCodeDataHomePath() + File.separator + "result" + File.separator + inputDate;
        File outputPathFile = new File(outputPath);
        if (outputPathFile.exists()) {
            CodeHelper.deleteFile(outputPathFile);
        }
        outputPathFile.mkdirs();
    }

    private void beforeDataCollect() {
        initBeforeCollectorMap();
        for (Collector collector : beforeCollectorMap.values()) {
            collector.collect();
        }
    }

    private void initBeforeCollectorMap() {
        beforeCollectorMap.put("TradeDate", new TradeDateFileCollector(inputDate));
    }

    private void initCollectorMap() {
        collectorMap.put("CodeBasic", new CodeBasicFileCollector(bsn_date));
        collectorMap.put("DayKData", new DayKDataFileCollector(bsn_begin_date, bsn_date));
        collectorMap.put("WeekKData", new WeekKDataFileCollector(bsn_begin_date, bsn_date));
    }

    /**
     * 时间条件初始化
     */
    private void dateInit(String date) {
        Reader<List<TradeDate>> reader = (Reader<List<TradeDate>>) beforeCollectorMap.get("TradeDate").getReader();
        List<TradeDate> tradeDateList = reader.getDataAll();
        List<TradeDate> filterTradeDateList = tradeDateList.stream().sorted(Comparator.comparing(TradeDate::getCalendarDate).reversed()).collect(Collectors.toList());
        int bsnCount = 0;
        for (TradeDate tradeDate : filterTradeDateList) {
            if (bsn_date == null && tradeDate.getCalendarDate().compareTo(date) <= 0 && "1" .equals(tradeDate.getIsTradingDay())) {
                bsn_date = tradeDate.getCalendarDate();
            }
            if (bsn_date != null && "1" .equals(tradeDate.getIsTradingDay())) {
                bsnCount++;
                if (bsnCount >= CodeConstant.TRADE_DATA_LENGTH) {
                    bsn_begin_date = tradeDate.getCalendarDate();
                    break;
                }
            }
        }
    }

    /**
     * 数据准备
     */
    private void dataCollect() {
        initCollectorMap();
        for (Collector collector : collectorMap.values()) {
            collector.collect();
        }
    }

    /**
     * 计算
     */
    private void codeAnalysis() {
        for (Strategy strategy : strategyList) {
            List<CodeBasic> result = strategy.match();
            String fileName = outputPath + File.separator + strategy.getName() + ".csv";
            CodeHelper.writeToFile(fileName, result.stream().sorted(Comparator.comparing(CodeBasic::getIndustry)).map(CodeBasic::toString).collect(Collectors.toList()));
            resultPathList.add(fileName);
        }
    }

}
