package com.ownplan.com.ownplan.activity.login.httpconnect;


import com.ownplan.com.ownplan.activity.login.beans.User;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    //登录
    public static OkHttpClient client = new OkHttpClient();
    public static void loginWithOkHttp(String address, String account, String password, Callback callback) {

        RequestBody body = new FormBody.Builder()
                .add("loginAccount", account)
                .add("loginPassword", password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void findpassword(String address, String account, Callback callback) {

        RequestBody body = new FormBody.Builder()
                .add("loginAccount", account)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 查看账户并注册
     * @param address
     * @param json
     * @param callback
     */
    public static void checkUsername(String address,String  json , Callback callback)
    {
        //传递json数据需要，因为我们需要把整个user对象都传递过去 ,合法我们就写入数据库。不合法就返回false

        RequestBody body = FormBody.create(MediaType.parse(HttpContains.MediaType_JSON)
                , json);

        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);


    }
}

