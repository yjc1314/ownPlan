package com.ownplan.com.ownplan.fragements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ownplan.com.ownplan.utils.DiarysAdapter;
import com.ownplan.com.ownplan.utils.Diarygroup;
import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的添加了一个recycleview 没有点击事件 图片过大，字体过大需要调小
 * 也没有加载数据  数据需要从哪儿拿呢。。
 * 后面需要加载数据，加载跳转的activity
 * 设置每个item的监听打开他的故事集，显示故事
 * 就这样了。
 */
public class Diaryfragment extends Fragment {
    RecyclerView mrecycleview;
    DiarysAdapter mdiaryadapter;
    List<Diarygroup> mlist = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diary_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initrecycleView(view);
    }

    private void initrecycleView(View view) {
        mrecycleview = view.findViewById(R.id.msd_recycleview);
        //添加数据
        for(int i = 0;i < 6 ;i++)
        {

            mlist.add(new Diarygroup(i,"图片"+i,i,null));

        }
         mdiaryadapter = new DiarysAdapter(mlist);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        mrecycleview.setLayoutManager(gridLayoutManager);
        mrecycleview.setAdapter(mdiaryadapter);
    }

}
