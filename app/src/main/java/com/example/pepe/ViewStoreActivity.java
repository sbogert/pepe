package com.example.pepe;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private String storeID;
    private String storeName;
    private Intent x;
    private LatLng storeLocation;
    private Location userLocation;


    private final ArrayList<String> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store);
        x = new Intent(this, OrderConfirm.class);

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
            storeID = extras.getString("storeID");
            storeLocation = (LatLng) extras.get("storeLocation");
            userLocation = (Location) extras.get("userLocation");
            System.out.println("user location: " + userLocation.getLongitude() + ", " + userLocation.getLatitude() );
            DocumentReference docRef = db.collection("sellers").document(storeID);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        storeName = document.getString("storeName");
                        StoreName.setText(storeName);

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
            String str = (String) adapterView.getItemAtPosition(i);
            // parse menu item info to pass along
            String itemName = str.split("\\(")[0];
            String leftover = str.split("\\(")[1];
            String itemCaff = leftover.split("m")[0];
            String lastPrt = leftover.split("m")[1];
            String itemPrice = lastPrt.split("\\$")[1];

            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db1 = FirebaseFirestore.getInstance();
            assert fUser != null;
            DocumentReference drinkerReference = db1.collection("drinkers").document(fUser.getUid());
            drinkerReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // get drinkerCaff
                        long drinkerCaff;
                        drinkerCaff = document.getLong("caffeineLimit");
                        int caffeine = Integer.parseInt(itemCaff);
                        // warn user about consuming too much caffeine
                        if (caffeine + drinkerCaff >= 400) {
                            Toast.makeText(ViewStoreActivity.this, "Warning!" +
                                    "\nYou have exceeded the recommended daily caffeine intake limit.",
                                    Toast.LENGTH_LONG).show();
                        }
                        // update drinker's caffeine intake total
                        drinkerCaff += caffeine;
                        drinkerReference.update("caffeineLimit", drinkerCaff);
                    }
                }
            });

            x.putExtra("storeID", storeID);
            x.putExtra("storeName", storeName);
            x.putExtra("itemName", itemName);
            x.putExtra("itemCaff", itemCaff);
            x.putExtra("itemPrice", itemPrice);
            x.putExtra("storeLocation", storeLocation);
            x.putExtra("userLocation", userLocation);
            startActivity(x);
        });

        // go back to the map page
        back.setOnClickListener(view -> openBack());
    }

    // function to go back to map page
    private void openBack() {
        startActivity(new Intent(this, MapsActivity.class));
    }
}
