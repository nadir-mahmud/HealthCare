package com.example.healthcare.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.LoginAL;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPL extends AppCompatActivity {

    Button button , forgetButton;
    TextView roleTV;
    TextInputEditText email;
    TextInputEditText password;
    Intent intentInfo;
    String role, emailText, passwordText,emailString;
    FirebaseAuth fa = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pl);

        button = findViewById(R.id.outlinedButton);
        forgetButton = findViewById(R.id.forget_pass_btn);
        roleTV = findViewById(R.id.role);
        intentInfo = getIntent();
        role = intentInfo.getStringExtra("role");
        roleTV.setText(role);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        // Initialize Firebase Auth

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailText = email.getText().toString().trim();
                passwordText = password.getText().toString().trim();
                signIn(emailText , passwordText, role);
            }
        });

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // password.setVisibility(View.INVISIBLE);
                emailString = email.getText().toString().trim();

                if (TextUtils.isEmpty(emailString)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                fa.sendPasswordResetEmail(emailString)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });
    }

    private void signIn(String email,String password, String role){
        LoginAL loginAL = new LoginAL();
        loginAL.initiateLogin(email, password,role, getApplicationContext());
    }
}