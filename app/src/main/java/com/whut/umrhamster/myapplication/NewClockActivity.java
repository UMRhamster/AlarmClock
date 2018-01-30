package com.whut.umrhamster.myapplication;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.umrhamster.myapplication.CustomUI.CustomDialog;

public class NewClockActivity extends AppCompatActivity implements NumberPicker.OnScrollListener, NumberPicker.OnValueChangeListener,View.OnClickListener{
    //获取系统时间
    java.util.Calendar calendar;
    //
    private NumberPicker numberPickerRoughTime;
    private NumberPicker numberPickerHour;
    private NumberPicker numberPickerminute;
    //闹钟时间提示
    private TextView textViewTimeTip;
    //“重复”功能
    private RelativeLayout relativeLayoutRepetition;
    //自定义对话框Dialog
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clock);
        //初始化视图
        InitView();
        //初始化事件
        InitEvent();
       // InitNumberPicker();
    }
    protected void InitView(){
        numberPickerRoughTime = findViewById(R.id.newClock_roughTime_np);
        numberPickerHour = findViewById(R.id.newClock_hour_np);
        numberPickerminute = findViewById(R.id.newClock_minute_np);
        //设置上下午选择内容
        numberPickerRoughTime.setDisplayedValues(getResources().getStringArray(R.array.roughTime_display));
        numberPickerRoughTime.setMaxValue(1);
        numberPickerRoughTime.setMinValue(0);
        //设置小时选择内容
        numberPickerHour.setDisplayedValues(getResources().getStringArray(R.array.hour_display));
        numberPickerHour.setMaxValue(11);
        numberPickerHour.setMinValue(0);
        //设置分钟选择内容
        numberPickerminute.setDisplayedValues(getResources().getStringArray(R.array.minute_display));
        numberPickerminute.setMaxValue(59);
        numberPickerminute.setMinValue(0);
        //闹钟时间提示
        textViewTimeTip = findViewById(R.id.newClock_timeTip_tv);
        //“重复”功能
        relativeLayoutRepetition = findViewById(R.id.newClock_repetition_rl);
    }
    protected void InitEvent(){
        //上下午选择监听
        numberPickerRoughTime.setOnScrollListener(this);
        numberPickerRoughTime.setOnValueChangedListener(this);
        //小时选择监听
        numberPickerHour.setOnScrollListener(this);
        numberPickerHour.setOnValueChangedListener(this);
        //分钟选择监听
        numberPickerminute.setOnScrollListener(this);
        numberPickerminute.setOnValueChangedListener(this);
        //监听重复点击
        relativeLayoutRepetition.setOnClickListener(this);
    }
    protected void InitNumberPicker(){
        //获取当前系统时间，根据时间设置默认的闹钟时间
        calendar = java.util.Calendar.getInstance();
        int hour = calendar.get(java.util.Calendar.HOUR);
        int minute = calendar.get(java.util.Calendar.MINUTE);
        calendar.getTimeInMillis();   //毫秒表示
        System.currentTimeMillis();
    }
    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        //获取系统时间
        calendar = java.util.Calendar.getInstance();
        //小时
        int hour = calendar.get(java.util.Calendar.HOUR);
        //分钟
        int minute = calendar.get(java.util.Calendar.MINUTE);
        //上下午切换
        if(numberPicker.getId() == R.id.newClock_hour_np){
            if((i == 10 && i1 ==11) || (i ==11 && i1 ==10)){
                if(numberPickerRoughTime.getValue() == 0){
                    numberPickerRoughTime.setValue(1);
                }else {
                    numberPickerRoughTime.setValue(0);
                }
            }
        }
        if(numberPickerHour.getValue() == 11){
            String string = getResources().getStringArray(R.array.minute_display)[numberPickerminute.getValue()];
            if(numberPickerRoughTime.getValue() == 0){
                textViewTimeTip.setText(String.format(getResources().getString(R.string.morning),string));
            }else{
                textViewTimeTip.setText(String.format(getResources().getString(R.string.afternoon),string));
            }
        }else {

            textViewTimeTip.setText(getResources().getStringArray(R.array.hour_display)[numberPickerHour.getValue()]+"小时"+numberPickerminute.getValue()+"分钟后响铃");
        }
        //Toast.makeText(this,,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newClock_repetition_rl:
                customDialog = new CustomDialog(this);
                customDialog.setOnNonRepetitionClickListener(new CustomDialog.onNonRepetitionClickListener() {
                    @Override
                    public void onNonRepetitionClick() {
                        Toast.makeText(getApplicationContext(),"nonrepetiton click!",Toast.LENGTH_SHORT).show();
                    }
                });
                customDialog.setOnEverydayClickListener(new CustomDialog.onEverydayClickListener() {
                    @Override
                    public void onEveryDayClick() {
                        Toast.makeText(getApplicationContext(),"everyday click!",Toast.LENGTH_SHORT).show();
                    }
                });
                customDialog.setOnCustomTimeClickListener(new CustomDialog.onCustomTimeClickListener() {
                    @Override
                    public void onCustomTimeClick() {
                        Toast.makeText(getApplicationContext(),"customtime click!",Toast.LENGTH_SHORT).show();
                    }
                });
                //设置弹出位置
                Window window = customDialog.getWindow();
                window.getDecorView().setPadding(0,0,0,0);
                //window.setGravity(Gravity.BOTTOM);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount = 0.5f;
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                window.setAttributes(lp);
                customDialog.show();
                break;
            default:
                break;
        }
    }
}
