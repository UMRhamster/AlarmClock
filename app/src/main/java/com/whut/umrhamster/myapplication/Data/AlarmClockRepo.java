package com.whut.umrhamster.myapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.whut.umrhamster.myapplication.Alarmmaster;

/**
 * Created by 12421 on 2018/1/29.
 */

public class AlarmClockRepo {
    private AlarmClockDBHelper alarmClockDBHelper;
    //构造函数
    public AlarmClockRepo(Context context){
        alarmClockDBHelper = new AlarmClockDBHelper(context);
    }
    /*
     ******************************************增加*****************************************
     */
    public int insert(Alarmmaster alarmmaster){
        //打开连接，写入数据
        SQLiteDatabase sqLiteDatabase = alarmClockDBHelper.getWritableDatabase();
        //
        ContentValues contentValues = new ContentValues();
        contentValues.put(Alarmmaster.KEY_ROUGHTIME,alarmmaster.getRoughTime());   //粗略时间 上下午
        contentValues.put(Alarmmaster.KEY_HOUR,alarmmaster.getHour());             //小时
        contentValues.put(Alarmmaster.KEY_MINUTE,alarmmaster.getMinute());         //分钟
        contentValues.put(Alarmmaster.KEY_REPETITION,alarmmaster.getRepetition()); //重复
        contentValues.put(Alarmmaster.KEY_STATUS,alarmmaster.getStatus());         //状态 开或关
        //
        long id = sqLiteDatabase.insert(Alarmmaster.TABLE,null,contentValues);  //插入数据库
        sqLiteDatabase.close();  //关闭连接
        return (int)id;
    }
    /*
     ******************************************删除*****************************************
     */
    public void delete(int id){
        SQLiteDatabase sqLiteDatabase = alarmClockDBHelper.getReadableDatabase();
        sqLiteDatabase.delete(Alarmmaster.TABLE,Alarmmaster.KEY_ID+"=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
    /*
     ******************************************修改*****************************************
     */
    public void update(Alarmmaster alarmmaster){
        SQLiteDatabase sqLiteDatabase = alarmClockDBHelper.getWritableDatabase();
        //
        ContentValues contentValues = new ContentValues();
        contentValues.put(Alarmmaster.KEY_ROUGHTIME,alarmmaster.getRoughTime());
        contentValues.put(Alarmmaster.KEY_HOUR,alarmmaster.getHour());
        contentValues.put(Alarmmaster.KEY_MINUTE,alarmmaster.getMinute());
        contentValues.put(Alarmmaster.KEY_REPETITION,alarmmaster.getRepetition());
        contentValues.put(Alarmmaster.KEY_STATUS,alarmmaster.getStatus());
        //
        sqLiteDatabase.update(Alarmmaster.TABLE,contentValues,Alarmmaster.KEY_ID+"=?",new String[]{String.valueOf(alarmmaster.getId())});
        sqLiteDatabase.close();
    }
}
