package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;

public class DoctorDetailsPL extends AppCompatActivity {
    TextView experience, department, name, description, patientVisit, recommendation, rating;
    Button bookAppointment;
    RatingBar ratingBar;
    Intent intent;
    String nameString, degreeString, departmentString, ratingString, imageString, experienceString;
    int imageID;
    ImageView doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details_pl);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        doctor = findViewById(R.id.doctor_detail_image);

        experience = findViewById(R.id.doctor_detail_experience);
        name = findViewById(R.id.doctor_detail_name);
        department = findViewById(R.id.doctor_detail_department);
        description = findViewById(R.id.doctor_detail_description);
        patientVisit = findViewById(R.id.doctor_detail_patient_appoinment);
        recommendation = findViewById(R.id.doctor_detail_recommendations);
        rating = findViewById(R.id.doctor_detail_total_rating);

        ratingBar = findViewById(R.id.doctor_detail_ratingbar);

        intent = getIntent();
        nameString = intent.getStringExtra("Name");
        degreeString = intent.getStringExtra("degree");
        departmentString = intent.getStringExtra("department");
        experienceString = intent.getStringExtra("experience");
        ratingString = intent.getStringExtra("rating");
        imageString = intent.getStringExtra("image");

//        experience.setText(experienceString);
//        department.setText(departmentString);
//        rating.setText(ratingString);
//        doctor.setImageResource(Integer.parseInt(imageString));
//        description.setText(nameString + " is one of the renowned doctor in Bangladesh. He has " + experienceString + " of experience. He is one of the best " + departmentString + " in Bangladesh.");

        bookAppointment = findViewById(R.id.doctor_detail_appointment);
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppointmentPL.class);
                intent.putExtra("name",nameString);
                intent.putExtra("degree",degreeString);
                intent.putExtra("department",departmentString);
                intent.putExtra("experience",experienceString);
                intent.putExtra("rating", ratingString);
                intent.putExtra("image", imageString);
                startActivity(intent);
            }
        });


       // imageID = intent.getIntExtra("Image", 0);
        //Toast.makeText(getApplicationContext(), String.valueOf(imageID),Toast.LENGTH_SHORT).show();

        name.setText(nameString);

        Encryption encryption = new Encryption();


        try {
            db.collection("doctors")
                    .whereEqualTo("name", encryption.encryptData(nameString.trim()))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {


                                    try {
                                        name.setText(encryption.decryptData(document.getData().get("name").toString()));
                                        //Toast.makeText(getApplicationContext(),document.getData().get("name").toString(),Toast.LENGTH_SHORT).show();
                                        department.setText(encryption.decryptData(document.getData().get("department").toString()));
                                        String ratingString = encryption.decryptData(document.getData().get("rating").toString());
                                        ratingBar.setRating(Float.parseFloat(ratingString));
                                        String experienceString = encryption.decryptData(document.getData().get("experience").toString());
                                        experience.setText("Over " + experienceString + " of experience");
                                        description.setText(encryption.decryptData(document.getData().get("name").toString()) + " is one of the renowned doctor in Bangladesh. He has " + experienceString + " of experience. He is one of the best " + encryption.decryptData(document.getData().get("department").toString()) + " in Bangladesh.");
                                        doctor.setImageResource(Integer.parseInt(encryption.decryptData(document.getData().get("image").toString())));


                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


    }
}

