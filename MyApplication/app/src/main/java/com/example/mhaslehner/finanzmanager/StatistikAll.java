package com.example.mhaslehner.finanzmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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
        } else {
            if (zahl == 2) {
                showMonths();
            }
        }

    }

    private void showMonths() {
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_A, null);

        if (cursor.moveToFirst()) {
            setAdapterMonths(cursor);
        }
    }

    private void showKategories() {
        Cursor cursor = finanzenDB.rawQuery("SELECT * FROM " + Constants.TBLNAME_K, null);

        if (cursor.moveToFirst()) {
            setAdapter(cursor);
        }

    }

    private void setAdapter(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants.KATEGORIENAME, Constants.KATEGORIE_AUSGABEN_CNT};
        final int[] ITEMS_TO_BE_FILLED = new int[]{android.R.id.text1, android.R.id.text2};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listView.setAdapter(adapter);
    }

    private void setAdapterMonths(Cursor cursor) {
        final String[] COLUMS_TO_BE_BOUND = new String[]{Constants.KATEGORIENAME, Constants.KATEGORIE_AUSGABEN_CNT};
        final int[] ITEMS_TO_BE_FILLED = new int[]{android.R.id.text1, android.R.id.text2};
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listView.setAdapter(adapter);
    }

}
