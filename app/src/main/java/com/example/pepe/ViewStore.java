package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewStore extends AppCompatActivity {
    private TextView storeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        storeName = (TextView) findViewById(R.id.storeName);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeName.setText(extras.getString("storeName"));
        }


    }


}