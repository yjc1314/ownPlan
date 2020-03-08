package com.ownplan.com.ownplan.activity.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.logintest.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 目前拿到了头像，设置到了那个cicleImagview中但是我们不能先剪切一下
 * 这是下午我们要做的。需要的过程我们需要获得需要剪切图片的uri然后就是剪切这个图片
 * 得到的图片一份返回给界面，我们还需要存储一份在sd卡中这样就可以保存下来头像了。
 */

public class MainActivity extends BasicActivity {
    public static final int CHOSE_PHOTO = 2;
    private static final int IMAGE_CUT = 3;
    public static String PHOTO_URL = null;//这个就是头像的路径
    private final Context mContext = this;
    private String userName, psw, spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name, et_psw;//编辑框
    public CircleImageView circleimageView;//头像框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        // L.d("我的路径是：",PHOTO_URL);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //回调处理我们的请求结果
        switch(requestCode)
        {
            case  1:
                if(grantResults.length > 0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    getPhoto();
                }
                else {
                    Toast.makeText(this,"你取消了请求",Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    //获取界面控件
    private void init() {
        //从main_title_bar中获取的id
        //从activity_login.xml中获取的
        TextView tv_register = (TextView) findViewById(R.id.register);
        TextView tv_find_psw = (TextView) findViewById(R.id.find_psw);
        Button btn_login = (Button) findViewById(R.id.login);
        circleimageView = findViewById(R.id.circleImageview);
        if(PHOTO_URL != null)
        {
            Bitmap b = BitmapFactory.decodeFile(PHOTO_URL);
            circleimageView.setImageBitmap(b);
        }else
        {
            circleimageView.setImageResource(R.drawable.friend);

        }
        //注册监听事件
        circleimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"点击切换头像",Toast.LENGTH_SHORT).show();
                //检查权限,因为就算再manifest中权限也需要动态权限。
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                } else {
                     String [] items = new String[]
                            {"选择图片","取消"};
                  Diolog(items,"设置头像");

                }

            }
        });

        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LostFindActivity.class));
            }
        });
        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                //对当前用户输入的密码进行MD5加密再进行比对判断, MD5Utils.md5( ); psw 进行加密判断是否一致
                String md5Psw = MD5Utils.md5(psw);
                // md5Psw ; spPsw 为 根据从SharedPreferences中用户名读取密码
                // 定义方法 readPsw为了读取用户名，得到密码
                spPsw = readPsw(userName);
                // TextUtils.isEmpty
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                } else if (md5Psw.equals(spPsw)) {
                    //一致登录成功
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, userName);
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin", true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK, data);
                    //销毁登录界面
                    MainActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(MainActivity.this, Index.class));
                } else if ((spPsw != null && !TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))) {
                    Toast.makeText(MainActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void setPhoto(String imagepath) {
        if (!imagepath.equals(null)) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);

            circleimageView.setImageBitmap(bitmap);
        } else {


            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPhoto() {

        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");

        startActivityForResult(intent, CHOSE_PHOTO);
    }


    /**
     * 从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName) {
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName, "");
    }

    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status, String userName) {
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.apply();
    }

    /**
     * 注册成功的数据返回至此
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        String Tempurl = null;
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    //是获取注册界面回传过来的用户名
                    // getExtra().getString("***");
                    String userName = data.getStringExtra("userName");
                    if (!TextUtils.isEmpty(userName)) {
                        //设置用户名到 et_user_name 控件
                        et_user_name.setText(userName);
                        //et_user_name控件的setSelection()方法来设置光标位置
                        et_user_name.setSelection(userName.length());
                    }
                }
                break;
            case CHOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //的到我们需要剪切前的url
                       handleImageOnKitkat(data);


                    } else {


                 handleImageBeforeKitkat(data);

                    }


                }
                //得到了tempurl 这个就是所在文件的目录
                break;
            case IMAGE_CUT:
                if(resultCode == RESULT_OK)
                {
                    if(data != null)
                    {
                    PHOTO_URL = createFile().getPath();
                    break;

                    }

                }

        }
        setPhoto(PHOTO_URL);


    }


    private void  handleImageBeforeKitkat(Intent data) {


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
        }else if ("content".equals(uri.getScheme())) {
                imagPath = getImagePath(uri, null);

        } else if ("file".equals(uri.getScheme())) {
                imagPath = uri.getPath();


        }

    //    PHOTO_URL = imagPath;
        //Bitmap bitmap = BitmapFactory.decodeFile(imagPath);
        //PHOTO_URL = savePhoto(bitmap);

        crop(uri);
        }

        public void crop(Uri uri)
        {
            //File input = new File(PHOTO_URL);
            File output = createFile();
            //Uri fileuri = Uri.fromFile(input);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setType("image/*");
            intent.setData(uri);
            intent.putExtra("aspecty",350);
            intent.putExtra("aspectx",350);
            intent.putExtra("outputy",350);
            intent.putExtra("outputx",350);

            intent.putExtra("return-data",false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(output));
            //intent.putExtra("path",path );
            startActivityForResult(intent,IMAGE_CUT);


        }

    public void Diolog(String [] strings, String title)
        {//弹出一个Diolog,参数对话框的item数组和一个标题
           // 这里边调用了getphoto函数得到照片

            new AlertDialog.Builder(mContext)
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


    public File createFile()
    {
        //创建一个存放头像的file
        String path = Environment.getExternalStorageDirectory().getPath()+"/Ownplan";
        String realPath = path + "/myICon";
        File dec = new File(path);
        File dec1 = new File(realPath);
        if(!dec.exists())
        {
            dec.mkdirs();
            if(!dec1.exists())
            {
                dec1.mkdirs();
            }

        }
        File file = new File(dec1,"myPhoto.jpg");
        return file;
    }
    public String savePhoto(Bitmap  bitmap)
    {
        String Photopath = null;
        createFile();
        File file = createFile();
        FileOutputStream fos = null;
        try {
             fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
          if(fos != null)
          {
              try {
                  fos.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }


          }
        }
        Photopath = file.getPath();



        return Photopath;
    }

}
