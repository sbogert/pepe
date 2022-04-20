package com.example.pepe.map;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.pepe.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import android.content.Intent;
import android.widget.Toolbar;

import com.example.pepe.R;
import com.example.pepe.ViewStore;
import com.example.pepe.model.StoreLocation;
import com.example.pepe.model.StoreLocationArray;
import com.google.android.gms.maps.model.Marker;

// TODO: set up onMarkerClick stuff
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private ActivityMapsBinding binding;
    private GoogleMap mMap;
    FirebaseFirestore db;
    private Intent i;
    private LatLng uscStart;
    private int storeID;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(toolbar);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        // initialize firebase firestore
        db = FirebaseFirestore.getInstance();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // map initially is focused on USC/centered on USC
        uscStart = new LatLng(34.02226492129773, -118.2876243116412);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uscStart, 13F));

        // get markers from firestore
        DocumentReference documentReference = db.collection("sellers").document("r51igMingFvB2Ko5MvCn");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    // create a geo point with info from firebase
                    GeoPoint mapMarker = value.getGeoPoint("location");

                    // getting lat and long from geo point and setting it to location.
                    LatLng location = new LatLng(mapMarker.getLatitude(), mapMarker.getLongitude());

                    // adding marker to each location on google maps
                    mMap.addMarker(new MarkerOptions().position(location).title(value.getString("storeName")));
                } else {
                    Toast.makeText(MapsActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                }
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
        i = new Intent(this, ViewStore.class);
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
}