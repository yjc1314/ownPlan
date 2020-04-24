package com.ownplan.com.ownplan.fragements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ownplan.com.ownplan.activity.login.Postvideo;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpContains;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpUtil;
import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.L;
import com.ownplan.com.ownplan.utils.Plan;
import com.ownplan.com.ownplan.utils.Video;
import com.ownplan.com.ownplan.utils.Video_recycle_adapter;
import com.ownplan.ownplan.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class tabfragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";
    private Context mcontext;
    private TextView mTVtitle;
    private String mTitle;
    private Plan mplan ;
    private Toolbar toolbar;
    private Toolbar video_toolbar;
    private List<Video> mvideo_list =new ArrayList<>() ;
    private Video_recycle_adapter video_recycle_adapter;

    private RecyclerView video_recyclerView ;

    public static tabfragment newInstance(String title) {

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE, title);

        tabfragment tabFragment = new tabfragment();
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
           // L.d("onCreate,Title = " + mTitle);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mTitle.equals("笔记")) {


            View myview = inflater.inflate(R.layout.mine, container, false);


           // viewPager.setAdapter(new FragmentStatePagerAdapter((Activity(mcontext)).getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            return myview ;

        }else if(mTitle.equals("视频"))
        {
           View myview = inflater.inflate(R.layout.video_layout, container, false);
           setHasOptionsMenu(true);
           return myview;
        }else
        return inflater.inflate(R.layout.tabfragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTitle.equals("笔记")) {
            toolbar = view.findViewById(R.id.minetoolbar);
            ((Index)getActivity()).setSupportActionBar(toolbar);
            //FloatingActionButton floatingActionButton = view.findViewById(R.id.floating);
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
                        toolbar.setTitle("笔记");

                        return new Diaryfragment();
                    }
                    else {
                        toolbar.setTitle("计划");
                        return new Studyfragment();
                    }
                }
            });


            //下面的作用是我们有一个关联的动画
            ((Index)getActivity()).setSupportActionBar(toolbar);//将toolbar与ActionBar关联
            DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    getActivity(), drawerLayout, toolbar, 0, 0);
            drawerLayout.addDrawerListener(toggle);//初始化状态
            toggle.syncState();


        } else if(mTitle.equals("视频"))
        {




            video_toolbar =view .findViewById(R.id.videotoolbar);
            video_toolbar.inflateMenu(R.menu.menu_video);
            video_recyclerView = view .findViewById(R.id.video_list);
            video_recycle_adapter = new Video_recycle_adapter(mvideo_list);
            video_recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
            video_recyclerView.setAdapter(video_recycle_adapter);
            //设置视频的点击事件

            //设置toolbar
            ((Index)getActivity()).setSupportActionBar(video_toolbar);
            getvideos();

        }else{
            mTVtitle = view.findViewById(R.id.vx_title);
            mTVtitle.setText(mTitle);
            Toolbar toolbar = view.findViewById(R.id.toolbar);

            //下面的作用是我们有一个关联的动画
            ((Index)getActivity()).setSupportActionBar(toolbar);//将toolbar与ActionBar关联
            DrawerLayout drawerLayout = ((Index)getActivity()).findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    getActivity(), drawerLayout, toolbar, 0, 0);
            drawerLayout.addDrawerListener(toggle);//初始化状态
            toggle.syncState();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(this.mTitle.equals("视频"))
        { Jzvd.releaseAllVideos();}

    }

    /**
     * 提供videos的函数
     * @return
     */
    public void getvideos()
    {
        HttpUtil.httpvideoes(HttpContains.URL + "videosprovide", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {


                    @Override

                    public void run() {
                        L.d(e.getMessage());
                        Toast.makeText(getActivity(),"失败"+e.getCause(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String s = response.body().string();
                getActivity().runOnUiThread(new Runnable() {


                    @Override
                    public void run() {

                        L.d(s);
                       Gson gson = new Gson();
                        JsonParser jsonParser = new JsonParser();
                        JsonArray jsonElements =null;

                        try {
                            jsonElements = jsonParser.parse(s).getAsJsonArray();//获取JsonArray对象

                        }catch (Exception e)
                        {

                        }
                            //jsonElements = jsonParser.parse(s).getAsJsonArray();//获取JsonArray对象


                       List<Video> list = new ArrayList<>();
                        if(null!=jsonElements) {
                            for (JsonElement bean : jsonElements) {
                                Video bean1 = gson.fromJson(bean, Video.class);//解析
                                list.add(bean1);
                            }
                        }
                      mvideo_list = list;
                       L.d(mvideo_list.toString());
                        video_recycle_adapter.shuaxin(mvideo_list);
                    }
                });
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_video,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.add :
                Intent intent = new Intent(getActivity(), Postvideo.class);
                intent.putExtra("name",Index.USER_NAME);
                intent.putExtra("photo",Index.PHOTO_URL);
                startActivity(intent);
                break;



        }
        return super.onOptionsItemSelected(item);
    }
}


