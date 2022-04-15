package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pepe.map.MapsActivity;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PastOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);
    }

    /** connect to database and verify user */
    private boolean getUserDB (String userID) {
        String url = "http://10.0.2.2:3001/drinker/get_history_order";
        String id;
        boolean noUser = true;

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
            id = Objects.requireNonNull(response.body()).string();
            if (response.code() == 200) {
                noUser = false;
                i.putExtra("USERID", id);
            } else if (response.code() != 200) {
                noUser = true;
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return noUser;
    }
}