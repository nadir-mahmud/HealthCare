package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.healthcare.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class AboutPL extends AppCompatActivity {
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_pl);

        mapView = findViewById(R.id.map);

    }
}