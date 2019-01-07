package com.timmy.androidbase.database;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;
import com.timmy.baselib.utils.LogUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日程添加或编辑
 */
public class ScheduleAddOrEditActivity extends BaseActivity {

    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mHourOfDay;
    private int mMinute;
    private EditText etTitle;
    private EditText etDesctiption;
    private EditText etRemindAheadTime;
    private Switch switchAllDay;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private long startTimeStamp;
    private long endTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_addedit);
        etTitle = findViewById(R.id.et_title);
        etDesctiption = findViewById(R.id.et_description);
        etRemindAheadTime = findViewById(R.id.et_remindAheadTime);
        switchAllDay = findViewById(R.id.switch_allday);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);

    }

    public void switchStartTime(View view) {
        showDatePickerDialog(0);
    }

    public void switchEndTime(View view) {
        showDatePickerDialog(1);
    }

    public void addSchedule(View view) {
        Schedule schedule = new Schedule();
        schedule.title = etTitle.getText().toString().trim();
        schedule.description = etDesctiption.getText().toString().trim();
        schedule.allDay = switchAllDay.isChecked() ? 1 : 0;
        schedule.remindAheadTime = Integer.valueOf(etRemindAheadTime.getText().toString().trim());
        schedule.startTime = startTimeStamp;
        schedule.endTime = endTimeStamp;
        ScheduleDao.add(schedule);
    }

    private void showDatePickerDialog(final int type) {
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, 0,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LogUtils.d("year:" + year + " ,month:" + month + " ,dayOfMonth:" + dayOfMonth);
                        mYear = year;
                        mMonth = month + 1;
                        mDayOfMonth = dayOfMonth;
                    }
                }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showTimePickerDialog(type);
            }
        });
    }

    private void showTimePickerDialog(final int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        LogUtils.d("hourOfDay:" + hourOfDay + " ,minute:" + minute);
                        mHourOfDay = hourOfDay;
                        mMinute = minute;
                    }
                }
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                , true
        );
        timePickerDialog.show();
        timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);
                if (type == 0) {
                    tvStartTime.setText(mYear + "-" + mMonth + "-" + mDayOfMonth + " " + mHourOfDay + ":" + mMinute + ":00");
                    //转成时间戳
                    startTimeStamp = calendar.getTimeInMillis();
                } else {
                    tvEndTime.setText(mYear + "-" + mMonth + "-" + mDayOfMonth + " " + mHourOfDay + ":" + mMinute + ":00");
                    endTimeStamp = calendar.getTimeInMillis();
                }
            }
        });
    }
}
