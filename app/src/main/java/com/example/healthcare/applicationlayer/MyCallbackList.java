package com.example.healthcare.applicationlayer;

import com.example.healthcare.entity.DoctorInfoModel;

import java.util.ArrayList;

public interface MyCallbackList {
    void onCallback(ArrayList<DoctorInfoModel> doctors);
}
