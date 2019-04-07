package com.example.ninerjaunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    static String SOURCE = "4JL4HG3";
    static String DEST = "JFL299F";
    Intent intent;
    String item1;
    String item2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                intent.putExtra(SOURCE, item1);
                intent.putExtra(DEST, item2);

                // Send intent
                startActivity(intent);
            }
        });




    }
}
