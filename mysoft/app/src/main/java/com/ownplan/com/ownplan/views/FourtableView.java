package com.ownplan.com.ownplan.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.List;

public class FourtableView extends LinearLayout {
    /*这是存放的注解面下面的四个Tabview的类

     */
    private Tabview butttonLT;
    private Tabview butttonStudy;
    private Tabview butttonMine;
    private Tabview butttonfriend;
    private List<Tabview> mTablist ;
    private ViewPager mViewPager;

    public FourtableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context,R.layout.fourtableview,this);
        init();

    }
    public void init()
    {

       // LinearLayout l = findViewById(R.id.sige);
        //构造四个tableView。并且初始化
        butttonLT = findViewById(R.id.butttonLT);
        butttonStudy = findViewById(R.id.butttonStudy);
        butttonfriend =findViewById(R.id.butttonFriend);
        butttonMine = findViewById(R.id.butttonMine);
       // mViewPager = findViewById(R.id.mViewPage);

       // L.d("init zhong R.id d的值是：",""+R.drawable.friend);
        butttonLT.setIconAndText(R.drawable.luntan, R.drawable.luntan1, "论坛");
        butttonStudy.setIconAndText(R.drawable.study, R.drawable.study1, "学习");
        butttonfriend.setIconAndText(R.drawable.friend, R.drawable.friend1, "朋友");
        butttonMine.setIconAndText(R.drawable.user, R.drawable.user1, "我");

        //加入到list中
        mTablist = new ArrayList<>();
        mTablist.add(butttonLT);
        mTablist.add(butttonfriend);
        mTablist.add(butttonStudy);
        mTablist.add(butttonMine);
        //今日开始选中的是界面中的论坛tableview
        butttonLT.setProgress(1);

        // myClickListener myClickListener = new myClickListener();注册监听
        butttonfriend.setOnClickListener(new myClickListener());
        butttonLT.setOnClickListener(new myClickListener());
        butttonMine.setOnClickListener(new myClickListener());
        butttonStudy.setOnClickListener(new myClickListener());



    }


    //这是监听的类
    class myClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.butttonLT:
                    mViewPager.setCurrentItem(0, false);

                    break;
                case R.id.butttonFriend:
                    mViewPager.setCurrentItem(1, false);
                    break;
                case R.id.butttonStudy:
                    mViewPager.setCurrentItem(2, false);
                    break;
                case R.id.butttonMine:
                    mViewPager.setCurrentItem(3, false);

                    break;


            }

            //设置点击tabview的点击事件
            for (int i = 0; i < mTablist.size(); i++) {
                if (mTablist.get(i).getId() == v.getId()) {
                    mTablist.get(i).setProgress(1);

                } else
                    mTablist.get(i).setProgress(0);


            }
        }
    }

     //下面是getandset函数
        public List<Tabview> getmTablist () {
        return mTablist;
    }

        public void setmTablist (List < Tabview > mTablist) {
        this.mTablist = mTablist;
    }

    public Tabview getButttonLT() {
        return butttonLT;
    }

    public void setButttonLT(Tabview butttonLT) {
        this.butttonLT = butttonLT;
    }

    public Tabview getButttonStudy() {
        return butttonStudy;
    }

    public void setButttonStudy(Tabview butttonStudy) {
        this.butttonStudy = butttonStudy;
    }

    public Tabview getButttonMine() {
        return butttonMine;
    }

    public void setButttonMine(Tabview butttonMine) {
        this.butttonMine = butttonMine;
    }

    public Tabview getButttonfriend() {
        return butttonfriend;
    }

    public void setButttonfriend(Tabview butttonfriend) {
        this.butttonfriend = butttonfriend;
    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }
}
