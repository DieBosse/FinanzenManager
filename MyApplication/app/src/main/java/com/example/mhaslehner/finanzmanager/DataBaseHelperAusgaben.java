package com.example.mhaslehner.finanzmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mhaslehner on 09.06.2016.
 */
public class DataBaseHelperAusgaben extends SQLiteOpenHelper{


    public DataBaseHelperAusgaben(Context context) {
        super(context, Constants.DBNAME_A, null, Constants.DBVERSION_A);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.TBLSQL_A);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+Constants.TBLNAME_A);
        onCreate(db);
    }
}
