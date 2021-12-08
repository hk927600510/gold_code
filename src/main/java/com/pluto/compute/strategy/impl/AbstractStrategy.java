package com.pluto.compute.strategy.impl;

import com.pluto.bean.CodeBasic;
import com.pluto.compute.condition.Condition;
import com.pluto.compute.strategy.Strategy;
import com.pluto.helper.LogUtils;

import java.util.List;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/5
 */
public abstract class AbstractStrategy implements Strategy {

    @Override
    public List<CodeBasic> match() {
        for (Condition condition : getConditions()) {
            LogUtils.log(getClass().getSimpleName() + " 策略：" + getName() + " has condition=" + condition.getName());
        }
        return strategyMatch();
    }

    abstract List<CodeBasic> strategyMatch();

    protected boolean checkCondition(String key) {
        for (Condition condition : getConditions()) {
            if (!condition.check(key)) {
                return false;
            }
        }
        return true;
    }

}
