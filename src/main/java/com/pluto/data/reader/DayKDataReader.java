package com.pluto.data.reader;

import com.pluto.bean.DayKData;
import com.pluto.helper.CodeHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKDataReader implements Reader<Map<String, DayKData>> {

    private final String dataPath;

    private Map<String, Map<String, DayKData>> dayKDataMap = new HashMap<>();

    public DayKDataReader(String dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public Map<String, DayKData> getDataAll() {
        throw new UnsupportedOperationException("unSupport get data all in day k reader");
    }

    @Override
    public Map<String, DayKData> getDataByCondition(String key) {
        if (dayKDataMap.get(key) == null) {
            List<String> contents = CodeHelper.readFromFile(dataPath + File.separator + key);
            Map<String, DayKData> dayKData = contents.stream().map(this::transferDayKData).filter(Objects::nonNull).collect(Collectors.toMap(DayKData::getDate, k -> k));
            dayKDataMap.put(key, dayKData);
        }
        return dayKDataMap.get(key);
    }

    private DayKData transferDayKData(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        String[] array = content.split(",", -1);
        DayKData data = new DayKData();
        data.setDate(array[0]);
        data.setCode(array[1]);
        data.setOpen(array[2]);
        data.setHigh(array[3]);
        data.setLow(array[4]);
        data.setClose(array[5]);
        data.setPreClose(array[6]);
        data.setVolume(array[7]);
        data.setAmount(array[8]);
        data.setAdjustFlag(array[9]);
        data.setTurn(array[10]);
        data.setTradeStatus(array[11]);
        data.setPctChg(array[12]);
        data.setIsST(array[13]);
        return data;
    }
}
