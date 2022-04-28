package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderConfirm extends AppCompatActivity implements OnMapReadyCallback {

    private static LatLng drinkerLocation;
    private String storeID;
    private String storeName;
    private TextView StoreName;
    private TextView ItemName;
    private TextView ItemPrice;
    private TextView ItemCaff;
    private EditText enterDeliveryAddress;
    private Button updateAddress;
    private LatLng storeLocation;
    private boolean changeLoc = false;
    private FirebaseUser fUser;
    private Location userLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        StoreName = (TextView) findViewById(R.id.storeNameReview);
        ItemName = (TextView) findViewById(R.id.itemName);
        ItemPrice= (TextView) findViewById(R.id.itemPrice);
        ItemCaff = (TextView) findViewById(R.id.itemCaff);
        enterDeliveryAddress = (EditText) findViewById(R.id.enterDeliveryAddr);
        updateAddress = (Button) findViewById(R.id.updateAddress);

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        // fill in textviews with information
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeID = extras.getString("storeID");
            storeName = extras.getString("storeName");
            StoreName.setText(storeName);
            storeLocation = (LatLng) extras.get("storeLocation");
            ItemName.setText(extras.getString("itemName"));
            ItemPrice.setText(extras.getString("itemPrice"));
            ItemCaff.setText(extras.getString("itemCaff"));
            userLocation = (Location) extras.get("userLocation");
            setCurrentAddress(userLocation);
            System.out.println("user location: " + userLocation.getLongitude() + ", " + userLocation.getLatitude() );
        }
        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
//        // connect to the Google Map
//        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.mapView, mapFragment)
//                .commit();

        // update the delivery address from current location to specified location
        updateAddress.setOnClickListener(view -> {
            changeLoc = true;
            String loc = "";
            String url;
            String prettyAddress;
            try {
                String urlParam = URLEncoder.encode(loc, StandardCharsets.UTF_8.toString());
                url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + urlParam +
                        ",+Los+Angeles,+CA&key=AIzaSyDvuIejoxu5HTZdwkJvhtWyLXFWmdN_ZxQ";
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex.getCause());
            }
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            try (Response response = client.newCall(request).execute()) {

                // Extract the results array
                JsonArray array =
                        JsonParser.parseString(Objects.requireNonNull(response.body()).string())
                                .getAsJsonObject()
                                .get("results")
                                .getAsJsonArray();

                // parse the results array
                for (int i = 0; i < array.size(); i++) {
                    JsonObject resultJsonObject = array.get(i).getAsJsonObject();
                    prettyAddress = resultJsonObject.getAsJsonPrimitive("formatted_address").getAsString();
                    enterDeliveryAddress.setText(prettyAddress);
                    JsonObject geometryJsonObject = resultJsonObject.get("geometry").getAsJsonObject();
                    JsonObject locationJsonObject = geometryJsonObject.get("location").getAsJsonObject();
                    dumpLocationJsonObject(locationJsonObject);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    // get location as LatLng
    private static void dumpLocationJsonObject(final JsonObject location) {
        final double latitude = location.getAsJsonPrimitive("lat").getAsDouble();
        final double longitude = location.getAsJsonPrimitive("lng").getAsDouble();
        drinkerLocation = new LatLng(latitude, longitude);
    }

    // add the markers for the route
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F));

        // add marker for store
        googleMap.addMarker(new MarkerOptions()
                .position(storeLocation)
                .title(storeName));

        //        add the user's marker
        MarkerOptions drinkerMarker = new MarkerOptions()
                .position(latLng)
                .title(fUser.getDisplayName());
        googleMap.addMarker(drinkerMarker);
    }

    public void setCurrentAddress(Location loc) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + loc.getLatitude() + ","
                + loc.getLongitude() + "&key=AIzaSyDvuIejoxu5HTZdwkJvhtWyLXFWmdN_ZxQ";;
        String prettyAddress;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        try (Response response = client.newCall(request).execute()) {
            // Extract the results array
            JsonArray array =
                    JsonParser.parseString(Objects.requireNonNull(response.body()).string())
                            .getAsJsonObject()
                            .get("results")
                            .getAsJsonArray();
            // parse the results array
            for (int i = 0; i < array.size(); i++) {
                JsonObject resultJsonObject = array.get(i).getAsJsonObject();
                prettyAddress = resultJsonObject.getAsJsonPrimitive("formatted_address").getAsString();
                enterDeliveryAddress.setText(prettyAddress);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}