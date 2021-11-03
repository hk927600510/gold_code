package com.pluto.data.reader;

import com.pluto.bean.BasicKData;
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
public class WeekKDataReader implements Reader<Map<String, BasicKData>> {

    private String bsn_date;

    private String bsn_begin_date;

    private final String dataPath;

    private Map<String, Map<String, BasicKData>> weekKDataMap = new HashMap<>();

    public WeekKDataReader(String dataPath, String bsn_begin_date, String bsn_date) {
        this.dataPath = dataPath;
        this.bsn_begin_date = bsn_begin_date;
        this.bsn_date = bsn_date;
    }

    @Override
    public Map<String, BasicKData> getDataAll() {
        throw new UnsupportedOperationException("unSupport get data all in week k reader");
    }

    @Override
    public Map<String, BasicKData> getDataByCondition(String key) {
        if (weekKDataMap.get(key) == null) {
            List<String> contents = CodeHelper.readFromFile(dataPath + File.separator + key);
            Map<String, BasicKData> basicKData = contents.stream().map(this::transferBasicKData).filter(Objects::nonNull).filter(p -> p.getDate().compareTo(bsn_begin_date) >= 0).collect(Collectors.toMap(BasicKData::getDate, k -> k));
            weekKDataMap.put(key, basicKData);
        }
        return weekKDataMap.get(key);
    }

    private BasicKData transferBasicKData(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        String[] array = content.split(",", -1);
        BasicKData data = new BasicKData();
        data.setDate(array[0]);
        data.setCode(array[1]);
        data.setOpen(array[2]);
        data.setHigh(array[3]);
        data.setLow(array[4]);
        data.setClose(array[5]);
        data.setVolume(array[6]);
        data.setAmount(array[7]);
        data.setAdjustFlag(array[8]);
        data.setTurn(array[9]);
        data.setPctChg(array[10]);
        return data;
    }
}
