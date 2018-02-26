package com.whut.umrhamster.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.whut.umrhamster.myapplication.CustomUI.SmoothCheckBox;
import com.whut.umrhamster.myapplication.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 12421 on 2018/1/26.
 */

public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockAdapter.ViewHolder> implements View.OnClickListener,View.OnLongClickListener{
    private List<Alarmmaster> alarmmasterList;
    private List<Boolean> alarmmasterCheck;
    private boolean isShow = false;
    private Context context;

    private onItemClickListener ItemClickListener;
    //构造函数
    public AlarmClockAdapter(List<Alarmmaster> alarmmasterList, List<Boolean> alarmmasterCheck, Context context){
        this.alarmmasterList = alarmmasterList;
        this.alarmmasterCheck = alarmmasterCheck;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_insert,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Log.d("222222222222","bvhooooooolder"+String.valueOf(position));
        final Alarmmaster alarmmaster = alarmmasterList.get(position);
        holder.textViewRoughTime.setText(Utils.getRoughTime(alarmmaster.getHour()));
        holder.textViewExactTime.setText(Utils.getExactTime(alarmmaster.getHour(),alarmmaster.getMinute()));
        holder.textViewRepetition.setText(alarmmaster.getRepetition());
        holder.aSwitchControl.setChecked(Utils.int2boolean(alarmmaster.getStatus()));
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        //
        if(isShow){
            holder.smoothCheckBoxDelete.setVisibility(View.VISIBLE);    //显示删除勾选
            holder.aSwitchControl.setVisibility(View.INVISIBLE);
            holder.smoothCheckBoxDelete.setChecked(isItemChecked(position));
        }else {
            holder.smoothCheckBoxDelete.setVisibility(View.GONE);
            holder.aSwitchControl.setVisibility(View.VISIBLE);
        }
        //处理闹钟开关///////////////////////////////////////////////////////////////////////////
        holder.aSwitchControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = new Intent(context,AlarmReceiver.class);
                intent.setAction("com.whut.umrhamster.alarmclock");
                intent.putExtra("clockAlarm",alarmmasterList.get(position));

                if(b){
                    Utils.setAlarmTime(context,alarmmaster.getHour(),alarmmaster.getMinute(),alarmmaster.getRepetition(),alarmmaster.getId(),intent);
                }else {
                    //context.stopService(intent);
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////
    }

    private void setItemChecked(int position, boolean isChecked){
        alarmmasterCheck.set(position,isChecked);
    }

    private boolean isItemChecked(int position){
        return alarmmasterCheck.get(position);
    }
    @Override
    public int getItemCount() {
        return alarmmasterList.size();
    }

    @Override
    public void onClick(View view) {
        if(ItemClickListener !=null){
            int position = (int)view.getTag();
            if(!isShow){
                //如果没有进入多选删除状态，则进行正常的单次点击事件
                //注意这里使用getTag方法获取position
                ItemClickListener.onItemClick(view,position);
                return;
            }
            //进入多选删除状态的处理
            if(isItemChecked(position)){
                setItemChecked(position,false);
            }else {
                setItemChecked(position,true);
            }
            notifyItemChanged(position);
            //notifyDataSetChanged();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(ItemClickListener != null){
            if (isShow){
                return false;   //如果已经进入多选状态，则不再进行长按事件处理，当做单次点击事件处理
            }else {
                isShow = true;
            }
            alarmmasterCheck.set((int)view.getTag(),true);
            /////////////暂时这样处理//////////////////////////////////////////
//            alarmmasterCheck = new ArrayList<>();                           //
//            for(int i=0;i<alarmmasterList.size();i++){                      //
//                if(i == (int)view.getTag()){                                //
//                    alarmmasterCheck.add(true);     //长按条目直接为选中状态 //
//                }else {                                                     //
//                    alarmmasterCheck.add(false);                            //
//                }                                                           //
//            }                                                               //
            //////////////////////////////////////////////////////////////////
            notifyDataSetChanged();
            ItemClickListener.onItemLongClick(view,(int)view.getTag());
        }
        return true;
    }
    //全选
    public void allSelected(){
        for(int i=0; i<alarmmasterCheck.size(); i++){
            alarmmasterCheck.set(i,true);
            notifyDataSetChanged();
        }
    }
    //取消编辑状态
    public void cancelDelete(){
        isShow = false;
        notifyDataSetChanged();
    }
    //进入编辑状态
    public void editStart(){
        isShow = true;
        notifyDataSetChanged();
    }
    //获取选中条目数
    public int getCheckedNumber(){
        int number = 0;
        for(int i=0;i<alarmmasterCheck.size();i++){
            if(isItemChecked(i)){
                number++;
            }
        }
        return number;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewRoughTime;           //粗略时间  上午和下午
        TextView textViewExactTime;           //精确时间  时分
        TextView textViewRepetition;          //闹钟重复  每天保持或者仅一天
        Switch aSwitchControl;                //开关控制
        SmoothCheckBox smoothCheckBoxDelete;  //删除勾选
        public ViewHolder(View itemView) {
            super(itemView);
            textViewRoughTime = itemView.findViewById(R.id.list_roughTime_tv);
            textViewExactTime = itemView.findViewById(R.id.list_exactTime_tv);
            textViewRepetition = itemView.findViewById(R.id.list_repetition_tv);
            aSwitchControl = itemView.findViewById(R.id.list_control_sh);
            smoothCheckBoxDelete = itemView.findViewById(R.id.list_delete_scb);
        }
    }

    //设置Listener方法,暴露给外部调用者
    public void setOnItemClickListener(onItemClickListener listener) {
        this.ItemClickListener = listener;
    }

    //item点击事件接口
    public interface onItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
