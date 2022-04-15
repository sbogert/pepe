package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pepe.map.MapsActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateMenuActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Price;
    private EditText Caffine;
    private Button Add;
    private Button Back;

    private ImageView Map;
    private ImageView History;
    private ImageView Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etName);
        Price = (EditText) findViewById(R.id.etPrice);
        Caffine = (EditText) findViewById(R.id.etCaffine);
        Add = (Button) findViewById(R.id.addButton);
        Back = (Button) findViewById(R.id.back);
        Map = (ImageView) findViewById(R.id.mapButton);
        History = (ImageView) findViewById(R.id.historyButton);
        Menu = (ImageView) findViewById(R.id.menuButton);

        //get intent ID
        Integer userid = null;
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b != null){
            userid = b.getInt("USERID");
        }
        else{
            System.out.println("could not find userid");
        }
        Integer finalUserid = userid;

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap(finalUserid);
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory(finalUserid);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBack(finalUserid);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value= Price.getText().toString();
                String value1= Caffine.getText().toString();
                int finalValue=Integer.parseInt(value);
                int finalValue1=Integer.parseInt(value1);

                try {
                    validate(Name.getText().toString(), finalValue, finalValue1 , finalUserid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openMap(Integer userID) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("USERID",userID);
        startActivity(intent);
    }

    private void openBack(Integer userID) {
        Intent intent = new Intent(this, menu.class);
        intent.putExtra("USERID",userID);
        startActivity(intent);
    }

    //Enter name
    private void openHistory(Integer userID) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("USERID",userID);
        startActivity(intent);
    }
}