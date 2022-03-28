package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private Button userLogin;
    private Button sellerLogin;
    private Button userSignup;
    private Button sellerSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userLogin = (Button) findViewById(R.id.userLogin);
        sellerLogin = (Button) findViewById(R.id.sellerLogin);
        userSignup = (Button) findViewById(R.id.userSignup);
        sellerSignup = (Button) findViewById(R.id.sellerSignup);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUL();
            }
        });

        sellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSL();
            }
        });

        userSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUS();
            }
        });

        sellerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSS();
            }
        });

    }

    private void openUL() {
        Intent intent = new Intent(this, LoginnActivity.class);
        startActivity(intent);
    }
    private void openSL() {
        Intent intent = new Intent(this, LoginnActivity_Seller.class);
        startActivity(intent);
    }
    private void openUS() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    private void openSS() {
        Intent intent = new Intent(this, SignupActivity_Sellers.class);
        startActivity(intent);
    }
}