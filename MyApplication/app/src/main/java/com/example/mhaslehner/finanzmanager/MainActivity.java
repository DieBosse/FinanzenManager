package com.example.mhaslehner.finanzmanager;

import android.content.ContentValues;
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
import android.widget.ArrayAdapter;
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

    private static double restlicheTageDouble = 0;
    private static double restlichesGeldDouble = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);
        finanzenDB = dataBaseOpenHelperFinanzen.getWritableDatabase();
        img = (ImageView) findViewById(R.id.imageView);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        restlichesGeldAnzeigen();
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("verdienst")) {
                    restlichesGeldAnzeigen();
                }
                if (key.equals("percentPrefs")) {
                    setSmiley();
                }
            }
        };

        insertMonths();

    }

    private void insertMonths() {

        for (int i = 0; i < 12; i++) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants._ID,i+1);
            contentValues.put(Constants.BETRAG, 0.0);
           long ins =  finanzenDB.insert(Constants.TBLNAME_M, null, contentValues);
            if (ins < 1)
            {
                return;
            }
        }
    }


    private void setSmiley() {
        String prefsString = prefs.getString("verdienst", "");
        double verdienst = 0;
        if (!prefsString.equals("")) {
            try {
                verdienst = Double.parseDouble(prefsString);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Keine gültiger Verdienst!"
                        , Toast.LENGTH_SHORT).show();
            }

        }

        GregorianCalendar calendarAktuell = new GregorianCalendar();
        Date aktuellesDatum = new Date();
        calendarAktuell.setTime(aktuellesDatum);
        int maxDays = calendarAktuell.getActualMaximum(Calendar.DAY_OF_MONTH);

        double geldSoll = verdienst / maxDays;
        double geldIst = restlichesGeldDouble / restlicheTageDouble;
        String prefsStringPercent = prefs.getString("percentPrefs", "15");

        double percentPrefs = 0;
        try {
            percentPrefs = Double.parseDouble(prefsStringPercent);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Keine gültigen Prozent!",
                    Toast.LENGTH_SHORT).show();
            percentPrefs = 15;
        }


        double percent = (geldSoll / 100) * percentPrefs;
        if (geldIst >= (geldSoll - percent)) {
            img.setImageResource(R.drawable.smiley_gruen);
            Toast.makeText(getApplicationContext(), "Ihre Finanzen sind gut!",
                    Toast.LENGTH_SHORT).show();
        }
        if (geldIst < (geldSoll - percent) && geldIst > (geldSoll - (3 * percent))) {
            img.setImageResource(R.drawable.smiley_gelb);
            Toast.makeText(getApplicationContext(), "Ihre Finanzen sind mittelmäßig!",
                    Toast.LENGTH_SHORT).show();
        }
        if (geldIst < (geldSoll - (3 * percent))) {
            img.setImageResource(R.drawable.smiley_rot);
            Toast.makeText(getApplicationContext(), "Ihre Finanzen sind schlecht, sparen Sie!",
                    Toast.LENGTH_SHORT).show();
        }

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
        if (id == R.id.einnahmenmenu) {
            startActivity(new Intent(this, AktivitaetenAnzeigen.class).putExtra("einnahmeausgabe", 1));
        }
        if (id == R.id.ausgabenmenu) {
            startActivity(new Intent(this, AktivitaetenAnzeigen.class).putExtra("einnahmeausgabe", 2));
        }
        if (id == R.id.resetmenu)
        {
            Constants.DBVERSION +=1;
            startActivity(new Intent(this, MainActivity.class));
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

    public void Statistik(final View view) {
        Intent i = new Intent(getApplicationContext(), Statistik.class);
        startActivity(i);
    }


    public void restlichesGeldAnzeigen() {
        TextView restlichesGeld;
        TextView restlicheTage;
        double ausgabenCounter = 0;
        double einnahmenCounter = 0;
        Date aktuellesDatum = new Date();
        GregorianCalendar calendarAktuell = new GregorianCalendar();
        calendarAktuell.setTime(aktuellesDatum);
        String prefsString = prefs.getString("verdienst", "");
        double verdienst = 0;
        if (!prefsString.equals("")) {
            try {
                verdienst = Double.parseDouble(prefsString);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Keine gültiger Verdienst!"
                        , Toast.LENGTH_SHORT).show();
            }

        }


        Cursor ausgaben = finanzenDB.query(Constants.TBLNAME_A,
                new String[]{Constants.BETRAG, Constants.DATUM}, null, null, null, null, Constants._ID);

        while (ausgaben.moveToNext()) {

            String datum = ausgaben.getString(1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date ausgabenDatum = null;


            try {
                ausgabenDatum = sdf.parse(datum);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GregorianCalendar calendarAusgabe = new GregorianCalendar();
            calendarAusgabe.setTime(ausgabenDatum);


            if ((calendarAktuell.get(Calendar.MONTH) + 1) == (calendarAusgabe.get(Calendar.MONTH) + 1)
                    && calendarAktuell.get(Calendar.YEAR) == calendarAusgabe.get(Calendar.YEAR)) {
                double betrag = ausgaben.getDouble(0);
                ausgabenCounter += betrag;
            }

        }

        Cursor einnahmen = finanzenDB.query(Constants.TBLNAME_E,
                new String[]{Constants.BETRAG, Constants.DATUM}, null, null, null, null, Constants._ID);

        while (einnahmen.moveToNext()) {

            String datum = einnahmen.getString(1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date einnahmenDatum = null;
            try {
                einnahmenDatum = sdf.parse(datum);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GregorianCalendar calendarEinnahme = new GregorianCalendar();
            calendarEinnahme.setTime(einnahmenDatum);

            if ((calendarEinnahme.get(Calendar.MONTH) + 1) == (calendarAktuell.get(Calendar.MONTH) + 1)
                    && calendarEinnahme.get(Calendar.YEAR) == calendarAktuell.get(Calendar.YEAR)) {
                double betrag = einnahmen.getDouble(0);
                einnahmenCounter += betrag;
            }
        }
        restlicheTageDouble = ((calendarAktuell.getActualMaximum(Calendar.DAY_OF_MONTH))
                - (calendarAktuell.get(Calendar.DAY_OF_MONTH) + 1));
        restlicheTage = (TextView) findViewById(R.id.textViewRestlicheTage);
        restlicheTage.setText("Restliche Tage: " + (int) restlicheTageDouble);
        restlichesGeldDouble = Math.round((verdienst - ausgabenCounter + einnahmenCounter) * 100.0) / 100.0;

        restlichesGeld = (TextView) findViewById(R.id.textViewRestlichesGeld);
        restlichesGeld.setText("Restlicher Betrag: " + restlichesGeldDouble + "€");

        setSmiley();
    }
}
