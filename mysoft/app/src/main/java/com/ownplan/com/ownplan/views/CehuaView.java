package com.ownplan.com.ownplan.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.navigation.NavigationView;
import com.ownplan.com.ownplan.utils.ChooseDialog;
import com.ownplan.logintest.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CehuaView extends LinearLayout {
    private Newbutton back;
    private Newbutton set;
    private NavigationView navigationView;
    private Context mcontext;
    private Bitmap bitmap;


    public CehuaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        inflate(context, R.layout.cehua, this);
        init();
    }

    public void init()
    {
        back = findViewById(R.id.finish);
        back.setImageAndText(R.drawable.friend, "返回");
        back.setBackgroundColor(Color.parseColor("#FF45C01A"));
        set = findViewById(R.id.setting);
        set.setImageAndText(R.drawable.user, "设置");
        set.setBackgroundColor(Color.parseColor("#008577"));
        navigationView = findViewById(R.id.navigation_view);


        /*---------------------------添加头布局和尾布局-----------------------------*/
        //先加载头布局
        View headerView = navigationView.inflateHeaderView(R.layout.navigationview_header);
        //在头布局中找到我们的circleImageView
        CircleImageView circleImageView = headerView.findViewById(R.id.circleImageview);
        circleImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"点击头像了",Toast.LENGTH_LONG).show();
                //这里需要得到现在的图片传递给我们后面的activity
                bitmap = BitmapFactory.decodeResource(getResources(),R.id.circleImageview);

                Intent  intent = new Intent(mcontext, ChooseDialog.class);
                intent.putExtra("Bitmap", bitmap);

                if(mcontext instanceof Activity)
                {
                    ((Activity)mcontext).startActivityForResult(intent, 1);
                }
//            mcontext.startActivity(intent);

            }
        });

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

        //设置条目点击监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //安卓
                Toast.makeText(mcontext, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }

    //下面是一堆getAndset
    public Newbutton getBack() {
        return back;
    }

    public void setBack(Newbutton back) {
        this.back = back;
    }

    public Newbutton getSet() {
        return set;
    }

    public void setSet(Newbutton set) {
        this.set = set;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

}
