package com.example.healthcare.presentationlayer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.MyCallback;
import com.example.healthcare.applicationlayer.PatientProfileAL;
import com.example.healthcare.datalayer.PatientProfileDL;
import com.example.healthcare.entity.PatientProfile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.GeneralSecurityException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientProfilePL#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientProfilePL extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    TextInputEditText name, phoneNumber, email, dateofBirth, age;
    Spinner gender, bloodGroup;
    PatientProfile patientProfile = new PatientProfile();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public PatientProfilePL() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientProfilePL newInstance(String param1, String param2) {
        PatientProfilePL fragment = new PatientProfilePL();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.patient_profile, container, false);

        name = root.findViewById(R.id.profile_name);
        phoneNumber = root.findViewById(R.id.profile_phone_number);
        email = root.findViewById(R.id.profile_email);
        dateofBirth = root.findViewById(R.id.profile_date_of_birth);
        age = root.findViewById(R.id.profile_age);

        gender = root.findViewById(R.id.gender);
        bloodGroup = root.findViewById(R.id.blood_group);

        Button update = root.findViewById(R.id.profile_update);
        email.setText(user.getEmail());


        PatientProfileAL patientProfileAL = new PatientProfileAL();
        try {
            patientProfileAL.fetchData(new MyCallback() {
                @Override
                public void onCallback(PatientProfile patientProfile1) {
                    PatientProfile patientProfile = patientProfile1;
                    name.setText(patientProfile.getName());
                    phoneNumber.setText(patientProfile.getPhoneNumber());
                    email.setText(patientProfile.getEmail());
                    dateofBirth.setText(patientProfile.getDateofBirth());
                    age.setText(patientProfile.getAge());
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                            R.array.spinnerItems,
                            android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    gender.setAdapter(adapter);
                    int genderSpinnerPosition = adapter.getPosition(patientProfile.getGender());
                    gender.setSelection(genderSpinnerPosition);

                    ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mContext,
                            R.array.blood_group,
                            android.R.layout.simple_spinner_item);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Toast.makeText(mContext , patientProfile.getBloodGroup() , Toast.LENGTH_SHORT).show();
                    bloodGroup.setAdapter(adapter1);
                    int bloodGroupSpinnerPosition = adapter1.getPosition(patientProfile.getBloodGroup());

                    bloodGroup.setSelection(bloodGroupSpinnerPosition);

                    //Toast.makeText(mContext , patientProfile.getPhoneNumber() , Toast.LENGTH_SHORT).show();
                }
            },mContext);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


        //Toast.makeText(mContext, patientProfile.getPhoneNumber() , Toast.LENGTH_SHORT).show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientProfile patientProfile = new PatientProfile();
                patientProfile.setName(name.getText().toString().trim());
                patientProfile.setPhoneNumber(phoneNumber.getText().toString().trim());
                patientProfile.setEmail(email.getText().toString().trim());
                patientProfile.setDateofBirth(dateofBirth.getText().toString().trim());
                patientProfile.setAge(age.getText().toString().trim());
                patientProfile.setGender(gender.getSelectedItem().toString().trim());
                patientProfile.setBloodGroup(bloodGroup.getSelectedItem().toString().trim());

                PatientProfileAL patientProfileAL = new PatientProfileAL();
                try {

                    patientProfileAL.authenticatePatientProfile(patientProfile, mContext,user);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

            }
        });

        return root;

    }

    public void onClickButton(){


    }
}