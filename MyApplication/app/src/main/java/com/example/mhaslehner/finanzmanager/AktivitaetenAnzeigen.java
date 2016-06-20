package com.example.mhaslehner.finanzmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by HP on 16.06.2016.
 */
public class AktivitaetenAnzeigen extends AppCompatActivity {
    private static DataBaseOpenHelperFinanzen dataBaseOpenHelperFinanzen;


    private static SQLiteDatabase finanzenDB;
    private static ListView listView;
    private static TextView gesamt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitaeten_anzeigen);

        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);

        finanzenDB = dataBaseOpenHelperFinanzen.getReadableDatabase();
        listView = (ListView) findViewById(R.id.listViewAktivitäten);
        gesamt = (TextView) findViewById(R.id.textViewGesamtAktivität);
        int zahl = getIntent().getIntExtra("einnahmeausgabe", -1);
        if (zahl == 1) {
            showEinnahmen();
        } else {
            if (zahl == 2) {
                showAusgaben();
            }
        }

    }

    private void showEinnahmen() {
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_E +
                " ORDER BY " + Constants.DATUM + " DESC", null);
        if (cursor.moveToFirst()) {
            int cnt = cursor.getCount();
            gesamt.setText("Anzahl Einnahmen: " + cnt);
            setAdapter(cursor);
        }
    }

    private void showAusgaben() {
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_A +
                " ORDER BY " + Constants.DATUM + " DESC", null);
        if (cursor.moveToFirst()) {
            int cnt = cursor.getCount();
            gesamt.setText("Anzahl Ausgaben: " + cnt);
            setAdapter(cursor);
        }
    }


    private void setAdapter(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants.BESCHREIBUNG, Constants.BETRAG, Constants.DATUM};
        final int[] ITEMS_TO_BE_FILLED = new int[]{R.id.textViewTextAktivität, R.id.textViewBetragAktivität, R.id.textViewDatumAktiviät};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.einnahmenausgaben,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listView.setAdapter(adapter);
    }
}
