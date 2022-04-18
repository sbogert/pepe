package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class ViewStore extends AppCompatActivity {
    private TextView StoreName;
    private Spinner Menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        StoreName = (TextView) findViewById(R.id.storeName);
        Menu = (Spinner) findViewById(R.id.menu);


        //populate menuItems with arraylist of menu items
        ArrayList<String> menuItems = new ArrayList<String>();

        String[] menu = menuItems.toArray(new String[menuItems.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, menu);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        Menu.setAdapter(adapter);




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StoreName.setText(extras.getString("storeName"));
        }

    }


}