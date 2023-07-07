package com.example.onlineshoppingappliation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
TextView txt,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        txt=findViewById(R.id.view);
        name=findViewById(R.id.textView6);
        txt.setText(getIntent().getStringExtra("harry-potter1"));
        name.setText(getIntent().getStringExtra("ID"));


    }
}