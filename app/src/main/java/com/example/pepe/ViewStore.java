package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pepe.map.MapsActivity;
import com.example.pepe.model.Item;
import com.example.pepe.map.MenuInfoAccess;

import java.util.ArrayList;
import java.util.List;

public class ViewStore extends AppCompatActivity {

    private TextView StoreName;
    private Spinner Menu;
    private Button Back;



    // do something about passing along store name vs email or waht
    // in case two stores have same name
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
            StoreName.setText(extras.getString("storeName"));

//            List<Item> menuItems = MenuInfoAccess.MenuInfo(storeID, Integer.parseInt(userID));
//
//            ArrayList<Item> menuArray = new ArrayList<>(menuItems);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, android.R.layout
//        .simple_spinner_item, menuArray);

//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        }
    }

    private void openBack() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
