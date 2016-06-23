package com.example.mhaslehner.finanzmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Calendar;

/**
 * Created by HP on 22.06.2016.
 */
public class StatistikAll extends AppCompatActivity {
    private static DataBaseOpenHelperFinanzen dataBaseOpenHelperFinanzen;
    private static SQLiteDatabase finanzenDB;
    private static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_all);

        dataBaseOpenHelperFinanzen = new DataBaseOpenHelperFinanzen(this);
        finanzenDB = dataBaseOpenHelperFinanzen.getWritableDatabase();
        listView = (ListView) findViewById(R.id.listViewStatistik);

        int zahl = getIntent().getIntExtra("button", -1);
        if (zahl == 1) {
            showKategories();

        }
        if (zahl == 2) {
            showMonths();
        }


    }

    private void showKategories() {
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_K, null);

        if (cursor.moveToFirst()) {
            setAdapterGeld(cursor);
        }
    }

    private void showMonths() {
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_M, null);

        if (cursor.moveToFirst()) {
            setAdapterMonths(cursor);
        }
    }


    private void setAdapterMonths(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants._ID, Constants.BETRAG};
        final int[] ITEMS_TO_BE_FILLED = new int[]{R.id.textViewMonateall, R.id.textViewBetragMonateAll};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.monate_all,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listView.setAdapter(adapter);
    }

    private void setAdapterGeld(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants.KATEGORIENAME, Constants.KATEGORIE_AUSGABEN_CNT, Constants.BETRAG};
        final int[] ITEMS_TO_BE_FILLED = new int[]{R.id.textViewKategorietext, R.id.textViewKategorieAnzahl, R.id.textViewKategorieBetrag};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.statistik_kategorien_listview,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listView.setAdapter(adapter);
    }

}
