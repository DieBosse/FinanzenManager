package com.example.mhaslehner.finanzmanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEinnahme extends AppCompatActivity {

    private static EditText editTextBeschreibung;
    private static EditText editTextBetrag;
    private static DatePicker datePicker;
    private static Button acceptButton;
    DataBaseHelperEinnahmen dataBaseHelperEinnahmen;
    SQLiteDatabase einnahmenDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_einnahme);
        dataBaseHelperEinnahmen = new DataBaseHelperEinnahmen(this);
        einnahmenDB = dataBaseHelperEinnahmen.getReadableDatabase();
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
        Double betrag = Double.parseDouble(editTextBetrag.getText().toString());
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String datum=day+"."+month+"."+year;

        ContentValues values = new ContentValues();
        values.put("beschreibung",beschreibung);
        values.put("betrag",betrag);
        values.put("datum",datum);

        if(beschreibung.equals("") || datum.equals("") ||  betrag != 0.0)
        {
            Toast.makeText(getApplicationContext(), "Ein oder mehrere Felder leer!", Toast.LENGTH_LONG).show();
        }
        else
        {
            einnahmenDB.insert(Constants.TBLNAME_E,null,values);
        }
    }
}