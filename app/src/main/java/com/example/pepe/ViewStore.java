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

import com.example.pepe.model.MenuItem_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
//    private ArrayList<MenuItem_Model> items = new ArrayList<>();
    private ArrayList<String> menuItems = new ArrayList<>();
    private ArrayList<String> itemCaff = new ArrayList<>();
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

        // fill in textview items and menu
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String storeID = extras.getString("storeID");
            DocumentReference docRef = db.collection("sellers").document(storeID);
            collectionReference = docRef.collection("menu");
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        StoreName.setText(document.getString("storeName"));
                        String storeAddress = Objects.requireNonNull(document.getString("address")).split(",")[0];
                        StoreAddress.setText(storeAddress);

                        // get the menu items
                        docRef.collection("menu")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                                MenuItem_Model menuItem =
//                                                        document.toObject(MenuItem_Model.class);
//                                                items.add(menuItem);
                                                String line = document.getString("name") + " ("
                                                        + document.getString("caffeine") + "mg) $"
                                                        + document.getString("price");
                                                System.out.println(line);
                                                menuItems.add(line);
                                            }
                                        } else {
                                            System.out.println("Error getting documents: " + task.getException());
                                        }
                                    }
                                });
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            });

            //fill array with menu items
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, menuItems);
            listView.setAdapter(arrayAdapter);
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
