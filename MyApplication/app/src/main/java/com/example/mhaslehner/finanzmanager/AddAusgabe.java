package com.example.mhaslehner.finanzmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAusgabe extends AppCompatActivity {

    private static EditText editTextBeschreibung;
    private static EditText editTextBetrag;
    private static DatePicker datePicker;
    private static Spinner spinnerKategorie;
    private static Button acceptButton;
    DataBaseOpenHelperFinanzen dataBaseHelperFinanzen;
    SQLiteDatabase finanzenDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ausgabe);
        dataBaseHelperFinanzen = new DataBaseOpenHelperFinanzen(getApplicationContext());
        finanzenDB = dataBaseHelperFinanzen.getReadableDatabase();

        editTextBeschreibung = (EditText) findViewById(R.id.editTextBeschreibungAusgaben);
        editTextBetrag = (EditText) findViewById(R.id.editTextBetragAusgaben);
        datePicker = (DatePicker) findViewById(R.id.datePickerAusgaben);
        spinnerKategorie = (Spinner) findViewById(R.id.spinnerKategorieAusgaben);
        acceptButton = (Button) findViewById(R.id.buttonOkAusgaben);
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_K, null);
        if (cursor.moveToFirst()) {
            setSpinnerAdapter(cursor);
        }


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ausgabeInDatenbankSchreiben();
            }
        });

    }

    private void setSpinnerAdapter(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants.KATEGORIENAME};
        final int[] ITEMS_TO_BE_FILLED = new int[]{android.R.id.text1};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        spinnerKategorie.setAdapter(adapter);
    }

    private void ausgabeInDatenbankSchreiben() {
        String beschreibung = editTextBeschreibung.getText().toString();
        Double betrag;
        try {
            betrag = Double.parseDouble(editTextBetrag.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Geben Sie einen gültigen Betrag ein!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String datum = day + "." + (month+1) + "." + year;
        String kategorie = "";
        int id = (int) spinnerKategorie.getSelectedItemId();
        Cursor cursor = finanzenDB.query(Constants.TBLNAME_K, new String[]{Constants.KATEGORIENAME},
                "_id=?", new String[]{id + ""}, null, null, Constants._ID);
        while (cursor.moveToNext()) {
            kategorie = cursor.getString(0);
        }

        ContentValues values = new ContentValues();
        values.put("beschreibung", beschreibung);
        values.put("betrag", betrag);
        values.put("datum", datum);
        values.put("kategorie", kategorie);


        if (beschreibung.equals("") || datum.equals("") || kategorie.equals("")) {
            Toast.makeText(getApplicationContext(), "Ein oder mehrere Felder leer!",
                    Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), beschreibung + ", " + datum + ", "
                    + kategorie, Toast.LENGTH_SHORT).show();


        } else {

            long insered = finanzenDB.insert(Constants.TBLNAME_A, null, values);
            if (insered > 0) {
                Cursor c = finanzenDB.rawQuery("UPDATE " + Constants.TBLNAME_K +
                        " SET " + Constants.KATEGORIE_AUSGABEN_CNT +
                        " = " + Constants.KATEGORIE_AUSGABEN_CNT + "+1 WHERE " +
                        Constants.KATEGORIENAME + " = '" + kategorie + "'", null);
                c.moveToFirst();
                c.close();
                Cursor updateBetrag = finanzenDB.rawQuery("UPDATE " + Constants.TBLNAME_K +
                        " SET " + Constants.BETRAG +
                        " = " + Constants.BETRAG + "+"+betrag+" WHERE " +
                        Constants.KATEGORIENAME + " = '" + kategorie + "'", null);
                updateBetrag.moveToFirst();
                updateBetrag.close();

                Toast.makeText(getApplicationContext(), beschreibung +
                        " wurde erfolgreich zu Ausgaben hinzugefügt.", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(this, MainActivity.class));

        }

    }
}
