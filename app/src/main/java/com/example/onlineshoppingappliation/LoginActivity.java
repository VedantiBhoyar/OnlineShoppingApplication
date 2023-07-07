package com.example.onlineshoppingappliation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    Button login;
    EditText password, email;
    TextView text;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.button);
        text = findViewById(R.id.textView2);
        password = findViewById(R.id.editTextTextPassword);
        email = findViewById(R.id.editTextTextEmailAddress);

        InputValidation validation = new InputValidation(LoginActivity.this);
        database = new Database(LoginActivity.this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validation.isEmailValid(email, "Enter Valid Email"))
                    return;
                if (!validation.isPasswordValid(password, "Invalid Password"))
                    return;

                if (database.checkUsernamePassword(email.getText().toString().trim(), password.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "Successfully login", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
                    intent.putExtra("EmailKey", email.getText().toString().trim());
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, "Please First Register Yourself", Toast.LENGTH_LONG).show();
            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}