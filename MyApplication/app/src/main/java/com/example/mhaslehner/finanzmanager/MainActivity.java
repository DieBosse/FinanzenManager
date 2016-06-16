package com.example.mhaslehner.finanzmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    ImageView img;

    private static DataBaseOpenHelperFinanzen dataBaseOpenHelperFinanzen;


    private static SQLiteDatabase finanzenDB;

    private static SharedPreferences prefs = null;
    private static SharedPreferences.OnSharedPreferenceChangeListener listener = null;
    private static TextView restlichesGeld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);
        finanzenDB = dataBaseOpenHelperFinanzen.getWritableDatabase();


        img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.smiley_gruen);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("verdienst")) {
                    restlichesGeldAnzeigen();
                }
            }
        };
        restlichesGeldAnzeigen();

    }

    @Override
    protected void onResume() {

        restlichesGeldAnzeigen();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.kategorienmenu) {
            startActivity(new Intent(this, KategorieVerwaltung.class));
        }
        if (id == R.id.verdienstmenu) {
            startActivity(new Intent(this, PreferenceActivity.class));
        }
        if (id == R.id.menuactivitys) {
            startActivity(new Intent(this, AktivitaetenAnzeigen.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAusgabe(final View view) {
        Intent i = new Intent(getApplicationContext(), AddAusgabe.class);
        startActivity(i);
    }

    public void addEinnahme(final View view) {
        Intent i = new Intent(getApplicationContext(), AddEinnahme.class);
        startActivity(i);
    }

    public void kategorieStatistik(final View view) {
        Intent i = new Intent(getApplicationContext(), KategorieStatistik.class);
        startActivity(i);
    }

    public void wochenStatistik(final View view) {
        Intent i = new Intent(getApplicationContext(), WochenStatistik.class);
        startActivity(i);
    }

    public void verbesserungen(final View view) {
        Intent i = new Intent(getApplicationContext(), Verbesserungen.class);
        startActivity(i);
    }

    public void restlichesGeldAnzeigen() {
        double ausgabenCounter = 0;
        double einnahmenCounter = 0;
        String prefsString = prefs.getString("verdienst", "");
        double verdienst = 0;
        if (!prefsString.equals("")) {
            verdienst = Double.parseDouble(prefsString);
        }


        Cursor ausgaben = finanzenDB.query(Constants.TBLNAME_A, new String[]{Constants.BETRAG, Constants.DATUM}, null, null, null, null, Constants._ID);

        while (ausgaben.moveToNext()) {

            String datum = ausgaben.getString(1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date ausgabenDatum = null;

            Date aktuellesDatum = new Date();
            try {
                ausgabenDatum = sdf.parse(datum);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GregorianCalendar calendarAusgabe = new GregorianCalendar();
            calendarAusgabe.setTime(ausgabenDatum);
            GregorianCalendar calendarAktuell = new GregorianCalendar();
            calendarAktuell.setTime(aktuellesDatum);

            if (calendarAktuell.get(Calendar.MONTH) == (calendarAusgabe.get(Calendar.MONTH) + 1) && calendarAktuell.get(Calendar.YEAR) == calendarAusgabe.get(Calendar.YEAR)) {
                Toast.makeText(getApplicationContext(), "geht in ausgaben rein", Toast.LENGTH_SHORT);
                double betrag = ausgaben.getDouble(0);
                ausgabenCounter += betrag;
            }

        }

        Cursor einnahmen = finanzenDB.query(Constants.TBLNAME_E, new String[]{Constants.BETRAG, Constants.DATUM}, null, null, null, null, Constants._ID);

        while (einnahmen.moveToNext()) {

            String datum = einnahmen.getString(1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date einnahmenDatum = null;
            Date aktuellesDatum = new Date();
            try {
                einnahmenDatum = sdf.parse(datum);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GregorianCalendar calendarEinnahme = new GregorianCalendar();
            calendarEinnahme.setTime(einnahmenDatum);
            GregorianCalendar calendarAktuell = new GregorianCalendar();
            calendarAktuell.setTime(aktuellesDatum);

            if ((calendarEinnahme.get(Calendar.MONTH) + 1) == calendarAktuell.get(Calendar.MONTH) && calendarEinnahme.get(Calendar.YEAR) == calendarAktuell.get(Calendar.YEAR)) {
                //Toast.makeText(getApplicationContext(),"geht in einnahmen rein",Toast.LENGTH_SHORT);
                double betrag = einnahmen.getDouble(0);
                einnahmenCounter += betrag;
            }
            TextView restlicheTage = (TextView) findViewById(R.id.textViewRestlicheTageEdit);
            restlicheTage.setText(" " + (calendarAktuell.getActualMaximum(Calendar.DAY_OF_MONTH) - calendarAktuell.get(Calendar.DAY_OF_MONTH)));

        }

        restlichesGeld = (TextView) findViewById(R.id.textViewRestlichesGeld);
        restlichesGeld.setText(verdienst - ausgabenCounter + einnahmenCounter + "");

    }
}
