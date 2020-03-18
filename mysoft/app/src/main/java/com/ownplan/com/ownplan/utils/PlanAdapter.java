package com.ownplan.com.ownplan.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ownplan.logintest.R;

import java.util.List;
/*
作用：是一dairy中的recycleview的adapter里面有一个ViewHolder的内部类。
list中有存放plan。
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    public PlanAdapter(List<Plan> mlist) {
        this.mlist = mlist;
    }

    private List<Plan> mlist ;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载子项的布局
       View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewitem_indiary,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
       return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plan plan = mlist.get(position);
        holder.time.setText(plan.getTime());
        holder.doWhat.setText(plan.getDoWhat());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
      TextView time ;
      TextView doWhat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         time = itemView.findViewById(R.id.time);
         doWhat = itemView.findViewById(R.id.doWhat);


        }
    }
}
