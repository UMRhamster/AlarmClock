package com.whut.umrhamster.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 12421 on 2018/1/26.
 */

public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockAdapter.ViewHolder> implements View.OnClickListener{
    private List<Alarmmaster> alarmmasterList;
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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alarmmaster alarmmaster = alarmmasterList.get(position);
        holder.textViewRoughTime.setText(alarmmaster.getRoughTime());
        holder.textViewExactTime.setText(alarmmaster.getExactTime());
        holder.textViewRepetition.setText(alarmmaster.getRepetition());
        holder.aSwitchControl.setChecked(alarmmaster.getStatus());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return alarmmasterList.size();
    }

    @Override
    public void onClick(View view) {
        if(ItemClickListener !=null){
            //注意这里使用getTag方法获取position
            ItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewRoughTime;           //粗略时间  上午和下午
        TextView textViewExactTime;           //精确时间  时分
        TextView textViewRepetition;          //闹钟重复  每天保持或者仅一天
        Switch aSwitchControl;                //开关控制
        public ViewHolder(View itemView) {
            super(itemView);
            textViewRoughTime = itemView.findViewById(R.id.list_roughTime_tv);
            textViewExactTime = itemView.findViewById(R.id.list_exactTime_tv);
            textViewRepetition = itemView.findViewById(R.id.list_repetition_tv);
            aSwitchControl = itemView.findViewById(R.id.list_control_sh);
        }
    }

    //设置Listener方法,暴露给外部调用者
    public void setOnItemClickListener(onItemClickListener listener) {
        this.ItemClickListener = listener;
    }

    //item点击事件接口
    public static interface onItemClickListener{
        void onItemClick(View view, int position);
    }
}
