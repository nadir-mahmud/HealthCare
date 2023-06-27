package com.example.healthcare.presentationlayer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.HistoryAdapter;
import com.example.healthcare.applicationlayer.MyOwnAdapter;
import com.example.healthcare.applicationlayer.NotificationAdapter;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.entity.Account;
import com.example.healthcare.entity.AppointedPatient;
import com.example.healthcare.entity.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


public class History extends Fragment {
    RecyclerView recyclerView;
    List<AppointedPatient> appointedPatients;
    Context context;
    HistoryAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        appointedPatients = new ArrayList<>();
        recyclerView = root.findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        geAppointedPatientList();

        return root;
    }

    private void geAppointedPatientList() {
        try {
            Encryption encryption = new Encryption();
            db.collection("appointed patient")
                    .whereEqualTo("patient uid", new Encryption().encryptData(user.getUid()))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData().);

                                    try {
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
                                adapter = new HistoryAdapter(context, appointedPatients, new RecyclerViewClickInterface() {
                                    @Override
                                    public void onItemClick(int position) {

                                    }
                                });
                                recyclerView.setAdapter(adapter);

                                //Toast.makeText(getApplicationContext(), accounts.get(0).getEmail(),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                            //Toast.makeText(getApplicationContext(),accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}