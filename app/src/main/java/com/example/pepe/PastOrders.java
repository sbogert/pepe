package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PastOrders extends AppCompatActivity {
    private TextView Display;
    private Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Display = (TextView) findViewById(R.id.display);
        Back = (Button) findViewById(R.id.goBack);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBack();
            }
        });

        //display past orders on screen
        request();
    }

    /**
     * connect to database and verify user
     */
    private void request() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3001/drinker/get_history_order";
        String userID = ((MyApplication) this.getApplication()).getUser();

        // given login information is sent to check
        RequestBody formBody = new FormBody.Builder()
                .add("userid", userID)
                .build();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        String fullUrl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                //print the past orders
                //Display.setText();
            } else if (response.code() != 200) {
                //print nothing
                Display.setText("");
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void openBack() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
