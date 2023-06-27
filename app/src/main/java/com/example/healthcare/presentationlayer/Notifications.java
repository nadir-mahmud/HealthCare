package com.example.healthcare.presentationlayer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.applicationlayer.MyOwnAdapter;
import com.example.healthcare.applicationlayer.NotificationAdapter;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.entity.Account;
import com.example.healthcare.entity.Notification;
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

public class Notifications extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Notification> notifications;

    NotificationAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notifications = new ArrayList<>();



        Toolbar toolbar = findViewById(R.id.toolbar_account_list);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getNotificationListInfo();

    }

    private void getNotificationListInfo() {
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
                                        Notification notification = new Notification();
                                        notification.setDoctorName(encryption.decryptData(document.getData().get("doctor name").toString()));
                                        notification.setDay(encryption.decryptData(document.getData().get("day").toString()));
                                        notification.setNotificationTime(encryption.decryptData(document.getData().get("booking time").toString()));
                                        //Toast.makeText(getApplicationContext(), encryption.decryptData(document.getData().get("doctor name").toString()),Toast.LENGTH_SHORT).show();
                                        notifications.add(notification);
                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    }


                                }
                                adapter = new NotificationAdapter(getApplicationContext(), notifications, new RecyclerViewClickInterface() {
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