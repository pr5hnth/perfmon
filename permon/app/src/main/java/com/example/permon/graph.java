package com.example.permon;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;

public class graph extends AppCompatActivity {
    String URL = "https://mistatwistapfm.000webhostapp.com/fetch.php";
    LineChart lineChart;
    RequestQueue queue;
    String pc1,pc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineChart = findViewById(R.id.lineChart);



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
                                 pc1 = object1.getString("pc1");
                                 pc2 = object1.getString("pc2");

                                ArrayList<Entry> yAXISsin = new ArrayList<>();
                                ArrayList<Entry> yAXIScos = new ArrayList<>();
                                ArrayList<Entry> values = new ArrayList<>();
                                float x = 0;
                                int numDataPoints = 100;
                                for (int j = 0; i < numDataPoints; i++) {
                                    float val = (float) (Math.random() * 100) + 3;
                                    values.add(new Entry(j, val));
                                }
                                ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
                                LineDataSet lineDataSet1 = new LineDataSet(values, "DataSet 1");
                                LineDataSet lineDataSet2 = new LineDataSet(yAXISsin, "sin");

                                lineDataSet1.setDrawCircles(false);
                                lineDataSet1.setColors(Color.RED);
                                lineDataSet2.setDrawCircles(false);
                                lineDataSet2.setColors(Color.BLUE);

                                lineDataSets.add(lineDataSet1);
                                lineDataSets.add(lineDataSet2);

                                lineChart.setData(new LineData(lineDataSets));
                                lineChart.setVisibleXRangeMaximum(65f);

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