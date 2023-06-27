package com.example.healthcare.presentationlayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface2;
import com.example.healthcare.applicationlayer.SearchAdapter;
import com.example.healthcare.entity.Doctor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.security.GeneralSecurityException;
import java.util.Locale;


public class SearchDoctor extends Fragment {

    RecyclerView recyclerView;
    SearchView searchView;
    private Context context;
    SearchAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("doctors");

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search_doctor, container, false);
        searchView = root.findViewById(R.id.search);
        recyclerView = root.findViewById(R.id.search_recycler);
        searchView.onActionViewExpanded();

        processSearch("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                s.toUpperCase();
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

        return root;

    }
    private void processSearch(String s){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Query query = null;

            query = FirebaseFirestore.getInstance()
                    .collection("search doctors")
                    .orderBy("name")
                    .startAt(s)
                    .endAt(s + "\uf8ff");



        FirestoreRecyclerOptions<Doctor> options = new FirestoreRecyclerOptions.Builder<Doctor>()
                .setQuery(query,Doctor.class)
                .build();
       // Toast.makeText(context, String.valueOf(options.getSnapshots().size()),Toast.LENGTH_SHORT).show();

        adapter = new SearchAdapter(context, options, new RecyclerViewClickInterface2() {
            @Override
            public void onItemClick(int position, Doctor doctor,int image) {
                Intent intent = new Intent(context, DoctorDetailsPL.class);

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