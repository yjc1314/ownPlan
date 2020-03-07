package com.ownplan.com.ownplan.index;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ownplan.com.ownplan.fragements.TabFragment;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.com.ownplan.views.CehuaView;
import com.ownplan.com.ownplan.views.FourtableView;
import com.ownplan.com.ownplan.views.Tabview;
import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Index extends BasicActivity {
    private CehuaView cehuaView;
    private FourtableView fourtableView;//这个是下面的四个按钮组成的一个view
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    protected List<String> mTitles = new ArrayList<>(Arrays.asList("论坛",
            "好友", "学习", "我"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        //这里fourtableView 需要Viewpager用于监听，Viewpager需要Tablist
        fourtableView = findViewById(R.id.sige);

        initview();//这里边有一个Viewpager
        //获得前面的Viewpager，里面的Listener要用
        fourtableView.setmViewPager(mViewPager);

    }

    public void initview() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPage);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    Tabview left = fourtableView.getmTablist().get(position);
                    Tabview right = fourtableView.getmTablist().get(position + 1);
                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                }

            }


            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(fourtableView.getmTablist().size());
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {

                return TabFragment.newInstance(mTitles.get(position));
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        cehuaView = findViewById(R.id.cehua);
        setSupportActionBar(toolbar);//将toolbar与ActionBar关联
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);//初始化状态
        toggle.syncState();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == 1)
            {


            }


        }
    }
}
