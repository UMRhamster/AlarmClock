package com.whut.umrhamster.myapplication.CustomUI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whut.umrhamster.myapplication.R;
import com.whut.umrhamster.myapplication.Utils.Utils;

/**
 * Created by 12421 on 2018/2/3.
 */

public class CustomWeekDialog extends Dialog implements View.OnClickListener{
    private String repetition;
    //
    private onWeekOKClickListener WeekOKClickListener;
    //
    private RelativeLayout[] relativeLayoutsWeek = new RelativeLayout[7];
    //
    private SmoothCheckBox[] smoothCheckBoxesWeek = new SmoothCheckBox[7];
    private boolean[] issChecked = new boolean[7];
    //
    private TextView textViewCancel;
    private TextView textViewOK;
    public CustomWeekDialog(@NonNull Context context, String repetition) {
        super(context,R.style.CustomDialog);
        this.repetition = repetition;
    }

    public void setOnWeekOKClickListener(onWeekOKClickListener WeekOKClickListener){
        this.WeekOKClickListener = WeekOKClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customweekdialog);
        InitView();
        InitEvent();
        InitData();
    }

    private void InitView(){
        relativeLayoutsWeek[0] = findViewById(R.id.customWeekDialog_monday_rl);
        relativeLayoutsWeek[1] = findViewById(R.id.customWeekDialog_tuesday_rl);
        relativeLayoutsWeek[2] = findViewById(R.id.customWeekDialog_wednesday_rl);
        relativeLayoutsWeek[3] = findViewById(R.id.customWeekDialog_thursday_rl);
        relativeLayoutsWeek[4] = findViewById(R.id.customWeekDialog_friday_rl);
        relativeLayoutsWeek[5] = findViewById(R.id.customWeekDialog_saturday_rl);
        relativeLayoutsWeek[6] = findViewById(R.id.customWeekDialog_sunday_rl);
        //
        smoothCheckBoxesWeek[0] = findViewById(R.id.customWeekDialog_monday_scb);
        smoothCheckBoxesWeek[1] = findViewById(R.id.customWeekDialog_tuesday_scb);
        smoothCheckBoxesWeek[2] = findViewById(R.id.customWeekDialog_wednesday_scb);
        smoothCheckBoxesWeek[3] = findViewById(R.id.customWeekDialog_thursday_scb);
        smoothCheckBoxesWeek[4] = findViewById(R.id.customWeekDialog_friday_scb);
        smoothCheckBoxesWeek[5] = findViewById(R.id.customWeekDialog_saturday_scb);
        smoothCheckBoxesWeek[6] = findViewById(R.id.customWeekDialog_sunday_scb);
        //
        textViewCancel = findViewById(R.id.customWeekDialog_cancel_tv);
        textViewOK = findViewById(R.id.customWeekDialog_ok_tv);
        //
        for(int i=0; i<7; i++){
            issChecked[i] = false;
        }
    }
    private void InitEvent(){
        for(int i =0; i<7; i++){
            relativeLayoutsWeek[i].setOnClickListener(this);
        }
        //
        textViewCancel.setOnClickListener(this);
        textViewOK.setOnClickListener(this);
    }

    private void InitData(){
        if(!(repetition.equals("不重复") || repetition.equals("每天"))){
            String[] repe = repetition.split("每周|,");
            for(int i=1; i<repe.length; i++){
                smoothCheckBoxesWeek[Utils.String2int(repe[i])].setChecked(true);
                issChecked[Utils.String2int(repe[i])] = true;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.customWeekDialog_monday_rl:
                if(!issChecked[0]){
                    smoothCheckBoxesWeek[0].setChecked(true,true);
                    issChecked[0] = true;
                }else {
                    smoothCheckBoxesWeek[0].setChecked(false,true);
                    issChecked[0] = false;
                }
                break;
            case R.id.customWeekDialog_tuesday_rl:
                if(!issChecked[1]){
                    smoothCheckBoxesWeek[1].setChecked(true,true);
                    issChecked[1] = true;
                }else {
                    smoothCheckBoxesWeek[1].setChecked(false,true);
                    issChecked[1] = false;
                }
                break;
            case R.id.customWeekDialog_wednesday_rl:
                if(!issChecked[2]){
                    smoothCheckBoxesWeek[2].setChecked(true,true);
                    issChecked[2] = true;
                }else {
                    smoothCheckBoxesWeek[2].setChecked(false,true);
                    issChecked[2] = false;
                }
                break;
            case R.id.customWeekDialog_thursday_rl:
                if(!issChecked[3]){
                    smoothCheckBoxesWeek[3].setChecked(true,true);
                    issChecked[3] = true;
                }else {
                    smoothCheckBoxesWeek[3].setChecked(false,true);
                    issChecked[3] = false;
                }
                break;
            case R.id.customWeekDialog_friday_rl:
                if(!issChecked[4]){
                    smoothCheckBoxesWeek[4].setChecked(true,true);
                    issChecked[4] = true;
                }else {
                    smoothCheckBoxesWeek[3].setChecked(false,true);
                    issChecked[4] = false;
                }
                break;
            case R.id.customWeekDialog_saturday_rl:
                if(!issChecked[5]){
                    smoothCheckBoxesWeek[5].setChecked(true,true);
                    issChecked[5] = true;
                }else {
                    smoothCheckBoxesWeek[5].setChecked(false,true);
                    issChecked[5] = false;
                }
                break;
            case R.id.customWeekDialog_sunday_rl:
                if(!issChecked[6]){
                    smoothCheckBoxesWeek[6].setChecked(true,true);
                    issChecked[6] = true;
                }else {
                    smoothCheckBoxesWeek[6].setChecked(false,true);
                    issChecked[6] = false;
                }
                break;
            case R.id.customWeekDialog_cancel_tv:
                dismiss();
                break;
            case R.id.customWeekDialog_ok_tv:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("每周");
                for (int i=0; i<7; i++){
                    if (issChecked[i]){
                        stringBuilder.append(Utils.int2String(i));
                    }
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                WeekOKClickListener.onWeekOKClick(stringBuilder.toString());
                dismiss();
                break;
            default:
                break;
        }
    }
    //接口
    public interface onWeekOKClickListener{
        void onWeekOKClick(String week);
    }
}
