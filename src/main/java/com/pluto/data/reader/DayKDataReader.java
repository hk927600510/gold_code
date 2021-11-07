package com.pluto.data.reader;

import com.pluto.bean.DayKData;
import com.pluto.helper.SqliteDAOHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class DayKDataReader implements Reader<Map<String, DayKData>> {

    @Override
    public Map<String, DayKData> getDataAll() {
        throw new UnsupportedOperationException("unSupport get data all in day k reader");
    }

    @Override
    public Map<String, DayKData> getDataByCondition(String key) {
        List<DayKData> result = SqliteDAOHelper.queryDayKData(" where code='" + key + "'");
        return result.stream().collect(Collectors.toMap(DayKData::getDate, k -> k));
    }

}
