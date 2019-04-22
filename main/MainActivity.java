package com.example.ninerjaunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // Class Variables
    public static String SOURCE = "4JL4HG3";
    public static String DEST = "JFL299F";
    Intent intent;
    private String item1;
    private String item2;
    public static String text = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // If user location can't be found
        if (getIntent() != null && getIntent().getExtras() != null) {
            Toast.makeText(this, getIntent().getExtras().getString(MapsActivity.TOAST_KEY), Toast.LENGTH_LONG).show();
        }


        // This is needed to read from points.txt to send to UNCCPath.java
        try {
            InputStream is = getAssets().open("points.txt");
            int size = is.available();
            Log.d("/d", "Buffer Size: " + size);
            byte[] buffer = new byte[size];
            is.read(buffer);

            is.close();
            text = new String(buffer);
        } catch (IOException e) {
            Log.d("/d", "EXCEPTION");
            e.printStackTrace();
        }


        // Create Intent so that program can transfer information
        intent = new Intent(this, MapsActivity.class);


        // Create both drop down menus
        Spinner spinnerSource = findViewById(R.id.spinnerSource);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.buildings));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerSource.setAdapter(myAdapter);

        Spinner spinnerDest = findViewById(R.id.spinnerDest);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.buildings));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerDest.setAdapter(myAdapter2);


        // Set on item selected for the spinners
        spinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item1 = parent.getItemAtPosition(position).toString();
                Log.d("/d", "Selected: " + parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set on item selected for the spinners
        spinnerDest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item2 = parent.getItemAtPosition(position).toString();
                Log.d("/d", "Selected: " + parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Create button on click listener
        Button submit = (Button) findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Put the intent start + end
                Log.d("/d", "Source Sent: " + item1);
                Log.d("/d", "Dest Sent: " + item2);


                // Attach intent extras
                intent.putExtra(SOURCE, item1);
                intent.putExtra(DEST, item2);


                // Send intent
                startActivity(intent);


            }

        });


    }
}
