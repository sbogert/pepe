package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SellerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);

        Button profile = (Button) findViewById(R.id.profile);
        Button edit = (Button) findViewById(R.id.userSignup);
        Button pastOrders = (Button) findViewById(R.id.sellerLogin);

        profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        edit.setOnClickListener(view -> startActivity(new Intent(this, UpdateMenuActivity.class)));
        pastOrders.setOnClickListener(view -> startActivity(new Intent(this, PastOrders.class)));
    }
}