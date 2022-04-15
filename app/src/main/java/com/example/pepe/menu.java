package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class menu extends AppCompatActivity {

    private TextView menuDisplay;
    private Button Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuDisplay = (TextView) findViewById(R.id.displaymenu);
        Back = (Button) findViewById(R.id.goBack);

        menuDisplay.setText(getMenu());



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBack();
            }
        });

    }

    public String getMenu(){
        //request to get menu from database
        return null;
    }

    private void openBack() {
        Intent intent = new Intent(this, UpdateMenuActivity.class);
        startActivity(intent);
    }
}



