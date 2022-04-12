package com.example.permon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    TextView v1, v2, v3, st;
    RequestQueue queue;
    ImageView i1;
    String URL = "https://mistatwistapfm.000webhostapp.com/fetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon);
        v1 = findViewById(R.id.cpu);
        v2 = findViewById(R.id.ram);
        v3 = findViewById(R.id.dsk);
        st=findViewById(R.id.stat);
        i1= findViewById(R.id.icn);
        final Handler handler = new Handler();
        queue = Volley.newRequestQueue(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            JSONArray array=object.getJSONArray("result");
                            for(int i=0;i<array.length();i++) {
                                JSONObject object1=array.getJSONObject(i);
                                String pc1 = object1.getString("pc1");
                                String pc2 = object1.getString("pc2");
                                String stat = object1.getString("stat");

                                if (stat.equals("Online")){
                                    i1.setImageResource(R.drawable.ic_location_dot_green);
                                }
                                else if(stat.equals("Offline")){
                                    i1.setImageResource(R.drawable.ic_location_red);
                            }
                                v1.setText("CPU Usage: "+pc1+" %");
                                v2.setText("RAM Usage: " +pc2+ " %");
                                st.setText(stat);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                    }
                });

                queue.add(request);
                handler.postDelayed(this, 1000);
            }


    };
        handler.post(runnable);
}
}



