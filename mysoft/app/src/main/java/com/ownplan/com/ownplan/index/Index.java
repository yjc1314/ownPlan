package com.ownplan.com.ownplan.index;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.ownplan.com.ownplan.fragements.TabFragment;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.com.ownplan.utils.L;
import com.ownplan.com.ownplan.views.Newbutton;
import com.ownplan.com.ownplan.views.Tabview;
import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Index extends BasicActivity {

    //侧滑栏下面的两个newButton
    private Newbutton back;
    private Newbutton set;
    private Tabview butttonLT;
    private Tabview butttonStudy;
    private Tabview butttonMine;
    private Tabview butttonfriend;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;



    private List<Tabview> mTablist = new ArrayList<>();
    protected List<String> mTitles = new ArrayList<>(Arrays.asList("论坛", "好友", "学习", "我"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        initview();
        mViewPager.setOffscreenPageLimit(mTablist.size());

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                L.d("title传进去的值是：" + mTitles.get(position));
                return TabFragment.newInstance(mTitles.get(position));
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });
        class myClickListener implements View.OnClickListener {


            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.butttonLT:
                        mViewPager.setCurrentItem(0,false);

                        break;
                    case R.id.butttonFriend:
                        mViewPager.setCurrentItem(1,false);
                        break;
                    case R.id.butttonStudy:
                        mViewPager.setCurrentItem(2,false);
                        break;
                    case R.id.butttonMine:
                        mViewPager.setCurrentItem(3,false);

                        break;


                }

                //设置点击tabview的点击事件
                for(int i = 0 ;i< mTablist.size();i++)
                {
                    if(mTablist.get(i).getId()==v.getId())
                    {
                        mTablist.get(i).setProgress(1);

                    }
                    else
                        mTablist.get(i).setProgress(0);


                }
            }


        }

       // myClickListener myClickListener = new myClickListener();
        butttonfriend.setOnClickListener(new myClickListener());
        butttonLT.setOnClickListener(new myClickListener());
        butttonMine.setOnClickListener(new myClickListener());
        butttonStudy.setOnClickListener(new myClickListener());

    }


    /**
     *
     */
    public void initview() {

        mViewPager = (ViewPager) findViewById(R.id.mViewPage);
        butttonLT = findViewById(R.id.butttonLT);
        butttonStudy = findViewById(R.id.butttonStudy);
        butttonfriend = findViewById(R.id.butttonFriend);
        butttonMine = findViewById(R.id.butttonMine);
        butttonLT.setIconAndText(R.drawable.luntan, R.drawable.luntan1, "论坛");
        butttonStudy.setIconAndText(R.drawable.study, R.drawable.study1, "学习");
        butttonfriend.setIconAndText(R.drawable.friend, R.drawable.friend1, "朋友");
        butttonMine.setIconAndText(R.drawable.user, R.drawable.user1, "我");

        mTablist.add(butttonLT);
        mTablist.add(butttonfriend);
        mTablist.add(butttonStudy);
        mTablist.add(butttonMine);

        butttonLT.setProgress(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //L.d("这里是看position: "+position);
                L.d("这里是看positionOffset: "+positionOffset);


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


        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        back = findViewById(R.id.finish);
        back.setImageAndText(R.drawable.friend,"返回");
        back.setBackgroundColor(Color.parseColor("#FF45C01A"));
        set  =findViewById(R.id.setting);
        set.setImageAndText(R.drawable.user,"设置");
        set.setBackgroundColor(Color.parseColor("#008577"));

        setSupportActionBar(toolbar);//将toolbar与ActionBar关联
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);//初始化状态
        toggle.syncState();


        /*---------------------------添加头布局和尾布局-----------------------------*/
        //获取xml头布局view
        View headerView = navigationView.getHeaderView(0);
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);

        //寻找头部里面的控件
        CircleImageView circleImageView = findViewById(R.id.circleImageview);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.black);
        //设置item的条目颜色
        navigationView.setItemTextColor(csl);
        //去掉默认颜色显示原来颜色  设置为null显示本来图片的颜色
        navigationView.setItemIconTintList(csl);
       /* LinearLayout llAndroid = (LinearLayout) navigationView.getMenu().findItem(R.id.single_1).getActionView();
        TextView msg= (TextView) llAndroid.findViewById(R.id.msg_bg);
        msg.setText("99+");*/

        //设置条目点击监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //安卓
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_LONG).show();
                //设置哪个按钮被选中
//                menuItem.setChecked(true);
                //关闭侧边栏
//                drawer.closeDrawers();
                return false;
            }
        });

    }
    }
