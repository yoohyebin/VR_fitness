package com.example.vr_fitness;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class join extends AppCompatActivity {

    private static String IP_ADDRESS = "192.168.0.149";
    private static String TAG = "phptest";

    private EditText Eemail;
    private EditText Epasswd;
    private EditText Ename;
    private EditText Ebirth;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Eemail = (EditText) findViewById(R.id.editemail);
        Epasswd = (EditText) findViewById(R.id.editpasswd);
        Ename = (EditText) findViewById(R.id.editName);
        Ebirth = (EditText) findViewById(R.id.editbirth);
        mTextViewResult = (TextView)findViewById(R.id.mresult);

        Button btnInsert =(Button)findViewById(R.id.btn1);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Eemail.getText().toString();
                String passwd = Epasswd.getText().toString();
                String name = Ename.getText().toString();
                String birth = Ebirth.getText().toString();
                Log.d(TAG, "email  - " + email);
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", email, passwd, name, birth);

                Eemail.setText("");
                Epasswd.setText("");
                Ename.setText("");
                Ebirth.setText("");
            }
        });
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(join.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String email = (String)params[1];
            String passwd = (String)params[2];
            String name = (String)params[3];
            String birth = (String)params[4];
            String serverURL = (String)params[0];
            String postParameters = "email=" + email + "&passwd=" + passwd+ "&name=" + name+ "&birth=" + birth;

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
                Log.d(TAG, "POST response code - " + responseStatusCode);

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
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
