package com.example.mhaslehner.finanzmanager;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by HP on 09.06.2016.
 */
public class KategorieVerwaltung extends AppCompatActivity {
    private static DataBaseHelperKategorien dataBaseHelperKategorien;
    private static ListView listkategorien = null;
    private static SQLiteDatabase db = null;
    Button addKategorieButton = null;
    Button deleteKategorienButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorie_verwaltung);

        listkategorien = (ListView) findViewById(R.id.listViewKategorien);
        dataBaseHelperKategorien = new DataBaseHelperKategorien(this);
        db = dataBaseHelperKategorien.getWritableDatabase();
        addKategorieButton = (Button) findViewById(R.id.buttonAddKategorie);
        deleteKategorienButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteKategorie();
            }
        });
        addKategorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKategorie();
            }
        });
        showKategories();
    }

    private void showKategories() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TBLNAME_K, null);
        if (cursor.moveToFirst()) {
            setAdapter(cursor);

        }
    }

    private void setAdapter(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants.KATEGORIENAME};
        final int[] ITEMS_TO_BE_FILLED = new int[]{android.R.id.text1};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listkategorien.setAdapter(adapter);
    }

    public void addKategorie() {
        final Dialog dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.dialog_add_kategorie, null);
        dialog.setContentView(v);
        final EditText nameeditText = (EditText) v.findViewById(R.id.editTextAddKategorieName);
        final EditText beschreibungeditText = (EditText) v.findViewById(R.id.editTextAddKategorieBeschreibung);
        Button addbutoon = (Button) v.findViewById(R.id.buttonAddKategorie);
        Button cancelbutton = (Button) v.findViewById(R.id.buttonCancelKategorie);
        dialog.setTitle("Kategorie hinzufügen");
        dialog.show();
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        addbutoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues vals = new ContentValues();
                String name = nameeditText.getText().toString();
                String beschreibung = beschreibungeditText.getText().toString();
                vals.put(Constants.KATEGORIENAME, name);
                vals.put(Constants.BESCHREIBUNG, beschreibung);
                Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TBLNAME_K +
                        " WHERE LOWER(" + Constants.KATEGORIENAME + ") = '" + name.toLowerCase() + "'", null);
                if (!cursor.moveToFirst()) {
                    long insertid = db.insert(Constants.TBLNAME_K, null, vals);
                    dialog.cancel();
                    if (insertid > 0) {
                        Toast.makeText(getApplicationContext(), "Die Kategorie " +
                                name + " wurde erfolgreich erstellt.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "Die Kategorie " +
                            name + " exestiert bereits.", Toast.LENGTH_LONG).show();

                }

            }
        });
        showKategories();

    }

    public static void deleteKategorie() {

    }
}
