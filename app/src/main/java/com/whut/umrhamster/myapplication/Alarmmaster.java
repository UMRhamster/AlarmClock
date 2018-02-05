package com.whut.umrhamster.myapplication;

import com.whut.umrhamster.myapplication.Utils.Utils;

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
    public static final String KEY_HOUR = "hour";             //闹钟小时
    public static final String KEY_MINUTE = "minute";         //闹钟分钟
    public static final String KEY_REPETITION = "repetition"; //闹钟重复
    public static final String KEY_RING = "ring";             //铃声
    public static final String KEY_SHAKE = "shake";           //震动
    public static final String KEY_TAG = "tag";               //标签
    public static final String KEY_STATUS = "status";         //闹钟状态 开或关
    /* ******************************************** */

    //private String roughTime;         //粗略时间 上下午
    //private String exactTime;         //精确时间 时分
    private int id;
    private int hour;                 //小时
    private int minute;               //分钟
    private String repetition;        //重复
    private String ring;              //铃声
    private int shake;                //震动
    private String tag;               //标签
    private int status;               //状态 开或关

    public Alarmmaster(){}
    //测试用
    public Alarmmaster(int hour, int minute, String repetition){
        //this.roughTime = roughTime;
        this.hour = hour;
        this.minute = minute;
        this.repetition = repetition;
    }

    public Alarmmaster(int hour, int minute, String repetition, String ring, int shake, String tag, int status){
        this.hour = hour;
        this.minute = minute;
        this.repetition = repetition;
        this.ring = ring;
        this.shake = shake;
        this.tag = tag;
        this.status = status;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getRoughTime() {
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

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public String getExactTime() {
        StringBuffer stringBuffer = new StringBuffer();
        if(hour<10){
            stringBuffer.append("0");
        }
        stringBuffer.append(hour)
                    .append(":");
        if(minute<10){
            stringBuffer.append("0");
        }
        stringBuffer.append(minute);
        return stringBuffer.toString();
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public String getRing() {
        return ring;
    }

    public void setShake(int shake) {
        this.shake = shake;
    }

    public boolean getShake() {
        return Utils.int2boolean(shake);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getStatus() {
        return Utils.int2boolean(status);
    }
}
