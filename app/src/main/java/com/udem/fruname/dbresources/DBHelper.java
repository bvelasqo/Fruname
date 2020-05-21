package com.udem.fruname.dbresources;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
