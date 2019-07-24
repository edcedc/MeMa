package com.yc.mema.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;

import cn.addapp.pickers.picker.DatePicker;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 11:17
 */
public class DatePickerUtils {


    public static void getYearMonthDayPicker(Activity act, final OnYearMonthDayListener listener){
        String[] times = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")).split("-");
        Integer year = Integer.valueOf(times[0]);
        Integer mon = Integer.valueOf(times[1]);
        Integer day = Integer.valueOf(times[2]);

        final DatePicker picker = new DatePicker(act);
        picker.setTitleText("选择生日");
        picker.setSubmitTextColor(Color.parseColor("#FA8099"));
        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(year - 20, mon, day);
        picker.setRangeEnd(year, mon, day);
        picker.setSelectedItem(year, mon, day);
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                if (listener != null){
                    listener.onTime(year, month, day);
                }
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    public interface OnYearMonthDayListener{
        void onTime(String year, String month, String day);
    }

}
