package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.SerialCallback;
import com.example.healthcare.entity.PatientProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppointmentPL extends AppCompatActivity {
    TextView doctorName, doctorEducation, doctorDepartment, doctorExperience, doctorRatingValue, doctorWorkplace;
    TextView patientName, patientMobileNumber, patientGender, patientAge;
    Button confirm;
    Spinner appointmentTime;
    RatingBar doctorRating;
    ImageView doctorImageView;
    FirebaseFirestore db;
    String nameString, appointmentString, degreeString, experienceString, ratingString, departmentString, imageString;
    int serial = 0;
    Intent intent;
    FirebaseUser user;
    Map<String,String> history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_pl);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        doctorName = findViewById(R.id.doctor_name_id);
        doctorEducation = findViewById(R.id.doctor_degree_id);
        doctorDepartment = findViewById(R.id.service_area_id);
        doctorExperience = findViewById(R.id.experience_id);
        doctorRatingValue = findViewById(R.id.ratingValue_id);
        doctorWorkplace = findViewById(R.id.workplace_id);

        intent = getIntent();
        nameString = intent.getStringExtra("name");
        degreeString = intent.getStringExtra("degree");
        departmentString = intent.getStringExtra("department");
        experienceString = intent.getStringExtra("experience");
        ratingString = intent.getStringExtra("rating");
        imageString = intent.getStringExtra("image");




        patientName = findViewById(R.id.patient_name);
        patientMobileNumber = findViewById(R.id.patient_mobile_number);
        patientGender = findViewById(R.id.patient_gender);
        patientAge = findViewById(R.id.patient_age);

        doctorRating = findViewById(R.id.ratingBar);
        doctorImageView = findViewById(R.id.doctor_profile_img);

//        doctorName.setText(nameString);
//        doctorEducation.setText(degreeString);
//        doctorDepartment.setText(departmentString);
//        doctorRatingValue.setText(ratingString);
//        doctorImageView.setImageResource(Integer.parseInt(imageString));
//        doctorRating.setRating(Float.parseFloat(ratingString));

        appointmentTime = findViewById(R.id.appointment_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.doctor_schedule,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentTime.setAdapter(adapter);


        intent = getIntent();
        nameString = intent.getStringExtra("name");

        setDoctorInfo();
        setPatientInfo();

        confirm = findViewById(R.id.confirm_btn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appointmentString = appointmentTime.getSelectedItem().toString();

                //Toast.makeText(getApplicationContext(), appointmentString , Toast.LENGTH_SHORT).show();
                getAppointedPatient();
                //getAppointmentNotification();

            }
        });


    }

    private void setDoctorInfo() {
        Encryption encryption = new Encryption();

        try {
            db.collection("doctors")
                    .whereEqualTo("name", encryption.encryptData(nameString))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    try {
                                        doctorName.setText(encryption.decryptData(document.getData().get("name").toString()));
                                        doctorEducation.setText(encryption.decryptData(document.getData().get("degree").toString()));
                                        doctorDepartment.setText(encryption.decryptData(document.getData().get("department").toString()));
                                        String experience = encryption.decryptData(document.getData().get("experience").toString());
                                        doctorExperience.setText("Experience: " + experience + " ");
                                        String ratingString = encryption.decryptData(document.getData().get("rating").toString());
                                        doctorRatingValue.setText("(" + ratingString + ")");
                                        doctorRating.setRating(Float.parseFloat(ratingString));
                                        doctorWorkplace.setText(encryption.decryptData(document.getData().get("workplace").toString()));
                                        String image = encryption.decryptData(document.getData().get("image").toString());
                                        doctorImageView.setImageResource(Integer.parseInt(image));
                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    }


                                }
                            } else {

                                Toast.makeText(getApplicationContext(), "Retrieved nothing, try again!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    private void setPatientInfo() {
        Encryption encryption = new Encryption();
        try {
            db.collection("patients")
                    .whereEqualTo("UID", encryption.encryptData(user.getUid()))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    try {

                                        patientName.setText(encryption.decryptData(document.getData().get("name").toString()));
                                        String name = encryption.decryptData(document.getData().get("name").toString());
                                        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                                        patientMobileNumber.setText(encryption.decryptData(document.getData().get("phone number").toString()));
                                        patientGender.setText(encryption.decryptData(document.getData().get("gender").toString()));
                                        patientAge.setText(encryption.decryptData(document.getData().get("age").toString()));

                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    }


                                }
                            } else {

                                Toast.makeText(getApplicationContext(), "Retrieved nothing, try again!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }



    private void getAppointmentInfo() {
        Encryption encryption = new Encryption();

        DocumentReference docRef = db.collection("appointment count").document(doctorName.getText().toString() + " " + appointmentString);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        try {




                            String serialStr = encryption.decryptData(document.getData().get("serial").toString());
                            serial = Integer.parseInt(serialStr);
                            serial++;

                            history = new HashMap<>();
                            history.put("doctor name", encryption.encryptData(doctorName.getText().toString()));
                            history.put("day", encryption.encryptData(appointmentString));


                            if( serial <= 20 ){
                                String serialAgain = String.valueOf(serial);

                                db.collection("appointed patient").document(patientName.getText().toString() + " " + appointmentString + " " + doctorName.getText().toString())
                                        .update("serial", encryption.encryptData(serialAgain));

                                //Toast.makeText(getApplicationContext(), serialAgain , Toast.LENGTH_SHORT).show();
                                history.put("serial", encryption.encryptData(serialAgain));

                                db.collection("appointment count").document(doctorName.getText().toString() + " " + appointmentString)
                                        .set(history)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Log.d(TAG, "DocumentSnapshot successfully written!");
                                                Toast.makeText(getApplicationContext(), "Appointment booked successfully", Toast.LENGTH_SHORT).show();
                                                getAppointmentNotification();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Log.w(TAG, "Error writing document", e);
                                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "No slot left", Toast.LENGTH_SHORT).show();
                            }


// Add a new document with a generated ID

                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Log.d(TAG, "No such document");
                        //Document didn't found
                        serial = 1;

                        try {
                            db.collection("appointed patient").document(patientName.getText().toString() + " " + appointmentString + " " + doctorName.getText().toString())
                                    .update("serial", encryption.encryptData(String.valueOf(serial)));
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }

                        history = new HashMap<>();
                        //Toast.makeText(getApplicationContext(), String.valueOf(serial), Toast.LENGTH_SHORT).show();
                        try {

                            history.put("doctor name", encryption.encryptData(doctorName.getText().toString()));
                            history.put("day", encryption.encryptData(appointmentString));
                            String serialSt = String.valueOf(serial);
                            history.put("serial", encryption.encryptData(serialSt));

                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }



// Add a new document with a generated ID
                        db.collection("appointment count").document(doctorName.getText().toString() + " " + appointmentString)
                                .set(history)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Log.d(TAG, "DocumentSnapshot successfully written!");
                                        Toast.makeText(getApplicationContext(), "Appointment booked successfully", Toast.LENGTH_SHORT).show();
                                        getAppointmentNotification();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Log.w(TAG, "Error writing document", e);
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    private void setAppointedPatient(){
        Encryption encryption = new Encryption();

        Map<String, String> appointedPatient = new HashMap<>();
        try {
            appointedPatient.put("patient uid", encryption.encryptData(user.getUid()) );
            appointedPatient.put("patient name", encryption.encryptData(patientName.getText().toString()) );
            appointedPatient.put("phone number", encryption.encryptData(patientMobileNumber.getText().toString()) );
            appointedPatient.put("doctor name", encryption.encryptData(doctorName.getText().toString()));
            appointedPatient.put("day", encryption.encryptData(appointmentString));
            appointedPatient.put("doctor degree", encryption.encryptData(doctorEducation.getText().toString()) );
            appointedPatient.put("doctor experience", encryption.encryptData(doctorExperience.getText().toString()) );
            appointedPatient.put("doctor rating", encryption.encryptData(doctorRatingValue.getText().toString()));
            appointedPatient.put("doctor image", encryption.encryptData(String.valueOf(doctorImageView.getId())));
            appointedPatient.put("doctor department", encryption.encryptData(doctorDepartment.getText().toString()));
            if(serial <= 20){
                appointedPatient.put("serial", encryption.encryptData(String.valueOf(serial)));
            }

            Date cal = Calendar.getInstance().getTime();
            String calString = DateFormat.getDateTimeInstance().format(cal);
            appointedPatient.put("booking time", encryption.encryptData(calString));

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }



// Add a new document with a generated ID
        db.collection("appointed patient").document(patientName.getText().toString() + " " + appointmentString + " " + doctorName.getText().toString())
                .set(appointedPatient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully written!");
                        //Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error writing document", e);
                        //Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getAppointedPatient(){
        DocumentReference docRef = db.collection("appointed patient").document(patientName.getText().toString() + " " + appointmentString + " " + doctorName.getText().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Toast.makeText(getApplicationContext(), "You have already booked an appointment!", Toast.LENGTH_SHORT).show();

                    } else {
                        //Log.d(TAG, "No such document");
                        setAppointedPatient();
                        getAppointmentInfo();
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    private void getAppointmentNotification(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //The code here will run after 5 sec, do what you want
                NotificationManager mNotificationManager;

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext(), "notify_001");


                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.bigText("You have an appointment with "+ doctorName.getText().toString() + " on " + appointmentString + "!");
                bigText.setBigContentTitle("Healthcare");
                bigText.setSummaryText("Text in detail");


                mBuilder.setSmallIcon(R.drawable.medical_equipment);
                mBuilder.setContentTitle("Healthcare");
                mBuilder.setContentText("You have an appointment with "+ doctorName.getText().toString() + " on " + appointmentString + "!");
                //mBuilder.setSubText(" ");
                mBuilder.setPriority(Notification.PRIORITY_MAX);
//                mBuilder.setStyle(bigText);

                mNotificationManager =
                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    String channelId = "Your_channel_id";
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(channel);
                    mBuilder.setChannelId(channelId);
                }

                mNotificationManager.notify(0, mBuilder.build());
//

            }
        }, 5*1000);
    }
}






