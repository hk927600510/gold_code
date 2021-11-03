package com.pluto.bean;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class TradeDate {

    // 日期
    private String calendarDate;
    // 是否交易日
    private String isTradingDay;

    public String getCalendarDate() {
        return calendarDate;
    }

    public String getIsTradingDay() {
        return isTradingDay;
    }

    private TradeDate(Builder builder) {
        calendarDate = builder.calendarDate;
        isTradingDay = builder.isTradingDay;
    }

    public static final class Builder {
        private String calendarDate;
        private String isTradingDay;

        public Builder() {
        }

        public Builder calendarDate(String val) {
            calendarDate = val;
            return this;
        }

        public Builder isTradingDay(String val) {
            isTradingDay = val;
            return this;
        }

        public TradeDate build() {
            return new TradeDate(this);
        }
    }
}
