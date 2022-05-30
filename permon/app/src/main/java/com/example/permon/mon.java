package com.example.permon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class mon extends AppCompatActivity {
    TextView v1, v2, v3, st,t1,t2,st2;
    ImageView i1,i2,i3,i4;
    RequestQueue queue;
    String URL = "https://mistatwistapfm.000webhostapp.com/fetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon);
        v1 = findViewById(R.id.cpu);
        t1 = findViewById(R.id.cpu2);
        v2 = findViewById(R.id.ram);
        t2 = findViewById(R.id.ram2);
        v3 = findViewById(R.id.dsk);
        st = findViewById(R.id.stat);
        st2 = findViewById(R.id.stat2);
        i1 = findViewById(R.id.icn);
        i3 = findViewById(R.id.icn2);
        i2 = findViewById(R.id.imageView);
        i4 = findViewById(R.id.imageView2);


        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mon.this, debug.class));
            }
        });

        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mon.this, debug.class));
            }
        });



        final Handler handler = new Handler();
        queue = Volley.newRequestQueue(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("result");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);
                                String pc1 = object1.getString("pc1");
                                String pc2 = object1.getString("pc2");
                                String stat = object1.getString("stat");
                                String pc12 = object1.getString("pc1");
                                String pc22 = object1.getString("pc2");
                                String stat2 = object1.getString("stat");

                                if (stat.equals("Online")) {
                                    i1.setImageResource(R.drawable.ic_location_dot_green);
                                } else if (stat.equals("Offline")) {
                                    i1.setImageResource(R.drawable.ic_location_red);
                                }
                                v1.setText("CPU Usage: " + pc1 + " %");
                                v2.setText("RAM Usage: " + pc2 + " %");
                                st.setText(stat);

                                if (stat2.equals("Online")) {
                                    i3.setImageResource(R.drawable.ic_location_dot_green);
                                } else if (stat2.equals("Offline")) {
                                    i3.setImageResource(R.drawable.ic_location_red);
                                }
                                t1.setText("CPU Usage: " + pc12 + " %");
                                t2.setText("RAM Usage: " + pc22 + " %");
                                st2.setText(stat);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });

                queue.add(request);
                handler.postDelayed(this, 1000);
            }


        };
        handler.post(runnable);

    }
}



