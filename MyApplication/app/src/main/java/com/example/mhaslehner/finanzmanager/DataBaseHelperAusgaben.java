package com.example.mhaslehner.finanzmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mhaslehner on 09.06.2016.
 */
public class DataBaseHelperAusgaben {

    public DataBaseOpenHelperAusgaben(Context context) {
        super(context, "name", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TBLSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE DETAILS");
        onCreate(db);
    }
}
