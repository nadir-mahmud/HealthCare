package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthcare.R;

public class BMICalculator extends AppCompatActivity {

    EditText weight,height;
    Double h,w;
    String height1,weight1;
    TextView result;
    static  final String TAG="ListDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);
        height=findViewById(R.id.height);
        height1=height.getText().toString();
        weight=findViewById(R.id.weight);
        weight1=weight.getText().toString();
        result=findViewById(R.id.bmi_result_TextView);
        findViewById(R.id.calculateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                height1=height.getText().toString();
                weight=findViewById(R.id.weight);
                weight1=weight.getText().toString();
                if(height.getText().toString().equals("")|| weight.getText().toString().equals(""))
                {
                    result.setText("Please Enter Height and Weight");

                }
                else
                {
                    h=Double.parseDouble(height1);
                    w=Double.parseDouble(weight1);
                    double r;
                    r=w/(h*h);
                    result.setText("Your BMI: "+  String.format("%.2f", r));

                }
            }
        });
    }

}