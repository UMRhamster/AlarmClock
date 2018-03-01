package com.whut.umrhamster.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AlarmClockAdapter.onItemClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Alarmmaster> alarmmasterList;
    private List<Boolean> alarmListChecked; //闹钟选中与否
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
        //test
//        Calendar calendar = Calendar.getInstance();
//        Log.d("year",String.valueOf(calendar.get(Calendar.YEAR)));
//        Log.d("hour",""+calendar.get(Calendar.HOUR));
//        Log.d("hour of day",""+calendar.get(Calendar.HOUR_OF_DAY));
//        Log.d("month",String.valueOf(calendar.get(Calendar.MONTH)));
//        Log.d("day",String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
//        Log.d("day of week", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
//        Log.d("week",String.valueOf(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)));
        //
        //初始化
        InitView();
        InitEvents();
        //InitDataFromDataBase();
        alarmClockAdapter.notifyDataSetChanged();
    }
    //初始化视图
    private void InitView(){
        recyclerView = findViewById(R.id.main_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //数据适配器
        alarmmasterList = new ArrayList<>();
        alarmListChecked = new ArrayList<>();//闹钟选中与否
        alarmClockAdapter = new AlarmClockAdapter(alarmmasterList,alarmListChecked,this);
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
        LitePal.getDatabase();
        alarmmasterList.addAll(DataSupport.findAll(Alarmmaster.class));
        for(int i=0;i<alarmmasterList.size();i++){
            alarmListChecked.add(false);
        }
    }

    @Override
    public void onClick(View view) {
        //test for service
        switch (view.getId()){
            case R.id.main_insert_fab:
                if (textViewEdit.getText().toString().equals("编辑")){
                    Intent intent = new Intent(this,NewClockActivity.class);
                    startActivityForResult(intent,1);
                    //startActivity(intent);
                }else {
                    for(int a = alarmListChecked.size()-1; a >= 0 ; a--){
                        if(alarmListChecked.get(a)) {
                            alarmmasterList.get(a).delete();      //删除数据库中的值
                            alarmmasterList.remove(a);            //删除recycleView中的显示
                            alarmListChecked.remove(a);           //同时删除同position的选中信息
                        }
                        alarmClockAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.main_edit_tv:
                //test for service
                if(textViewEdit.getText().toString().equals("编辑")){
                    alarmClockAdapter.editStart();
                    textViewCancel.setVisibility(View.VISIBLE);
                    floatingActionButtonInsert.setImageResource(R.drawable.delete);
                    floatingActionButtonInsert.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themeColor)));
                    textViewEdit.setText("全选");
                }else {
                    alarmClockAdapter.allSelected();
                }
                textViewTitle.setText(String.format(getResources().getString(R.string.selectedNumber),alarmClockAdapter.getCheckedNumber()));
                break;
            case R.id.main_cancel_tv:
                //test for service
                alarmClockAdapter.cancelDelete();
                textViewEdit.setText("编辑");
                textViewTitle.setText("闹钟");
                textViewCancel.setVisibility(View.GONE);
                floatingActionButtonInsert.setImageResource(R.drawable.insert);
                floatingActionButtonInsert.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                //取消删除时，恢复所有的选中信息为未选中，即false
                for(int i = 0; i<alarmListChecked.size();i++){
                    if(alarmListChecked.get(i)){
                        alarmListChecked.set(i,false);
                        alarmClockAdapter.notifyItemChanged(i);
                    }
                }
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
            //Log.d("position",String.valueOf(position));
            //Log.d("id",String.valueOf(alarmmasterList.get(position).getId()));
            intent.putExtra("position",position);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        textViewTitle.setText(String.format(getResources().getString(R.string.selectedNumber),alarmClockAdapter.getCheckedNumber()));
        textViewCancel.setVisibility(View.VISIBLE);
        textViewEdit.setText("全选");
        floatingActionButtonInsert.setImageResource(R.drawable.delete);
        floatingActionButtonInsert.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.themeColor)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 & resultCode == 1){
            //Alarmmaster a = (Alarmmaster) data.getSerializableExtra("editClock");
            //Log.d("a",String.valueOf(a.getHour()));
            alarmmasterList.add((Alarmmaster) data.getSerializableExtra("editClock"));
            alarmListChecked.add(false);
            //alarmClockAdapter.notifyItemChanged(alarmmasterList.size()-1);
        }else if(requestCode == 2 & resultCode == 2){
            int position = data.getIntExtra("backPosition",-1);
            alarmmasterList.set(position,(Alarmmaster) data.getSerializableExtra("editClock"));
            //alarmClockAdapter.notifyItemChanged(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume");
        alarmmasterList.clear();
        alarmListChecked.clear();
        InitDataFromDataBase();
        alarmClockAdapter.notifyDataSetChanged();
    }
}
