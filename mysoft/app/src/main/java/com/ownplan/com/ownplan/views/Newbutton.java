package com.ownplan.com.ownplan.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ownplan.logintest.R;

public class Newbutton  extends LinearLayout {
    private ImageView mimageView ;
    private TextView mTextView;

    public Newbutton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.newbutton, this);
        mimageView = findViewById(R.id.imageview);
        mTextView = findViewById(R.id.textview);
    }
    public void  setImageAndText(int id , String text)
    {
        this.mTextView.setText(text);
        this.mimageView.setImageResource(id);


    }

}
