package com.ownplan.com.ownplan.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ownplan.logintest.R;

public class ChooseDialog extends BasicActivity {
    private ImageView mImageView;
    private Button cancle;
    private Button change;
    private final  Context mContext = this ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dialog);
        //Toast.makeText(this,"",Toast.LENGTH_LONG).show();


    }

    public void initView() {
        mImageView = (ImageView) findViewById(R.id.icon);
        //mImageView.setImageResource();
        cancle = (Button) findViewById(R.id.cancle);
        change = (Button) findViewById(R.id.change);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"你点击了取消",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"你点击了修改",Toast.LENGTH_LONG).show();


            }
        });

    }

}
