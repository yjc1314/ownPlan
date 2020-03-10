package com.ownplan.com.ownplan.fragements;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.L;
import com.ownplan.logintest.R;

public class TabFragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";
    private Context mcontext;
    private TextView mTVtitle;
    private String mTitle;


    public static TabFragment newInstance(String title) {

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE, title);
        L.d("newInstance" + title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle argument = getArguments();
        mcontext = getContext();
        if (argument != null) {

            mTitle = argument.getString(BUNDLE_KEY_TITLE, "");
            L.d("onCreate,Title = " + mTitle);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mTitle.equals("我")) {
            View myview = inflater.inflate(R.layout.mine, container, false);


           // viewPager.setAdapter(new FragmentStatePagerAdapter((Activity(mcontext)).getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            return myview ;

        }
        return inflater.inflate(R.layout.tabfragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTitle.equals("我")) {
            Button diary = view.findViewById(R.id.dairy_button);
            Button study = view.findViewById(R.id.study_buttton);

            final ViewPager viewPager = view.findViewById(R.id.myviewpager);
            ((Index)getActivity()).getSupportFragmentManager();
            viewPager.setAdapter(new FragmentStatePagerAdapter(((Index) getActivity()).getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                @Override
                public int getCount() {
                    return 2;
                }

                @NonNull
                @Override
                public Fragment getItem(int position) {
                    if(position == 0)
                    {

                        return new Diaryfragment();
                    }

                    return new Studyfragment();
                }
            });
            diary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(0);

                }
            });
            study.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(1);

                }
            });


        } else {
            mTVtitle = view.findViewById(R.id.vx_title);
            mTVtitle.setText(mTitle);
        }
    }
}
