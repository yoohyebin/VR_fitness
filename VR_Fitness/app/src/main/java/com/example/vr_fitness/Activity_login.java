package com.example.vr_fitness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_login extends AppCompatActivity {

    private static String IP_ADDRESS = "192.168.0.149";
    EditText Eemail;
    EditText Epasswd;
    Button btn;
    private TextView mTextViewResult;
    String email;
    String passwd ;
    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn=(Button)findViewById(R.id.btn);
        Eemail=(EditText)findViewById(R.id.email);
        Epasswd=(EditText)findViewById(R.id.passwd);
        mTextViewResult = (TextView)findViewById(R.id.mresult);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData task = new InsertData();
                String email =Eemail.getText().toString();
                String passwd =Epasswd.getText().toString();
                task.execute("http://" + IP_ADDRESS + "/login.php", email, passwd);
            }
        });
    }
    public void click(View v){
        switch (v.getId()) {
            case R.id.btn2:
                startActivity(new Intent(this, join.class));
                break;
        }
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Activity_login.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);

            if(result.equals("로그인 완료")){
                SharedPreferences prefs = getSharedPreferences("login_info",0);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("email",email);
                editor.putString("passwd",passwd);
                editor.apply();
            }
         }


        @Override
        protected String doInBackground(String... params) {
            email = (String)params[1];
            passwd = (String)params[2];
            String serverURL = (String)params[0];
            String postParameters = "email=" + email + "&passwd=" + passwd;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
