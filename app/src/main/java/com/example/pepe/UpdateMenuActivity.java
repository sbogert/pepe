package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateMenuActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Price;
    private EditText Caffine;
    private Button Add;
    private ImageView Map;
    private ImageView History;
    private ImageView Menu;



    private static final String url = "jdbc:mysql://localhost:3001/seller/update_menu\n";
    private static final String user = "root";
    private static final String pass = "root";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);

        Name = (EditText) findViewById(R.id.etName);
        Price = (EditText) findViewById(R.id.etPrice);
        Caffine = (EditText) findViewById(R.id.etCaffine);
        Add = (Button) findViewById(R.id.addButton);
        Map = (ImageView) findViewById(R.id.mapButton);
        History = (ImageView) findViewById(R.id.historyButton);
        Menu = (ImageView) findViewById(R.id.menuButton);

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value= Price.getText().toString();
                String value1= Caffine.getText().toString();
                int finalValue=Integer.parseInt(value);
                int finalValue1=Integer.parseInt(value1);

                try {
                    validate(Name.getText().toString(), finalValue, finalValue1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //Enter name
    private void openHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void validate(String name, Integer price, Integer caffine) throws Exception {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            Statement stmt = con.createStatement();
                // the mysql insert statement
                String query = " insert into users (seller_id, name, price, caffine)"
                        + " values (?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                Integer userID = loguser.getUserId();
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, userID);
                preparedStmt.setString(2, name);
                preparedStmt.setInt(3, price);
                preparedStmt.setInt(4, caffine);


                // execute the preparedstatement
                preparedStmt.execute();

                con.close();
        } catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }


}