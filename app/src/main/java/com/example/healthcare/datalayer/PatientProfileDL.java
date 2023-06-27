package com.example.healthcare.datalayer;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.MyCallback;
import com.example.healthcare.entity.PatientProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientProfileDL {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public void insertPatientInfo(PatientProfile patientProfile, Context context) throws GeneralSecurityException {
        // Create a new user with a first and last name
        Encryption encryption = new Encryption();
        Map<String, String> patient = new HashMap<>();
        patient.put("UID", encryption.encryptData(user.getUid()) );
        patient.put("name", encryption.encryptData(patientProfile.getName()));
        patient.put("phone number", encryption.encryptData(patientProfile.getPhoneNumber()));
        patient.put("email", encryption.encryptData(user.getEmail()));
        patient.put("date of Birth", encryption.encryptData(patientProfile.getDateofBirth()));
        patient.put("age", encryption.encryptData(patientProfile.getAge()));
        patient.put("gender", encryption.encryptData(patientProfile.getGender()));
        patient.put("blood group", encryption.encryptData(patientProfile.getBloodGroup()));


// Add a new document with a generated ID
        db.collection("patients").document(user.getUid())
                .set(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error writing document", e);
                        Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void retrieveData(MyCallback myCallback, Context context) throws GeneralSecurityException {
        Encryption encryption = new Encryption();
        PatientProfile patientProfile = new PatientProfile();
        db.collection("patients")
                .whereEqualTo("UID", encryption.encryptData(user.getUid()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                try {
                                    patientProfile.setName(encryption.decryptData(document.getData().get("name").toString()));
                                    patientProfile.setPhoneNumber(encryption.decryptData(document.getData().get("phone number").toString()));
                                    patientProfile.setEmail(encryption.decryptData(document.getData().get("email").toString()));
                                    patientProfile.setDateofBirth(encryption.decryptData(document.getData().get("date of Birth").toString()));
                                    patientProfile.setAge(encryption.decryptData(document.getData().get("age").toString()));
                                    patientProfile.setGender(encryption.decryptData(document.getData().get("gender").toString()));
                                    patientProfile.setBloodGroup((encryption.decryptData(document.getData().get("blood group").toString())));
                                    myCallback.onCallback(patientProfile);

                                } catch (GeneralSecurityException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            patientProfile.setName("nothing");
                            Toast.makeText(context, "Retrieved nothing, try again!", Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(context , patientProfile.getPhoneNumber() , Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
