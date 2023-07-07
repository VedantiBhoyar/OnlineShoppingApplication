package com.example.onlineshoppingappliation;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

public class InputValidation {
    private Context context;


    public InputValidation(Context c) {
        this.context = c;
    }

    public boolean isInputTextEmpty(EditText text, String message) {
        String value = text.getText().toString().trim();
        if (value.isEmpty()) {
            text.setError(message);
            return false;
        }
        return true;
    }

    public boolean isEmailValid(EditText mail, String message) {
        String value = mail.getText().toString().trim();

        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches())
            mail.setError(message);
        return true;


    }

    public boolean isPasswordMatch(EditText pass, EditText conPass, String message) {
        String val1 = pass.getText().toString().trim();
        String val2 = conPass.getText().toString().trim();

        if (!val1.contentEquals(val2))
            conPass.setError(message);
        return true;
    }

    public boolean isPasswordValid(EditText pass, String message) {
        String value = pass.getText().toString().trim();
        Pattern password = Pattern.compile("^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&])"
                + "(?=\\S+$).{8,20}$");

        if (value.isEmpty() || !password.matcher(value).matches())
            pass.setError(message);
        return true;
    }
}
