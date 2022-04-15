package com.example.pepe.map;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pepe.R;
import com.example.pepe.ViewStore;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.example.pepe.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

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
        LatLng uscStart = new LatLng(34.02226492129773, -118.2876243116412);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uscStart, 13F));
        // getting the map markers
        try {
            Intent i = new Intent(this, ViewStore.class);
            StoreLocationArray markerArray = MenuInfoAccess.getMarkers();
            for (StoreLocation storeLoc : markerArray.getStoreLocationArray()) {
                System.out.println("map marker");
                float lat = (float) storeLoc.getLatitude();
                float lng = (float) storeLoc.getLongitude();
                LatLng latlng = new LatLng(lat, lng);
                System.out.println(lat + "\t" + lng);
                mMap.addMarker(new MarkerOptions()
                            .position(latlng)
                            .title(storeLoc.getName()));
        }

            // something about them choosing a store
            // pass store info along to next pg
//            startActivity(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        // adding each store location to the map
//
    }
}
//        try {
//            // connect to db and get seller information
//
//           Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/310project?user=" +
//                    "=root&password=root");
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT username, latitude, longitude FROM 310project.sellers");
//            // Do something with result set
//            System.out.println(rs);
//
//            // add markers for each store
//            while (rs.next()) {
//                LatLng loc = new LatLng(rs.getDouble("latitude"), rs.getDouble("longitude"));
//                mMap.addMarker(new MarkerOptions().position(loc).title(rs.getString("username")));
//
//            }
//        } catch (SQLException /*| ClassNotFoundException | IllegalAccessException | InstantiationException */
//        e) {
//            // handle any errors
//            System.out.println("SQLException: " + e.getMessage());
//            e.printStackTrace();
////            System.out.println("SQLState: " + e.getSQLState());
////            System.out.println("VendorError: " + e.getErrorCode());
//        }
//    }
//}