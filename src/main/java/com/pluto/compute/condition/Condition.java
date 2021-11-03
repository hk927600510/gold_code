package com.pluto.compute.condition;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public interface Condition {

    /**
     * 检测名称
     *
     * @return
     */
    String getName();

    /**
     * 条件检测
     *
     * @return
     */
    boolean check(String code);

}
