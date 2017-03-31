package com.cs442.sgupta50.foodmenu.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**********************************
 Name : OrderHistoryDbHelper

 Use: This class is for defining the database and cresting the table
 ***********************************/

public class OrderHistoryDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OrderReader.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + OrderReaderContract.OrderReader.TABLE_NAME + " (" +
                    OrderReaderContract.OrderReader._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    OrderReaderContract.OrderReader.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    OrderReaderContract.OrderReader.COLUMN_NAME_SELECTED_ITEMS + TEXT_TYPE + COMMA_SEP +
                    OrderReaderContract.OrderReader.COLUMN_NAME_TOTAL + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + OrderReaderContract.OrderReader.TABLE_NAME;

    public OrderHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
