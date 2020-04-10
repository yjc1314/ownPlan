package com.ownplan.com.ownplan.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//recyccleView 的adapter的封装
public class RcyclerAdapter<Data> extends RecyclerView.Adapter<RcyclerAdapter.ViewHolder<Data>> {
    private final List<Data> mDataList = new ArrayList();


    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent就是recycleview

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        Data data = mDataList.get(position);
        holder.bind(data);//绑定数据到一个holder
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{

        protected Data mData ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        void  bind(Data data)
        {//绑定数据
            mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定的时候必须要复写
         * @param data
         */
        protected  abstract  void onBind(Data data);
        public void updataData(Data  data)
        {


        }

    }
}
