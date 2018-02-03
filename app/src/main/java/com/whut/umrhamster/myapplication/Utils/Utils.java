package com.whut.umrhamster.myapplication.Utils;

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
}
