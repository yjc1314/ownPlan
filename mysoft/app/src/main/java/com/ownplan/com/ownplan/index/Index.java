package com.ownplan.com.ownplan.index;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ownplan.com.ownplan.fragements.TabFragment;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.com.ownplan.utils.L;
import com.ownplan.com.ownplan.utils.Plan;
import com.ownplan.com.ownplan.views.CehuaView;
import com.ownplan.com.ownplan.views.FourtableView;
import com.ownplan.com.ownplan.views.Tabview;
import com.ownplan.logintest.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Index extends BasicActivity {
    public static final int CHONSE_PLAN = 4;
    public static final int REQ_PERMISON = 1;
    public static final int CHOSE_PHOTO = 2;
    private static final int IMAGE_CUT = 3;
    public static String PHOTO_URL = null;//这个就是头像的路径
    private Plan mplan = null;

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
        fourtableView = findViewById(R.id.sige);

        initview();//这里边有一个Viewpager
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


        cehuaView = findViewById(R.id.cehua);
        if (PHOTO_URL != null) {
            PHOTO_URL = createFile().getPath();
            Bitmap temp = BitmapFactory.decodeFile(PHOTO_URL);
            cehuaView.setPhoto(temp);
        }

       /*toolbar = findViewById(R.id.toolbar);
        //下面的作用是我们有一个关联的动画
        setSupportActionBar(toolbar);//将toolbar与ActionBar关联
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);//初始化状态
        toggle.syncState();*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case CHOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //的到我们需要剪切前的url
                        handleImageOnKitkat(data);


                    } else {


                        handleImageBeforeKitkat(data);

                    }


                }
                break;
            case IMAGE_CUT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        PHOTO_URL = createFile().getPath();
                        Bitmap temp = BitmapFactory.decodeFile(PHOTO_URL);
                        cehuaView.setPhoto(temp);


                    }
                    break;
                }
            case CHONSE_PLAN:
                if (requestCode == RESULT_OK) {
                    if (data.getBooleanExtra("ok", false)) {//这个条件好像有问题
                        returnPlan(data.getStringExtra("time"), data.getStringExtra("doWhat"));
                        Toast.makeText(this, mplan.getTime() + mplan.getDoWhat(), Toast.LENGTH_SHORT).show();


                    } else {
                        mplan = null;
                        Toast.makeText(this, "你取消了设置计划", Toast.LENGTH_SHORT).show();

                    }
                }
                break;


        }

    }

    private void returnPlan(String time, String doWhat) {

        mplan = new Plan(time, doWhat);
    }

    public Plan putPlan() {

        return mplan;
    }

    private void handleImageBeforeKitkat(Intent data) {


        Uri uri = data.getData();

        String imagPath = null;

        //PHOTO_URL = imagPath;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitkat(Intent data) {

        String imagPath = null;

        Uri uri = data.getData();


        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagPath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.provides.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagPath = getImagePath(contentUri, null);
            }
        } else if ("content".equals(uri.getScheme())) {
            imagPath = getImagePath(uri, null);

        } else if ("file".equals(uri.getScheme())) {
            imagPath = uri.getPath();


        }


        crop(uri);
    }

    public void crop(Uri uri) {
        //File input = new File(PHOTO_URL);
        // L.d("uri"+uri.toString());
        Uri Photo_uri = createFile();
        //Uri fileuri = Uri.fromFile(input);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setData(uri);
        intent.putExtra("aspecty", 50);
        intent.putExtra("aspectx", 50);
        intent.putExtra("outputy", 50);
        intent.putExtra("outputx", 50);

        intent.putExtra("return-data", false);
        //存储到目的位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Photo_uri);
        //intent.putExtra("path",path );
        //intent.putExtra("path",Uri.fromFile(output));

        startActivityForResult(intent, IMAGE_CUT);


    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString((cursor.getColumnIndex(MediaStore.Images.Media.DATA)));


            }
            cursor.close();


        }
        return path;
    }

    public Uri createFile() {
        //创建一个存放头像的file
        String path = Environment.getExternalStorageDirectory().toString();
        File newfile = new File(path+"/Myicon.PNG");
        if(newfile.getParentFile()!=null &&!newfile.getParentFile().exists())
        {
            newfile.getParentFile().mkdirs();
        }
        if (!newfile.exists()) {
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                Toast.makeText(this, "创建头像失败", Toast.LENGTH_SHORT).show();
            }

        }
        L.d("uri"+newfile.getPath());
        return Uri.fromFile(newfile);
    }

    //这个方法是请求权限的回调方法，如果请求成功我们就设置头像
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //回调处理我们的请求结果
        switch (requestCode) {
            case REQ_PERMISON:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String[] items = new String[]
                            {"选择图片", "取消"};
                    cehuaView.Diolog(items, "设置头像");

                } else {
                    Toast.makeText(this, "你取消了请求", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }
}



