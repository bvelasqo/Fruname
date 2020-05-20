package com.udem.fruname;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteAssetHelper {
    private static final int VERSION=1;
    private static final String DIRECTORY = "/data/data/com.udem.fruname/databases";

    public DBHelper(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, DIRECTORY, factory, VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, VERSION);
    }

}
