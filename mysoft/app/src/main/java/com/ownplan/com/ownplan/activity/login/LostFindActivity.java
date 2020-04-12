package com.ownplan.com.ownplan.activity.login;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ownplan.com.ownplan.activity.login.beans.User;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpContains;
import com.ownplan.com.ownplan.activity.login.httpconnect.HttpUtil;
import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.com.ownplan.utils.L;
import com.ownplan.logintest.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LostFindActivity extends BasicActivity {
    private EditText Inputname_edt;
    private TextView password_texV;
    private Button back, sumbmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        init_view();

    }

    private void init_view() {
        Inputname_edt = findViewById(R.id.inputname);
        password_texV = findViewById(R.id.youpassword);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LostFindActivity.this.finish();
            }
        });
        sumbmit = findViewById(R.id.submit);
        sumbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getpassword();
            }
        });
    }

    private void getpassword() {
        String userName = Inputname_edt.getText().toString().trim();
        //点击之后提交账号
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(LostFindActivity.this, "请输入你的用户名字!", Toast.LENGTH_SHORT).show();
        } else {

            getfromhost(userName);
        }


    }

    private void getfromhost(String userName) {
        HttpUtil.findpassword(HttpContains.URL + "findpassword", userName, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                runOnUiThread(new Runnable() {
                    @Override

                    public void run() {
                       // L.d(e.getMessage());
                        Toast.makeText(LostFindActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                //拿到服务器传回来的json数据
                final String json = response.body().string();

                L.d(json);
                runOnUiThread(new Runnable() {
                    @Override

                    public void run() {
                        if(json == null || json == ""||json.equals("null"))
                        {

                            Toast.makeText(LostFindActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();

                        }else
                        {

                            Toast.makeText(LostFindActivity.this,"找到了密码",Toast.LENGTH_SHORT).show();

                            Gson gson = new Gson();
                            User user = gson.fromJson(json, User.class);
                            password_texV.setText(user.getPassword());
                        }

                    }
                });

            }
        });
    }
}