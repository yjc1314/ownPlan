package com.ownplan.com.ownplan.utils;

import android.util.Log;

import com.ownplan.logintest.BuildConfig;

public class L {
    private static final String TAG = "yjc";
    public  static  boolean isDebug = BuildConfig.DEBUG;
    public  static  void d(String msg,Object ...args){

        if(!isDebug)
        {
            return ;
        }
        Log.d(TAG,String.format(msg,args));



    }
}
