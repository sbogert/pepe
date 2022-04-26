package com.example.pepe;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pepe.model.SellerInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
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

/** class for seller signup */
public class SignupActivity_Sellers extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private EditText StoreName;
    private EditText StoreLocation;
    FirebaseAuth fAuth;
    private static GeoPoint geoPoint;
    private String prettyAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_sellers);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Email = (EditText) findViewById(R.id.etEmail);
        StoreName = (EditText) findViewById(R.id.etStoreName);
        Password = (EditText) findViewById(R.id.etPassword);
        StoreLocation = (EditText) findViewById(R.id.etLocation);
        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        // sign out any previous users so that a new user can register
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            fAuth.signOut();
        }

        // send to login screen
        login.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity_Seller.class)));

        // signup verification on button click
        signup.setOnClickListener(view -> {
            String email = Email.getText().toString().trim();
            String name = StoreName.getText().toString().trim();
            String pass = Password.getText().toString().trim();
            String loc = StoreLocation.getText().toString().trim();

            // get the latitude and longitude of the address with geocode api
            String url;
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
                    StoreLocation.setText(prettyAddress);
                    JsonObject geometryJsonObject = resultJsonObject.get("geometry").getAsJsonObject();
                    JsonObject locationJsonObject = geometryJsonObject.get("location").getAsJsonObject();
                    dumpLocationJsonObject(locationJsonObject);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            if (TextUtils.isEmpty(email)) {
                Email.setError("Email is Required.");
                return; }
            if (TextUtils.isEmpty(name)) {
                StoreName.setError("Store Name is Required.");
                return; }
            if (TextUtils.isEmpty(pass)) {
                Password.setError("Password is Required.");
                return;
            }
            if (pass.length() < 6) {
                Password.setError("Password must be more than 6 characters");
                return;
            }
            if (TextUtils.isEmpty(loc)) {
                StoreLocation.setError("Store Location is Required.");
                return;
            }

            // create the authenticated user in firebase
            fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String email1 = Email.getText().toString();
                    String name1 = StoreName.getText().toString();
                    String password = Password.getText().toString();

                    // set seller's display name
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates =
                            new UserProfileChangeRequest.Builder().setDisplayName(name1).build();
                    assert user != null;
                    user.updateProfile(profileUpdates);

                    // add seller to database
                    SellerInfo newSeller = new SellerInfo(email1, name1, password, geoPoint, prettyAddress);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String uniqueId = user.getUid();
                    CollectionReference sellersReference = db.collection("sellers");
                    sellersReference.document(uniqueId).set(newSeller);

                    // add menu collection
                    DocumentReference sellerDocRef = sellersReference.document(uniqueId);
                    sellerDocRef.collection("menu");

                    Toast.makeText(SignupActivity_Sellers.this, "Welcome " + name1 + "!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SellerMainActivity.class));
                } else {
                    Toast.makeText(SignupActivity_Sellers.this, "Error!!!", Toast.LENGTH_SHORT).show();
                }
            });
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
    private static void dumpLocationJsonObject(final JsonObject location) {
        final double latitude = location.getAsJsonPrimitive("lat").getAsDouble();
        final double longitude = location.getAsJsonPrimitive("lng").getAsDouble();
        geoPoint = new GeoPoint(latitude, longitude);
    }
}