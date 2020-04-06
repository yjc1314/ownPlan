package com.ownplan.com.ownplan.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.com.ownplan.utils.Diary;
import com.ownplan.com.ownplan.utils.DiaryAdater;
import com.ownplan.com.ownplan.utils.Diarygroup;
import com.ownplan.logintest.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Show_dairys extends BasicActivity {
    List<Diary> list = new ArrayList<>();
    RecyclerView mrecycleview  ;
    DiaryAdater diaryAdater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_diarys);
        Intent intent = getIntent();
        Serializable diarygroup = intent.getSerializableExtra("diarygroup");
        Diarygroup temp =((Diarygroup)diarygroup);

        //准备list数据
        initData(temp);
        //初始化布局
        initview();



    }

    private void initview() {
        mrecycleview = findViewById(R.id.diaryrecycleView);
        diaryAdater = new DiaryAdater(list);
        mrecycleview.setLayoutManager(new LinearLayoutManager(this));
        mrecycleview.setAdapter(diaryAdater);

    }

    /**
     * 添加所有的diary到diarys
     * @param temp
     */
    private void initData(Diarygroup temp) {
        int i = 0;
        while(i < 5)
        {
            Diary s = Getdiary();
            list.add(s);
            i++;
        }
    }

    /**
     *
     * @return 的到位存储的一个diary对象
     */

    private Diary Getdiary() {

        Diary s = new Diary();
        s.setId(1);
        s.setContext("我是内容");
        Date date = new Date();
        s.setTime(date);
        s.setTitle("我是标题");

        return s ;
    }
}
