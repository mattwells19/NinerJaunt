package com.example.ninerjaunt;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Google Maps Variable
    private GoogleMap mMap;

    private LatLng source;
    private LatLng destination;
    private String sourceString;
    private String destString;
    public static ArrayList<LatLng> latLngArrayList = new ArrayList<>();


    // API Requirements
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

            // Call method return latlng of source and dest (Utilize the Controller)
            source = PathController.spinnerSwitch(sourceString);
            destination = PathController.spinnerSwitch(destString);


            UNCCPath path = new UNCCPath(source.latitude, source.longitude, destination.latitude, destination.longitude);

            // latLngArrayList.add(source);


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
            // latLngArrayList.add(destination);


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

        // Add Markers for start and end point
        mMap.addMarker(new MarkerOptions().position(source).title(sourceString));
        mMap.addMarker(new MarkerOptions().position(destination).title(destString));


        // Create a polyline using latLngArrayList
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .width(5)
                .color(Color.GREEN));
        line.setPoints(latLngArrayList);


        // Add a marker + move the camera (End Point)
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 18));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(source));

        Log.d("/d", "Source String: " + sourceString);
        Log.d("/d", "Source LatLNG: " + source);

        Log.d("/d", "Dest String: " + destString);
        Log.d("/d", "Dest LatLNG: " + destination);


    }
}
