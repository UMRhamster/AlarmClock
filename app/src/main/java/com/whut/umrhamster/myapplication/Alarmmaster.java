package com.whut.umrhamster.myapplication;

/**
 * Created by 12421 on 2018/1/26.
 */

public class Alarmmaster {
    String roughTime;         //粗略时间
    String exactTime;         //精确时间
    String repetition;        //重复

    public Alarmmaster(){}

    public Alarmmaster(String roughTime, String exactTime, String repetition){
        this.roughTime = roughTime;
        this.exactTime = exactTime;
        this.repetition = repetition;
    }

    public void setRoughTime(String roughTime) {
        this.roughTime = roughTime;
    }

    public String getRoughTime() {
        return roughTime;
    }

    public void setExactTime(String exactTime) {
        this.exactTime = exactTime;
    }

    public String getExactTime() {
        return exactTime;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getRepetition() {
        return repetition;
    }
}
