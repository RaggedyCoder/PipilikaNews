package com.pipilika.news.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tuman on 4/5/2015.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Pipilika.db";
    public static final int DB_VERSION = 1;
    public static final String ID_COLUMN = "_id";
    public static final String LATEST_COLUMN = "is_latest";
    public static final String DATA_TABLE = "data";
    private static final String CREATE = "CREATE";
    private static final String COLUMN = "COLUMN";
    private static final String TABLE = "TABLE";
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String FOREIGN_KEY = "FOREIGN KEY";
    private static final String REFERENCES = "REFERENCES";
    private static final String DROP = "DROP";
    private static final String IF_NOT_EXISTS = "IF NOT EXISTS";
    private static final String INTEGER = "INTEGER";
    private static final String VARCHAR = "VARCHAR";
    private static final String DATA_CREATE = CREATE + " " + TABLE + " "
            + IF_NOT_EXISTS + " " + DATA_TABLE + "(" + ID_COLUMN +
            " " + VARCHAR + "," + " " + LATEST_COLUMN + " " + INTEGER + "," + " "
            + PRIMARY_KEY + "(" + ID_COLUMN + ")" + ");";
    private static final String DATA_DROP = DROP + " " + TABLE + " "
            + DATA_TABLE;
    private Context context;

    public DataBaseOpenHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATA_CREATE);
            Log.i("Database Create", "Created data table");
        } catch (Exception e) {
            Log.e("Database Create", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DATA_DROP);
            Log.i("Database Create", "Dropped data table");
        } catch (Exception e) {
            Log.e("Database Drop", e.getMessage());
        }
        onCreate(db);
    }
}
