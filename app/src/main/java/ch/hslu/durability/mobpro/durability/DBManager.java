package ch.hslu.durability.mobpro.durability;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static ch.hslu.durability.mobpro.durability.DatabaseHandler.KEY_DATE;

/**
 * Created by Sandro Fasser on 24.05.2017.
 */

public class DBManager {

    private DatabaseHandler dbHandler;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHandler = new DatabaseHandler(context);
        database = dbHandler.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHandler.close();
    }

    public void insert(String name, String date) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHandler.KEY_NAME, name);
        contentValue.put(KEY_DATE, date);
        database.insert(DatabaseHandler.TABLE_PRODUCTS, null, contentValue);

    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHandler.KEY_ID, DatabaseHandler.KEY_NAME, KEY_DATE };
        Cursor cursor = database.query(DatabaseHandler.TABLE_PRODUCTS, columns, null, null, null, null, KEY_DATE +" ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.KEY_NAME, name);
        contentValues.put(KEY_DATE, date);
        int i = database.update(DatabaseHandler.TABLE_PRODUCTS, contentValues, DatabaseHandler.KEY_ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHandler.TABLE_PRODUCTS, DatabaseHandler.KEY_ID + "=" + _id, null);
    }
}
