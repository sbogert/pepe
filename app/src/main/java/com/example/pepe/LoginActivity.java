package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.map.MapsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/** class for users logging into the app */
public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button login;
    private Button signup;
    private FirebaseAuth fAuth;

    //private Intent i;
    //private final OkHttpClient client = new OkHttpClient();
    //private int count = 4;
    //public String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);
        fAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
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

                fAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(LoginActivity.this, MapsActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}




        /*
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean noUser;
                if  (count > 0) {
                    noUser = getUserDB(Name.getText().toString(), Password.getText().toString());
                    // if a user logs in successfully
                    if (!noUser) {
                        startActivity(i);
                    }
                    // if the information they entered doesn't match any user, form clears and they can retry
                    // they have 4 attempts before getting sent to the signup page
                    else {
                        count--;
                        Name.setText("");
                        Password.setText("");
                    }
                }
                if (count == 0) {
                    openSignUp();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();
            }
        });
    }


    private void openSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


    private boolean getUserDB (String givenName, String givenPass) {
        i = new Intent(this, MapsActivity.class);
        String url = "http://10.0.2.2:3001/drinker/login";
        boolean noUser = true;

        // given login information is sent to check
        RequestBody formBody = new FormBody.Builder()
                .add("username", Name.getText().toString())
                .add("password", Password.getText().toString())
                .build();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        String fullUrl = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            userID = Objects.requireNonNull(response.body()).string();
            if (response.code() == 200) {
                noUser = false;
            } else if (response.code() != 200) {
                noUser = true;
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return noUser;
    }
    */
