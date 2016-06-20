package com.example.mhaslehner.finanzmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by HP on 20.06.2016.
 */
public class Statistik extends AppCompatActivity {
    Button kategorienall;
    Button monateall;
    TextView kategorienVerbrauch;
    TextView monatVerbrauch;
    private static DataBaseOpenHelperFinanzen dataBaseOpenHelperFinanzen;
    private static SQLiteDatabase finanzenDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);

        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);
        finanzenDB = dataBaseOpenHelperFinanzen.getWritableDatabase();
        kategorienall = (Button) findViewById(R.id.buttonStatistikAlleKategorien);
        monateall = (Button) findViewById(R.id.buttonStatistikAlleMonate);
        kategorienVerbrauch = (TextView) findViewById(R.id.textViewStatistikKategorie);
        monatVerbrauch = (TextView) findViewById(R.id.textViewStatistikMonat);

        kategorienall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        monateall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getMonthMoney();
        getKategorieMoney();
    }

    private void getKategorieMoney() {

    }

    private void getMonthMoney() {
        GregorianCalendar calendarAktuell = new GregorianCalendar();
        Date aktuellesDatum = new Date();
        calendarAktuell.setTime(aktuellesDatum);
        int month = calendarAktuell.get(Calendar.MONTH);
        
    }
}
