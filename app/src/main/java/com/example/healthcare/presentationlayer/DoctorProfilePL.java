package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.MyCallback;
import com.example.healthcare.entity.PatientProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class DoctorProfilePL extends AppCompatActivity {
    TextView userID;
    TextInputEditText name, workplace, department, education;
    Button doctorProfileUpdate;
    int imageID;
    Intent intent;
    ImageView doctorImage;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db;
    int doctorImgID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_pl);

        db = FirebaseFirestore.getInstance();

        userID = findViewById(R.id.user_id);
        name = findViewById(R.id.doctor_name);
        workplace = findViewById(R.id.doctor_profile_workplace);
        department = findViewById(R.id.doctor_department);
        education = findViewById(R.id.doctor_education);

        doctorImage = findViewById(R.id.doctor_image);

        doctorProfileUpdate = findViewById(R.id.doctor_profile_button);

        try {
            retrieveData();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        intent = getIntent();
        imageID = intent.getIntExtra("Image", 0);
        //Toast.makeText(getApplicationContext(), String.valueOf(imageID),Toast.LENGTH_SHORT).show();


        doctorProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    insertDoctorInfo();
                    retrieveData();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void insertDoctorInfo() throws GeneralSecurityException {
        // Create a new user with a first and last name



        Encryption encryption = new Encryption();


        Map<String, String> doctor = new HashMap<>();
        doctor.put("uid", encryption.encryptData(user.getUid()) );
        doctor.put("name", encryption.encryptData(name.getText().toString().trim()));
        doctor.put("workplace", encryption.encryptData(workplace.getText().toString().trim()));
        doctor.put("department", encryption.encryptData(department.getText().toString().trim()));
        doctor.put("degree", encryption.encryptData(education.getText().toString().trim()));

        if(workplace.getText().toString().trim().equals("DMC")){
            doctor.put("rating", encryption.encryptData("5"));
            doctor.put("experience", encryption.encryptData("10 years"));
            int doc1 = R.drawable.doc1;
            doctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        if(workplace.getText().toString().trim().equals("MMC")){
            doctor.put("rating", encryption.encryptData("3.2"));
            doctor.put("experience", encryption.encryptData("2 years"));
            int doc1 = R.drawable.doc2;
            doctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        if(workplace.getText().toString().trim().equals("SSMC") ){
            doctor.put("rating", encryption.encryptData("4.4"));
            doctor.put("experience", encryption.encryptData("5 years"));
            int doc1 = R.drawable.doc3;
            doctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        if(workplace.getText().toString().trim().equals("CMC")){
            doctor.put("rating", encryption.encryptData("4.1"));
            doctor.put("experience", encryption.encryptData("3 years"));
            int doc1 = R.drawable.doc4;
            doctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        Map<String, String> searchDoctor = new HashMap<>();
        searchDoctor.put("uid", encryption.encryptData(user.getUid()) );
        searchDoctor.put("name", name.getText().toString());
        searchDoctor.put("workplace", encryption.encryptData(workplace.getText().toString()));
        searchDoctor.put("department", department.getText().toString());
        searchDoctor.put("degree", encryption.encryptData(education.getText().toString()));

        if(workplace.getText().toString().trim().equals("DMC")){
            searchDoctor.put("rating", encryption.encryptData("5"));
            searchDoctor.put("experience", encryption.encryptData("10 years"));
            int doc1 = R.drawable.doc1;
            searchDoctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        if(workplace.getText().toString().trim().equals("MMC")){
            searchDoctor.put("rating", encryption.encryptData("3.2"));
            searchDoctor.put("experience", encryption.encryptData("2 years"));
            int doc1 = R.drawable.doc2;
            searchDoctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        if(workplace.getText().toString().trim().equals("SSMC") ){
            searchDoctor.put("rating", encryption.encryptData("4.4"));
            searchDoctor.put("experience", encryption.encryptData("5 years"));
            int doc1 = R.drawable.doc3;
            searchDoctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        if(workplace.getText().toString().trim().equals("CMC")){
            searchDoctor.put("rating", encryption.encryptData("4.1"));
            searchDoctor.put("experience", encryption.encryptData("3 years"));
            int doc1 = R.drawable.doc4;
            searchDoctor.put("image", encryption.encryptData(String.valueOf(doc1)));
        }

        db.collection("search doctors").document(user.getUid()).set(searchDoctor);



// Add a new document with a generated ID
        db.collection("doctors").document(user.getUid())
                .set(doctor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error writing document", e);
                        Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveData() throws GeneralSecurityException {
        Encryption encryption = new Encryption();
        PatientProfile patientProfile = new PatientProfile();
        db.collection("doctors")
                .whereEqualTo("uid", encryption.encryptData(user.getUid()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                try {
                                    String image = encryption.decryptData(document.getData().get("image").toString());
                                    if(image != null){
                                        doctorImage.setImageResource(Integer.parseInt(image));
                                    }

                                    userID.setText("UserId: " + encryption.decryptData(document.getData().get("name").toString()));
                                    //Toast.makeText(getApplicationContext(), encryption.decryptData(document.getData().get("rating").toString()), Toast.LENGTH_SHORT).show();
                                    name.setText((encryption.decryptData(document.getData().get("name").toString())));
                                    workplace.setText(encryption.decryptData(document.getData().get("workplace").toString()));
                                    department.setText(encryption.decryptData(document.getData().get("department").toString()));
                                    education.setText(encryption.decryptData(document.getData().get("degree").toString()));

                                } catch (GeneralSecurityException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Retrieved nothing, try again!", Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(context , patientProfile.getPhoneNumber() , Toast.LENGTH_SHORT).show();
                    }
                });

    }
}