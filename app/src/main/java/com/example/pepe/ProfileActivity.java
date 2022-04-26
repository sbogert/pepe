package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
        Button edit = (Button) findViewById(R.id.Edit);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String uniqueId = user.getUid();

        // things needed to fill in past orders
        lv = (ListView) findViewById(R.id.menuList);

        //print name and email
        DocumentReference docRef = db.collection("drinkers").document(uniqueId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    //fill name and email
                    Name.setText(document.getString("name"));
                    Email.setText(document.getString("email"));

                    //fill past orders
                    // need too add check about if the list is empty or not
                    List<String> menuItems = (List<String>) document.get("pastOrders");
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                            ProfileActivity.this,
                            android.R.layout.simple_list_item_1,
                            menuItems);

//                    lv.setAdapter(arrayAdapter);
                }
            } else {
                System.out.println("get failed with " + task.getException());
            }
        });


        //open edit page
        edit.setOnClickListener(view -> openEdit());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
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