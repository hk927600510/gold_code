package com.pluto.compute.strategy;

import com.pluto.bean.CodeBasic;
import com.pluto.compute.condition.Condition;

import java.util.List;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public interface Strategy {

    /**
     * 策略名称
     *
     * @return
     */
    String getName();

    /**
     * 策略匹配
     *
     * @return
     */
    List<CodeBasic> match();

    /**
     * 获取匹配条件
     *
     * @return
     */
    List<Condition> getConditions();

}
