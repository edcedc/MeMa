package com.yc.mema.weight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/21
 * Time: 15:58
 */
public class DayCompare {

    private int year;
    private int month;
    private int day;

    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int dayComparePrecise(String time, String time1){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = format.parse(time);
            toDate = format.parse(time1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay  - fromDay;
        return day;
    }

}
