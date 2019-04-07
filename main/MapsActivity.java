package com.example.ninerjaunt;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Google Maps Variable
    private GoogleMap mMap;
    private LatLng epicBuilding = new LatLng(35.309354, -80.741596);
    private LatLng dukeCentennial = new LatLng(35.312156, -80.741289);
    private LatLng source;
    private LatLng destination;
    private String sourceString;
    private String destString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Receive intent
        if (getIntent() != null && getIntent().getExtras() != null) {
            Log.d("/d", "Success Intent Received");
            Log.d("/d", "Source Received: " + getIntent().getExtras().getString(MainActivity.SOURCE));
            Log.d("/d", "Dest Received: " + getIntent().getExtras().getString(MainActivity.DEST));

             sourceString = getIntent().getExtras().getString(MainActivity.SOURCE);
             destString = getIntent().getExtras().getString(MainActivity.DEST);

            // Switch for source
            switch (sourceString) {
                case "EPIC":
                   source = epicBuilding;
                   Log.d("/d", "Source = epic");
                   break;
                case "Duke Hall":
                    source = dukeCentennial;
                    Log.d("/d", "Source = duke");
                    break;
            }

            // Switch for dest
            switch (destString) {
                case "EPIC":
                    destination = epicBuilding;
                    Log.d("/d", "dest = epic");
                    break;
                case "Duke Hall":
                    destination = dukeCentennial;
                    Log.d("/d", "dest = duke");
                    break;
            }

        }
    } // end of onCreate function


    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {




        Log.d("/d", "onMapReady: Executing");


        // Google Map Variable
        mMap = googleMap;

        // On Camera Move
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
            Log.d("/d", "Camera Moving...");
            }
        });


        // Set map to hybrid viewer
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker (Start Point)
        mMap.addMarker(new MarkerOptions().position(source).title(sourceString));
        mMap.addMarker(new MarkerOptions().position(destination).title(destString));


        // This portion will be created upon export from DB
        LatLng p1 = new LatLng(35.311210, -80.741331);
        LatLng p2 = new LatLng(35.311448, -80.741667);
        LatLng p3 = new LatLng(35.311608, -80.741487);



        // Add a marker + move the camera (End Point)
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 18));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(source));

        Log.d("/d", "Source String: " + sourceString);
        Log.d("/d", "Source LatLNG: " + source);

        Log.d("/d", "Dest String: " + destString);
        Log.d("/d", "Dest LatLNG: " + destination);



        // Create a polyline from Epic to Duke Centennial Hall
        Polyline line = mMap.addPolyline(new PolylineOptions()
        .add(source,p1, p2, p3 ,destination)
        .width(5)
        .color(Color.GREEN));
    }
}
