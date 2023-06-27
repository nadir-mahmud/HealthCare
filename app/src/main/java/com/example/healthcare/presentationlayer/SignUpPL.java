package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.healthcare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpPL extends AppCompatActivity {
    TextInputEditText email, password;
    Spinner role;
    Button signUpButton,accountList;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String emailString, passwordString, roleString;
    Boolean intentGmail;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_pl);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        intentGmail = false;

        email = findViewById(R.id.sign_up_email);
        password = findViewById(R.id.sign_up_password);
        role = findViewById(R.id.sign_up_spinner);
        signUpButton = findViewById(R.id.sign_up_button);
        accountList = findViewById(R.id.list_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailString = email.getText().toString().trim();
                passwordString = password.getText().toString().trim();
                roleString = role.getSelectedItem().toString().trim();


                String[] emailExtra = {emailString};
                String body = "Congratulations to you for joining our Healthcare community." + "\n" + "\n" +
                              "Here is your email and password: " + "\n" + "\n" +
                              "Email: " + emailString + "\n" +
                              "Password: " + passwordString + "\n" + "\n" +
                              "Stay with us. Thank you!";

                intent = new Intent(Intent.ACTION_SENDTO); // only email apps should handle this
                Uri uri = Uri.parse("mailto:");
                intent.setData(uri);
                intent.putExtra(Intent.EXTRA_EMAIL, emailExtra);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Email and password of Healthcare App");
                intent.putExtra(Intent.EXTRA_TEXT, body);

                signUp(emailString,passwordString);
//                accountList.setVisibility(View.VISIBLE);


            }
        });

        accountList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), AccountListPL.class);
                startActivity(intent1);
            }
        });

    }

    private void signUp(String emailString, String passwordString) {
        mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            //Log.d(TAG, "createUserWithEmail:success");
                            insertAccountInfo(emailString, passwordString, roleString);

                            FirebaseUser user = mAuth.getCurrentUser();
                            traverseActivity();
                            Toast.makeText(getApplicationContext(), "Successfully account created!", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Account creation failed!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void insertAccountInfo(String emailString, String passwordString, String roleString) {
        // Create a new user with a first and last name
        Map<String, Object> account = new HashMap<>();
        account.put("email", emailString);
        account.put("password", passwordString);
        account.put("role", roleString);

// Add a new document with a generated ID
        db.collection("accounts")
                .add(account)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public interface AgainTest{
        void callIntent();
    }


    public void traverseActivity() {
        test(new AgainTest() {
            @Override
            public void callIntent() {
                Intent intent1 = new Intent(getApplicationContext(), AccountListPL.class);
                startActivity(intent1);
            }
        });
    }
    private void test(AgainTest test) {
        test.callIntent();
    }
}