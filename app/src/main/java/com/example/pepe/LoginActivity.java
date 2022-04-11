package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pepe.data.model.UserProfiles;
import com.example.pepe.map.MapsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button login;
    private Button signup;
    private static final String url = "http://10.0.2.2:3001/310project/drinkers";
    private static final String user = "root";
    private static final String pass = "root";
    private UserProfiles userProfiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        TextView signupInfo =  findViewById(R.id.haveaccount);
        Info = findViewById(R.id.incorrect);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);

        Info.setText("");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    validate(Name.getText().toString(), Password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    System.out.println(Name.getText().toString() + " " + Password.getText().toString());
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

    //Enter name for signup page
    private void openSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void httpGet(){
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .build();

        System.out.println("Connected");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("onResponse");

                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }

                    System.out.println(responseBody.string());

                    Gson gson =
                            new GsonBuilder().registerTypeAdapter(UserProfiles.class,
                                    new MyDeserializer()).create();
                    userProfiles = gson.fromJson(Objects.requireNonNull(response.body()).string(),
                            UserProfiles.class);

                }

                finally {
                    response.close();
                }

            }
        });

//
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            public void onResponse(Call call, Response response)
//                    throws IOException {
//                // ...
//            }
//
//            public void onFailure(Call call, IOException e) {
//                fail();
//            }
//        });
    }



    private void validate(String username, String password) throws Exception {
        try {
//            //establish connection
//            //Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection(url, user, pass);
//            Statement stmt = con.createStatement();
//
//            //database name
//            ResultSet rs = stmt.executeQuery("select * from users");
//            while (rs.next()) {
//                String u = rs.getString("username");
//                String p = rs.getString("password");
//                //user exists
//                if (u.equals(username) && p.equals(password)) {
//                    //get user id
//                    rs = stmt.executeQuery("select id from users where username = " + username);
//                    Integer userID = rs.getInt("id");
//                    Intent i = new Intent(this, MapsActivity.class);
//                    i.putExtra("USERID",userID);
//                    startActivity(i);
//                }
//                //user does not exist
//                else {
//                    Info.setText("Username or Password Incorrect");
//                }
//            }
            httpGet();
            // idk do something like this to get the user's info from arraylist that is returned
            userProfiles.findUser(password);
            Integer userID = .getInt("id");
                    Intent i = new Intent(this, MapsActivity.class);
                    i.putExtra("USERID",userID);
            startActivity(i);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
    class MyDeserializer implements JsonDeserializer<UserProfiles> {
        @Override
        public UserProfiles deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            JsonElement content = je.getAsJsonObject();

            return new Gson().fromJson(content, UserProfiles.class);
        }
    }
}

    /*
     private void t() throws Exception {
        URL url = new URL("http://localhost:3001");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");



        HttpGet request = new HttpGet("https://");

        // add request headers
        request.addHeader("custom-key", "mkyong");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        }
    }
     */
