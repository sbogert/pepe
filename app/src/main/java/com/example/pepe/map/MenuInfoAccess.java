package com.example.pepe.map;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** get an array of store information from the db */
public class MenuInfoAccess extends AppCompatActivity {

    public static StoreLocationArray getMarkers() throws ClassNotFoundException {
        String url = "http://10.0.2.2:3001/drinker/get_near_by_sellers";
        StoreLocationArray storeLocArray = new StoreLocationArray();

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .build();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        String fullUrl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // parse response
            JSONArray jsonArr = new JSONArray(Objects.requireNonNull(response.body()).string());
            Gson gson = new Gson();
            List<StoreLocation> storeLocList =
                    gson.fromJson(String.valueOf(jsonArr),
                    new TypeToken<List<StoreLocation>>() {}.getType());

            // add locations to the array
            for (StoreLocation s :storeLocList) {
                storeLocArray.addStore(s);
            }
        } catch (IOException | NullPointerException | JSONException e) {
            e.printStackTrace();
        }
        return storeLocArray;
    }
}