package com.example.vr_fitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Activity_Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }
    public void onclick(View v){
        switch (v.getId()) {
            case R.id.btnplan:
                startActivity(new Intent(this, Plan.class));
                break;
            case R.id.btnlogout:
                break;
        }
    }
}
