package com.example.pepe;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
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
    private boolean locationPermissionGranted;
    private final LatLng uscStart = new LatLng(34.02226492129773, -118.2876243116412);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        i = new Intent(this, ViewStoreActivity.class);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(toolbar);

        // initialize firebase firestorm
        db = FirebaseFirestore.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
        // map initially is centered on USC
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

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(@NonNull final Marker marker) {
        // find sellerID to pass to next activity
        db.collection("sellers").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    db.collection("sellers").document(document.getId())
                            .addSnapshotListener((value, error) -> {
                                if (value != null && value.exists()) {
                                    if (Objects.requireNonNull(value.getString("storeName")).equals(marker.getTitle())) {
                                        i.putExtra("storeID", value.getId());
                                        i.putExtra("storeName", value.getString("storeName"));

                                        LatLng location =
                                                new LatLng(Objects.requireNonNull(value.getGeoPoint("location")).getLatitude(),
                                                        Objects.requireNonNull(value.getGeoPoint("location")).getLongitude());
                                        i.putExtra("storeLocation", location);
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

    // for the toolbar actions
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    // Request location permission to get location of device
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

       @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
           locationPermissionGranted = false;
           if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
                // If request is cancelled, the
                // result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
            updateLocationUI();
        }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // get the most recent device location
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            i.putExtra("userLocation", lastKnownLocation);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), 13F));
                        } else {
                            System.out.println("Current location is null. Using defaults.");
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(uscStart, 13F));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    } else {
                        System.out.println("Current location is null. Using defaults.");
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(uscStart, 13F));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e)  {
            System.out.println("Exception: " + e.getMessage());
        }
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