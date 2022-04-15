package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pepe.map.MapsActivity;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class menu extends AppCompatActivity {

    private TextView menuDisplay;
    private Button Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        menuDisplay = (TextView) findViewById(R.id.displaymenu);
        Back = (Button) findViewById(R.id.goBack);

        menuDisplay.setText(getMenu());



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBack();
            }
        });

    }

    private void getUserDB(String userID, String sellerID) {
        String url = "http://10.0.2.2:3001/drinker/get_menu";

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userid", userID)
                .add("seller_id",sellerID )
                .build();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        String fullUrl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code());
            System.out.println(Objects.requireNonNull(response.body()).string());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String getMenu(){
        //request to get menu from database
        return null;
    }

    private void openBack() {
        Intent intent = new Intent(this, UpdateMenuActivity.class);
        startActivity(intent);
    }
}



