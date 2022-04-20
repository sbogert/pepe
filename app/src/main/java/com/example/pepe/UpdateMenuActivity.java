package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pepe.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseGeoPoint;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;


public class UpdateMenuActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Price;
    private EditText Caffeine;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        Name = (EditText) findViewById(R.id.etEmail);
        Price = (EditText) findViewById(R.id.etPrice);
        Caffeine = (EditText) findViewById(R.id.etCaffine);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.addButton);
        Button back = (Button) findViewById(R.id.back);

        FirebaseUser seller = FirebaseAuth.getInstance().getCurrentUser();
        String uniqueId = seller.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*seller's menu*/
        CollectionReference menuReference =
                db.collection("sellers").document(uniqueId).collection("menu");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String value1 = Price.getText().toString();
                Double price = Double.parseDouble(value1);
                String value2 = Caffeine.getText().toString();
                Integer caffeine = Integer.parseInt(value2);
                Item newItem = new Item(name, price, caffeine);
                menuReference.add(newItem);
                Toast.makeText(UpdateMenuActivity.this, name + " added menu", Toast.LENGTH_LONG).show();

                Name.setText("");
                Price.setText("");
                Caffeine.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                backMain();
                startActivity(i);
            }
        });
    }

    private void backMain() {
        i = new Intent(this, SellerMain.class);
    }
}
