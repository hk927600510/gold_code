package com.pluto.data.reader;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/2
 */
public interface Reader<T> {

    /**
     * 获取全部数据
     *
     * @return
     */
    T getDataAll() ;

    /**
     * 根据条件获取数据
     *
     * @param key
     * @return
     */
    T getDataByCondition(String key);

}
