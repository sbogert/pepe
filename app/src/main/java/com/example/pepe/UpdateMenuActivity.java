package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pepe.Objects.Item;
import com.example.pepe.map.MapsActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateMenuActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Price;
    private EditText Caffeine;
    private Button Add;
    private Button Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Name = (EditText) findViewById(R.id.etName);
        Price = (EditText) findViewById(R.id.etPrice);
        Caffeine = (EditText) findViewById(R.id.etCaffine);
        Add = (Button) findViewById(R.id.addButton);
        Back = (Button) findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = Name.getText().toString();
                String value1 = Price.getText().toString();
                String value2 = Caffeine.getText().toString();

                Integer v = Integer.parseInt(value1);
                Integer v1 = Integer.parseInt(value2);

                request(value, v, v1);
            }
        });
    }

    private void openMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /** connect to database and verify user */
    private void request(String n, Integer p, Integer c) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/seller/update_menu";

        Item drink = new Item(n, p, c);
        String myItem  = new Gson().toJson(drink);

        // given login information is sent to check
        RequestBody formBody = new FormBody.Builder()
                .add("items", myItem)
                .build();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        String fullUrl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                //success
            } else if (response.code() != 200) {
                //failure
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}