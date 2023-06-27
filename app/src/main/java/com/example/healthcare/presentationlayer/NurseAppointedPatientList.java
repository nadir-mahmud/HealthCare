package com.example.healthcare.presentationlayer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.HistoryAdapter2;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.entity.AppointedPatient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class NurseAppointedPatientList extends AppCompatActivity {

    List<AppointedPatient> appointedPatients;
    RecyclerView recyclerView;
    HistoryAdapter2 adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_appointed_patient_list);

        Toolbar toolbar = findViewById(R.id.toolbar_nurse);
        toolbar.setTitle("Appointed Patient");
        setSupportActionBar(toolbar);

        appointedPatients = new ArrayList<>();
        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        geAppointedPatientList();
    }

    private void geAppointedPatientList() {
        Encryption encryption = new Encryption();

            db.collection("appointed patient")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData().);
                                    //Toast.makeText(getApplicationContext(),"Hello in",Toast.LENGTH_SHORT).show();
                                    try {
                                        //Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();

                                        AppointedPatient appointedPatient = new AppointedPatient();
                                        appointedPatient.setDoctorName(encryption.decryptData(document.getData().get("doctor name").toString()));
                                        appointedPatient.setDay(encryption.decryptData(document.getData().get("day").toString()));
                                        appointedPatient.setDoctorDegree(encryption.decryptData(document.getData().get("doctor degree").toString()));
                                        appointedPatient.setDoctorDepartment(encryption.decryptData(document.getData().get("doctor department").toString()));
                                        appointedPatient.setPatientUid(encryption.decryptData(document.getData().get("patient uid").toString()));
                                        appointedPatient.setPatientName(encryption.decryptData(document.getData().get("patient name").toString()));
                                        appointedPatient.setSerial(encryption.decryptData(document.getData().get("serial").toString()));
                                        //Toast.makeText(getApplicationContext(), encryption.decryptData(document.getData().get("doctor name").toString()),Toast.LENGTH_SHORT).show();
                                        appointedPatients.add(appointedPatient);
                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    }
                                }
                                adapter = new HistoryAdapter2(getApplicationContext(), appointedPatients, new RecyclerViewClickInterface() {
                                    @Override
                                    public void onItemClick(int position) {

                                    }
                                });
                                recyclerView.setAdapter(adapter);

                                //Toast.makeText(getApplicationContext(), accounts.get(0).getEmail(),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                //Toast.makeText(getApplicationContext(),"None",Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(getApplicationContext(),accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();

                        }
                    });


        }
    }
