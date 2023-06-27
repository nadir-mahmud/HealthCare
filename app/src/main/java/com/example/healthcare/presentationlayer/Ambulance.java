package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthcare.R;

public class Ambulance extends AppCompatActivity {
    ImageView ambulance1,ambulance2,ambulance3,ambulance4;
    private static final int REQUEST_CALL = 1;
    EditText mEditTextNumber;
    String number1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);




        findViewById(R.id.ambulance1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                number1="+8801869022340";
                makePhoneCall(number1);
            }
        });
        findViewById(R.id.ambulance2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number1="+8801537145043";
                makePhoneCall(number1);
            }
        });
        findViewById(R.id.ambulance3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number1="+8801629830910";
                makePhoneCall(number1);
            }
        });
        findViewById(R.id.ambulance4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number1="+8801568031201";
                makePhoneCall(number1);
            }
        });


    }

    private void makePhoneCall (String number) {
        if (ContextCompat.checkSelfPermission(Ambulance.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Ambulance.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(number1);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


}