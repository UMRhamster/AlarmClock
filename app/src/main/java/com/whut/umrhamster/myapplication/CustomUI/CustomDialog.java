package com.whut.umrhamster.myapplication.CustomUI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whut.umrhamster.myapplication.R;

/**
 * Created by 12421 on 2018/1/29.
 */

public class CustomDialog extends Dialog implements View.OnClickListener{
    private String repetition;
    private RelativeLayout relativeLayoutNonRepetition; //不重复
    private RelativeLayout relativeLayoutEveryday;      //每天
    private RelativeLayout relativeLayoutCustomTime;    //自定义时间
    private TextView textViewCancel;                    //取消
    //右边勾号图片
    private ImageView imageViewNonRepetition;
    private ImageView imageViewEveryday;
    private ImageView imageViewCustom;
    //监听器
    private onNonRepetitionClickListener nonRepetitionClickListener;
    private onEverydayClickListener everydayClickListener;
    private onCustomTimeClickListener customTimeClickListener;

    public CustomDialog(@NonNull Context context, String repetition) {
        super(context,R.style.CustomDialog);//使用自定义主题
        this.repetition = repetition;
    }

    public void setOnNonRepetitionClickListener(onNonRepetitionClickListener nonRepetitionClickListener){
        this.nonRepetitionClickListener = nonRepetitionClickListener;
    }

    public void setOnEverydayClickListener(onEverydayClickListener everydayClickListener){
        this.everydayClickListener = everydayClickListener;
    }

    public void setOnCustomTimeClickListener(onCustomTimeClickListener customTimeClickListener){
        this.customTimeClickListener = customTimeClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customdialog);
        //点击空白处可取消
        setCanceledOnTouchOutside(true);
        //初始化视图
        InitView();
        //初始化事件
        InitEvent();
    }

    protected void InitView(){
        relativeLayoutNonRepetition = findViewById(R.id.customDialog_non_repetition_rl);
        relativeLayoutEveryday = findViewById(R.id.customDialog_everyday_rl);
        relativeLayoutCustomTime = findViewById(R.id.customDialog_customTime_rl);
        textViewCancel = findViewById(R.id.customDialog_cancel);
        //勾号图片
        imageViewNonRepetition = findViewById(R.id.customDialog_non_repetition_iv);
        imageViewEveryday = findViewById(R.id.customDialog_everyday_iv);
        imageViewCustom = findViewById(R.id.customDialog_customTime_iv);
        if(repetition.equals("不重复")){
            imageViewNonRepetition.setVisibility(View.VISIBLE);
        }else if(repetition.equals("每天")){
            imageViewEveryday.setVisibility(View.VISIBLE);
        }else {
            imageViewCustom.setVisibility(View.VISIBLE);
        }
    }

    protected void InitEvent(){
        relativeLayoutNonRepetition.setOnClickListener(this);
        relativeLayoutEveryday.setOnClickListener(this);
        relativeLayoutCustomTime.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.customDialog_non_repetition_rl:
                //显示控制
                imageViewNonRepetition.setVisibility(View.VISIBLE);
                imageViewEveryday.setVisibility(View.INVISIBLE);
                imageViewCustom.setVisibility(View.INVISIBLE);
                //事件处理
                nonRepetitionClickListener.onNonRepetitionClick();
                break;
            case R.id.customDialog_everyday_rl:
                //显示控制
                imageViewNonRepetition.setVisibility(View.INVISIBLE);
                imageViewEveryday.setVisibility(View.VISIBLE);
                imageViewCustom.setVisibility(View.INVISIBLE);
                //事件处理
                everydayClickListener.onEveryDayClick();
                break;
            case R.id.customDialog_customTime_rl:
                //显示控制
                imageViewNonRepetition.setVisibility(View.INVISIBLE);
                imageViewEveryday.setVisibility(View.INVISIBLE);
                imageViewCustom.setVisibility(View.VISIBLE);
                //时间处理
                customTimeClickListener.onCustomTimeClick("周一,二,三,四,五");
                break;
            case R.id.customDialog_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    //接口
    public interface onNonRepetitionClickListener{
        void onNonRepetitionClick();
    }
    public interface onEverydayClickListener{
        void onEveryDayClick();
    }
    public interface onCustomTimeClickListener{
        void onCustomTimeClick(String custom);
    }
}
