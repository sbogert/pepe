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

import com.example.pepe.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseGeoPoint;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;


public class SignupActivity_Sellers extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button login;
    private Button signup;
    FirebaseAuth fAuth;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_sellers);

        Name = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);
// firebase cant get teh current user bc they dont exist yet
        fAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if(fAuth.getCurrentUser() != null){
            fAuth.signOut();
        }

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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Name.getText().toString().trim();
                String pass = Password.getText().toString().trim();

                if(TextUtils.isEmpty(user)){
                    Name.setError("Username is Required.");
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

                fAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String email = Name.getText().toString();
                            String password = Password.getText().toString();
                            UserInfo newUser = new UserInfo(email, password);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            CollectionReference menuReference = db.collection("sellers");
                            menuReference.document(email).set(newUser);
                            Toast.makeText(SignupActivity_Sellers.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SellerMain.class));
                        }else{
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