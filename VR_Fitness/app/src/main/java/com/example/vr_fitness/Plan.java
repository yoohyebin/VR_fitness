package com.example.vr_fitness;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Plan extends AppCompatActivity {

    EditText eplan;
    info Info = new info();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);
        eplan = (EditText)findViewById(R.id.editplan);
    }
    public void OnClick(View v){
        if(v.getId() == R.id.btnPlan_save){
            Info.setPlan(eplan.getText().toString());
            SharedPreferences prefs = getSharedPreferences("info",0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("plan",eplan.getText().toString());
            editor.apply();
        }
        else if(v.getId() == R.id.btnPlan_home){
            Intent intentHome = new Intent(this,MainActivity.class);
            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentHome);
            finish();
        }
    }
}
