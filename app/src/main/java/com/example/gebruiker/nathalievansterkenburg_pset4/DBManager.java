package com.example.gebruiker.nathalievansterkenburg_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by Gebruiker on 3-3-2017.
 */

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    // constructor
    public DBManager(Context c) { context = c; }

    // opening the database
    public DBManager open() throws SQLiteException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // closing the database
    public void close() { dbHelper.close(); }

    // insert item into database
    public void insert(String name) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
        fetch();
    }

    // retrieve item from database
    public Cursor fetch() {

        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.SUBJECT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.d("cursor", Integer.toString(cursor.getCount()));
        return cursor;
    }

    // update database
    public int update(long _id, String name) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues,
                DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    // deletes item from database
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + " = " + _id, null);
    }
}
