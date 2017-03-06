package com.example.gebruiker.nathalievansterkenburg_pset4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nathalie van Sterkenburg on 3-3-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TODOLISTDATABASE";
    private static final int DB_VERSION = 1;

    public static final String _ID = "_id";
    public static final String TABLE_NAME = "todolist";
    public static final String SUBJECT = "subject";
    public static final String DONE = "done";

    public static final String CROSS = "@mipmap/cross";
    public static final String CHECK = "@mipmap/checkmark";

    public static final String WELCOME = "Welcome";
    public static final String TOHAVEDONE = "Click on an item set it as done";
    public static final String TOREMOVE = "Hold an item remove it from the list";
    public static final String LETSTRY = "Try it yourself";

    // create table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DONE +
            " TEXT " + ");";


    // constructor
    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("hier zou", "ik wel moeten komen");
        db.execSQL(CREATE_TABLE);
//        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + SUBJECT + ", " + DONE + ") VALUES (" +
//            WELCOME + ", " + CROSS + "), (" +
//            TOHAVEDONE + ", " + CROSS + "), (" +
//            TOREMOVE + ", " + CROSS + "), (" +
//            LETSTRY + ", " + CROSS + ");");

        Log.i("hier juist", "niet");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
