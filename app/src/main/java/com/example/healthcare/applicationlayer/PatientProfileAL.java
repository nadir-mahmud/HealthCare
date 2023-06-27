package com.example.healthcare.applicationlayer;

import android.content.Context;

import com.example.healthcare.datalayer.PatientProfileDL;
import com.example.healthcare.entity.PatientProfile;
import com.google.firebase.auth.FirebaseUser;

import java.security.GeneralSecurityException;

public class PatientProfileAL {
    PatientProfile patientProfile1 = new PatientProfile();

    public void authenticatePatientProfile(PatientProfile patientProfile, Context context, FirebaseUser user) throws GeneralSecurityException {
        PatientProfileDL profileDL = new PatientProfileDL();
        profileDL.insertPatientInfo(patientProfile, context);
    }
    public void fetchData(MyCallback myCallback ,Context context) throws GeneralSecurityException {
        PatientProfileDL profileDL = new PatientProfileDL();
        profileDL.retrieveData(myCallback,context);
        //Toast.makeText(context, patientProfile1.getPhoneNumber().toString() , Toast.LENGTH_SHORT).show();

    }
}
