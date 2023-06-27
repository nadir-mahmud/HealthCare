package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.healthcare.R;

public class NurseHome extends AppCompatActivity {

    LinearLayout profile, patientList, barchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);

        Toolbar toolbar = findViewById(R.id.toolbar_nurse_home);
        toolbar.setTitle("Healthcare");
        toolbar.inflateMenu(R.menu.doctor_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.doctor_logout){
                    Intent intent = new Intent(getApplicationContext(), LoginPL.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                return false;
            }
        });

        profile = findViewById(R.id.nurse_home_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NurseProfile.class);
                startActivity(intent);
            }
        });

        patientList = findViewById(R.id.nurse_home_patient_list);
        patientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NurseAppointedPatientList.class);
                startActivity(intent);
            }
        });

        barchart = findViewById(R.id.nurse_barchart);
        barchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Barchart.class);
                startActivity(intent);
            }
        });
    }
}