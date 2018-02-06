package com.whut.umrhamster.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.whut.umrhamster.myapplication.CustomUI.SmoothCheckBox;
import com.whut.umrhamster.myapplication.Utils.Utils;

import java.util.ArrayList;
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
    public AlarmClockAdapter(List<Alarmmaster> alarmmasterList, Context context){
        this.alarmmasterList = alarmmasterList;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("222222222222","bvhooooooolder"+String.valueOf(position));
        Alarmmaster alarmmaster = alarmmasterList.get(position);
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
            //进入多选删除状态的处理
            if(isItemChecked(position)){
                setItemChecked(position,false);
            }else {
                setItemChecked(position,true);
            }
            notifyItemChanged(position);
            //notifyDataSetChanged();
            //如果没有进入多选删除状态，则进行正常的单次点击事件
            //注意这里使用getTag方法获取position
            ItemClickListener.onItemClick(view,position);
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
            /////////////暂时这样处理//////////////////////////////////////////
            alarmmasterCheck = new ArrayList<>();                           //
            for(int i=0;i<alarmmasterList.size();i++){                      //
                if(i == (int)view.getTag()){                                //
                    alarmmasterCheck.add(true);     //长按条目直接为选中状态 //
                }else {                                                     //
                    alarmmasterCheck.add(false);                            //
                }                                                           //
            }                                                               //
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
        alarmmasterCheck = new ArrayList<>();
        for (int i=0;i<alarmmasterList.size();i++){
            alarmmasterCheck.add(false);
        }
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
    public static interface onItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
