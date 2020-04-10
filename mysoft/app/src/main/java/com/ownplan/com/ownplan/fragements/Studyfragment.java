package com.ownplan.com.ownplan.fragements;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.DatabaseMananger;
import com.ownplan.com.ownplan.utils.MysqliteHelper;
import com.ownplan.com.ownplan.utils.Plan;
import com.ownplan.com.ownplan.utils.PlanAdapter;
import com.ownplan.com.ownplan.utils.Sqlconstants;
import com.ownplan.com.ownplan.views.Edit_plan;
import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 达成目标添button 可以出去数据库，拿到数据
 * 悬浮按钮的图标不好看。
 *
 */
public class Studyfragment extends Fragment{
    private MysqliteHelper helper;
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

    private void inserttplan(){
        if(((Index)getActivity()).putPlan() != null) {
            Plan mplan = (((Index)getActivity())).putPlan();
            //Toast.makeText(getContext(), mplan.getTime() + "你要干什么" + mplan.getDoWhat(), Toast.LENGTH_LONG).show();

            //  L.d(((Index) context).putPlan().getTime()+((Index) context).putPlan().getDoWhat());
           //调用Index中的putplan方法
            Plan temp = ((Index)getActivity()).putPlan();
            helper =DatabaseMananger.getIntance(getActivity());
            SQLiteDatabase db = helper.getWritableDatabase();
            String sql = "insert into "+Sqlconstants.TABLE_PLAN +"("+Sqlconstants.TIME+","+Sqlconstants.DO_WHAT+") values('"+temp.getTime()+"','"+temp.getDoWhat()+"')";

            DatabaseMananger.DBinsert(db,sql);
            plans.add(temp);

            mplanAdapter.notifyDataSetChanged();
            temp = null;
        }
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
        //从数据库得到我们的数据
        helper =DatabaseMananger.getIntance(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+Sqlconstants.TABLE_PLAN+" ";
        Cursor cursor = DatabaseMananger.DBselect(db,sql,null);
       while(cursor.moveToNext())
       {
         Plan temp = new Plan(cursor.getString(cursor.getColumnIndex(Sqlconstants.TIME)),
               cursor.getString(cursor.getColumnIndex(Sqlconstants.DO_WHAT)));
         plans.add(temp);
       }

       //cursor也要关闭
       cursor.close();
       db.close();
        mplanAdapter =new PlanAdapter(plans);
        mRecycleView.setAdapter(mplanAdapter);
        //设置一条默认下划线在item之间
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }


    @Override
    public void onResume() {
        super.onResume();
        inserttplan();

    }



}
