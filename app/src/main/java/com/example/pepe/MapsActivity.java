package com.example.pepe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.example.pepe.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import android.content.Intent;
import android.widget.Toolbar;

import com.google.android.gms.maps.model.Marker;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

// TODO: set up onMarkerClick stuff
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private ActivityMapsBinding binding;
    private GoogleMap mMap;
    FirebaseFirestore db;
    private LatLng uscStart;
    private int storeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(toolbar);

//        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        // initialize firebase firestore
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
        uscStart = new LatLng(34.02226492129773, -118.2876243116412);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uscStart, 13F));

        // get markers from firestore
        db.collection("sellers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    System.out.println(document.getId() + " => " + document.getData());
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
//        // getting the map markers
//        try {
//            StoreLocationArray markerArray = MenuInfoAccess.getMarkers();
//            for (StoreLocation storeLoc : markerArray.getStoreLocationArray()) {
//                System.out.println("map marker");
//                float lat = (float) storeLoc.getLatitude();
//                float lng = (float) storeLoc.getLongitude();
//                LatLng latlng = new LatLng(lat, lng);
//                mMap.addMarker(new MarkerOptions()
//                            .position(latlng)
//                            .title(storeLoc.getName()));
//        }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        // send store name to next page
        Intent i = new Intent(this, ViewStore.class);
        i.putExtra("storeName", marker.getTitle());
        System.out.println(marker.getTitle());
        startActivity(i);
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
        startActivity(new Intent(this, UpdateProfile.class));
    }

    public void toMap(MenuItem item) {
    }
}