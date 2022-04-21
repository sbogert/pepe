package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Button pass = (Button) findViewById(R.id.button3);
        Button name = (Button) findViewById(R.id.button);
        Button email = (Button) findViewById(R.id.button2);
        Button done = (Button) findViewById(R.id.button4);
        EditText entPass = (EditText) findViewById(R.id.editTextTextPassword);
        EditText entName = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText entEmail = (EditText) findViewById(R.id.editTextTextPersonName2);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uniqueId = user.getUid();

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NEWPASSWORD = entPass.getText().toString().trim();
                if(TextUtils.isEmpty(NEWPASSWORD)){
                    entPass.setError("Password is Required.");
                    return;
                }

                //change db
                db.collection("drinkers").document(uniqueId)
                        .update(
                                "password", NEWPASSWORD
                        );

                //change actual user
                user.updatePassword(NEWPASSWORD)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditProfile.this, "User Password updated.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NEWNAME = entName.getText().toString().trim();
                if(TextUtils.isEmpty(NEWNAME)){
                    entName.setError("Name is Required.");
                    return;
                }

                //change db
                db.collection("drinkers").document(uniqueId)
                        .update(
                                "name", NEWNAME
                        );
                Toast.makeText(EditProfile.this, "User Name updated.", Toast.LENGTH_SHORT).show();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NEWEMAIL = entEmail.getText().toString().trim();
                if(TextUtils.isEmpty(NEWEMAIL)){
                    entEmail.setError("Email is Required.");
                    return;
                }

                //change db
                db.collection("drinkers").document(uniqueId)
                        .update(
                                "email", NEWEMAIL
                        );

                //change actual user
                user.updateEmail(NEWEMAIL)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditProfile.this, "User Email updated.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDone();
            }
        });
    }

    private void openDone() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}