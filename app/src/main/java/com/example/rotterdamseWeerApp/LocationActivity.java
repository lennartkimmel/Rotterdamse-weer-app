package com.example.rotterdamseWeerApp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LocationActivity extends AppCompatActivity {

    public TextInputLayout longtitude;
    public TextInputLayout latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivities(new Intent[]{new  Intent(getApplicationContext(), MainActivity.class)});
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

//    //Constructor
//    public LocationActivity(TextInputLayout longtitude, TextInputLayout latitude){
//        this.longtitude = longtitude;
//        this.latitude = latitude;
//    }

    }
}