package com.ownplan.com.ownplan.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.ownplan.com.ownplan.activity.login.beans.User;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpContains;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpUtil;
import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.com.ownplan.utils.L;
import com.ownplan.logintest.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BasicActivity {

    private String userName, psw, spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name, et_psw;//编辑框
    public CircleImageView circleimageView;//头像框
    public  static final int POST_BACK = 111;
    public static final int REGIETER_CODE = 1;
    private  static final String url = HttpContains.URL; //服务器验证
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       SharedPreferences ps = getSharedPreferences("loginInfo", MODE_PRIVATE);
        if(checkLogin(ps))
        {
            startActivity(new Intent(MainActivity.this, Index.class));
            MainActivity.this.finish();
            //跳转到主界面，登录成功的状态传递到 MainActivity 中


        }
        setContentView(R.layout.activity_main);
        //设置此界面为竖屏
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        // L.d("我的路径是：",PHOTO_URL);

    }
    //获取界面控件
    private void init() {
        TextView tv_register = (TextView) findViewById(R.id.register);
        TextView tv_find_psw = (TextView) findViewById(R.id.find_psw);
        Button btn_login = (Button) findViewById(R.id.login);
        circleimageView = findViewById(R.id.circleImageview);
        circleimageView.setImageResource(R.drawable.nophoto);


        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REGIETER_CODE);
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

                // TextUtils.isEmpty
                User user = new User();
                user.setPassword(psw);
                user.setName(userName);

                if (TextUtils.isEmpty(userName)) {//用户名为空
                    Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(psw)) {//密码为空
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();

                } else //都不是空的调用httplogin
                {
                    loginWithOkHttp(url+"login",userName,psw);
                }
            }
        });
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
    //第二次之后的登录从sp中读取数据，如果已经登录了就不需要在进入登录界面
    private Boolean checkLogin(SharedPreferences sp)
    {
        Boolean Islogin = sp.getBoolean("isLogin",false);
        return Islogin;
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

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REGIETER_CODE:
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


        }


    }


    /**
     * 传入密码和账户里边处理逻辑
     * @param address
     * @param account
     * @param password
     */
    public void loginWithOkHttp(String address, final String account, String password){
        HttpUtil.loginWithOkHttp(address,account,password, new Callback() {
            final String name = account;
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {


                    @Override

                    public void run() {
                        L.d(e.getMessage());
                        Toast.makeText(MainActivity.this,"失败"+e.getCause(),Toast.LENGTH_SHORT).show();
                    }
                });
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseData.equals("true")){

                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            saveLoginStatus(true,account);
                            Intent intent = new Intent(MainActivity.this,Index.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this,"登录失败"+responseData.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

