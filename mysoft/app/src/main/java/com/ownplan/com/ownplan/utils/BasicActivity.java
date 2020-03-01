package com.ownplan.com.ownplan.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollecter.Add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollecter.Remove(this);
    }
}
