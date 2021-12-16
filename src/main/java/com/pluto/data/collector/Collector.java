package com.pluto.data.collector;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public interface Collector {

    /**
     * 数据收集
     */
    void collect() throws Exception;

    /**
     * 收集状态
     *
     * @return
     */
    boolean finish();

}
