package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.healthcare.R;

public class DoctorDepartmentPL extends AppCompatActivity {
    LinearLayout cardiology;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_department_pl);

        Toolbar toolbar = findViewById(R.id.toolbar_department);
        setSupportActionBar(toolbar);

        cardiology = findViewById(R.id.cardiology);

        cardiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DepartmentRecycler.class);
                startActivity(intent);
            }
        });

    }
}