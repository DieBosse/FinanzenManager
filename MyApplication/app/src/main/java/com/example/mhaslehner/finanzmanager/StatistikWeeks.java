package com.example.mhaslehner.finanzmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatistikWeeks extends AppCompatActivity {

    TextView woche1;
    TextView woche2;
    TextView woche3;
    TextView woche4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_weeks);
        woche1 = (TextView) findViewById(R.id.textViewW1);
        woche2 = (TextView) findViewById(R.id.textViewW2);
        woche3 = (TextView) findViewById(R.id.textViewW3);
        woche4 = (TextView) findViewById(R.id.textViewW4);

        Intent i = getIntent();
        double[] weeks = i.getDoubleArrayExtra("array");
        woche1.setText(weeks[0]+" €");
        woche2.setText(weeks[1]+" €");
        woche3.setText(weeks[2]+" €");
        woche4.setText(weeks[3]+" €");
    }
}
