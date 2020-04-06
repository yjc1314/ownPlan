package com.ownplan.com.ownplan.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ownplan.logintest.R;

import java.util.List;

public class DiaryAdater extends RecyclerView.Adapter<DiaryAdater.ViewHolder> {
    List<Diary> mlists;
    Context mcontext;

    /**
     * 构造函数获取我们需要的list
     * @param mlists
     */
    public DiaryAdater(List<Diary> mlists) {
        this.mlists = mlists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();
        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_diarys,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final  Diary temp = mlists.get(position);
        holder.time.setText(temp.getTime().toString());
        holder.context.setText(temp.getContext());
        holder.title.setText(temp.getTitle());
        //设置itemview的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mcontext,temp.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mlists.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time ;
        TextView title;
        TextView context;
        public ViewHolder(@NonNull View view) {
            super(view);
            time = view.findViewById(R.id.time);
            title = view.findViewById(R.id.title);
            context = view.findViewById(R.id.context);

        }


    }
}
