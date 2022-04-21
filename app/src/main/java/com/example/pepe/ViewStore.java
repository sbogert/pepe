package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewStore extends AppCompatActivity {

    private TextView StoreName;
    private Spinner Menu;
    private Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        StoreName = (TextView) findViewById(R.id.storeName);
        Menu = (Spinner) findViewById(R.id.menu);
        Back = (Button) findViewById(R.id.backtomap);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBack();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StoreName.setText(extras.getString("storeID"));
        }
    }
    private void openBack() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
