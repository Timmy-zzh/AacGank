package com.timmy.androidbase.database;

import java.util.List;

public class Schedule {

    public String scheduleId;   //日程id
    public String createTime;//创建时间
    public String title;    //日程标题
    public String description;  //日程描述
    public long startTime;  //日程开始时间
    public long endTime;    //日程结束时间
    public int  allDay;     //是否是全天事件 0全天 1非全天
    public long remindAheadTime; //提前多久提醒
    public long eventId;    //日程事件id
    public long remindId;   //日程提醒id
    public List<Long> remindIdList;//一个日程可以有多个提醒

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", allDay=" + allDay +
                ", remindAheadTime=" + remindAheadTime +
                ", eventId=" + eventId +
                ", remindId=" + remindId +
                ", remindIdList=" + remindIdList +
                '}';
    }
}
