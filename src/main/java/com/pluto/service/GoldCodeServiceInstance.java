package com.pluto.service;

import com.pluto.bean.CodeBasic;
import com.pluto.compute.strategy.Strategy;
import com.pluto.compute.strategy.impl.FirstStrategy;
import com.pluto.compute.strategy.impl.KeepRedStrategy;
import com.pluto.compute.strategy.impl.NewHighStrategy;
import com.pluto.data.collector.CodeBasicFileCollector;
import com.pluto.data.collector.Collector;
import com.pluto.data.collector.DayKDataFileCollector;
import com.pluto.data.collector.TradeDateFileCollector;
import com.pluto.data.collector.WeekKDataFileCollector;
import com.pluto.helper.CodeHelper;
import com.pluto.helper.LogUtils;
import com.pluto.helper.MailHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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

    private String outputPath;

    private final List<Collector> beforeCollectors = new ArrayList<>();

    private final List<Collector> collectors = new ArrayList<>();

    private final List<Strategy> strategyList = new ArrayList<>();

    private final List<String> resultPathList = new ArrayList<>();

    /**
     * 开始任务
     */
    public void start(String date) throws Exception {
        inputDate = checkInputDate(date);
        LogUtils.log("开始任务,inputDate=" + inputDate);
        LogUtils.log("DataHome=" + CodeHelper.getCodeDataHomePath());
        clear();
        LogUtils.log("历史结果文件清理完成");
        // 提前批处理、获取交易时间
        beforeDataCollect();
        LogUtils.log("提前批处理完成");
        // 交易时间初始化
        dateInit(inputDate);
        LogUtils.log("交易时间初始化完成");
        // 数据收集
        dataCollect();
        LogUtils.log("数据收集批处理完成");
        // 分析
        codeAnalysis();
        LogUtils.log("股票分析完成");
        // 发送邮件
        sendEmail();
        LogUtils.log("发送邮件完成");
    }

    private void sendEmail() throws Exception {
        MailHelper.sendMail(inputDate, resultPathList);
    }

    private void clear() {
        outputPath = CodeHelper.getCodeDataHomePath() + File.separator + "result" + File.separator + inputDate;
        File outputPathFile = new File(outputPath);
        if (outputPathFile.exists()) {
            CodeHelper.deleteFile(outputPathFile);
        }
        outputPathFile.mkdirs();
    }

    private void beforeDataCollect() throws Exception {
        initBeforeCollectorMap();
        for (Collector collector : beforeCollectors) {
            collector.collect();
        }
    }

    private void initBeforeCollectorMap() {
        beforeCollectors.add(new TradeDateFileCollector(inputDate));
    }

    private void initCollectorMap() {
        collectors.add(new CodeBasicFileCollector(bsn_date));
        collectors.add(new DayKDataFileCollector(bsn_date));
        collectors.add(new WeekKDataFileCollector(bsn_date));
    }

    /**
     * 时间条件初始化
     */
    private void dateInit(String date) {
        bsn_date = CodeHelper.getBsnDateWithInterval(date, 0);
        LogUtils.log(getClass().getSimpleName() + ": bsn_date=" + bsn_date);
    }

    /**
     * 数据准备
     */
    private void dataCollect() throws Exception {
        initCollectorMap();
        for (Collector collector : collectors) {
            collector.collect();
        }
    }

    /**
     * 计算
     */
    private void codeAnalysis() {
        initStrategyList();
        for (Strategy strategy : strategyList) {
            LogUtils.log("strategy:[" + strategy.getName() + "] check start");
            List<CodeBasic> result = strategy.match();
            String fileName = outputPath + File.separator + strategy.getName() + ".csv";
            CodeHelper.writeToFile(fileName, result.stream().sorted(Comparator.comparing(CodeBasic::getIndustry)).map(CodeBasic::toString).collect(Collectors.toList()));
            resultPathList.add(fileName);
            LogUtils.log("strategy:[" + strategy.getName() + "] check finish");
        }
    }

    private void initStrategyList() {
        strategyList.add(new FirstStrategy(bsn_date));
        strategyList.add(new NewHighStrategy(bsn_date));
        strategyList.add(new KeepRedStrategy(bsn_date));
    }

    private String checkInputDate(String date) {
        Date input = CodeHelper.transToDate(date);
        Calendar calendar = Calendar.getInstance();
        String now = CodeHelper.formatDate(calendar.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (date.equals(now) && hour < 18) {
            LogUtils.log(getClass().getSimpleName() + " : time is before 18 hour ,use the day before inputDate ");
            calendar.setTime(input);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            return CodeHelper.formatDate(calendar.getTime());
        }
        return date;
    }

}
