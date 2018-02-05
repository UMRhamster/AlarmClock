package com.whut.umrhamster.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

import com.whut.umrhamster.myapplication.CustomUI.CustomDialog;
import com.whut.umrhamster.myapplication.CustomUI.CustomEditDialog;
import com.whut.umrhamster.myapplication.Utils.Utils;

public class NewClockActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener,View.OnClickListener{
    //获取系统时间
    private Calendar calendar;
    //监听系统时间变化
    private TimeChangeReceiver timeChangeReceiver;
    //控件
    private NumberPicker numberPickerRoughTime;
    private NumberPicker numberPickerHour;
    private NumberPicker numberPickerMinute;
    private TextView textViewComplete;        //工具栏“完成”
    private TextView textViewCancel;          //工具栏“取消”
    private TextView textViewTitle;
    private TextView textViewTag;
    private TextView textViewRepetition;
    private TextView textViewRing;
    private Switch switchShake;
    //闹钟时间提示
    private TextView textViewTimeTip;
    //闹钟设置
    private RelativeLayout relativeLayoutRepetition;  //“重复”功能
    private RelativeLayout relativeLayoutTag;         //“标签”功能
    private RelativeLayout relativeLayoutRing;        //“铃声”功能
    //闹钟对象
    private Alarmmaster alarmmaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_clock);
        //初始化视图
        InitView();
        //初始化事件
        InitEvent();
        //初始化数据
        InitData();
        //注册广播，监听分钟变化
        IntentFilter intentFilter;
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(timeChangeReceiver,intentFilter);
    }
    //初始化视图
    protected void InitView(){
        numberPickerRoughTime = findViewById(R.id.newClock_roughTime_np);
        numberPickerHour = findViewById(R.id.newClock_hour_np);
        numberPickerMinute = findViewById(R.id.newClock_minute_np);
        textViewTitle = findViewById(R.id.newClock_tb_tv);
        textViewComplete = findViewById(R.id.newClock_complete_tv); //“完成”
        textViewCancel = findViewById(R.id.newClock_cancel_tv);     //“取消”
        textViewTag = findViewById(R.id.newClock_tag_show_tv);
        textViewRepetition = findViewById(R.id.newClock_repetition_show_tv);
        textViewRing = findViewById(R.id.newClock_ring_show_tv);
        switchShake = findViewById(R.id.newClock_shake_st);
        //设置上下午选择内容
        numberPickerRoughTime.setDisplayedValues(getResources().getStringArray(R.array.roughTime_display));
        numberPickerRoughTime.setMaxValue(1);
        numberPickerRoughTime.setMinValue(0);
        //设置小时选择内容
        numberPickerHour.setDisplayedValues(getResources().getStringArray(R.array.hour_display));
        numberPickerHour.setMaxValue(11);
        numberPickerHour.setMinValue(0);
        //设置分钟选择内容
        numberPickerMinute.setDisplayedValues(getResources().getStringArray(R.array.minute_display));
        numberPickerMinute.setMaxValue(59);
        numberPickerMinute.setMinValue(0);
        //闹钟时间提示
        textViewTimeTip = findViewById(R.id.newClock_timeTip_tv);
        //闹钟设置
        relativeLayoutRepetition = findViewById(R.id.newClock_repetition_rl); //“重复”
        relativeLayoutTag = findViewById(R.id.newClock_tag_rl);               //“标签”
        relativeLayoutRing = findViewById(R.id.newClock_ring_rl);             //“铃声”
    }
    //初始化事件
    protected void InitEvent(){
        //上下午选择监听
        numberPickerRoughTime.setOnValueChangedListener(this);
        //小时选择监听
        numberPickerHour.setOnValueChangedListener(this);
        //分钟选择监听
        numberPickerMinute.setOnValueChangedListener(this);
        //
        textViewComplete.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
        //闹钟设置
        relativeLayoutRepetition.setOnClickListener(this); //“重复”
        relativeLayoutTag.setOnClickListener(this);        //“标签”
        relativeLayoutRing.setOnClickListener(this);       //“铃声”
    }
    //初始化数据
    protected void InitData(){
        alarmmaster = (Alarmmaster) getIntent().getSerializableExtra("alarmClock");
        if(alarmmaster != null){
            //设置标题为“编辑闹钟”
            textViewTitle.setText("编辑闹钟");
            //设置数字选择器
            NumberPickerSet(alarmmaster.getHour(),alarmmaster.getMinute());
            //设置
            TimeTipSet();   //剩余时间设置
            textViewRepetition.setText(alarmmaster.getRepetition()); //设置重复
            textViewRing.setText(alarmmaster.getRing());             //设置铃声
            textViewTag.setText(alarmmaster.getTag());               //设置标签
            switchShake.setChecked(alarmmaster.getShake());          //设置震动
        }else {
            //初始化数字选择器
            InitNumberPicker();
            alarmmaster = new Alarmmaster();
            alarmmaster.setRepetition(getResources().getString(R.string.non_repetition)); //设置默认不重复
        }
    }
    //初始化时间选择器
    protected void InitNumberPicker(){
        //获取当前系统时间，根据时间设置默认的闹钟时间
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //设置数字选择器
        NumberPickerSet(hour,minute);
        textViewTimeTip.setText(getResources().getString(R.string.defaultTimeTip));
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
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
        TimeTipSet();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newClock_repetition_rl:
                //弹出对话框
                CustomDialog customDialog = new CustomDialog(this, alarmmaster.getRepetition());
                customDialog.setOnNonRepetitionClickListener(new CustomDialog.onNonRepetitionClickListener() {
                    @Override
                    public void onNonRepetitionClick() {
                        textViewRepetition.setText(getResources().getString(R.string.non_repetition)); //不重复
                        alarmmaster.setRepetition(getResources().getString(R.string.non_repetition));
                    }
                });
                customDialog.setOnEverydayClickListener(new CustomDialog.onEverydayClickListener() {
                    @Override
                    public void onEveryDayClick() {
                        textViewRepetition.setText(getResources().getString(R.string.everyDay));  //每天
                        alarmmaster.setRepetition(getResources().getString(R.string.everyDay));
                    }
                });
                customDialog.setOnCustomTimeClickListener(new CustomDialog.onCustomTimeClickListener() {
                    @Override
                    public void onCustomTimeClick(String custom) {
                        textViewRepetition.setText(custom);
                        alarmmaster.setRepetition(custom);
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
            case R.id.newClock_tag_rl:
                CustomEditDialog customEditDialog = new CustomEditDialog(this,textViewTag.getText().toString());
                customEditDialog.setOnOKClickListener(new CustomEditDialog.onOKClickListener() {
                    @Override
                    public void onOKClock(String tag) {
                        textViewTag.setText(tag);
                    }
                });
                customEditDialog.show();
                break;
            case R.id.newClock_ring_rl:
                startActivity(new Intent(this,RingChooseActivity.class));
                overridePendingTransition(R.anim.actionsheet_ringchooseactivity_in,R.anim.actionsheet_newclockactivity_out);
                break;
            case R.id.newClock_complete_tv:
                NewClockComplete();
                break;
            case R.id.newClock_cancel_tv:
                finish();
                break;
            default:
                break;
        }
    }
    class TimeChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intent.ACTION_TIME_TICK:
                    TimeTipSet();
                    break;
            }
        }
    }
    //设置闹钟剩余时间提示
    public void TimeTipSet(){
        if(numberPickerHour.getValue() == 11){    //小时为12
            String minute = getResources().getStringArray(R.array.minute_display)[numberPickerMinute.getValue()]; //得到时间选择器上的分钟
            if(numberPickerRoughTime.getValue() == 0){
                //上午
                textViewTimeTip.setText(String.format(getResources().getString(R.string.morning),minute)); //凌晨12:xx闹钟响
            }else{
                //下午
                textViewTimeTip.setText(String.format(getResources().getString(R.string.afternoon),minute));//中午12:xx闹钟响
            }
        }else {
            //计算出小时和分钟
            int[] time = Utils.TimeCalculate(numberPickerRoughTime.getValue(),numberPickerHour.getValue(),numberPickerMinute.getValue());
            if(time[0] == 0){    //剩余0小时
                if(time[1] == 0){    //剩余0分钟
                    textViewTimeTip.setText(getResources().getString(R.string.lessThanOneMinute));//不到1分钟后闹钟响
                }else {
                    textViewTimeTip.setText(String.format(getResources().getString(R.string.justMinute),time[1]));//xx分钟后闹钟响
                }
            }else {
                textViewTimeTip.setText(String.format(getResources().getString(R.string.clockTime),time[0],time[1]));//xx小时xx分钟后闹钟响
            }
        }
    }
    //设置时间选择器
    public void NumberPickerSet(int hour, int minute){
        if(hour >=12 ){   //下午
            if(hour > 12){
                numberPickerHour.setValue(hour-13);
            }else {
                numberPickerHour.setValue(11);
            }
            numberPickerRoughTime.setValue(1);
        }else {    //上午
            if(hour > 0){
                numberPickerHour.setValue(hour-1);
            }else {
                numberPickerHour.setValue(11);
            }
            numberPickerRoughTime.setValue(0);
        }
        numberPickerMinute.setValue(minute);
    }
    //完成“新建闹钟”或者“编辑闹钟”
    public void NewClockComplete(){
        if(numberPickerRoughTime.getValue() == 0){
            alarmmaster.setHour(numberPickerHour.getValue()+1);
        }else {
            alarmmaster.setHour(numberPickerHour.getValue()+13);
        }
        alarmmaster.setMinute(numberPickerMinute.getValue());
        alarmmaster.setRepetition(textViewRepetition.getText().toString());
        alarmmaster.setRing("无铃声");
        alarmmaster.setTag(textViewTag.getText().toString());
        alarmmaster.setShake(Utils.boolean2int(switchShake.isChecked()));
        alarmmaster.setStatus(1);             //刚编辑好的闹钟默认为打开状态
    }
    @Override
    protected void onDestroy() {
        if(timeChangeReceiver != null){
            unregisterReceiver(timeChangeReceiver);
            timeChangeReceiver = null;
        }
        super.onDestroy();
    }
}
