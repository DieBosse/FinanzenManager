package com.example.mhaslehner.finanzmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by HP on 09.06.2016.
 */
public class KategorieVerwaltung extends AppCompatActivity {
    private static DataBaseHelperKategorien dataBaseHelperKategorien;
    private static ListView listkategorien = null;
    private static SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorie_verwaltung);

        listkategorien = (ListView) findViewById(R.id.listViewKategorien);
        dataBaseHelperKategorien = new DataBaseHelperKategorien(this);
        db = dataBaseHelperKategorien.getWritableDatabase();
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
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                cursor,
                COLUMS_TO_BE_BOUND,
                ITEMS_TO_BE_FILLED, 0);
        listkategorien.setAdapter(adapter);
    }

    public static void addKategorie() {

    }

    public static void deleteKategorie() {

    }
}
