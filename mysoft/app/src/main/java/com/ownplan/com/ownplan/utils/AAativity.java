package com.ownplan.com.ownplan.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class AAativity extends BasicActivity {
    public static  AAativity instance = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }
}
