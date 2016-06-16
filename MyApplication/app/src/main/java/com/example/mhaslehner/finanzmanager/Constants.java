package com.example.mhaslehner.finanzmanager;

/**
 * Created by HP on 09.06.2016.
 */
public class Constants {
    public static final String DBNAME = "finanzen.db";
    public static final int DBVERSION = 5;
    public static final String TBLNAME_A = "ausgaben";
    public static final String TBLNAME_E = "einnahmen";
    public static final String TBLNAME_K = "kategorien";



    public static final String _ID = "_id";
    public static final String BESCHREIBUNG = "beschreibung";
    public static final String BETRAG = "betrag";
    public static final String DATUM = "datum";
    public static final String KATEGORIE = "kategorie";
    public static final String KATEGORIENAME = "name";
    public static final String KATEGORIE_AUSGABEN_CNT = "ausgabencnt";

    public static final String TBLSQL_A = "CREATE TABLE " + TBLNAME_A + " ( " + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BESCHREIBUNG + " TEXT NOT NULL , " +
            BETRAG + " DOUBLE NOT NULL , " +
            DATUM + " TEXT NOT NULL , " +
            KATEGORIE + " TEXT NOT NULL);";

    public static final String TBLSQL_E = "CREATE TABLE " + TBLNAME_E + " ( " + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BESCHREIBUNG + " TEXT NOT NULL , " +
            BETRAG + " DOUBLE NOT NULL , " +
            DATUM + " TEXT NOT NULL);";

    public static final String TBLSQL_K = "CREATE TABLE " + TBLNAME_K + " ( " + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KATEGORIENAME + " TEXT NOT NULL, " +
            BESCHREIBUNG + " TEXT, " +
            KATEGORIE_AUSGABEN_CNT + " INTEGER DEFAULT '0'); ";


}
