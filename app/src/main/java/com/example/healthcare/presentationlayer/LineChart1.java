package com.example.healthcare.presentationlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthcare.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineChart1 extends AppCompatActivity {

    EditText w1,w2,w3,w4,w5,w6,w7;
    static  final String TAG="ListDemo";
    LineChart mchart;
    ListView listD;
    Button addweightbtn;

    private static final String[] DAYS = { "Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6", "Week 7" };

    ArrayList<Entry> yvalues=new ArrayList<>();
    Float n1,n2,n3,n4,n5,n6,n7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        Toolbar toolbar = findViewById(R.id.toolbar_line);
        setTitle("Analyse Weight");
        setSupportActionBar(toolbar);

        mchart=findViewById(R.id.linechart);
        w1 = findViewById(R.id.weight_id1);
        w2 = findViewById(R.id.weight_id2);
        w3 = findViewById(R.id.weight_id3);
        w4 = findViewById(R.id.weight_id4);
        w5 = findViewById(R.id.weight_id5);
        w6 = findViewById(R.id.weight_id6);
        w7 = findViewById(R.id.weight_id7);
        Button addweightbtn = findViewById(R.id.addweight);
        Description description=new Description();
        description.setText("Week vs  Weight");
        description.setTextSize(15);
        description.setTextColor(Color.BLUE);
        mchart.setDescription(description);
        mchart.setDragEnabled(true);
        mchart.setScaleEnabled(false);

        mchart.setVisibility(View.GONE);
        addweightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((w1.getText().toString().equals("")) ||( w2.getText().toString().equals("")) ||(
                        w3.getText().toString().equals("")) ||(w4.getText().toString().equals("") )||(w5.getText().toString().equals("")) ||
                        (w6.getText().toString().equals("") )||(w7.getText().toString().equals("") )){
                    Toast.makeText(getApplicationContext(), "Please Enter Weight", Toast.LENGTH_SHORT).show();
                }
                else {

                yvalues.clear();
                    n1 = Float.parseFloat(w1.getText().toString());
                    n2 = Float.parseFloat(w2.getText().toString());
                    n3 = Float.parseFloat(w3.getText().toString());
                    n4 = Float.parseFloat(w4.getText().toString());
                    n5 = Float.parseFloat(w5.getText().toString());
                    n6 = Float.parseFloat(w6.getText().toString());
                    n7 = Float.parseFloat(w7.getText().toString());

                    yvalues.add(new Entry(0, n1));
                    yvalues.add(new Entry(1, n2));
                    yvalues.add(new Entry(2, n3));
                    yvalues.add(new Entry(3, n4));
                    yvalues.add(new Entry(4, n5));
                    yvalues.add(new Entry(5, n6));
                    yvalues.add(new Entry(6, n7));
                    mchart.setVisibility(View.VISIBLE);
                    LineDataSet set1 = new LineDataSet(yvalues, "Dataset 1");
                    set1.setFillAlpha(110);
                    set1.setValueTextSize(12f);
                    set1.setValueTextColor(Color.BLUE);
                    set1.setColor(Color.RED);
                    set1.getValueTextSize();
                    set1.setCircleColor(Color.BLUE);
                    set1.setLineWidth(3f);
                    set1.setLabel("Week vs Weight");
                    ArrayList<ILineDataSet> datasets = new ArrayList<>();
                    datasets.add(set1);
                    LineData lineData1 = new LineData(datasets);
                    mchart.setData(lineData1);
                    configureChartAppearance();

                }
            }
        });
    }
    private void configureChartAppearance() {
        mchart.getDescription().setEnabled(true);
        XAxis xAxis=mchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });
    }}