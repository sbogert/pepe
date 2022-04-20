package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.model.SellerInfo;
import com.example.pepe.model.UserInfo;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseGeoPoint;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;


public class SignupActivity_Sellers extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private EditText StoreName;
    private EditText StoreLocation;
    private String prettyLocation;
    private static LatLng storeLatLng;
    private Button login;
    private Button signup;
    FirebaseAuth fAuth;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_sellers);

        Email = (EditText) findViewById(R.id.etEmail);
        StoreName = (EditText) findViewById(R.id.etStoreName);
        Password = (EditText) findViewById(R.id.etPassword);
        StoreLocation = (EditText) findViewById(R.id.etLocation);
        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);

        fAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // sign out any previous users so that a new user can register
        if(fAuth.getCurrentUser() != null){
            fAuth.signOut();
        }

        // send to login screen
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity_Seller.class));
            }
        });

        /*in some textview container or somethign to get the current location, or autofill the seller's
        entered text

        LocationGpsListener mGPS = new LocationGpsListener(this);
        currentLocation = mGPS.getLocation();
*/

        // signup verification on button click
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String name = StoreName.getText().toString().trim();
                String pass = Password.getText().toString().trim();
                String loc = StoreLocation.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    StoreName.setError("Store Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Password.setError("Password is Required.");
                    return;
                }
                if(pass.length() < 6){
                    Password.setError("Password must be more than 6 characters");
                    return;
                }
                if(TextUtils.isEmpty(loc)){
                    StoreLocation.setError("Store Location is Required.");
                    return;
                }

                // getting the correct location
                URL url;
                try {
                    String urlParam = URLEncoder.encode(loc, StandardCharsets.UTF_8.toString()).replace("+", "%20");
                    url = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input" +
                            "=" + urlParam + "&inputtype=textquery&fields=formatted_address%2Cgeometry&key=AIzaSyDvuIejoxu5HTZdwkJvhtWyLXFWmdN_ZxQ");
                    System.out.println(url.toString());
                } catch (UnsupportedEncodingException | MalformedURLException ex) {
                    throw new RuntimeException(ex.getCause());
                }

//                OkHttpClient client = new OkHttpClient().newBuilder()
//                        .build();
//                Request request = new Request.Builder()
//                        .url("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + urlParam + "&inputtype=textquery&fields=formatted_address%2Cgeometry&key=AIzaSyDvuIejoxu5HTZdwkJvhtWyLXFWmdN_ZxQ")
//                        .method("GET", null)
//                        .build();
                try (final Reader reader = new BufferedReader(new InputStreamReader(url.openStream()))){
//                    Response response = client.newCall(request).execute();
                    final JsonParser jsonParser = new JsonParser();
                    // Extract the `results` array
                    final JsonArray array =
                            jsonParser.parse(reader)
                            .getAsJsonObject()
                            .get("candidates")
                            .getAsJsonArray();
//                    JSONArray array = new JSONArray(response);
                    for(int i=0; i<array.size(); i++) {
                        JsonObject resultJsonObject = array.get(i).getAsJsonObject();
                        prettyLocation =
                                resultJsonObject.getAsJsonPrimitive("formatted_address").getAsString();
                        System.out.println(resultJsonObject.getAsJsonPrimitive("formatted_address").getAsString());
                        StoreLocation.setText(resultJsonObject.getAsJsonPrimitive("formatted_address").getAsString());
//                        JSONObject object = array.getJSONObject(i);
//                        String fancyLocation = object.getString("formatted_address");
                        JsonObject geometryJsonObject = resultJsonObject.get("geometry").getAsJsonObject();
                        // And dumping the location
                        JsonObject locationJsonObject = geometryJsonObject.get("location").getAsJsonObject();
                        dumpLocationJsonObject("Location", locationJsonObject);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String email = Email.getText().toString();
                            String name = StoreName.getText().toString();
                            String password = Password.getText().toString();
                            SellerInfo newSeller = new SellerInfo(email, name, password, storeLatLng);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            CollectionReference menuReference = db.collection("sellers");
                            menuReference.document(email).set(newSeller);
                            Toast.makeText(SignupActivity_Sellers.this, "Welcome " + name + "!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SellerMain.class));
                        } else {
                            Toast.makeText(SignupActivity_Sellers.this, "Error!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    // get location as LatLng
    private static void dumpLocationJsonObject(final String name, final JsonObject location) {
        final double latitude = location.getAsJsonPrimitive("lat").getAsDouble();
        final double longitude = location.getAsJsonPrimitive("lng").getAsDouble();
        storeLatLng = new LatLng(latitude, longitude);
        System.out.println("\t" + name + ": (" + latitude + "; " + longitude + ")");
    }

//    private void getLocation() {
//        int requestLoc = 1;
//        if(ActivityCompat.checkSelfPermission(UsersActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UsersActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(UsersActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        }
//        else {
//            // getting last know user's location
//            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            // checking if the location is null
//            if(location != null){
//                // if it isn't, save it to Back4App Dashboard
//                ParseGeoPoint currentUserLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
//
//                ParseUser currentUser = ParseUser.getCurrentUser();
//
//                if (currentUser != null) {
//                    currentUser.put("Location", currentUserLocation);
//                    currentUser.saveInBackground();
//                } else {
//                    // do something like coming back to the login activity
//                }
//            }
//            else {
//                // if it is null, do something like displaying error and coming back to the menu activity
//            }
//        }
//
}