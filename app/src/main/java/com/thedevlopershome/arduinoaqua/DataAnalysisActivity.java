package com.thedevlopershome.arduinoaqua;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DataAnalysisActivity extends AppCompatActivity {

    LineChart chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);

          chart = (LineChart) findViewById(R.id.chart);

        new myAsyncTask(this, new myAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(List<DetailsData> output) {
                List<Entry> entries = new ArrayList<Entry>();
                int i=5,j=9;
                for (DetailsData data : output) {

                    // turn your data into Entry objects
                    entries.add(new Entry(i++, j++));
                }

                LineDataSet dataSet = new LineDataSet(entries, "TANK LEVEL"); // add entries to dataset
                dataSet.setColor(Color.BLUE);
                dataSet.setValueTextColor(Color.BLACK);


                LineData lineData = new LineData(dataSet);
                chart.setData(lineData);
                chart.invalidate();



            }
        },1).execute();







    }
}
