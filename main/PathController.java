package com.example.ninerjaunt;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PathController {
    // Global Variables for Building LatLngs
    public static LatLng epicBuilding = new LatLng(35.309354, -80.741596);
    public static LatLng dukeCentennial = new LatLng(35.312156, -80.741289);
    public static LatLng bioinformatics = new LatLng(35.312492, -80.741848);
    public static LatLng griggHall = new LatLng(35.311427, -80.741726);
    public static LatLng kulwickiLab = new LatLng(35.312462, -80.740782);
    public static LatLng motorSports = new LatLng(35.312478, -80.740398);
    public static LatLng portal = new LatLng(35.311528, -80.742859);

    // Method for the spinner
    public static LatLng spinnerSwitch(String source) {
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
            case "Kulwicki Laboratory":
                mapValue = kulwickiLab;
                Log.d("/d", "Source = kulwicki");
                break;
            case "Motorsports Research":
                mapValue = motorSports;
                Log.d("/d", "Source = motorsports");
                break;
            case "PORTAL":
                mapValue = portal;
                Log.d("/d", "Source = portal");
                break;

            default:
                mapValue = null;

        }
        return mapValue;
    }


}
