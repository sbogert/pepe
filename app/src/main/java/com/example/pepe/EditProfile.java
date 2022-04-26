package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;


/** EditProfile class is where the user actually changes db and authentication information about their
 * account
 * */
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
        assert user != null;
        String uniqueId = user.getUid();

        pass.setOnClickListener(view -> {
            String NEW_PASSWORD = entPass.getText().toString().trim();
            if(TextUtils.isEmpty(NEW_PASSWORD)){
                entPass.setError("Password is Required.");
                return;
            }

            //change db
            db.collection("drinkers").document(uniqueId)
                    .update(
                            "password", NEW_PASSWORD
                    );
            //change actual user
            user.updatePassword(NEW_PASSWORD)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfile.this, "User Password updated.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        name.setOnClickListener(view -> {
            String NEW_NAME = entName.getText().toString().trim();
            if(TextUtils.isEmpty(NEW_NAME)){
                entName.setError("Name is Required.");
                return;
            }
            System.out.println(NEW_NAME);
            //change db
            db.collection("drinkers").document(uniqueId)
                    .update(   "name", NEW_NAME);
            // change authentication display name
            UserProfileChangeRequest nameUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(NEW_NAME)
                    .build();
            user.updateProfile(nameUpdate)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfile.this, "User Name updated.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        email.setOnClickListener(view -> {
            String NEW_EMAIL = entEmail.getText().toString().trim();
            if(TextUtils.isEmpty(NEW_EMAIL)){
                entEmail.setError("Email is Required.");
                return;
            }
            //change db
            db.collection("drinkers").document(uniqueId)
                    .update(
                            "email", NEW_EMAIL
                    );
            //change actual user
            user.updateEmail(NEW_EMAIL)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfile.this, "User Email updated.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        done.setOnClickListener(view -> openDone());
    }

    private void openDone() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}