package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/** class for drinker to view and update their profile */
public class ProfileActivity extends AppCompatActivity {

    private TextView Name;
    private TextView Email;
    private Button Edit;
    FirebaseAuth fAuth;
    private CollectionReference collectionReference;
    private ListView lv;


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

        //things needed to fill in past orders
        lv = (ListView) findViewById(R.id.menuList);

        //print name and email
        DocumentReference docRef = db.collection("drinkers").document(uniqueId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //fill name and email
                        Name.setText(document.getString("name"));
                        Email.setText(document.getString("email"));

                        //fill past orders
                        List<String> menuItems = (List<String>) document.get("pastOrders");
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                ProfileActivity.this,
                                android.R.layout.simple_list_item_1,
                                menuItems );

                        lv.setAdapter(arrayAdapter);
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            }
        });


        //open edit page
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