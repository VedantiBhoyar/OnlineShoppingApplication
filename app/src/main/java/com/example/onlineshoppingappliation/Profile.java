package com.example.onlineshoppingappliation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


public class Profile extends AppCompatActivity {


    TextView backView, Name, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        backView = findViewById(R.id.app);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, NavDrawerActivity.class);
                startActivity(intent);
            }
        });


        CoordinatorLayout layout = findViewById(R.id.clayout);

        Email = layout.findViewById(R.id.Email);
        Name = layout.findViewById(R.id.Name);

        // Getting Value From Intent
        Intent intent = getIntent();

        Name.setText(intent.getStringExtra("UserName"));
        Email.setText(intent.getStringExtra("Email"));


    }


}
