package com.example.ninerjaunt;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PathController {
    // Global Variables for Building LatLngs
    private LatLng epicBuilding = new LatLng(35.309331, -80.741411);
    private LatLng dukeCentennial = new LatLng(35.31103, -80.74166);
    private LatLng bioinformatics = new LatLng(35.312492, -80.741848);
    private LatLng griggHall = new LatLng(35.311427, -80.741726);
    private LatLng kulwickiLab = new LatLng(35.312462, -80.740782);
    private LatLng motorSports = new LatLng(35.312478, -80.740398);
    private LatLng portal = new LatLng(35.31151, -80.74282);
    private LatLng baseball = new LatLng(35.30880, -80.73978);

    // Method for the spinner
    public LatLng spinnerSwitch(String source) {
        LatLng mapValue = null;
        switch (source) {
            case "EPIC":
                mapValue = epicBuilding;
                Log.d("/d", "Source = epic");
                break;
            case "Duke Hall":
                mapValue = dukeCentennial;
                Log.d("/d", "Source = duke");
                break;
            case "Bioinformatics":
                mapValue = bioinformatics;
                Log.d("/d", "Source = bio");
                break;
            case "Grigg Hall":
                mapValue = griggHall;
                Log.d("/d", "Source = grigg");
                break;
            case "Motorsports Research":
                mapValue = motorSports;
                Log.d("/d", "Source = motorsports");
                break;
            case "PORTAL":
                mapValue = portal;
                Log.d("/d", "Source = portal");
                break;
            case "Baseball Field":
                mapValue = baseball;
                Log.d("/d", "Source = baseball");
                break;
            case "Current Location":
                mapValue = null;
                Log.d("/d", "source = current location");
                break;
            default:
                mapValue = null;

        }
        return mapValue;
    }


}
