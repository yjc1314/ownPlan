package com.ownplan.com.ownplan.fragements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ownplan.com.ownplan.utils.L;
import com.ownplan.logintest.R;


import org.w3c.dom.Text;

public class TabFragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";

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
        if (argument != null) {

            mTitle = argument.getString(BUNDLE_KEY_TITLE, "");
            L.d("onCreate,Title = " + mTitle);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mTitle.equals("我")) {
            return inflater.inflate(R.layout.mine, container, false);

        }
        return inflater.inflate(R.layout.tabfragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTitle.equals("我")) {
            mTVtitle = view.findViewById(R.id.wode);



        } else {
            mTVtitle = view.findViewById(R.id.vx_title);
            mTVtitle.setText(mTitle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.d("onDestroyView" + mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("onDestroy" + mTitle);
    }
}
