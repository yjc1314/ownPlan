package com.ownplan.com.ownplan.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ownplan.logintest.R;

public class Tabview extends FrameLayout {
    public static final int COLOR_BLACK = Color.parseColor("#ff000000");
    public static final int COLOR_GREEN = Color.parseColor("#FF45C01A");

    ImageView imageView;
    ImageView imageView_select;
    TextView textView;

    public void setIconAndText(int icon, int iconselect, String text) {
        imageView.setImageResource(icon);
        imageView_select.setImageResource(iconselect);
        textView.setText(text);


    }

    public TextView getTextView() {
        return textView;
    }

    public ImageView getImageView_select() {
        return imageView_select;
    }

    public void setImageView_select(ImageView imageView_select) {
        this.imageView_select = imageView_select;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public Tabview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.tabview, this);
        imageView = findViewById(R.id.imageview);
        imageView_select = findViewById(R.id.imageviewselect);
        textView = findViewById(R.id.textview);
        setProgress(0);

    }

    public void setProgress(float progress) {
        imageView.setAlpha(1 - progress);//当为零的时候为透明，1的时候显现出来
        imageView_select.setAlpha(progress);
        textView.setTextColor(evaluate(progress, COLOR_BLACK, COLOR_GREEN));
       /* if (progress == 1)
        textView.setTextColor(COLOR_GREEN);
        else
            textView.setTextColor(COLOR_BLACK);
*/
    }

    public int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) (startA + (int) (fraction * (endA - startA)) << 24) |
                (int) (startR + (int) (fraction * (endR - startR)) << 16) |
                (int) (startG + (int) (fraction * (endG - startG)) << 8) |
                (int) (startB + (int) (fraction * (endB - startB)));


    }

}
