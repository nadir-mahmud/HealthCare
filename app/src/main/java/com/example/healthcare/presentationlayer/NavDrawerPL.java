package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.BlankFragment2;
import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.example.healthcare.entity.PatientProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;

public class NavDrawerPL extends AppCompatActivity {

    NavigationView nav;
    BottomNavigationView bottomNavigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Intent roleIntent;
    TextView navName,navNumber,navUserId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_pl);

        navHeaderData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav = findViewById(R.id.nav_menu);
        drawerLayout = findViewById(R.id.nav_drawer);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar , R.string.open, R.string.close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container2, new PatientHomeFrag()).commit();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp = new PatientHomeFrag();
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_myProfile:
                        temp = new PatientProfilePL();
                        break;
                    case R.id.nav_about:
                        temp = new BlankFragment2();
                        break;
                    case R.id.nav_logout:
                        roleIntent = getIntent();
                        String role = roleIntent.getStringExtra("role");
                        Intent intent = new Intent(NavDrawerPL.this , LoginPL.class);
                        intent.putExtra("role", role);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container2, temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        temp = new PatientHomeFrag();
                        break;
                    case R.id.nav_search:
                        temp = new SearchDoctor();
                        break;
                    case R.id.nav_history:
                        temp = new History();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container2, temp).commit();

                return true;
            }
        });



    }

    private void navHeaderData(){
        NavigationView navigationView = findViewById(R.id.nav_menu);
        View root = navigationView.inflateHeaderView(R.layout.nav_header);

        navName = root.findViewById(R.id.nav_header_name);
        navNumber = root.findViewById(R.id.nav_header_number);
        navUserId = root.findViewById(R.id.nav_header_user_id);

        Encryption encryption = new Encryption();
        PatientProfile patientProfile = new PatientProfile();
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
                                        navName.setText(encryption.decryptData(document.getData().get("name").toString()));
                                        navNumber.setText(encryption.decryptData(document.getData().get("phone number").toString()));
                                        navUserId.setText("User ID: " + encryption.decryptData(document.getData().get("name").toString()));

                                    } catch (GeneralSecurityException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                patientProfile.setName("nothing");
                                Toast.makeText(getApplicationContext(), "Retrieved nothing, try again!", Toast.LENGTH_SHORT).show();
                            }

                            //Toast.makeText(context , patientProfile.getPhoneNumber() , Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }
}




