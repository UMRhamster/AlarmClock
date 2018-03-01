package com.whut.umrhamster.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.whut.umrhamster.myapplication.Utils.Utils;

import org.litepal.crud.DataSupport;

/**
 * Created by 12421 on 2018/2/8.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("1","闹钟响了!!!!!!!!!!!!!!");
        Alarmmaster alarmmaster = (Alarmmaster) intent.getSerializableExtra("clockAlarm");
        Log.d("alarm", "id:"+alarmmaster.getId()
                +"hour:"+alarmmaster.getHour()
                +"minute:"+alarmmaster.getMinute()
                +"repetition:"+alarmmaster.getRepetition()
                +"tag:"+alarmmaster.getTag()
                +"status"+alarmmaster.getStatus());
        Intent i = new Intent(context,AlarmService.class);
        context.startService(i);
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{600,1000,600,1000,600,1000},-1);

        if(alarmmaster.getRepetition().equals("每天")){
            Intent intent1 = new Intent(context,AlarmReceiver.class);
            intent1.setAction("com.whut.umrhamster.alarmclock");
            intent1.putExtra("clockAlarm",alarmmaster);
            Utils.setAlarmTime(context,alarmmaster.getHour(),alarmmaster.getMinute(),alarmmaster.getRepetition(),alarmmaster.getId(),intent1);
        }else if(alarmmaster.getRepetition().equals("不重复")){
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(PendingIntent.getBroadcast(context,alarmmaster.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT));
            //修改数据库中的值
            Alarmmaster a = DataSupport.find(Alarmmaster.class,alarmmaster.getId());
            a.setStatus(0);
            a.save();
            Log.d("AlarmReceiver",""+alarmmaster.getId());
            //alarmmaster.save();
            //alarmmaster.update(alarmmaster.getId());  //一次性闹钟响铃完毕，更改闹钟状态为关闭，并修改数据库
        }else {
            Intent intent2 = new Intent(context,AlarmReceiver.class);
            intent2.setAction("com.whut.umrhamster.alarmclock");
            intent2.putExtra("clockAlarm",alarmmaster);
            int roughTime;
            int hour;
            if(alarmmaster.getHour() < 12){
                roughTime = 0;
            }else {
                roughTime = 1;
                hour = alarmmaster.getHour() - 12;
            }
            //Utils.TimeCalculate(roughTime,)
            //2018.2.26
        }
    }
}
