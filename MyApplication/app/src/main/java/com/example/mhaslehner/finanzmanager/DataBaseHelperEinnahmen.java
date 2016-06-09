package com.example.mhaslehner.finanzmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mhaslehner on 09.06.2016.
 */
public class DataBaseHelperEinnahmen extends SQLiteOpenHelper{


    public DataBaseHelperEinnahmen(Context context) {
        super(context, Constants.DBNAME_E, null, Constants.DBVERSION_E);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.TBLSQL_E);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+Constants.TBLNAME_E);
        onCreate(db);
    }
}
