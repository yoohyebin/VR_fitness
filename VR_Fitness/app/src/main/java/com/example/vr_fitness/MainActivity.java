package com.example.vr_fitness;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_CONTACT_REQUEST =1 ;
    String email;
    TextView textView;
    TextView text1;
    TextView text2;
    TextView text3;
    info Info = new info();
    String plan;
    String heart;
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Date time = new Date();

    String s_time;
    String e_time;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.name);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);

        if(savedInstanceState == null){
            SharedPreferences prefs = getSharedPreferences("login_info",0);
            email =prefs.getString("email","0");
            textView.setText(email);
            String passwd = prefs.getString("passwd","0");
            if(email.equals("0")) {
                login();
            }
            SharedPreferences prefss = getSharedPreferences("info",0);
            plan = prefss.getString("plan","");
            heart= prefss.getString("heart","");
        }
    }

    public void login(){
        startActivity(new Intent(this, Activity_login.class));
    }
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnHome:
                break;
            case R.id.btnUnity:
                s_time = format.format(System.currentTimeMillis());
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.jinyoung.train");
                startActivity(intent);
                break;
            case R.id.btnCalendar:
                startActivity(new Intent(this, Activity_Calendar.class));
                break;
            case R.id.btnGraph:
                break;
            case R.id.btnHeart:
                startActivity(new Intent(this, Heart.class));
                break;
            case R.id.btnSetting:
                startActivity(new Intent(this, Activity_Setting.class));
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefss = getSharedPreferences("info",0);
        plan = prefss.getString("plan","");
        heart= prefss.getString("heart","");
        text1.setText("오늘의 운동량 : 00:30");
        text2.setText("오늘의 목표 : "+ plan);
        text3.setText("오늘의 심박수 : "+heart);
    }
}
