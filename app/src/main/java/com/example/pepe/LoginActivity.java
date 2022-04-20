package com.example.pepe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.atomic.AtomicBoolean;


/** class for users logging into the app */
public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            String email = Email.getText().toString().trim();
            String pass = Password.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Email.setError("Email is Required.");
                return;
            }
            if(TextUtils.isEmpty(pass)){
                Password.setError("Password is Required.");
                return;
            }

            fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Welcome "+ email +
                                    "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Incorrect email or password",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        signup.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SignupActivity.class)));
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();

        if (currentUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("drinkers").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    AtomicBoolean isDrinker = new AtomicBoolean(false);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        db.collection("drinkers").document(document.getId())
                                .addSnapshotListener((value, error) -> {
                                    if (document.getId().equals(currentUser.getUid())) {
                                        isDrinker.set(true);
                                        Intent i = new Intent(this, MapsActivity.class);
                                        startActivity(i);
                                        this.finish();
                                    }
                                });
                    }
                    if (!isDrinker.get()) {
                        fAuth.signOut();
                    }
                }
            });
        }
    }
}