package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
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

public class NurseProfile extends AppCompatActivity {

    TextView userID;
    TextInputEditText name, ward, education,workplace;
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
        setContentView(R.layout.activity_nurse_profile);

        db = FirebaseFirestore.getInstance();

        userID = findViewById(R.id.user_id);
        name = findViewById(R.id.nurse_name);
        workplace = findViewById(R.id.nurse_workplace);
        ward = findViewById(R.id.nurse_ward_no);
        education = findViewById(R.id.nurse_degree);

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


        Map<String, String> nurse = new HashMap<>();
        nurse.put("uid", encryption.encryptData(user.getUid()) );
        nurse.put("name", encryption.encryptData(name.getText().toString()));
        nurse.put("workplace", encryption.encryptData(workplace.getText().toString()));
        nurse.put("ward no", encryption.encryptData(ward.getText().toString()));
        nurse.put("degree", encryption.encryptData(education.getText().toString()));





// Add a new document with a generated ID
        db.collection("nurses").document(user.getUid())
                .set(nurse)
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
        db.collection("nurses")
                .whereEqualTo("uid", encryption.encryptData(user.getUid()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                try {

                                    userID.setText("User ID: " + encryption.decryptData(document.getData().get("name").toString()));
                                    //Toast.makeText(getApplicationContext(), encryption.decryptData(document.getData().get("rating").toString()), Toast.LENGTH_SHORT).show();
                                    name.setText((encryption.decryptData(document.getData().get("name").toString())));
                                    workplace.setText(encryption.decryptData(document.getData().get("workplace").toString()));
                                    ward.setText(encryption.decryptData(document.getData().get("ward no").toString()));
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