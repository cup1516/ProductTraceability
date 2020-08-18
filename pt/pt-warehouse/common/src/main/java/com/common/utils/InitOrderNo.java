package com.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: InitOrderNo
 *
 * @Author: huangmin
 * @Date: 2020/4/11 22:55
 * Description:
 * Version: 1.0
 */

public class InitOrderNo {
    public static String getNowFormatDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String monthString = "";
        String dayString = "";
        String hourString = "";
        String minuteString = "";
        String secondString = "";

        if (month >= 0 && month <= 9) {
            monthString = "0" + month;
        }else {
            monthString = String.valueOf(month);
        }

        if (day >= 0 && day <= 9) {
            dayString = "0" + day;
        }else {
            dayString = String.valueOf(day);
        }

        if (hour >= 0 && hour <= 9) {
            hourString = "0" + hour;
        }else {
            hourString = String.valueOf(hour);
        }

        if (minute >= 0 && minute <= 9) {
            minuteString = "0" + minute;
        }else {
            minuteString = String.valueOf(minute);
        }

        if (second >= 0 && second <= 9) {
            secondString = "0" + second;
        }else {
            secondString = String.valueOf(second);
        }
        return year + monthString + dayString + hourString + minuteString + secondString + calendar.get(Calendar.MILLISECOND);
    }
}