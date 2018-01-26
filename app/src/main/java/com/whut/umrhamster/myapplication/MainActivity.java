package com.whut.umrhamster.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Alarmmaster> alarmmasterList;
    private AlarmClockAdapter alarmClockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        InitView();
        alarmmasterList.add(new Alarmmaster("上午","08:00","每天"));
        alarmmasterList.add(new Alarmmaster("上午","08:02","每天"));
        alarmmasterList.add(new Alarmmaster("上午","08:23","每天"));
        alarmmasterList.add(new Alarmmaster("下午","05:00","周一"));
        alarmmasterList.add(new Alarmmaster("上午","06:20","周一,二"));
        alarmClockAdapter.notifyDataSetChanged();
    }
    //初始化视图
    private void InitView(){
        recyclerView = (RecyclerView) findViewById(R.id.main_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //数据适配器
        alarmmasterList = new ArrayList<Alarmmaster>();
        alarmClockAdapter = new AlarmClockAdapter(alarmmasterList,this);
        recyclerView.setAdapter(alarmClockAdapter);
    }
}
