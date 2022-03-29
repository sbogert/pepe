package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginnActivity_Seller extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private TextView SignupInfo;
    private Button login;
    private Button signup;
    private static final String url = "jdbc:mysql://localhost:3001/seller/login";
    private static final String user = "root";
    private static final String pass = "root";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        SignupInfo = (TextView) findViewById(R.id.haveaccount);
        Info = (TextView) findViewById(R.id.incorrect);
        login = (Button) findViewById(R.id.signupButton);
        signup = (Button) findViewById(R.id.loginButton);

        Info.setText("");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    validate(Name.getText().toString(), Password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
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

    private void validate(String username, String password) throws Exception {
        try {
            //establish connection
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();

            //database name
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                String u = rs.getString("username");
                String p = rs.getString("password");
                //user exists
                if (u == username && p == password) {
                    //get user id
                    rs = stmt.executeQuery("select id from users where username = " + username);
                    Integer userID = rs.getInt("id");
                    Intent i = new Intent(this, MapsActivity.class);
                    i.putExtra("USERID",userID);
                    startActivity(i);
                }
                //user does not exist
                else {
                    Info.setText("Username or Password Incorrect");
                }
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
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