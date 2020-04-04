package com.ownplan.com.ownplan.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.ownplan.com.ownplan.utils.BasicActivity;
import com.ownplan.logintest.R;

/*
布局不是很好。编辑的框没有边框，timepiker没有到中间来。就这样吧。
 */
public class Edit_plan extends BasicActivity {
    public static final int CHONSE_PLAN = 4;
    private String time ;
    private String doWhat ;
    private Button ok_button;
    private Button cancle_button;
    private  TimePicker timePicker;
    private EditText  editText ;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("ok",false);

        setResult(RESULT_OK,intent);

        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    void init ()
    {
        //获得timepick中的时间返回字符串
        timePicker = findViewById(R.id.timepick);
        timePicker.setIs24HourView(true);

        time = timePicker.getHour()+ ":" + timePicker.getMinute();
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String min = minute+"";
                if(minute < 10)
                {
                 min = "0"+minute;
                }
                time = hourOfDay + ":" + min;
            }
        });

        editText = findViewById(R.id.doWhat);

        //初始化button
        ok_button = findViewById(R.id.ok);
        cancle_button = findViewById(R.id.cancle);
        ok_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //这里确认设置
            doWhat = editText.getText().toString().trim();

            Intent intent = new Intent();
            intent.putExtra("ok",true);
            intent.putExtra("time",time);
            intent.putExtra("doWhat",doWhat);
            setResult(RESULT_OK,intent);
            finish();
        }
    });
        cancle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里确认设置
                Intent intent = new Intent();
                intent.putExtra("ok",false);
                setResult(RESULT_OK,intent);
                finish();
            }
        });




    }
}
