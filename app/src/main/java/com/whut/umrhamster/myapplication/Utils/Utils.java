package com.whut.umrhamster.myapplication.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.whut.umrhamster.myapplication.AlarmReceiver;
import com.whut.umrhamster.myapplication.Alarmmaster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by 12421 on 2018/2/3.
 */

public class Utils {
    /*计算单次闹钟响铃时间
     *输入：alarmRough 0表示上午，1表示下午    alarmHour 小时（12小时制）    alarmMinute 分钟
     *输出：当前系统时间与制定闹钟时间的时间差,拆分为小时和分钟
     */
    public static int TimeCalculate(int alarmRough, int alarmHour, int alarmMinute){
        //获取系统时间
        Calendar calendar = Calendar.getInstance();
        long currentTimeInMillis = calendar.getTimeInMillis(); //系统时间
        //设置时间以计算时间差////////////////////////////////
        Calendar calendarAlarm = Calendar.getInstance();
        if(alarmRough == 0){
            if(alarmHour == 11){    //11代表的是时间选择器上的12，上午12点实际上是凌晨0点
                calendarAlarm.set(Calendar.HOUR_OF_DAY,0);
            }else {
                calendarAlarm.set(Calendar.HOUR_OF_DAY,alarmHour+1);
            }
        }else {
            if(alarmHour == 11){
                calendarAlarm.set(Calendar.HOUR_OF_DAY,12);
            }else {
                calendarAlarm.set(Calendar.HOUR_OF_DAY,alarmHour+13);
            }
        }
        calendarAlarm.set(Calendar.MINUTE,alarmMinute);
        calendarAlarm.set(Calendar.MILLISECOND,0);
        long alarmTimeInMillis = calendarAlarm.getTimeInMillis();
        int result;
        if(currentTimeInMillis < alarmTimeInMillis){
            result = (int) ((alarmTimeInMillis - currentTimeInMillis));
        }else {
            result = (int) ((24*60*60*1000 - (currentTimeInMillis - alarmTimeInMillis)));
        }
        //////////////////////////////////////////////////////
        return result;
    }

    /*计算重复闹钟响铃时间
     *输入：alarmRough 0表示上午，1表示下午    alarmHour 小时（12小时制）    alarmMinute 分钟
     *输出：选择与当前系统时间最近的闹钟的时间差
     */
    public static int TimeCalculate(int alarmRough, int alarmHour, int alarmMinute, String repetition){
        List<Integer> timeList = new ArrayList<>();
        String[] repe = repetition.split("每周|,");
        for (String str : repe){
            int timeCalculate;
            switch (String2int(str)){
                case 0:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.MONDAY);
                    break;
                case 1:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.TUESDAY);
                    break;
                case 2:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.WEDNESDAY);
                    break;
                case 3:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.THURSDAY);
                    break;
                case 4:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.FRIDAY);
                    break;
                case 5:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.SATURDAY);
                    break;
                case 6:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.SUNDAY);
                    break;
                default:
                    timeCalculate = TimeCalculate(alarmRough,alarmHour,alarmMinute,Calendar.SUNDAY);// 此处无作用。不会执行到
                    break;
            }
            timeList.add(timeCalculate);
        }
        return Collections.min(timeList);
    }

    /*计算重复中的每一天闹钟响铃时间
     *输入：alarmRough 0表示上午，1表示下午    alarmHour 小时（12小时制）    alarmMinute 分钟
     *输出：当前系统时间与制定闹钟时间的时间差
     */
    public static int TimeCalculate(int alarmRough, int alarmHour, int alarmMinute, int week){
        //获取系统时间
        Calendar calendar = Calendar.getInstance();
        long currentTimeInMillis = calendar.getTimeInMillis(); //系统时间
        //设置时间以计算时间差////////////////////////////////
        Calendar calendarAlarm = Calendar.getInstance();
        if(alarmRough == 0){
            if(alarmHour == 11){    //11代表的是时间选择器上的12，上午12点实际上是凌晨0点
                calendarAlarm.set(Calendar.HOUR_OF_DAY,0);
            }else {
                calendarAlarm.set(Calendar.HOUR_OF_DAY,alarmHour+1);
            }
        }else {
            if(alarmHour == 11){
                calendarAlarm.set(Calendar.HOUR_OF_DAY,12);
            }else {
                calendarAlarm.set(Calendar.HOUR_OF_DAY,alarmHour+13);
            }
        }
        calendarAlarm.set(Calendar.DAY_OF_WEEK,week);
        calendarAlarm.set(Calendar.MINUTE,alarmMinute);
        calendarAlarm.set(Calendar.MILLISECOND,0);
        long alarmTimeInMillis = calendarAlarm.getTimeInMillis();
        int result;
        if(currentTimeInMillis < alarmTimeInMillis){
            result = (int) (alarmTimeInMillis - currentTimeInMillis);
        }else {
            result = (int) ((7*24*60*60*1000 - (currentTimeInMillis - alarmTimeInMillis)));
        }
        //////////////////////////////////////////////////////
        return result;
    }

    //将时间差拆分为天、小时和分钟
    public static int[] result2array(int result){
        result = result/1000;
        int[] time = new int[3];
        time[0] = result/(3600*24); //天数
        time[1] = (result%(3600*24))/3600;      //小时数
        time[2] = (result%3600)/60; //分钟数
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
        if(hour >=19){
            return "晚上";
        }else if(hour > 12){
            return "下午";
        }else if(hour == 12){
            return "中午";
        }else if(hour > 5){
            return "上午";
        }else {
            return "凌晨";
        }
    }

    public static String getExactTime(int hour, int minute){
        StringBuilder stringBuilder = new StringBuilder();
        if(hour>12){
            hour = hour -12;
        }
        if(hour == 0){
            stringBuilder.append("12");
        }else {
            if(hour<10){
                stringBuilder.append("0");
            }
            stringBuilder.append(hour);
        }
        stringBuilder.append(":");
        if(minute<10){
            stringBuilder.append("0");
        }
        stringBuilder.append(minute);
        return stringBuilder.toString();
    }

    public static void setAlarmTime(Context context, int hour, int minute, String repetition, int id,Intent intent){
        long timeInMillis;
        if(repetition.equals("不重复") || repetition.equals("每天")){
            timeInMillis = toTimeMillis(hour,minute);
        }else {
            timeInMillis = toTimeMillis(hour,minute);
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);

    }
    //“不重复”和“每天” 通过小时和分钟转化为第一次响铃时的毫秒数
    public static long toTimeMillis(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        long currentTimeMillis = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long targetTimeMillis = calendar.getTimeInMillis();
        if(currentTimeMillis < targetTimeMillis){
            return targetTimeMillis;
        }else {
            return 24*60*60*1000+targetTimeMillis;
        }
    }
    //“自定义” 通过小时、分钟和周期计算出第一次响铃的毫秒数
    public static long toTimeMillis(int hour, int minute, String repetition){
        Calendar calendar = Calendar.getInstance();
        long currentTimeMillis = calendar.getTimeInMillis();
        //calendar.set(Calendar.DAY_OF_WEEK,);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long targetTimeMillis = calendar.getTimeInMillis();
        if(currentTimeMillis < targetTimeMillis){

        }
        repetition = repetition.substring(2,3);
        if(calendar.get(Calendar.DAY_OF_WEEK) < String2int(repetition))
        String2int(repetition);
        return 0;
    }

    /*
     *
     * 5.0以上的JobScheduler
     *
     */
    public static void test(Context context){
        JobScheduler jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(context.getPackageName(), AlarmReceiver.class.getName()));

        builder.setPeriodic(60 * 1000); //每隔60秒运行一次
        builder.setRequiresCharging(true);
        builder.setPersisted(true);  //设置设备重启后，是否重新执行任务
        builder.setRequiresDeviceIdle(true);

        if (jobScheduler.schedule(builder.build()) == JobScheduler.RESULT_FAILURE) {
            //If something goes wrong
        }
    }
}
