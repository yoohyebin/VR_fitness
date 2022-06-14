package com.example.vr_fitness;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class info  {
    String email = "1";
    String plan="" ;
    String heart="";

    public void setEmail (String email){ this.email=email; }
    public void setHeart (String heart){
                    this.heart=heart;
                }
    public void setPlan(String plan){ this.plan=plan; }
    public String getEmail(){ return email; }
    public String getHeart(){ return heart; }
    public String getPlan(){ return plan; }
}
