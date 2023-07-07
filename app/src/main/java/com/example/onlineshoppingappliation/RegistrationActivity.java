package com.example.onlineshoppingappliation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    EditText name, email, password, confirmPassword;
    TextView text;
    Button register;
    InputValidation valid;
    UserPojo user;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        name = findViewById(R.id.editTextText5);
        email = findViewById(R.id.editTextTextEmailAddress3);
        password = findViewById(R.id.editTextTextPassword3);
        confirmPassword = findViewById(R.id.editTextTextPassword4);
        text = findViewById(R.id.textView10);
        register = findViewById(R.id.button8);

        // If user already have an account the user will move to login page
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        valid = new InputValidation(RegistrationActivity.this);
        db = new Database(RegistrationActivity.this);
        user = new UserPojo();

        // Checking Validation
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valid.isInputTextEmpty(name, "Enter Full Name")) {
                    return;
                }

                if (!valid.isEmailValid(email, "Enter Valid Email")) {
                    return;
                }
                if (!valid.isInputTextEmpty(password, "Enter Password")) {
                    return;
                }
                if (!valid.isPasswordValid(password, "Your Password must contain atleast 8 characters,Atleast one number,special symbol,captial letter")) {
                    return;
                }
                if (!valid.isPasswordMatch(password, confirmPassword, "Password Does Not Matches")) {
                    return;
                }
                if (!db.checkEmail(email.getText().toString().trim())) {
                    user.setName(name.getText().toString().trim());
                    user.setEmail(email.getText().toString().trim());
                    user.setPassword(password.getText().toString().trim());
                    db.addUser(user);


                    Toast.makeText(RegistrationActivity.this, "Registration Successfully Completed", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(RegistrationActivity.this, "Email Already Exists", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}