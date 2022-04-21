package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewStore extends AppCompatActivity {

    private TextView StoreName;
    private TextView StoreAddress;
    private Spinner Menu;
    private Button Back;
    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        StoreName = (TextView) findViewById(R.id.storeName);
        StoreAddress = (TextView) findViewById(R.id.storeAddress);
        Menu = (Spinner) findViewById(R.id.menu);
        Back = (Button) findViewById(R.id.backtomap);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // get store object
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String storeID = extras.getString("storeID");
            DocumentReference docRef = db.collection("sellers").document(storeID);
            collectionReference = docRef.collection("menu");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            StoreName.setText(document.getString("storeName"));
                            StoreAddress.setText(document.getString("address"));
                        }
                    } else {
                        System.out.println("get failed with " + task.getException());
                    }
                }
            });
        }

        // go back to the map page
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBack();
            }
        });
    }

    // function to go back to map page
    private void openBack() {
        startActivity(new Intent(this, MapsActivity.class));
    }
}
