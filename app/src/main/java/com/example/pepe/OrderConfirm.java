package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class OrderConfirm extends AppCompatActivity {

    // have radio button or something so that the user can choose to set a default delivery address for
    // future orders
    // jk no radio button
    // but need to get the user's current location somehow from when they log in
    // that is how you are also going to get the delivery time/route estimates
    // have the delivery address box default filled with the user's current location
    // also have the marker and route on the map
    // but they can change this and the route and map will update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
    }
}