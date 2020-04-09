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
import com.ownplan.com.ownplan.activity.login.beans.User;
import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.logintest.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
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

    private  static final String url ="http://192.168.43.99/demo"; //服务器验证
    private OkHttpClient client = new OkHttpClient();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case POST_BACK:

                    String conme = msg.getData().get("result").toString();
                    Gson gson = new Gson();

                    break;
            }
        }
    };

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        // L.d("我的路径是：",PHOTO_URL);

    }
    //获取界面控件
    private void init() {
        //从main_title_bar中获取的id
        //从activity_login.xml中获取的
        TextView tv_register = (TextView) findViewById(R.id.register);
        TextView tv_find_psw = (TextView) findViewById(R.id.find_psw);
        Button btn_login = (Button) findViewById(R.id.login);
        circleimageView = findViewById(R.id.circleImageview);
        circleimageView.setImageResource(R.drawable.luntan);


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
                spPsw = readPsw(userName);
                // TextUtils.isEmpty
                User user = new User();
                user.setPassword(spPsw);
                user.setName(userName);

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                } else if (loginhost(user)) {
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
                } else if ((spPsw != null && !TextUtils.isEmpty(spPsw) && !loginhost(user))) {
                    Toast.makeText(MainActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
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


        }


    }

    /**
     * 从服务器判断我们是否登录成功
     * @param needuser
     * @return
     */
    public boolean loginhost( User needuser)
    {

        User user = null;
        //将对象转化为json对象
        final Gson gson = new Gson();
        String s = gson.toJson(needuser);
        //使用 okhttp    client 已经有了 在最上面是成员变量
        //因为我们是post所以我们需要构造我们的reqpostbody  html 中的请求体
        RequestBody requestBody = FormBody.create(MediaType.parse(
                "application/json;charset=utf-8"),s);
        // 获取request
        Request request = new Request.Builder().url(url).post(requestBody).build();

        //异步开启 管理我们的request 这里不因该是异步，因该同步
        Call call = client.newCall(request);
        //使用call执行
        //登录用同步
        try {
            Response response = call.execute();
          user = gson.fromJson(response.body().string(), User.class);
          if(user.getName() == needuser.getName()&&user.getPassword() == needuser.getPassword())
          {
              return true;
          }

        } catch (IOException e) {
          Toast.makeText(this,"连接网络失败",Toast.LENGTH_LONG).show();
        }
        return false;


    }
}

