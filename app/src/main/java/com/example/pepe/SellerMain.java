package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pepe.map.MapsActivity;

public class SellerMain extends AppCompatActivity {

    private Button Profile;
    private Button Edit;
    private Button PastOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);

         Profile = (Button) findViewById(R.id.profile);
         Edit = (Button) findViewById(R.id.userSignup);
         PastOrders = (Button) findViewById(R.id.sellerLogin);

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEdit();
            }
        });

        PastOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPastOrders();
            }
        });

    }

    private void openProfile() {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }
    private void openEdit() {
        Intent intent = new Intent(this, UpdateMenuActivity.class);
        startActivity(intent);
    }
    private void openPastOrders() {
        Intent intent = new Intent(this, PastOrders.class);
        startActivity(intent);
    }
}