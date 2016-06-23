package com.example.mhaslehner.finanzmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 16.06.2016.
 */
public class DataBaseOpenHelperFinanzen extends SQLiteOpenHelper {

    public DataBaseOpenHelperFinanzen(Context context) {
        super(context, Constants.DBNAME, null, Constants.DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.TBLSQL_K);
        db.execSQL(Constants.TBLSQL_E);
        db.execSQL(Constants.TBLSQL_A);
        db.execSQL(Constants.TBLSQL_M);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TBLNAME_K);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TBLNAME_A);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TBLNAME_E);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TBLNAME_M);
        onCreate(db);
    }


}
