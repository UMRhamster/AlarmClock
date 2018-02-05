package com.whut.umrhamster.myapplication.CustomUI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.whut.umrhamster.myapplication.R;

/**
 * Created by 12421 on 2018/2/1.
 */

public class CustomEditDialog extends Dialog implements View.OnClickListener{
    //控件
    private TextView textViewOK;
    private TextView textViewCancel;
    private EditText editTextTag;
    //监听器
    private onOKClickListener OKClickListener;
    private onCancelClickListener CancelClickListener;
    //标签内容
    private String tag;
    public CustomEditDialog(@NonNull Context context,String tag) {
        super(context, R.style.CustomDialog);//使用自定义主题
        this.tag = tag;
    }

    public void setOnOKClickListener(onOKClickListener OKClickListener){
        this.OKClickListener = OKClickListener;
    }

    public void setOnCancelClickListener(onCancelClickListener CancelClickListener){
        this.CancelClickListener = CancelClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customeditdialog);
        InitView();
        InitEvent();
    }

    private void InitView(){
        textViewOK = findViewById(R.id.customEditDialog_ok_tv);
        textViewCancel = findViewById(R.id.customEditDialog_cancel_tv);
        editTextTag = findViewById(R.id.customEdtDialog_et);
        if(!tag.equals("无")){
            editTextTag.setText(tag);                 //设置标签
            editTextTag.setSelection(tag.length());   //移动光标到文字末尾
        }
    }

    private void InitEvent(){
        textViewOK.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.customEditDialog_ok_tv:
                if(OKClickListener != null){
                    if(editTextTag.getText().toString().isEmpty()){
                        OKClickListener.onOKClock("无");
                    }else {
                        OKClickListener.onOKClock(editTextTag.getText().toString());
                }
                }
                dismiss();
                break;
            case R.id.customEditDialog_cancel_tv:
                dismiss();
                break;
            default:
                break;
        }
    }

    //接口
    public interface onOKClickListener{
        void onOKClock(String tag);
    }

    public interface onCancelClickListener{
        void onCancelClick();
    }
}
