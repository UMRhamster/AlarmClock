package com.whut.umrhamster.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QusetionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qusetion);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
