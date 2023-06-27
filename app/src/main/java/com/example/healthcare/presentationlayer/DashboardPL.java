package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthcare.R;

public class DashboardPL extends AppCompatActivity {
    CardView admin, doctor, nurse, patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pl);

        admin = findViewById(R.id.admin_card);
        doctor = findViewById(R.id.nurse_card);
        patient = findViewById(R.id.patient_card);
        nurse = findViewById(R.id.doctor_card);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardPL.this, LoginPL.class);
                intent.putExtra("role", "Admin");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardPL.this, LoginPL.class);
                intent.putExtra("role", "Doctor");
                startActivity(intent);
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardPL.this, LoginPL.class);
                intent.putExtra("role", "Patient");
                startActivity(intent);
            }
        });

        nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardPL.this, LoginPL.class);
                intent.putExtra("role", "Nurse");
                startActivity(intent);
            }
        });


    }
}