package com.ownplan.com.ownplan.activity.login.httpconnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.ownplan.com.ownplan.activity.login.beans.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 *                           失败的httpconnect封装，无法解决线程传递参数问题
 * 需要功能
 * 我们需要注册时需要用到传送给服务器我们的user对象
 * 1 把我们的user转化为json数据
 * 2传送给服务器
 * 登录的时候需要验证密码和用户名
 */
public class UserHttpconnnect {
    //提供我们的上传图片，用户名，密码，json对象

    public  static final int POST_BACK = 111;
    private  String url;
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


    /**
     * 传入我们的json数据，判断是否登录成功
     */
    public String get(String s)
    {

        return s;
    }
    public User login(String json) {



        RequestBody requestBody = FormBody.create(MediaType.parse(
                "application/json; charset=utf-8"),json);
        final Request request = new Request.Builder()
                .url(url + "/HttpWeb/LoginServlet")//请求的url
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                String s = response.body().toString();
                Message msg = new Message();
                msg.what = POST_BACK;
                Bundle bundle=new Bundle();
                bundle.putString("result",s);
                msg.setData(bundle);
                handler.sendMessage(msg);


            }

            @Override

            public void onFailure(Call call, IOException e) {



            }
                  });

    return null;}


}



