package com.example.vr_fitness;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Activity_logout extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("login_info", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("email", "0");
        editor.putString("passwd", "0");
        editor.apply();

        //앱종료
    }
}
