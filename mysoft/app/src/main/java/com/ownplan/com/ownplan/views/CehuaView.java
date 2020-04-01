package com.ownplan.com.ownplan.views;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.navigation.NavigationView;
import com.ownplan.com.ownplan.index.Index;
import com.ownplan.logintest.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CehuaView extends LinearLayout {
    public static final int  REQ_PERMISON = 1;
    public static final int CHOSE_PHOTO = 2;
    private static final int IMAGE_CUT = 3;
    //public static String PHOTO_PATH = null;
    private Newbutton back;
    private Newbutton set;
    private NavigationView navigationView;
    private Context mcontext;
    private Bitmap bitmap;
    private CircleImageView circleImageView ;


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
         circleImageView = headerView.findViewById(R.id.circleImageview);

        circleImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"点击头像了",Toast.LENGTH_LONG).show();
                String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
                List <String> nopermisioned = new ArrayList();
                Boolean allPermission = false;

                for(int i = 0 ;i<permissions.length;i++)
                {
                    if(ContextCompat.checkSelfPermission(getContext(),permissions[i])!= PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions((Index)getContext(),new String[]{permissions[i]},REQ_PERMISON);
                        nopermisioned.add(permissions[i]);
                    }
                    allPermission = true;
                }
                for(String s :nopermisioned)
                {
                    if(ContextCompat.checkSelfPermission(getContext(),s)!= PackageManager.PERMISSION_GRANTED)
                    {
                        allPermission = false;
                    }


                }
                  if(nopermisioned.size()<1){
                        String[] items = new String[]
                                {"选择图片", "取消"};
                        Diolog(items, "设置头像");

                    }

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


    public void Diolog(String [] strings, String title)
    {//弹出一个Diolog,参数对话框的item数组和一个标题
        // 这里边调用了getphoto函数得到照片

        new AlertDialog.Builder(mcontext)
                .setTitle(title)
                .setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which)
                        {
                            case 0:
                                getPhoto();//得到我在内中我们图片的位置
                                //savePhoto(PHOTO_URL);
                                break;
                            case 1:
                                dialog.dismiss();

                        }
                    }
                }).show();
    }
    public void getPhoto() {

        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");

        if(mcontext instanceof Activity)
        {
            ((Activity)mcontext).startActivityForResult(intent, CHOSE_PHOTO);
        }
        //(Activity(mcontext)).startActivityForResult(intent, CHOSE_PHOTO);
    }
    public void setPhoto(Bitmap  s)
    {

        circleImageView.setImageBitmap(s);


    }
}
