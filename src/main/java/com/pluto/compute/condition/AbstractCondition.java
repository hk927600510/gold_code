package com.pluto.compute.condition;

import com.pluto.bean.DayKData;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/4
 */
public abstract class AbstractCondition implements Condition {

    /**
     * 判断停牌
     *
     * @return
     */
    protected boolean isTradeSuspend(DayKData data) {
        if (data == null) {
            return true;
        }
        if ("0".equals(data.getTradeStatus()) || data.getPctChg().isEmpty()) {
            return true;
        }
        return false;
    }

}
