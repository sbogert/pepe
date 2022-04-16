package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class ViewStore extends AppCompatActivity {
    private TextView storeName;
    private Spinner menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        storeName = (TextView) findViewById(R.id.storeName);
        menu = (Spinner) findViewById(R.id.menu);

        ArrayList<String> menuItems = new ArrayList<String>();

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, android.R.layout.simple_spinner_item, menuItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeName.setText(extras.getString("storeName"));
        }



    }


}