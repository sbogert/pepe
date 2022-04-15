package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pepe.map.MapsActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignupActivity_Sellers extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private EditText Location;
    private TextView Info;
    private TextView SignupInfo;
    private Button login;
    private Button signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_sellers);

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Location = (EditText) findViewById(R.id.location);
        SignupInfo = (TextView) findViewById(R.id.haveaccount);
        Info = (TextView) findViewById(R.id.incorrect);
        login = (Button) findViewById(R.id.signupButton);
        signup = (Button) findViewById(R.id.loginButton);

        Info.setText("");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //validate(Name.getText().toString(), Password.getText().toString(), Location.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
    }

    //Enter name for signup page
    private void openLogin() {
        Intent intent = new Intent(this, LoginActivity_Seller.class);
        startActivity(intent);
    }

    /*


    private void validate(String username, String password, String location) throws Exception {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from sellers where username = " + username);
            if (rs.next()) {
                Info.setText(R.string.username_exists);
            } else {
                // the mysql insert statement
                String query = " insert into sellers (username, password, location)"
                        + " values (?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, username);
                preparedStmt.setString(2, password);
                preparedStmt.setString(3, location);

                // execute the preparedstatement
                preparedStmt.execute();

                con.close();

                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }
        } catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }

     */

}