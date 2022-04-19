package com.example.pepe.map;

import android.os.Bundle;
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
import com.example.pepe.R;
import com.example.pepe.ViewStore;
import com.example.pepe.model.StoreLocation;
import com.example.pepe.model.StoreLocationArray;
import com.google.android.gms.maps.model.Marker;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ActivityMapsBinding binding;
    private GoogleMap mMap;
    FirebaseFirestore db;
    private Intent i;
    private LatLng uscStart;
    private int storeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        DocumentReference documentReference = db.collection("sellers").document("r51igMingFvB2Ko5MvCn");

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    // below line is to create a geo point and we are getting
                    // geo point from firebase and setting to it.
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
//        // Set a listener for marker click.
//        mMap.setOnMarkerClickListener(this);
//    }

//    /** Called when the user clicks a marker. */
//    @Override
//    public boolean onMarkerClick(final Marker marker) {
//        // send store name to next page
//        i = new Intent(this, ViewStore.class);
//        i.putExtra("storeName", marker.getTitle());
//        i.putExtra("storeID", storeID);
//        startActivity(i);
//        return true;
//    }
}