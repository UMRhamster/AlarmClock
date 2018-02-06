package com.whut.umrhamster.myapplication.Utils;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by 12421 on 2018/2/3.
 */

public class Utils {
    /*计算单次时间
     *输入：alarmRough 0表示上午，1表示下午    alarmHour 小时（12小时制）    alarmMinute 分钟
     *输出：当前系统时间与制定闹钟时间的时间差,拆分为小时和分钟
     */
    public static int[] TimeCalculate(int alarmRough, int alarmHour, int alarmMinute){
        //获取系统时间
        Calendar calendar = Calendar.getInstance();
        long currentTimeInMillis = calendar.getTimeInMillis(); //系统时间
        //设置时间以计算时间差////////////////////////////////
        Calendar calendarAlarm = Calendar.getInstance();
        if(alarmRough == 0){
            calendarAlarm.set(Calendar.HOUR_OF_DAY,alarmHour+1);
        }else {
            calendarAlarm.set(Calendar.HOUR_OF_DAY,alarmHour+13);
        }
        calendarAlarm.set(Calendar.MINUTE,alarmMinute);
        calendarAlarm.set(Calendar.MILLISECOND,0);
        long alarmTimeInMillis = calendarAlarm.getTimeInMillis();
        int result;
        if(currentTimeInMillis < alarmTimeInMillis){
            result = (int) ((alarmTimeInMillis - currentTimeInMillis)/1000);
        }else {
            result = (int) ((24*60*60*1000 - (currentTimeInMillis - alarmTimeInMillis))/1000);
        }
        //////////////////////////////////////////////////////
        //使用数组保存小时和分钟，time[0]保存小时,time[1]保存分钟
        int[] time = new int[2];
        time[0] = result/3600;
        time[1] = (result%3600)/60;
        return time;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static String int2String(int number){
        switch (number){
            case 0:
                return "一,";
            case 1:
                return "二,";
            case 2:
                return "三,";
            case 3:
                return "四,";
            case 4:
                return "五,";
            case 5:
                return "六,";
            case 6:
                return "日,";
            default:
                return "";
        }
    }

    public static int String2int(String string){
        switch (string){
            case "一":
                return 0;
            case "二":
                return 1;
            case "三":
                return 2;
            case "四":
                return 3;
            case "五":
                return 4;
            case "六":
                return 5;
            case "日":
                return 6;
            default:
                return -1;
        }
    }

    public static int boolean2int(boolean b){
        if(b){
            return 1;
        }else {
            return 0;
        }
    }

    public static boolean int2boolean(int i){
        switch (i){
            case 0:
                return false;
            case 1:
                return true;
            default:
                return false;
        }
    }

    public static String getRoughTime(int hour){
        if(hour >12){
            return "下午";
        }else if(hour == 12){
            return "中午";
        }else if(hour == 0){
            return "凌晨";
        }else {
            return "上午";
        }
    }

    public static String getExactTime(int hour, int minute){
        StringBuilder stringBuilder = new StringBuilder();
        if(hour>12){
            hour = hour -12;
        }
        if(hour<10){
            stringBuilder.append("0");
        }
        stringBuilder.append(hour)
                .append(":");
        if(minute<10){
            stringBuilder.append("0");
        }
        stringBuilder.append(minute);
        return stringBuilder.toString();
    }
}
