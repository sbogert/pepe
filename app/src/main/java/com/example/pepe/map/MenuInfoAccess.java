package com.example.pepe.map;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pepe.model.Item;
import com.example.pepe.model.SellerInfo;
import com.example.pepe.model.StoreLocation;
import com.example.pepe.model.StoreLocationArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

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

    private static OkHttpClient client = new OkHttpClient();

    public static StoreLocationArray getMarkers() throws ClassNotFoundException {
        String url = "http://10.0.2.2:3001/drinker/get_near_by_sellers";
        StoreLocationArray storeLocArray = new StoreLocationArray();

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

    public static List<Item> MenuInfo(Integer sellerID, Integer drinkerID) {
        String url = "http://10.0.2.2:3001/drinker/get_menu";
        String email = "";
        String storeName = "";
        String password = "";
//        SellerInfo sellerMenu; = new SellerInfo(email, storeName, password);
        List<Item> itemList = null;

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
            itemList =
                    gson.fromJson(String.valueOf(jsonArr),
                            new TypeToken<List<Item>>() {}.getType());

            // add locations to the array
            for (Item i :itemList) {
                System.out.println(i.getName());
            }
        } catch (IOException | NullPointerException | JSONException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}