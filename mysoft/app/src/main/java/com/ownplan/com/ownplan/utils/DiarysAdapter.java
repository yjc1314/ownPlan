package com.ownplan.com.ownplan.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.views.Show_dairys;
import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.List;

public class DiarysAdapter extends RecyclerView.Adapter<DiarysAdapter.ViewHolder> {
    static final  int DIARY_GROUP_EDIT = 6;
    Context mcontext;//这个是上下文对象
    List<Diarygroup> mlist = new ArrayList<>();//存放我们的成员
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      mcontext = parent.getContext();
      //加载布局
      View view = LayoutInflater.from(parent.getContext()).
              inflate(R.layout.recycleviewitem_msddiary, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);//创建viewholder
        return viewHolder;
    }

    public DiarysAdapter(List<Diarygroup> mlist) {
        this.mlist = mlist;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Diarygroup diarygroup = mlist.get(position);
//设置各种控件
        holder.count.setText(diarygroup.getDiarycount()+"个");
        if(diarygroup.getImage()!= null) {
            holder.photo.setImageBitmap(BitmapFactory.decodeFile(diarygroup.getImage()));
        }
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, Show_dairys.class);
                Toast.makeText(mcontext,"进入日记集合",Toast.LENGTH_LONG).show();
                intent.putExtra("diarygroup", diarygroup);
                ((Index)mcontext).startActivityForResult(intent,DIARY_GROUP_EDIT);

            }
        });
        holder.name.setText(diarygroup.getName());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //对控件的初始化
        ImageButton photo;
        TextView name ;
        TextView count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.diaryphoto);
            name = itemView.findViewById(R.id.name);
            count = itemView.findViewById(R.id.count);


        }
    }
}
