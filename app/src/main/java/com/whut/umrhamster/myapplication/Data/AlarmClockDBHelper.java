package com.whut.umrhamster.myapplication.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whut.umrhamster.myapplication.Alarmmaster;

/**
 * Created by 12421 on 2018/1/29.
 */

public class AlarmClockDBHelper extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION = 1;
    //数据库名字
    private static final String DATABASE_NAME = "clock.db";

    public AlarmClockDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据表
        String CREATE_TABLE_ALARMCLOCK = "CREATE TABLE "
                +Alarmmaster.TABLE + "("
                +Alarmmaster.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Alarmmaster.KEY_ROUGHTIME + " TEXT,"
                +Alarmmaster.KEY_HOUR + " TEXT,"
                +Alarmmaster.KEY_MINUTE + " TEXT,"
                +Alarmmaster.KEY_REPETITION + " TEXT,"
                +Alarmmaster.KEY_STATUS + "TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_ALARMCLOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //如果旧表存在，删除
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Alarmmaster.TABLE);
        //再次创建表
        onCreate(sqLiteDatabase);
    }
}
