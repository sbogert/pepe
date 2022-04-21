package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class food_list extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> foodNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        listView = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,foodNames);
        listView.setAdapter(arrayAdapter);

        //when add to database
        //foodNames.add();
        arrayAdapter.notifyDataSetChanged();

    }
}

