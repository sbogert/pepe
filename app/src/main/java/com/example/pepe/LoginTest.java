package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginTest extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);

        Name = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
    }
}