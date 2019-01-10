package com.timmy.androidbase.calendar;

/**
 * 日历操作回调
 */
public interface CalendarCallback {

    void onSuccess(Schedule schedule);

    void onFail(Schedule schedule);
}
