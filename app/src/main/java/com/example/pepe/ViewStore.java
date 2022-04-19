package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pepe.data.model.Item;
import com.example.pepe.map.MenuInfoAccess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewStore extends AppCompatActivity {

    private TextView StoreName;
    private Spinner Menu;
    private int storeID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        StoreName = (TextView) findViewById(R.id.storeName);
        Menu = (Spinner) findViewById(R.id.menu);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StoreName.setText(extras.getString("storeName"));
            storeID = Integer.parseInt(extras.getString("storeID"));
            System.out.println(storeID);

            //Bundle extras = getIntent().getExtras();
            if (extras != null) {
                StoreName.setText(extras.getString("storeName"));
            }

            String userID = ((MyApplication) this.getApplication()).getUser();
            List<Item> menuItems = MenuInfoAccess.MenuInfo(storeID, Integer.parseInt(userID));

            ArrayList<Item> menuArray = new ArrayList<>(menuItems);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, android.R.layout
//        .simple_spinner_item, menuArray);

//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        }
    }
}
