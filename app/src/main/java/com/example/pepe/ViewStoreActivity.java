package com.example.pepe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Objects;

/** TODO: set up menu items display
 * remember to have a check for if it is empty
 * format for scrolling
 * */
public class ViewStoreActivity extends AppCompatActivity {

    private TextView StoreName;
    private TextView StoreAddress;
    private ListView listView;

//    private ArrayList<MenuItem_Model> items = new ArrayList<>();
    private final ArrayList<String> menuItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StoreName = (TextView) findViewById(R.id.storeName);
        StoreAddress = (TextView) findViewById(R.id.storeAddress);
        Button back = (Button) findViewById(R.id.back_to_map);
        listView = (ListView) findViewById(R.id.menuView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // fill in textview items and menu
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String storeID = extras.getString("storeID");
            DocumentReference docRef = db.collection("sellers").document(storeID);
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
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                            String line = document1.getString("name") + " ("
                                                    + document1.getString("caffeine") + "mg) $"
                                                    + document1.getString("price");
                                            menuItems.add(line);

                                            //fill array with menu items
                                            final ArrayAdapter<String> arrayAdapter =
                                                    new ArrayAdapter<>(getApplicationContext(),
                                                            R.layout.activity_listview, menuItems);
                                            listView.setAdapter(arrayAdapter);
                                        }
                                    } else {
                                        System.out.println("Error getting documents: " + task1.getException());
                                    }
                                });
                    }
                } else {
                    System.out.println("get failed with " + task.getException());
                }
            });
        }



        // go to checking delivery address and confirm order
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            // add to checkout cart
        });



        // go back to the map page
        back.setOnClickListener(view -> openBack());
    }
    // function to go back to map page
    private void openBack() {
        startActivity(new Intent(this, MapsActivity.class));
    }
}
