package com.example.pepe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import android.content.Intent;
import android.widget.Toolbar;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.Objects;


/** MapsActivity displays the map and user can select map markers to view stores */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(toolbar);

        // initialize firebase firestorm
        db = FirebaseFirestore.getInstance();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // map initially is focused on USC/centered on USC
        LatLng uscStart = new LatLng(34.02226492129773, -118.2876243116412);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uscStart, 13F));

        // get markers from firestorm
        db.collection("sellers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    db.collection("sellers").document(document.getId())
                            .addSnapshotListener((value, error) -> {
                                if (value != null && value.exists()) {
                                    // create a geo point with info from firebase
                                    GeoPoint mapMarker = value.getGeoPoint("location");

                                    // getting lat and long from geo point and setting it to location.
                                    assert mapMarker != null;
                                    LatLng location = new LatLng(mapMarker.getLatitude(), mapMarker.getLongitude());

                                    // adding marker to each location on google maps
                                    mMap.addMarker(new MarkerOptions().position(location).title(value.getString("storeName")));
                                } else {
                                    Toast.makeText(MapsActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            } else {
                System.out.println("Error getting documents");
            }
        });
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(@NonNull final Marker marker) {
        Intent i = new Intent(this, ViewStore.class);
        // find sellerID to pass to next activity
        db.collection("sellers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    db.collection("sellers").document(document.getId())
                            .addSnapshotListener((value, error) -> {
                                if (value != null && value.exists()) {
                                    if (Objects.requireNonNull(value.getString("storeName")).equals(marker.getTitle())) {
                                        i.putExtra("storeID", value.getId());
                                        startActivity(i);
                                    }
                                } else {
                                    Toast.makeText(MapsActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            } else {
                System.out.println("Error getting documents");
            }
        });
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SplashActivity.class));
    }
    public void toProfile(MenuItem item) {
        startActivity(new Intent(this, ProfileActivity.class));
    }
    public void toMap(MenuItem item) {}
}