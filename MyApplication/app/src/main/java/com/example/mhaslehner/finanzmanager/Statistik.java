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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by HP on 20.06.2016.
 */
public class Statistik extends AppCompatActivity {
    private static Button kategorienall;
    private static Button monateall;
    private static Button weeksall;
    private static TextView kategorienVerbrauch;
    private static TextView monatVerbrauch;
    private static TextView teuersteKategorie;
    private static TextView actualWeekView;
    private static DataBaseOpenHelperFinanzen dataBaseOpenHelperFinanzen;
    private static SQLiteDatabase finanzenDB;
    double[] weeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);

        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);
        finanzenDB = dataBaseOpenHelperFinanzen.getWritableDatabase();
        kategorienall = (Button) findViewById(R.id.buttonAllKategorien);
        monateall = (Button) findViewById(R.id.buttonStatistikAlleMonate);
        kategorienVerbrauch = (TextView) findViewById(R.id.textViewStatistikKategorie);
        monatVerbrauch = (TextView) findViewById(R.id.textViewStatistikMonat);
        teuersteKategorie = (TextView) findViewById(R.id.textViewTeuersteKategorien);
        actualWeekView = (TextView) findViewById(R.id.textViewActualWeek);
        weeksall = (Button) findViewById(R.id.buttonWeek);


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

        weeksall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StatistikWeeks.class).putExtra("button", 3).putExtra("array", weeks));
            }
        });

        getKategorieCnt();
        getMonthMoney2();
        getTeuersteKategorie();
        getWeekMoney();
    }

    private void getKategorieCnt() {
        Cursor cursor = finanzenDB.rawQuery("SELECT " + Constants.KATEGORIENAME +
                ", MAX(" + Constants.KATEGORIE_AUSGABEN_CNT + "), " + Constants.KATEGORIE_AUSGABEN_CNT + " FROM " +
                Constants.TBLNAME_K, null);

        if (cursor.moveToFirst()) {
            kategorienVerbrauch.setText(cursor.getString(cursor.getColumnIndex(Constants.KATEGORIENAME))
                    + "\nAnzahl: " + cursor.getString(cursor.getColumnIndex(Constants.KATEGORIE_AUSGABEN_CNT)));
        }
        cursor.close();
    }

    private void getTeuersteKategorie() {

        Cursor cursor2 = finanzenDB.rawQuery("SELECT " + Constants.KATEGORIENAME +
                ", MAX(" + Constants.BETRAG + "), " + Constants.BETRAG + " FROM " +
                Constants.TBLNAME_K, null);

        if (cursor2.moveToFirst()) {
            teuersteKategorie.setText(cursor2.getString(cursor2.getColumnIndex(Constants.KATEGORIENAME))
                    + " (" + cursor2.getString(cursor2.getColumnIndex(Constants.BETRAG)) + "€)");

        }
        cursor2.close();
    }


    private void getMonthMoney2() {
        double ausgabencounter = 0.0;
        GregorianCalendar calAktuell = new GregorianCalendar();
        GregorianCalendar calAusgabe = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        calAktuell.setTime(date); //aktuelles Datum im Kalender
        Cursor c = finanzenDB.query(Constants.TBLNAME_A, new String[]{Constants.BETRAG, Constants.DATUM}, null, null, null, null, null);
        while (c.moveToNext()) {
            Date d = null;
            try {
                d = sdf.parse(c.getString(c.getColumnIndex(Constants.DATUM)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (d != null) {
                calAusgabe.setTime(d);
            } else {
                Toast.makeText(getApplicationContext(), "Fehler bei Datumsumwandlung!", Toast.LENGTH_SHORT).show();
            }

            if (calAktuell.get(Calendar.MONTH) == calAusgabe.get(Calendar.MONTH) && calAktuell.get(Calendar.YEAR) == calAusgabe.get(Calendar.YEAR)) {
                ausgabencounter += c.getDouble(c.getColumnIndex(Constants.BETRAG));
            }

        }

        monatVerbrauch.setText(ausgabencounter + " €");
    }

    private void getWeekMoney()
    {
        weeks = new double[4];
        double actualweek = 0;
        GregorianCalendar calAktuell = new GregorianCalendar();
        GregorianCalendar calAusgabe = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        calAktuell.setTime(date); //aktuelles Datum im Kalender
        Cursor c = finanzenDB.query(Constants.TBLNAME_A,new String[]{Constants.BETRAG,Constants.DATUM},null,null,null,null,null);
        while (c.moveToNext())
        {
            Date d=null;
            try {
                d = sdf.parse(c.getString(c.getColumnIndex(Constants.DATUM)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(d!=null)
            {
                calAusgabe.setTime(d);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Fehler bei Datumsumwandlung!",Toast.LENGTH_SHORT).show();
            }

            if(calAktuell.get(Calendar.MONTH) == calAusgabe.get(Calendar.MONTH) && calAktuell.get(Calendar.YEAR) == calAusgabe.get(Calendar.YEAR))
            {
                if(calAusgabe.get(Calendar.WEEK_OF_MONTH) == 1)
                {
                    weeks[0]+=c.getDouble(c.getColumnIndex(Constants.BETRAG));
                }
                if(calAusgabe.get(Calendar.WEEK_OF_MONTH) == 2)
                {
                    weeks[1]+=c.getDouble(c.getColumnIndex(Constants.BETRAG));
                }
                if(calAusgabe.get(Calendar.WEEK_OF_MONTH) == 3)
                {
                    weeks[2]+=c.getDouble(c.getColumnIndex(Constants.BETRAG));
                }
                if(calAusgabe.get(Calendar.WEEK_OF_MONTH) == 4)
                {
                    weeks[3]+=c.getDouble(c.getColumnIndex(Constants.BETRAG));
                }
                if(calAusgabe.get(Calendar.WEEK_OF_MONTH) == calAktuell.get(Calendar.WEEK_OF_MONTH))
                {
                    actualweek+= c.getDouble(c.getColumnIndex(Constants.BETRAG));
                }

            }




        }

        actualWeekView.setText(actualweek+" €");

        //monatVerbrauch.setText(ausgabencounter+" €");
    }

}
