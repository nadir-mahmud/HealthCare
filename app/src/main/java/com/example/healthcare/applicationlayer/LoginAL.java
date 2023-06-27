package com.example.healthcare.applicationlayer;

import android.content.Context;

import com.example.healthcare.datalayer.LoginDL;

public class LoginAL {
    public void initiateLogin(String email, String password,String role, Context context){
        LoginDL loginDL = new LoginDL();
        loginDL.confirmLogin(email, password,role, context);
    }
}
