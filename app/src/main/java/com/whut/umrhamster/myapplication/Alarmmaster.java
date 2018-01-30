package com.whut.umrhamster.myapplication;

import java.io.Serializable;

/**
 * Created by 12421 on 2018/1/26.
 */

public class Alarmmaster implements Serializable{
    /*
     *用于数据库建表
     */
    //表名
    public static final String TABLE = "AlarmClock";
    //表的各域
    public static final String KEY_ID = "id";                 //闹钟id
    public static final String KEY_ROUGHTIME = "roughTime";   //闹钟上下午
    public static final String KEY_HOUR = "hour";             //闹钟小时
    public static final String KEY_MINUTE = "minute";         //闹钟分钟
    public static final String KEY_REPETITION = "repetition"; //闹钟重复
    public static final String KEY_STATUS = "status";         //闹钟状态 开或关
    /* ******************************************** */

    private int id;
    private String roughTime;         //粗略时间
    private String hour;              //小时
    private String minute;            //分钟
    private String exactTime;         //精确时间
    private String repetition;        //重复
    private String status;            //状态 开或关

    public Alarmmaster(){}

    public Alarmmaster(String roughTime, String hour, String minute, String repetition){
        this.roughTime = roughTime;
        this.hour = hour;
        this.minute = minute;
        this.exactTime = exactTime;
        this.repetition = repetition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setRoughTime(String roughTime) {
        this.roughTime = roughTime;
    }

    public String getRoughTime() {
        return roughTime;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMinute() {
        return minute;
    }

    public void setExactTime(String exactTime) {
        this.exactTime = exactTime;
    }

    public String getExactTime() {
        StringBuffer stringBuffer = new StringBuffer();
        if(hour.length() <= 1){
            stringBuffer.append("0");
        }
        stringBuffer .append(hour)
                     .append(":")
                     .append(minute);
        return stringBuffer.toString();
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
