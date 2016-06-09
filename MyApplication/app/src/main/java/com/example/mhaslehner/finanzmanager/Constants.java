package com.example.mhaslehner.finanzmanager;

/**
 * Created by HP on 09.06.2016.
 */
public class Constants {
    public static final String DBNAME_A = "ausgaben.db";
    public static final int DBVERSION_A = 1;
    public static final String TBLNAME_A = "ausgaben";

    public static final String DBNAME_E = "einnahmen.db";
    public static final int DBVERSION_E = 1;
    public static final String TBLNAME_E = "einnahmen";

    static final String _ID = "_id";
    static final String BESCHREIBUNG = "beschreibung";
    static final String BETRAG = "betrag";
    static final String DATUM = "datum";
    static final String KATEGORIE = "kategorie";

    public static final String TBLSQL_A = "CREATE TABLE " + TBLNAME_A + " ( " + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BESCHREIBUNG + " TEXT NOT NULL , " +
            BETRAG + " DOUBLE NOT NULL , " +
            DATUM + " DATE NOT NULL , " +
            KATEGORIE + " TEXT NOT NULL);";

    public static final String TBLSQL_E = "CREATE TABLE " + TBLNAME_E + " ( " + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BESCHREIBUNG + " TEXT NOT NULL , " +
            BETRAG + " DOUBLE NOT NULL , " +
            DATUM + " DATE NOT NULL , " +
            KATEGORIE + " TEXT NOT NULL);";
}
