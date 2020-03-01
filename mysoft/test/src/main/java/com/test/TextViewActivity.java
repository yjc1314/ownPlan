package com.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class TextViewActivity extends AppCompatActivity {
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        mTv4=findViewById(R.id.tv_1);

        mTv4.setPaintFlags(Paint.START_HYPHEN_EDIT_INSERT_HYPHEN);
        //mTv4.getPaint().setAntiAlias(true);    //去除锯齿

        mTv5=(TextView)findViewById(R.id.tv_4);
        mTv5.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
       //下划线
        mTv6=(TextView)findViewById(R.id.tv_3);
        mTv6.setText(Html.fromHtml("<u>泽神在奔跑</u>"));



    }
}
