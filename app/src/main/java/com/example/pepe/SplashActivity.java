package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button userLogin = (Button) findViewById(R.id.userLogin);
        Button sellerLogin = (Button) findViewById(R.id.sellerLogin);
        Button userSignup = (Button) findViewById(R.id.userSignup);
        Button sellerSignup = (Button) findViewById(R.id.sellerSignup);

        userLogin.setOnClickListener(view -> openUL());

        sellerLogin.setOnClickListener(view -> openSL());

        userSignup.setOnClickListener(view -> openUS());

        sellerSignup.setOnClickListener(view -> openSS());

    }

    private void openUL() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void openSL() {
        Intent intent = new Intent(this, LoginActivity_Seller.class);
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