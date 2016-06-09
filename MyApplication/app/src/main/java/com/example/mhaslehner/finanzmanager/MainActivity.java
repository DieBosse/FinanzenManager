package com.example.mhaslehner.finanzmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    private static DataBaseHelperEinnahmen dataBaseHelperEinnahmen;
    private static DataBaseHelperAusgaben dataBaseHelperAusgaben;
    private static DataBaseHelperKategorien dataBaseHelperKategorien;

    private static SQLiteDatabase einnahmenDB;
    private static SQLiteDatabase ausgabenDB;
    private static SQLiteDatabase kategorienDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelperEinnahmen = new DataBaseHelperEinnahmen(this);
        dataBaseHelperAusgaben = new DataBaseHelperAusgaben(this);
        dataBaseHelperKategorien = new DataBaseHelperKategorien(this);

        einnahmenDB = dataBaseHelperEinnahmen.getReadableDatabase();
        ausgabenDB = dataBaseHelperAusgaben.getReadableDatabase();
        kategorienDB = dataBaseHelperKategorien.getReadableDatabase();

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
