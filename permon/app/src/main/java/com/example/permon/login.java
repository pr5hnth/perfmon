package com.example.permon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    //
    EditText username, password;
    Button login;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar pbar;
    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "gatepass" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.uid);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.buttonlogin);
        pbar=findViewById(R.id.pbar);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        database = FirebaseDatabase.getInstance("https://perfmon-a4274-default-rtdb.firebaseio.com/");
        myRef = database.getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(username.getText().toString().equals("")) && !(password.getText().toString().equals("")) && (username.getText().toString().length() > 4) && (password.getText().toString().length() >=5)) {
                    // Condition to check if the fields are not empty and greater than some value
                    pbar.setVisibility(View.VISIBLE);
                    addNotification();
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("users").hasChild(username.getText().toString()))
                            // Condition to check whether there is a username in firebase
                            {


                                if(username.getText().toString().equals(dataSnapshot.child("users"))) {



                                    if (password.getText().toString().equals(dataSnapshot.child("users").child(username.getText().toString()).child("password").getValue().toString()))
                                    // Condition to check the password is matching with the entered password
                                    {
                                        // To go to next windows based on their selection with spinner..
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("user",username.getText().toString());
                                        editor.commit();
                                        pbar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(login.this, MainActivity.class));
                                        username.getText().clear();
                                        password.getText().clear();


                                    }
                                    else {
                                        Toast.makeText(login.this, "Please check your password", Toast.LENGTH_SHORT).show();
                                        pbar.setVisibility(View.INVISIBLE);

                                    }
                                }


                                else if(username.getText().toString().matches("[a-zA-Z0-9]+")) {
                                    if (password.getText().toString().equals(dataSnapshot.child("users").child(username.getText().toString()).child("password").getValue().toString())) {
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("user",username.getText().toString());
                                        editor.commit();
                                        pbar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(login.this, MainActivity.class));
                                        username.getText().clear();
                                        password.getText().clear();
                                    }
                                    else {
                                        Toast.makeText(login.this, "Please check your password", Toast.LENGTH_SHORT).show();
                                        pbar.setVisibility(View.INVISIBLE);

                                    }
                                }


                            }

                            else
                            {
                                Toast.makeText(login.this, "Username does not exist.", Toast.LENGTH_SHORT).show();
                                pbar.setVisibility(View.INVISIBLE);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });


                } else {
                    if ((username.getText().toString().equals("")) || !(username.getText().toString().length() > 4)) {
                        pbar.setVisibility(View.INVISIBLE);
                        username.setError("Enter valid username");


                    } else {
                        username.setError(null);
                    }


                    if ((password.getText().toString().equals("")) || !(password.getText().toString().length() >= 5)) {
                        pbar.setVisibility(View.INVISIBLE);
                        password.setError("Enter valid password");

                    } else {
                        password.setError(null);
                    }
                }


            }


        });



    }

    private void addNotification() {

        NotificationCompat.Builder builder; //set priority of notification
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.serv) //set icon for notification
                .setContentTitle("Notifications Example") //set title of notification
                .setContentText("This is a notification message")//this is notification message
                .setAutoCancel(true) // makes auto cancel of notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();
    }




}
