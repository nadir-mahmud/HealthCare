package com.example.healthcare.presentationlayer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.HorizontalAdapter;
import com.example.healthcare.applicationlayer.MyCallbackList;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface2;
import com.example.healthcare.entity.Doctor;
import com.example.healthcare.entity.DoctorInfoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientHomeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientHomeFrag extends Fragment {

    ArrayList<DoctorInfoModel> InfoModels = new ArrayList<>();
    int[] doctorimg = {R.drawable.doc1, R.drawable.doc2, R.drawable.doc3, R.drawable.doc2, R.drawable.doc1, R.drawable.doc3,
            R.drawable.doc1, R.drawable.doc2, R.drawable.doc3};
    LinearLayout linearLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context mContext;
    LinearLayout doctorPage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientHomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientHomeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientHomeFrag newInstance(String param1, String param2) {
        PatientHomeFrag fragment = new PatientHomeFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_patient_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.hrecycle_id);

        setDoctorInfoModels(new MyCallbackList() {
            @Override
            public void onCallback(ArrayList<DoctorInfoModel> doctors) {
                HorizontalAdapter adapter = new HorizontalAdapter(mContext, doctors, doctorimg, new RecyclerViewClickInterface2() {
                    @Override
                    public void onItemClick(int position, Doctor doctor, int image) {
                        Intent intent = new Intent(mContext, DoctorDetailsPL.class);
                        intent.putExtra("Name", doctors.get(position).getName());
//                        intent.putExtra("degree", doctor.getDegree());
//                        intent.putExtra("department", doctor.getDepartment());
//                        intent.putExtra("experience", doctor.getExperience());
//                        intent.putExtra("rating", doctor.getRating());
//                        intent.putExtra("image", String.valueOf(image) );
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

            }
        });

        doctorPage = root.findViewById(R.id.doctor_page);
        doctorPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DoctorDepartmentPL.class);
                startActivity(intent);
            }
        });

        LinearLayout notificationPage = root.findViewById(R.id.notification_page);
        notificationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Notifications.class);
                startActivity(intent);
            }
        });

        LinearLayout ambulancePage = root.findViewById(R.id.ambulance);
        ambulancePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Ambulance.class);
                startActivity(intent);
            }
        });

        LinearLayout bmi = root.findViewById(R.id.bmi);
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BMICalculator.class);
                startActivity(intent);
            }
        });

        LinearLayout weight = root.findViewById(R.id.weight);
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LineChart1.class);
                startActivity(intent);
            }
        });

        LinearLayout location = root.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Location.class);
                startActivity(intent);
            }
        });

        LinearLayout nursingCare = root.findViewById(R.id.nursing_care);
        nursingCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NursingCare.class);
                startActivity(intent);
            }
        });

        LinearLayout payment = root.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OnlinePayment.class);
                startActivity(intent);
            }
        });

        LinearLayout medicine = root.findViewById(R.id.medicine);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Medipedia.class);
                startActivity(intent);
            }
        });



        return root;
    }

    private void setDoctorInfoModels(MyCallbackList myCallbackList) {

        db.collection("search doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData().);
                                DoctorInfoModel doctorInfoModel = new DoctorInfoModel();
                                doctorInfoModel.setName(document.getData().get("name").toString());
                                doctorInfoModel.setDegree(document.getData().get("degree").toString());
                                doctorInfoModel.setWorkplace(document.getData().get("workplace").toString());
                                doctorInfoModel.setRating(document.getData().get("rating").toString());
                                doctorInfoModel.setDepartment(document.getData().get("department").toString());
                                doctorInfoModel.setImage(document.getData().get("image").toString());
                                InfoModels.add(doctorInfoModel);
                            }
                            myCallbackList.onCallback(InfoModels);
                            //Toast.makeText(getApplicationContext(), InfoModels.get(0).getDoctorName(),Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

