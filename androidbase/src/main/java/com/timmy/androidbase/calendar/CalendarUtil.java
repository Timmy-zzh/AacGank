package com.timmy.androidbase.calendar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;

import com.timmy.baselib.utils.LogUtils;

import java.util.TimeZone;

/**
 * 系统日历相关操作：
 * 1。读写日历权限
 * 2。新建日历账户
 * 3。日历事件的增删改查
 */
public class CalendarUtil {
    //系统Calendar ContentProvider相关URL
    private static String CALENDAR_URL = "content://com.android.calendar/calendars";
    //日历事件
    private static String CALENDAR_EVENT_URL = "content://com.android.calendar/events";
    //日历事件提醒
    private static String CALENDAR_REMIDER_URL = "content://com.android.calendar/reminders";

    //日历账户名称
    private static final String CALENDAR_NAME = "TimmyCalendar";
    private static final String CALENDAR_ACCOUNT_NAME = "timmy_zzh@163.com";
    private static final String CALENDAR_ACCOUNT_TYPE = "com.timmy";
    private static final String CALENDAR_DISPLAY_NAME = "Timmy账户";

    /**
     * 先检查账户，没有账户再添加
     */
    private static int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }

    /**
     * 检查是否有有存在的账户
     */
    private static int checkCalendarAccount(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(Uri.parse(CALENDAR_URL),
                    null, null, null, null);
            if (cursor == null) {
                return -1;
            }
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.NAME));
                if (CALENDAR_NAME.equals(name)) {
                    return cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars._ID));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d(e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1;
    }

    /**
     * 添加账户
     */
    private static long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.NAME, CALENDAR_NAME);
        values.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME);
//        账户类型
//        values.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDAR_ACCOUNT_TYPE);
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_DISPLAY_NAME);
        //?
        values.put(CalendarContract.Calendars.VISIBLE, 1);
        values.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.RED);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDAR_ACCOUNT_NAME);
        values.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALENDAR_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDAR_ACCOUNT_TYPE)
                .build();

        Uri result = context.getContentResolver().insert(calendarUri, values);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    /**
     * 添加日历事件
     */
    public static void addCalendarEvent(Context context, Schedule schedule, CalendarCallback callback) {
        if (context == null) {
            if (callback != null) {
                callback.onFail(schedule);
            }
            return;
        }
        int calId = checkAndAddCalendarAccount(context); //获取日历账户的id
        if (calId < 0) { //获取账户id失败直接返回，添加日历事件失败
            if (callback != null) {
                callback.onFail(schedule);
            }
            return;
        }
        //添加日历事件
        ContentValues eventValues = new ContentValues();
        eventValues.put(CalendarContract.Events.CALENDAR_ID, calId); //插入账户的id
        eventValues.put(CalendarContract.Events.TITLE, schedule.title);
        eventValues.put(CalendarContract.Events.DESCRIPTION, schedule.description);
        //日程时间
        eventValues.put(CalendarContract.Events.DTSTART, schedule.startTime);
        eventValues.put(CalendarContract.Events.DTEND, schedule.endTime);
        //全天事件 0非 false --- 1 是 true
        eventValues.put(CalendarContract.Events.ALL_DAY, schedule.allDay == 1);
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
//        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");//这个是时区，必须有
       //事件状态  暂停0 确认1  取消2
        eventValues.put(CalendarContract.Events.STATUS,1);
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());//这个是时区，必须有
        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALENDAR_EVENT_URL), eventValues); //添加事件
        if (newEvent == null) { //添加日历事件失败直接返回
            if (callback != null) {
                callback.onFail(schedule);
            }
            return;
        }
        long eventId = Long.parseLong(newEvent.getLastPathSegment());
        schedule.eventId = eventId;

        //事件提醒的设定
        if (schedule.remindAheadTime >= 0) {
            ContentValues remindValues = new ContentValues();
            remindValues.put(CalendarContract.Reminders.EVENT_ID, eventId);
            remindValues.put(CalendarContract.Reminders.MINUTES, schedule.remindAheadTime);// 提前previousDate天有提醒
            remindValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            Uri eventRemind = context.getContentResolver().insert(Uri.parse(CALENDAR_REMIDER_URL), remindValues);
            if (eventRemind != null) { //添加事件提醒失败直接返回
                long remindId = Long.parseLong(eventRemind.getLastPathSegment());
                schedule.remindId = remindId;
            }
        }
        if (callback != null) {
            callback.onSuccess(schedule);
        }
    }

    /**
     * 删除日历事件
     */
    public static int deleteCalendarEvent(Context context, Schedule schedule) {
        if (context == null) {
            return -1;
        }
        int rows;
        try {
            Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALENDAR_EVENT_URL), schedule.eventId);
            rows = context.getContentResolver().delete(deleteUri, null, null);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d(e.toString());
            rows = -1;
        }
        return rows;
    }

    public static void updateCalendarEvent(Context context, Schedule schedule, CalendarCallback callback) {
        if (context == null) {
            if (callback != null) {
                callback.onFail(schedule);
            }
            return;
        }
        //添加日历事件
        ContentValues eventValues = new ContentValues();
        eventValues.put(CalendarContract.Events.TITLE, schedule.title);
        eventValues.put(CalendarContract.Events.DESCRIPTION, schedule.description);
        //日程时间
        eventValues.put(CalendarContract.Events.DTSTART, schedule.startTime);
        eventValues.put(CalendarContract.Events.DTEND, schedule.endTime);
        //全天事件 0非 false --- 1 是 true
        eventValues.put(CalendarContract.Events.ALL_DAY, schedule.allDay == 1);
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒

        eventValues.put(CalendarContract.Events.STATUS,1);
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());//这个是时区，必须有

        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, schedule.eventId);
        int updateRows = context.getContentResolver().update(updateUri, eventValues, null, null);
        if (updateRows<=0){
            LogUtils.d("编辑日程失败");
        }
    }

//        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALENDAR_EVENT_URL), null, null, null, null);
//        try {
//            if (eventCursor == null) { //查询返回空值
//                return;
//            }
//            if (eventCursor.getCount() > 0) {
//                //遍历所有事件，找到title跟需要查询的title一样的项
//                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
//                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
//                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
//                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
//                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALENDAR_EVENT_URL), id);
//                        int rows = context.getContentResolver().delete(deleteUri, null, null);
//                        if (rows == -1) { //事件删除失败
//                            return;
//                        }
//                    }
//                }
//            }
//        } finally {
//            if (eventCursor != null) {
//                eventCursor.close();
//            }
//        }
//    }
}
