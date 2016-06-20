package com.example.mhaslehner.finanzmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddEinnahme extends AppCompatActivity {

    private static EditText editTextBeschreibung;
    private static EditText editTextBetrag;
    private static DatePicker datePicker;
    private static Button acceptButton;
    DataBaseOpenHelperFinanzen dataBaseOpenHelperFinanzen;
    SQLiteDatabase finanzenDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_einnahme);
        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);
        finanzenDB = dataBaseOpenHelperFinanzen.getReadableDatabase();
        editTextBeschreibung = (EditText) findViewById(R.id.editTextBeschreibungE);
        editTextBetrag = (EditText) findViewById(R.id.editTextBetragE);
        datePicker = (DatePicker) findViewById(R.id.datePickerE);
        acceptButton = (Button) findViewById(R.id.buttonOkE);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                einnahmeInDatenbankSchreiben();
            }
        });

    }

    private void einnahmeInDatenbankSchreiben() {
        String beschreibung = editTextBeschreibung.getText().toString();
        Double betrag;
        try {
            betrag = Double.parseDouble(editTextBetrag.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Geben Sie einen gültigen Betrag ein!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String datum = day + "." + (month+1) + "." + year;

        ContentValues values = new ContentValues();
        values.put("beschreibung", beschreibung);
        values.put("betrag", betrag);
        values.put("datum", datum);

        if (beschreibung.equals("") || datum.equals("")) {
            Toast.makeText(getApplicationContext(), "Ein oder mehrere Felder leer!",
                    Toast.LENGTH_SHORT).show();
        } else {

            finanzenDB.insert(Constants.TBLNAME_E, null, values);
            Toast.makeText(getApplicationContext(), beschreibung +
                    " wurde erfolgreich zu Einnahmen hinzugefügt.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));


        }
    }
}