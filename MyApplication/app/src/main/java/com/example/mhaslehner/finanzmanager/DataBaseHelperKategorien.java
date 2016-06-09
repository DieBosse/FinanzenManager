package com.example.mhaslehner.finanzmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by blang on 11.02.2016.
 */
public class DataBaseHelperKategorien extends SQLiteOpenHelper {


    public DataBaseHelperKategorien(Context context) {
        super(context, Constants.DBNAME_K, null, Constants.DBVERSION_K);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constants.TBLSQL_K);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TBLNAME_K);
        onCreate(db);
    }
}
