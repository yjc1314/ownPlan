package com.ownplan.com.ownplan.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.Plan;
import com.ownplan.com.ownplan.utils.PlanAdapter;
import com.ownplan.com.ownplan.views.Edit_plan;
import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 目前进度，可以加入一个计划，但是没有保存下来，就是app退出直接消失，
 * 加的东西就没有了，所以需要保存我们的list这样子每次加载我么从文件中读取
 * 就不会消失了，还需要我们加入删除的按钮，可以删除我们的每一个计划。
 *
 */
public class Studyfragment extends Fragment {
    public static final int CHONSE_PLAN = 4;
    private PlanAdapter mplanAdapter;
    private RecyclerView mRecycleView;
    private List<Plan> plans = new ArrayList<Plan>();
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.study_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initcycleView(view);
        initFlaotactionBar(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initFlaotactionBar(View view) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_plan.class);
                getActivity().startActivityForResult(intent,CHONSE_PLAN);


            }
        });
    }

    private void initcycleView(View view) {
        mRecycleView = view.findViewById(R.id.mRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecycleView.setLayoutManager(linearLayoutManager);


    //下面是测试数据的add
        for(int i = 0; i < 3; i++)
        {
            plans.add(new Plan("打开电视","1：00"));
            plans.add(new Plan("睡觉","2：00"));
            plans.add(new Plan("起床","3：00"));


        }

        mplanAdapter =new PlanAdapter(plans);
        mRecycleView.setAdapter(mplanAdapter);
        //设置一条默认下划线在item之间
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(((Index)getActivity()).putPlan() != null) {
            Plan mplan = (((Index)getActivity())).putPlan();
            Toast.makeText(getContext(), mplan.getTime() + "你要干什么" + mplan.getDoWhat(), Toast.LENGTH_LONG).show();

            //  L.d(((Index) context).putPlan().getTime()+((Index) context).putPlan().getDoWhat());
            plans.add(((Index)getActivity()).putPlan());
            mplanAdapter.notifyDataSetChanged();
        }
    }
}
