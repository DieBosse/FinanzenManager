package com.example.mhaslehner.finanzmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by HP on 16.06.2016.
 */
public class AktivitaetenAnzeigen extends AppCompatActivity {
    private static DataBaseOpenHelperFinanzen dataBaseOpenHelperFinanzen;


    private static SQLiteDatabase finanzenDB;

    private static ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitaeten_anzeigen);

        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);

        finanzenDB = dataBaseOpenHelperFinanzen.getReadableDatabase();
        listView = (ListView) findViewById(R.id.listViewAktivitäten);

        showActivitys();
    }

    private void showActivitys() {
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_A +
                " a LEFT OUTER JOIN " + Constants.TBLNAME_E + " b ON a." + Constants._ID + " = b." +
                Constants._ID, null);
        setAdapter(cursor);
    }

    private void setAdapter(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants.BESCHREIBUNG, Constants.BETRAG};
        final int[] ITEMS_TO_BE_FILLED = new int[]{android.R.id.text1, android.R.id.text2};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listView.setAdapter(adapter);
    }
}
