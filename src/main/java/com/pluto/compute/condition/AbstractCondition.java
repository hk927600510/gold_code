package com.pluto.compute.condition;

import com.pluto.bean.BasicKData;
import com.pluto.bean.CodeBasic;
import com.pluto.bean.DayKData;
import com.pluto.data.collector.Collector;
import com.pluto.data.reader.Reader;

import java.util.Map;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/4
 */
public abstract class AbstractCondition implements Condition {

    /**
     * 获取数据收集器map
     *
     * @return
     */
    abstract Map<String, Collector> getCollectorMap();


    protected Reader<Map<String, DayKData>> getDayKReader() {
        return (Reader<Map<String, DayKData>>) getCollectorMap().get("DayKData").getReader();
    }


    protected Reader<Map<String, BasicKData>> getWeekKReader() {
        return (Reader<Map<String, BasicKData>>) getCollectorMap().get("WeekKData").getReader();
    }

    protected Reader<Map<String, CodeBasic>> getCodeBasicReader() {
        return (Reader<Map<String, CodeBasic>>) getCollectorMap().get("CodeBasic").getReader();
    }

    /**
     * 判断停牌
     *
     * @return
     */
    protected boolean isTradeSuspend(DayKData data) {
        if (data == null) {
            return true;
        }
        if ("0" .equals(data.getTradeStatus()) || data.getPctChg().isEmpty()) {
            return true;
        }
        return false;
    }

}
