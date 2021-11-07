package com.pluto.data.reader;

import com.pluto.bean.BasicKData;
import com.pluto.helper.SqliteDAOHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public class WeekKDataReader implements Reader<Map<String, BasicKData>> {

    @Override
    public Map<String, BasicKData> getDataAll() {
        throw new UnsupportedOperationException("unSupport get data all in week k reader");
    }

    @Override
    public Map<String, BasicKData> getDataByCondition(String key) {
        List<BasicKData> result = SqliteDAOHelper.queryWeekKData(" where code='" + key + "'");
        return result.stream().collect(Collectors.toMap(BasicKData::getDate, k -> k));
    }
}
