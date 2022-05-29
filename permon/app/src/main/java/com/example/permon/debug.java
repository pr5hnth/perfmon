package com.example.permon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import java.util.HashMap;

public class debug extends AppCompatActivity {

    Button b1,b2;
    String s1;
    private static final String UPDATE_URL = "https://mistatwistapfm.000webhostapp.com/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        b1= findViewById(R.id.sd);
        b2 = findViewById(R.id.rs);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SwipeButton swipeButton = (SwipeButton)findViewById(R.id.swipe_btn);

                swipeButton.setVisibility(View.VISIBLE);

                swipeButton.setOnStateChangeListener(new OnStateChangeListener() {

                    @Override

                    public void onStateChange(boolean active) {

                        s1="SD";
                        UpdateUser();

                    }

                });
                Toast. makeText(getApplicationContext(),"Shutting Down Server",Toast. LENGTH_SHORT).show();
            }
        });


        


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s1="RS";
                Toast. makeText(getApplicationContext(),"Restarting Server",Toast. LENGTH_SHORT).show();
                UpdateUser();

            }
        });


    }

    private void UpdateUser() {

        registers(s1);
    }

    private void registers(String cmd) {

        class RegisterUsers extends AsyncTask<String, Void, String> {
           // ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
             //   loading = ProgressDialog.show(debug.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("cmd", params[0]);

                String result = ruc.sendPostRequest(UPDATE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(cmd);

    }
}