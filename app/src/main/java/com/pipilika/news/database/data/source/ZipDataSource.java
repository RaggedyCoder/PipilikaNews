package com.pipilika.news.database.data.source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.pipilika.news.database.DataBaseOpenHelper;
import com.pipilika.news.database.data.item.Item;
import com.pipilika.news.database.data.item.ZipDataItem;
import com.pipilika.news.database.exception.DataSourceException;

import java.util.ArrayList;

/**
 * Created by tuman on 4/5/2015.
 */
public class ZipDataSource {

    protected Context context;
    protected SQLiteDatabase database;
    protected DataBaseOpenHelper dbHelper;
    private String[] allColumns = {
            DataBaseOpenHelper.ID_COLUMN,
            DataBaseOpenHelper.LATEST_COLUMN};

    public ZipDataSource(Context context) {
        this.context = context;
        dbHelper = new DataBaseOpenHelper(this.context);
    }

    public ZipDataItem insertZipDataItem(String id, int latestOne)
            throws DataSourceException {
        ContentValues values = new ContentValues();

        values.put(DataBaseOpenHelper.ID_COLUMN, id);
        values.put(DataBaseOpenHelper.LATEST_COLUMN, latestOne);

        database.insert(DataBaseOpenHelper.DATA_TABLE, null, values);

        Cursor cursor = database.query(DataBaseOpenHelper.DATA_TABLE,
                allColumns, DataBaseOpenHelper.ID_COLUMN + " LIKE "
                        + "'" + id + "'", null, null, null, null);
        cursor.moveToFirst();
        return cursorToZipDataItem(cursor);
    }

    public ZipDataItem insertZipDataItem(ZipDataItem item)
            throws DataSourceException {
        return insertZipDataItem(item.getId(), item.getLatestOne());
    }

    public ZipDataItem getZipDataItem(ZipDataItem item) {
        Cursor cursor = database.query(DataBaseOpenHelper.DATA_TABLE,
                allColumns, DataBaseOpenHelper.LATEST_COLUMN + " = " + item.getLatestOne(), null, null, null, null);
        cursor.moveToFirst();
        return cursorToZipDataItem(cursor);
    }

    public void updateZipDataItem(ZipDataItem item) {
        ContentValues values = new ContentValues();
        values.put(DataBaseOpenHelper.LATEST_COLUMN, item.getLatestOne());
        database.update(DataBaseOpenHelper.DATA_TABLE, values, DataBaseOpenHelper.ID_COLUMN + " = " + item.getId(), null);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteBlooditem(ZipDataItem item) {
        String id = item.getId();
        database.delete(DataBaseOpenHelper.DATA_TABLE,
                DataBaseOpenHelper.ID_COLUMN + " = " + id, null);
    }

    public ArrayList<Item> getAllZipDataItem() {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = database.query(DataBaseOpenHelper.DATA_TABLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        do {
            ZipDataItem item = cursorToZipDataItem(cursor);
            items.add(item);
        } while (cursor.moveToNext());
        return items;
    }

    private ZipDataItem cursorToZipDataItem(Cursor cursor) {
        ZipDataItem item = new ZipDataItem();
        item.setId(cursor.getString(cursor
                .getColumnIndex(DataBaseOpenHelper.ID_COLUMN)));
        item.setLatestOne(cursor.getInt(cursor
                .getColumnIndex(DataBaseOpenHelper.LATEST_COLUMN)));

        return item;
    }
}
