package com.whut.umrhamster.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AlarmClockAdapter.onItemClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Alarmmaster> alarmmasterList;
    private AlarmClockAdapter alarmClockAdapter;

    //新增闹钟
    private FloatingActionButton floatingActionButtonInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        InitView();
        alarmmasterList.add(new Alarmmaster(1,55,"每天","无铃声",0,"起床",1));
        alarmmasterList.add(new Alarmmaster(23,15,"每天","无铃声",1,"学习",0));
        alarmmasterList.add(new Alarmmaster(0,3,"不重复","无铃声",1,"学习",1));
        alarmmasterList.add(new Alarmmaster(11,0,"周一,二","无铃声",1,"学习",1));
        alarmmasterList.add(new Alarmmaster(12,55,"每天","无铃声",0,"学习",0));
        alarmClockAdapter.notifyDataSetChanged();
    }
    //初始化视图
    private void InitView(){
        recyclerView = findViewById(R.id.main_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //数据适配器
        alarmmasterList = new ArrayList<>();
        alarmClockAdapter = new AlarmClockAdapter(alarmmasterList,this);
        recyclerView.setAdapter(alarmClockAdapter);
        alarmClockAdapter.setOnItemClickListener(this);
        //新增闹钟
        floatingActionButtonInsert = findViewById(R.id.main_insert_fab);
        floatingActionButtonInsert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_insert_fab:
                Intent intent = new Intent(this,NewClockActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this,NewClockActivity.class);
        intent.putExtra("alarmClock",alarmmasterList.get(position));
        startActivity(intent);
    }
}
