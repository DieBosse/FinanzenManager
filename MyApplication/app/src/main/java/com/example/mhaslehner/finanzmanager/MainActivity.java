package com.example.mhaslehner.finanzmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.smiley_gruen);

    }

    public void addAusgabe(View view) {
        Intent i = new Intent(getApplicationContext(), AddAusgabe.class);
        startActivity(i);
    }

    public void addEinnahme(View view) {
        Intent i = new Intent(getApplicationContext(), AddEinnahme.class);
        startActivity(i);
    }

    public void kategorieStatistik(View view) {
        Intent i = new Intent(getApplicationContext(), KategorieStatistik.class);
        startActivity(i);
    }

    public void wochenStatistik(View view) {
        Intent i = new Intent(getApplicationContext(), WochenStatistik.class);
        startActivity(i);
    }

    public void verbesserungen(View view) {
        Intent i = new Intent(getApplicationContext(), Verbesserungen.class);
        startActivity(i);
    }
}
