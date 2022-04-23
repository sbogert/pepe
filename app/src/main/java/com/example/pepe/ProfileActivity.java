package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


/** class for drinker to view and update their profile */
public class ProfileActivity extends AppCompatActivity {

    private TextView Name;
    private TextView Email;
    private Button Edit;
    FirebaseAuth fAuth;
    private CollectionReference collectionReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Name = (TextView) findViewById(R.id.displayName);
        Email = (TextView) findViewById(R.id.displayEmail);
        Edit = (Button) findViewById(R.id.Edit);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uniqueId = user.getUid();

        //get info of current user
        fAuth = FirebaseAuth.getInstance();


        DocumentReference docRef = db.collection("drinkers").document(uniqueId);
        System.out.println(docRef);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Name.setText(document.getString("name"));
                        Email.setText(document.getString("email"));
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });

        // fill in textview items
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String storeID = extras.getString("storeID");
            collectionReference = docRef.collection("menu");

        }
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEdit();
            }
        });

    }
    private void openEdit() {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SplashActivity.class));
    }
    public void toProfile(MenuItem item) {
    }
    public void toMap(MenuItem item) {
        startActivity(new Intent(this, MapsActivity.class));
    }
}