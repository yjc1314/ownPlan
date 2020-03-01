package com.ownplan.com.ownplan.fragements;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyPageAdapt extends FragmentPagerAdapter {
    protected List<String> mTitles= new ArrayList<>(Arrays.asList("我","好友","论坛","学习"));

    public MyPageAdapt(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        L.d("title传进去的值是："+ mTitles.get(position));
        return TabFragment.newInstance(mTitles.get(position));
    }

    @Override
    public int getCount() {

        return mTitles.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
