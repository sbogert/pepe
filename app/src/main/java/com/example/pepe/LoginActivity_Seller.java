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


/** class for seller login*/
public class LoginActivity_Seller extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seller);

        Email = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            String email = Email.getText().toString().trim();
            String pass = Password.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Email.setError("Email is Required.");
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                Password.setError("Password is Required.");
                return;
            }

            fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    Toast.makeText(LoginActivity_Seller.this, "Welcome "+ user.getDisplayName() +
                            "!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                } else {
                    Toast.makeText(this, "Incorrect email or password",
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
            fAuth.signOut();
        }
    }
}