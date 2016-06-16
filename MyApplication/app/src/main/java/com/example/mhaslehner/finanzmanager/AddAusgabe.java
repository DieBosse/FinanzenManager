package com.example.mhaslehner.finanzmanager;

import android.content.ContentValues;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAusgabe extends AppCompatActivity {

    private static EditText editTextBeschreibung;
    private static EditText editTextBetrag;
    private static DatePicker datePicker;
    private static Spinner spinnerKategorie;
    private static Button acceptButton;
    DataBaseHelperAusgaben dataBaseHelperAusgaben;
    SQLiteDatabase ausgabenDB;
    DataBaseHelperKategorien dataBaseHelperKategorien;
    SQLiteDatabase kategorienDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ausgabe);
        dataBaseHelperAusgaben = new DataBaseHelperAusgaben(this);
        ausgabenDB = dataBaseHelperAusgaben.getReadableDatabase();
        dataBaseHelperKategorien = new DataBaseHelperKategorien(this);
        kategorienDB = dataBaseHelperKategorien.getReadableDatabase();
        editTextBeschreibung = (EditText) findViewById(R.id.editTextBeschreibung);
        editTextBetrag = (EditText) findViewById(R.id.editTextBetrag);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        spinnerKategorie = (Spinner) findViewById(R.id.spinnerKategorie);
        acceptButton = (Button) findViewById(R.id.buttonOk);
        Cursor cursor = kategorienDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_K, null);
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
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        spinnerKategorie.setAdapter(adapter);
    }

    private void ausgabeInDatenbankSchreiben() {
        String beschreibung = editTextBeschreibung.getText().toString();
        Double betrag = Double.parseDouble(editTextBetrag.getText().toString());
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String datum=day+"."+month+"."+year;
        String kategorie = (String) spinnerKategorie.getSelectedItem();

        ContentValues values = new ContentValues();
        values.put("beschreibung",beschreibung);
        values.put("betrag",betrag);
        values.put("datum", datum);
        values.put("kategorie", kategorie);

        if(beschreibung.equals("") || datum.equals("") || kategorie.equals("") || betrag != 0.0)
        {
            Toast.makeText(getApplicationContext(),"Ein oder mehrere Felder leer!",Toast.LENGTH_LONG).show();
        }
        else
        {
            ausgabenDB.insert(Constants.TBLNAME_A,null,values);
        }

    }
}
