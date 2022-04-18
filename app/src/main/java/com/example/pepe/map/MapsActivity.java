package com.example.pepe.map;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pepe.R;
import com.example.pepe.ViewStore;
import com.example.pepe.data.model.StoreLocation;
import com.example.pepe.data.model.StoreLocationArray;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.example.pepe.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Intent i;
    private LatLng uscStart;
    private int storeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap mMap) {
        // map initially is focused on USC/centered on USC
        uscStart = new LatLng(34.02226492129773, -118.2876243116412);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uscStart, 13F));
        // getting the map markers
        try {
            StoreLocationArray markerArray = MenuInfoAccess.getMarkers();
            for (StoreLocation storeLoc : markerArray.getStoreLocationArray()) {
                System.out.println("map marker");
                float lat = (float) storeLoc.getLatitude();
                float lng = (float) storeLoc.getLongitude();
                LatLng latlng = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions()
                            .position(latlng)
                            .title(storeLoc.getName()));
        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        // send store name to next page
        i = new Intent(this, ViewStore.class);
        i.putExtra("storeName", marker.getTitle());
        i.putExtra("storeID", storeID);
        startActivity(i);
        return true;
    }
}