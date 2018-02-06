package com.whut.umrhamster.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AlarmClockAdapter.onItemClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Alarmmaster> alarmmasterList;
    private AlarmClockAdapter alarmClockAdapter;

    //ToolBar上控件
    private TextView textViewCancel;
    private TextView textViewTitle;
    private TextView textViewEdit;
    //新增闹钟
    private FloatingActionButton floatingActionButtonInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        InitView();
        InitEvents();
        //InsertDataForTest();
        //InitDataFromDataBase();
        alarmmasterList.add(new Alarmmaster(1,55,"每天","无铃声",0,"起床",1));
        alarmmasterList.add(new Alarmmaster(23,15,"每天","无铃声",1,"学习",0));
        alarmmasterList.add(new Alarmmaster(0,3,"不重复","无铃声",1,"学习",1));
        alarmmasterList.add(new Alarmmaster(11,0,"每周一,二,三","无铃声",1,"学习",1));
        alarmmasterList.add(new Alarmmaster(12,55,"每周二,四,五,六","无铃声",0,"学习",0));
        alarmClockAdapter.notifyDataSetChanged();
    }
    public void InsertDataForTest(){
        LitePal.getDatabase();
        Alarmmaster alarmmaster = new Alarmmaster();
        alarmmaster.setHour(15);
        alarmmaster.setMinute(59);
        alarmmaster.setTag("学习");
        alarmmaster.setRepetition("每天");
        alarmmaster.setRing("无铃声");
        alarmmaster.setShake(1);
        alarmmaster.setStatus(1);
        alarmmaster.save();
    }
    //初始化视图
    private void InitView(){
        recyclerView = findViewById(R.id.main_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //数据适配器
        alarmmasterList = new ArrayList<>();
        //////////////////////////////////////test
        alarmClockAdapter = new AlarmClockAdapter(alarmmasterList,this);
        recyclerView.setAdapter(alarmClockAdapter);
        alarmClockAdapter.setOnItemClickListener(this);
        //新增闹钟
        floatingActionButtonInsert = findViewById(R.id.main_insert_fab);
        //ToolBar上
        textViewCancel = findViewById(R.id.main_cancel_tv);
        textViewTitle = findViewById(R.id.main_tb_tv);
        textViewEdit = findViewById(R.id.main_edit_tv);
    }
    private void InitEvents(){
        //新增闹钟
        floatingActionButtonInsert.setOnClickListener(this);
        //编辑
        textViewEdit.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
    }

    private void InitDataFromDataBase(){
        alarmmasterList.addAll(DataSupport.findAll(Alarmmaster.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_insert_fab:
                Intent intent = new Intent(this,NewClockActivity.class);
                startActivity(intent);
                break;
            case R.id.main_edit_tv:
                if(textViewEdit.getText().toString().equals("编辑")){
                    alarmClockAdapter.editStart();
                    textViewCancel.setVisibility(View.VISIBLE);
                    textViewEdit.setText("全选");
                }else {
                    alarmClockAdapter.allSelected();
                }
                textViewTitle.setText(String.format(getResources().getString(R.string.selectedNumber),alarmClockAdapter.getCheckedNumber()));
                break;
            case R.id.main_cancel_tv:
                alarmClockAdapter.cancelDelete();
                textViewEdit.setText("编辑");
                textViewTitle.setText("闹钟");
                textViewCancel.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if(textViewCancel.getVisibility() == View.VISIBLE){
            textViewTitle.setText(String.format(getResources().getString(R.string.selectedNumber),alarmClockAdapter.getCheckedNumber()));
        }else {
            Intent intent = new Intent(this,NewClockActivity.class);
            intent.putExtra("alarmClock",alarmmasterList.get(position));
            startActivity(intent);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        textViewTitle.setText(String.format(getResources().getString(R.string.selectedNumber),alarmClockAdapter.getCheckedNumber()));
        textViewCancel.setVisibility(View.VISIBLE);
        textViewEdit.setText("全选");
    }
}
