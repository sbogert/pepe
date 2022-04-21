package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView Name;
    private TextView Email;
    private Button Edit;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Name = (TextView) findViewById(R.id.displayName);
        Email = (TextView) findViewById(R.id.displayEmail);
        Edit = (Button) findViewById(R.id.Edit);

        //get info of current user
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currUser != null;
        String userUid = currUser.getUid();
        DocumentReference docRef = db.collection("drinkers").document(userUid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserInfo user = documentSnapshot.toObject(UserInfo.class);
                System.out.println(user);
                assert user != null;
                String userName = user.getDisplayName();
                String userEmail = user.getEmail();
                Name.setText(userName);
                Email.setText(userEmail);
            }
        });
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