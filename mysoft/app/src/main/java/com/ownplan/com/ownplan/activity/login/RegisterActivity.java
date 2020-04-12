package com.ownplan.com.ownplan.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ownplan.com.ownplan.activity.login.beans.User;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpContains;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpUtil;
import com.ownplan.com.ownplan.index.Index;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.com.ownplan.utils.L;
import com.ownplan.logintest.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends BasicActivity {

    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again,et_age;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,psw,pswAgain,age;
    private RadioGroup Sex;
    private RadioButton nan,nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        init();
    }


    private void init() {
        //从activity_register.xml 页面中获取对应的UI控件


        Button btn_register = (Button) findViewById(R.id.btn_register);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R.id.et_psw);
        et_psw_again= (EditText) findViewById(R.id.et_psw_again);
        Sex= (RadioGroup) findViewById(R.id.SexRadio);
        et_age = findViewById(R.id.age);
        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                String  sex;
                int sexChoseId = Sex.getCheckedRadioButtonId();
                switch (sexChoseId) {
                    case R.id.mainRegisterRdBtnFemale:
                        sex = "男";
                        break;
                    case R.id.mainRegisterRdBtnMale:
                        sex = "女";
                        break;
                    default:
                        sex = null;
                        break;
                }
                User user = new User();
                user.setName(userName);
                user.setPassword(psw);
                user.setSex(sex);
                if(age == null)
                {
                    user.setAge(0);
                }
                user.setAge(Integer.getInteger(age));

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if (sex == null){
                    Toast.makeText(RegisterActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();


                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();

                    //下面从服务器判断是否合法并处理
                } else{
                    isExistUserName(user);
                }
            }
        });
    }
    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
        age =et_age.getText().toString().trim();
    }
    /**
     *从服务器判断是否存在该用户
     * 不存在就注册返回
     * 存在toast
     */
    private void isExistUserName(final User user){

        Gson gson = new Gson();
        String json = gson.toJson(user);

        HttpUtil.checkUsername(HttpContains.URL+"register",json, new Callback() {
            final String name = user.getName();
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {


                    @Override

                    public void run() {
                        L.d(e.getMessage());
                        Toast.makeText(RegisterActivity.this,"网络错误，稍后重试",Toast.LENGTH_SHORT).show();
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
                        //返会true表示可以使用该用户名 并且在服务器注册
                        if (responseData.equals("true")){
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent data = new Intent();
                            data.putExtra("userName", name);
                            setResult(RESULT_OK, data);
                            //RESULT_OK为Activity系统常量，状态码为-1，
                            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                            RegisterActivity.this.finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"该用户名已经存在"+responseData.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}
