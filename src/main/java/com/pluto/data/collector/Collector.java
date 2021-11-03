package com.pluto.data.collector;

import com.pluto.data.reader.Reader;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public interface Collector<T> {

    /**
     * 数据收集
     */
    void collect();

    /**
     * 收集状态
     *
     * @return
     */
    boolean finish();

    /**
     * 获得数据
     *
     * @return
     */
    Reader<T> getReader();

}
