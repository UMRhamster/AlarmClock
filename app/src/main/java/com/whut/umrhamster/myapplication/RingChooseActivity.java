package com.whut.umrhamster.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RingChooseActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageViewBack;   //返回
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_choose);
        InitView();
        InitEvent();
    }

    protected void InitView(){
        imageViewBack = findViewById(R.id.ringChoose_back);
    }

    protected void InitEvent(){
        imageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ringChoose_back:
                finish();
                overridePendingTransition(R.anim.actionsheet_newclockactivity_in,R.anim.actionsheet_ringchooseactivity_out);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.actionsheet_newclockactivity_in,R.anim.actionsheet_ringchooseactivity_out);
    }
}
