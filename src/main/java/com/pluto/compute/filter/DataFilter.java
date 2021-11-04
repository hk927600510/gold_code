package com.pluto.compute.filter;

import com.pluto.bean.BasicKData;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/4
 */
public class DataFilter {

    public static boolean dateFilterByBeginAndEndDate(Object object, String dataBegin, String dataEnd) {
        if (object instanceof String) {
            String input = (String) object;
            return input.compareTo(dataBegin) >= 0 && input.compareTo(dataEnd) <= 0;
        }
        if (object instanceof BasicKData) {
            BasicKData data = (BasicKData) object;
            return data.getDate().compareTo(dataBegin) >= 0 && data.getDate().compareTo(dataEnd) <= 0;
        }
        throw new UnsupportedOperationException("DataFilter unSupport Class : " + object.getClass().toString());
    }

}
