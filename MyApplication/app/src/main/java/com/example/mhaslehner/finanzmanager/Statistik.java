package com.example.mhaslehner.finanzmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by HP on 20.06.2016.
 */
public class Statistik extends AppCompatActivity {
    private static Button kategorienall;
    private static Button monateall;
    private static TextView kategorienVerbrauch;
    private static TextView monatVerbrauch;
    private static TextView teuersteKategorie;
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
        teuersteKategorie = (TextView) findViewById(R.id.textViewTeuersteKategorien);

        kategorienall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), StatistikAll.class).putExtra("button", 1));

            }
        });
        monateall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StatistikAll.class).putExtra("button", 2));
            }
        });
        getMonthMoney();
        getKategorieCnt();
        getTeuersteKategorie();
    }

    private void getKategorieCnt() {
        Cursor cursor = finanzenDB.rawQuery("SELECT " + Constants.KATEGORIENAME +
                ", MAX(" + Constants.KATEGORIE_AUSGABEN_CNT + ") FROM " +
                Constants.TBLNAME_K + " GROUP BY (" + Constants.KATEGORIENAME + ")", null);
        if (cursor.moveToFirst()) {
            kategorienVerbrauch.setText(cursor.getString(cursor.getColumnIndex(Constants.KATEGORIENAME)));
        }
        cursor.close();
    }

    private void getTeuersteKategorie() {

        Cursor cursor2 = finanzenDB.rawQuery("SELECT " + Constants.KATEGORIENAME +
                ", MAX(" + Constants.BETRAG + "), " + Constants.BETRAG + " FROM " +
                Constants.TBLNAME_K, null);
        Toast.makeText(getApplicationContext(),""+ cursor2.getColumnName(2),Toast.LENGTH_SHORT).show();

        if (cursor2.moveToFirst()) {
            teuersteKategorie.setText(cursor2.getString(cursor2.getColumnIndex(Constants.KATEGORIENAME))
                    + " (" + cursor2.getString(cursor2.getColumnIndex(Constants.BETRAG)) + "€)");

        }
        cursor2.close();
    }

    private void getMonthMoney() {
        GregorianCalendar calendarAktuell = new GregorianCalendar();
        Date aktuellesDatum = new Date();
        calendarAktuell.setTime(aktuellesDatum);
        int month = calendarAktuell.get(Calendar.MONTH) + 1;
        int year = calendarAktuell.get(Calendar.YEAR);
        int maxday = calendarAktuell.getActualMaximum(Calendar.DAY_OF_MONTH);
        String dateMax = maxday + "." + month + "." + year;
        String dateMin = 1 + "." + month + "." + year;

        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_A +
                " WHERE " + Constants.DATUM + " BETWEEN '" + dateMin + "' AND '" + dateMax + "'", null);
        if (cursor.moveToFirst()) {
           // Toast.makeText(getApplicationContext(), "" + cursor.getCount(), Toast.LENGTH_SHORT).show();
        }
    }


}
