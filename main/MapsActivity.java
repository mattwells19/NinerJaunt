package com.example.ninerjaunt;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Google Maps Variable
    private GoogleMap mMap;

    // Class Variables
    private LatLng source;
    private LatLng destination;
    private String sourceString;
    private String destString;
    public static ArrayList<LatLng> latLngArrayList = new ArrayList<>();
    int x = 0;


    // API Requirements
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Reset the Array if user reloads App
        latLngArrayList.clear();


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Receive Intent from MainActivity.java
        if (getIntent() != null && getIntent().getExtras() != null) {
            Log.d("/d", "Source Received: " + getIntent().getExtras().getString(MainActivity.SOURCE));
            Log.d("/d", "Dest Received: " + getIntent().getExtras().getString(MainActivity.DEST));

            sourceString = getIntent().getExtras().getString(MainActivity.SOURCE);
            destString = getIntent().getExtras().getString(MainActivity.DEST);

            PathController pathController = new PathController();

            // Call method return latlng of source and dest (Utilize the Controller)
            source = pathController.spinnerSwitch(sourceString);
            destination = pathController.spinnerSwitch(destString);


            UNCCPath path = new UNCCPath(source.latitude, source.longitude, destination.latitude, destination.longitude);


            // Get the path generated load into arraylist
            try {
                LinkedList<double[]> p = path.getPath();
                Log.d("/d", "Size of Path: " + String.valueOf(p.size()));
                for (double[] c : p) {
                    latLngArrayList.add(new LatLng((c[0]), c[1]));
                    Log.d("/d", "[" + String.valueOf(c[0]) + "," + String.valueOf(c[1]) + "]");
                }

            } catch (IOException e) {
                Log.d("/d", "Exc Throw");
                e.printStackTrace();
            }


        }
    } // end of onCreate function


    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        // Log Statement to indicate onMapRunning
        Log.d("/d", "onMapReady: Executing");


        // Google Map Variable
        mMap = googleMap;

        // Set map to Hybrid View (Satellite + Street)
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        // Change mapType functionality
        Button button = findViewById(R.id.ChangeMapView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x == 0) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    x = 1;
                } else if (x == 1) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    x = 2;
                } else if (x == 2) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    x = 3;
                } else if (x == 3) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    x = 0;
                }

            }
        });

        // Add Markers for start and end Point
        Log.d("/d", "Source String: " + sourceString);
        Log.d("/d", "Source position: " + source.latitude + ", " + source.longitude);
        Log.d("/d", "Dest position: " + destination.latitude + ", " + destination.longitude);
        Log.d("/d", "Dest String: " + destString);
        mMap.addMarker(new MarkerOptions().position(source).title(sourceString));
        mMap.addMarker(new MarkerOptions().position(destination).title(destString));


        // Create a polyline using latLngArrayList
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .width(5)
                .color(Color.GREEN));
        line.setPoints(latLngArrayList);


        // Add a marker + move the camera (End Point)
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(source));

        // Console Printouts
        Log.d("/d", "Source String: " + sourceString);
        Log.d("/d", "Source LatLNG: " + source);
        Log.d("/d", "Dest String: " + destString);
        Log.d("/d", "Dest LatLNG: " + destination);


    }
}
