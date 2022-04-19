package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pepe.Models.MenuItem_Model;
import com.example.pepe.map.MapsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
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
    private FirebaseDatabase db;
    private FirebaseAuth fAuth;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Name = (EditText) findViewById(R.id.etName);
        Price = (EditText) findViewById(R.id.etPrice);
        Caffeine = (EditText) findViewById(R.id.etCaffine);
        Add = (Button) findViewById(R.id.addButton);
        Back = (Button) findViewById(R.id.back);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        dbReference = db.getReference("Items");

        String uniqueId = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Message");
        myRef.child(uniqueId).setValue("Hello World !");


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = Name.getText().toString();
                String value1 = Price.getText().toString();
                Double p = Double.parseDouble(value1);
                String value2 = Caffeine.getText().toString();
                Integer c = Integer.parseInt(value2);

                String itemID = value;

                MenuItem_Model item = new MenuItem_Model(value, p, c);

                dbReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dbReference.child().setValue(item);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Name.setText("");
                Price.setText("");
                Caffeine.setText("");
            }
        });
    }


/*
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

    private void openMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void request(String n, Integer p, Integer c) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/seller/update_menu";

        Item drink = new Item(n, p, c);
        String myItem  = new Gson().toJson(drink);

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
                System.out.println("adding menu item success!");
            } else if (response.code() != 200) {
                //failure
                System.out.println("adding menu item failure:(");
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

*/