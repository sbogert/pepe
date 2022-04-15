package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pepe.map.MapsActivity;

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
    private Intent i;
    private OkHttpClient client = new OkHttpClient();
    private int count = 4;
    public String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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


    /** send user to the signup page*/
    private void openSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


    /** connect to database and verify user */
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
                i.putExtra("USERID", userID);
            } else if (response.code() != 200) {
                noUser = true;
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return noUser;
    }
}