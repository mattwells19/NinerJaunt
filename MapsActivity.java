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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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


        // Add a marker for Epic and move the camera (Start Point)
        LatLng epicBuilding = new LatLng(35.309354, -80.741596);
        mMap.addMarker(new MarkerOptions().position(epicBuilding).title("EPIC Building"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(epicBuilding));


        // Create path points
        // This portion will be created upon export from DB
        LatLng p1 = new LatLng(35.311210, -80.741331);
        LatLng p2 = new LatLng(35.311448, -80.741667);
        LatLng p3 = new LatLng(35.311608, -80.741487);



        // Create LatLng Variable for Duke Centennial Hall (End Point)
        LatLng dukeCentennial = new LatLng(35.312156, -80.741289);
        mMap.addMarker(new MarkerOptions().position(dukeCentennial).title("Duke Centennial Hall"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo((float) 15));


        // Create a polyline from Epic to Duke Centennial Hall
        Polyline line = mMap.addPolyline(new PolylineOptions()
        .add(epicBuilding,p1, p2, p3 ,dukeCentennial)
        .width(5)
        .color(Color.GREEN));
    }
}
