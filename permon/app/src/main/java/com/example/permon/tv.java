package com.example.permon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tv extends AppCompatActivity {

    TextView t1;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        t1 = findViewById(R.id.isn);

        database = FirebaseDatabase.getInstance("https://perfmon-a4274-default-rtdb.firebaseio.com");
        myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.child("users").child("admin").child("tickets").getValue().toString();
                t1.setText(a);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}