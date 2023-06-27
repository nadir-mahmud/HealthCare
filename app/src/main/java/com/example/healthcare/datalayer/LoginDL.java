package com.example.healthcare.datalayer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.applicationlayer.MyOwnAdapter;
import com.example.healthcare.applicationlayer.RecyclerViewClickInterface;
import com.example.healthcare.entity.Account;
import com.example.healthcare.presentationlayer.AccountListPL;
import com.example.healthcare.presentationlayer.DoctorHome;
import com.example.healthcare.presentationlayer.DoctorProfilePL;
import com.example.healthcare.presentationlayer.LoginPL;
import com.example.healthcare.presentationlayer.NavDrawerPL;
import com.example.healthcare.presentationlayer.NurseHome;
import com.example.healthcare.presentationlayer.SignUpPL;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginDL extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Boolean traverse = true;
    String role;

    public void confirmLogin(String email, String password,String role, Context context){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(context, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();

                            traverseIntent(user,context);




                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void traverseIntent(FirebaseUser user,Context context){

        db.collection("accounts")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                role = document.getData().get("role").toString();

                                    if(role.equals("Patient")){

                                        Intent intent = new Intent(context, NavDrawerPL.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                    }

                                    if(role.equals("Doctor")){

                                        Intent intent = new Intent(context, DoctorHome.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                    }

                                if(role.equals("Nurse")){

                                    Intent intent = new Intent(context, NurseHome.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }


                                if(role.equals("Admin")){
                                    Intent intent = new Intent(context, AccountListPL.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }

                            }
                            //Toast.makeText(getApplicationContext(), accounts.get(0).getEmail(),Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());


                        }
                        //Toast.makeText(getApplicationContext(),accounts.get(1).getEmail(),Toast.LENGTH_SHORT).show();

                    }
                });

    }
    private void getAdminIntent(FirebaseUser user, Context context){

    }
}
