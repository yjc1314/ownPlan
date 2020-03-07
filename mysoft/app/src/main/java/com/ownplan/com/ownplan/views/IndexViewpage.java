package com.ownplan.com.ownplan.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ownplan.com.ownplan.utils.L;
import com.ownplan.logintest.R;

import java.util.List;

//**************这个类还没有弄好，不太好弄。ViewPager需要外部传入参数***************
public class IndexViewpage extends LinearLayout {
    //需要提供List<Tabview>和mViewPager的 Adpater
    private ViewPager mViewPager;
    protected List<String> mTitles;
    private List<Tabview> mTablist;
    PagerAdapter pagerAdapter;


    public IndexViewpage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewPager = (ViewPager) findViewById(R.id.mViewPage);
        mViewPager.setOffscreenPageLimit(mTablist.size());
        //需要我们的外部提供一个pagerAdapter
        mViewPager.setAdapter(pagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //L.d("这里是看position: "+position);



                if(positionOffset > 0) {
                    Tabview left = mTablist.get(position);
                    L.d("left: " + mTitles.get(position));
                    Tabview right = mTablist.get(position + 1);
                    L.d("right: " + mTitles.get(position));
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

    }
    //下面是getAndset
    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public List<String> getmTitles() {
        return mTitles;
    }

    public void setmTitles(List<String> mTitles) {
        this.mTitles = mTitles;
    }

    public List<Tabview> getmTablist() {
        return mTablist;
    }

    public void setmTablist(List<Tabview> mTablist) {
        this.mTablist = mTablist;
    }

    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }
}




