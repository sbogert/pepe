package com.example.pepe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** TODO: set up menu items display
 * remember to have a check for if it is empty
 * format for scrolling
 * */
public class ViewStore extends AppCompatActivity {

    private TextView StoreName;
    private TextView StoreAddress;
    private Spinner Menu;
    private Button Back;
    private CollectionReference collectionReference;
    private ListView listView;
    private ArrayList<String> foodNames = new ArrayList<>();
    private ArrayList<String> hello = new ArrayList<>();
    private Map<String, String> map = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StoreName = (TextView) findViewById(R.id.storeName);
        StoreAddress = (TextView) findViewById(R.id.storeAddress);
        Menu = (Spinner) findViewById(R.id.menu);
        Back = (Button) findViewById(R.id.back_to_map);
        listView = (ListView) findViewById(R.id.menuView);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //how do you put info in the second elemnt???
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,foodNames);
        listView.setAdapter(arrayAdapter);
        

        // fill in textview items
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
                            String storeAddress = Objects.requireNonNull(document.getString("address")).split(",")[0];
                            StoreAddress.setText(storeAddress);
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
