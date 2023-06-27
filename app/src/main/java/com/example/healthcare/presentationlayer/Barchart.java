package com.example.healthcare.presentationlayer;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.healthcare.R;
import com.example.healthcare.applicationlayer.Encryption;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Barchart extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int aPos = 0;
    int aNeg = 0;
    int bPos = 0;
    int bNeg = 0;
    int abPos = 0;
    int abNeg = 0;
    int oPos = 0;
    int oNeg = 0;

    BarChart barChart;
    private static final String[] DAYS = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = findViewById(R.id.barchart);

        drawBarchartStartInitialization();
        getBloodData();
        //Toast.makeText(getApplicationContext(),String.valueOf(aPos), Toast.LENGTH_SHORT).show();



    }

    private void drawBarchart() {
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(0,aPos));
        barEntries.add(new BarEntry(1,aNeg));
        barEntries.add(new BarEntry(2,bPos));
        barEntries.add(new BarEntry(3,bNeg));
        barEntries.add(new BarEntry(4,abPos));
        barEntries.add(new BarEntry(5,abNeg));
        barEntries.add(new BarEntry(6,oPos));
        barEntries.add(new BarEntry(7,oNeg));

        BarDataSet set=new BarDataSet(barEntries,"DataSet");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);
        set.getLabel();
        BarData data=new BarData(set);
        barChart.setData(data);
        configureChartAppearance();
        barChart.invalidate();
        barChart.animateY(700);
    }

    private void configureChartAppearance() {
        barChart.getDescription().setEnabled(false);
        YAxis yAxis = barChart.getAxis(YAxis.AxisDependency.LEFT);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setValueFormatter(new ValueFormatter() {
        });
        XAxis xAxis=barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });
    }
    private void getBloodData(){
        Encryption encryption = new Encryption();
        db.collection("patients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData().);
                                try {
                                    String bloodGroup = encryption.decryptData(document.getData().get("blood group").toString());
                                    //Toast.makeText(getApplicationContext(), "In", Toast.LENGTH_SHORT).show();
                                    if(bloodGroup.equals("A+")){
                                        aPos++;
                                        //Toast.makeText(getApplicationContext(),String.valueOf(aPos), Toast.LENGTH_SHORT).show();
                                    }
                                    if(bloodGroup.equals("A-")){
                                        aNeg++;
                                    }
                                    if(bloodGroup.equals("B+")){
                                        bPos++;
                                    }
                                    if(bloodGroup.equals("B-")){
                                        bNeg++;
                                    }
                                    if(bloodGroup.equals("AB+")){
                                        abPos++;
                                    }
                                    if(bloodGroup.equals("AB-")){
                                        abNeg++;
                                    }
                                    if(bloodGroup.equals("O+")){
                                        oPos++;
                                    }
                                    if(bloodGroup.equals("O-")){
                                        oNeg++;
                                    }
                                } catch (GeneralSecurityException e) {
                                    e.printStackTrace();
                                }
                            }
                            drawBarchart();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });


    }

    private void drawBarchartStartInitialization() {
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(0,0));
        barEntries.add(new BarEntry(1,0));
        barEntries.add(new BarEntry(2,0));
        barEntries.add(new BarEntry(3,0));
        barEntries.add(new BarEntry(4,0));
        barEntries.add(new BarEntry(5,0));
        barEntries.add(new BarEntry(6,0));
        barEntries.add(new BarEntry(7,0));

        BarDataSet set=new BarDataSet(barEntries,"DataSet");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);
        set.getLabel();
        BarData data=new BarData(set);
        barChart.setData(data);
        configureChartAppearance();
        barChart.invalidate();
        barChart.animateY(500);
    }

}