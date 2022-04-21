package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.pepe.model.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


/** class for drinker signup */
public class SignupActivity extends AppCompatActivity {
    private EditText Email;
    private EditText Name;
    private EditText Password;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Email = findViewById(R.id.etEmail);
        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.loginButton);
        Button signup = findViewById(R.id.signupButton);

        // sign out any previous users so that a new user can register
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            fAuth.signOut();
        }

        // send to login screen
        login.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        // signup verification on button click
        signup.setOnClickListener(view -> {
            String email = Email.getText().toString().trim();
            String name = Name.getText().toString().trim();
            String pass = Password.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Email.setError("Email is Required.");
                return;
            }
            if(TextUtils.isEmpty(name)){
                Name.setError("Name is Required.");
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

            // create the authenticated user in firebase
            fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    String email1 = Email.getText().toString();
                    String name1 = Name.getText().toString();
                    String password = Password.getText().toString();

                    // set drinker's display name
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates =
                            new UserProfileChangeRequest.Builder().setDisplayName(name1).build();
                    assert user != null;
                    user.updateProfile(profileUpdates);

                    // add drinker to database
                    UserInfo newUser = new UserInfo(email1, name1, password);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String uniqueId = user.getUid();
                    CollectionReference collectionReference = db.collection("drinkers");
                    collectionReference.document(uniqueId).set(newUser);

                    // add orderHistory collection to drinker document
                    DocumentReference sellerDocRef = collectionReference.document(uniqueId);
                    sellerDocRef.collection("orderHistory");

                    Toast.makeText(SignupActivity.this, "Welcome " + name1 + "!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                }else{
                    Toast.makeText(SignupActivity.this, "Error:/ please retry", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                }
            });
        });
    }
}
