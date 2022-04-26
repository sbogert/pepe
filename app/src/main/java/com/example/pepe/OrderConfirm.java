package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class OrderConfirm extends AppCompatActivity {

    private String storeID;
    private TextView StoreName;
    private TextView ItemName;
    private TextView ItemPrice;
    private TextView ItemCaff;

    // but need to get the user's current location somehow from when they log in
    // that is how you are also going to get the delivery time/route estimates
    // have the delivery address box default filled with the user's current location
    // also have the marker and route on the map
    // but they can change this and the route and map will update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        StoreName = (TextView) findViewById(R.id.storeNameReview);
        ItemName = (TextView) findViewById(R.id.itemName);
        ItemPrice= (TextView) findViewById(R.id.itemPrice);
        ItemCaff = (TextView) findViewById(R.id.itemCaff);


        // fill in textviews with information
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeID = extras.getString("storeID");
            ItemName.setText(extras.getString("itemName"));
            ItemPrice.setText(extras.getString("itemPrice"));
            ItemCaff.setText(extras.getString("itemCaff"));
        }


    }



}