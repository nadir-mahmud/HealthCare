package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.healthcare.presentationlayer.AppStart;
import com.example.healthcare.presentationlayer.DashboardPL;
import com.example.healthcare.presentationlayer.LoginPL;

public class MainActivity extends AppCompatActivity {
    Boolean start = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(start){
            Intent intent = new Intent(getApplicationContext(), AppStart.class);
            start = false;
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), DashboardPL.class);
            start = false;
            startActivity(intent);
        }
    }
}