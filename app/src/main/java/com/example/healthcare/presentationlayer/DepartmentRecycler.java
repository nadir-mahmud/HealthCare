package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface2;
import com.example.healthcare.applicationlayer.SearchAdapter;
import com.example.healthcare.entity.Doctor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.security.GeneralSecurityException;

public class DepartmentRecycler extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_recycler);

        Toolbar toolbar = findViewById(R.id.toolbar_department_recycler);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.department_recycler);
        processSearch();
    }

    private void processSearch(){

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            Query query = null;
            query = FirebaseFirestore.getInstance()
                    .collection("search doctors")
                    .whereEqualTo("department", "Cardiology");

            FirestoreRecyclerOptions<Doctor> options = new FirestoreRecyclerOptions.Builder<Doctor>()
                    .setQuery(query,Doctor.class)
                    .build();

            adapter = new SearchAdapter(getApplicationContext(), options, new RecyclerViewClickInterface2() {
                @Override
                public void onItemClick(int position,Doctor doctor, int image) {
                    Intent intent = new Intent(getApplicationContext(), DoctorDetailsPL.class);

                    intent.putExtra("Name", doctor.getName());
                    intent.putExtra("degree", doctor.getDegree());
                    intent.putExtra("department", doctor.getDepartment());
                    intent.putExtra("experience", doctor.getExperience());
                    intent.putExtra("rating", doctor.getRating());
                    intent.putExtra("image", String.valueOf(image) );
                        startActivity(intent);

                }
            });
            adapter.startListening();

            recyclerView.setAdapter(adapter);

    }
}