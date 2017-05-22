package ch.hslu.durability.mobpro.durability;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    ListView listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listProducts = (ListView)findViewById(R.id.listProducts);

        DatabaseHandler db = new DatabaseHandler(this);
        SQLiteDatabase db1 = db.getWritableDatabase();

        Cursor todoCursor = db1.rawQuery("SELECT date, name FROM tbl_myproducts", null);
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, todoCursor);

        listProducts.setAdapter(todoAdapter);
/*
        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        DBHelper handler = new DBHelper(this);
        // Get access to the underlying writeable database
        SQLiteDatabase db = handler.getWritableDatabase();
        // Query for items from the database and get a cursor back
        Cursor todoCursor = db.rawQuery("SELECT date, name FROM tbl_myproducts", null);

        // Setup cursor adapter using cursor from last step
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, todoCursor);
        // Attach cursor adapter to the ListView
        listProducts.setAdapter(todoAdapter);
*/
    }


/*

    public class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MyProducts";
        private static final int DATABASE_VERSION = 1;

        public DBHelper(final Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db){
            db.execSQL("CREATE TABLE [IF NOT EXISTS] tbl_myproducts (date DATE NOT NULL, name TEXT NOT NULL, id INTEGER PRIMARY KEY;)");
            db.execSQL("INSERT INTO tbl_myproducts (date, name) VALUES ('fred','jan 1 2009 13:22:15');");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }



    public class TodoCursorAdapter extends CursorAdapter {
        public TodoCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.myproducts, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
            TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
            // Extract properties from cursor
            String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
            int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
            // Populate fields with extracted properties
            tvBody.setText(body);
            tvPriority.setText(String.valueOf(priority));
        }
    }

    /*   public class DbAdapter{
        private final DBHelper dbHelper;
        private SQLiteDatabase db;

        public DbAdapter(final Context context){
            dbHelper = new DBHelper(context);
        }

        public void open(){
            if(db == null || !db.isOpen()){
                db = dbHelper.getWritableDatabase();
                try {
                    Cursor c = db.rawQuery("SELECT date, name FROM tbl_myproducts", null);

                    if (c != null ) {
                        if  (c.moveToFirst()) {
                            do {
                                String firstName = c.getString(c.getColumnIndex("name"));
                                int date = c.getInt(c.getColumnIndex("date"));
                            }while (c.moveToNext());
                        }
                    }
                } catch (SQLiteException se ) {
                    Log.e(getClass().getSimpleName(), "Could not create or Open the database");
                } finally {
                    db.close();
                }
            }
        }
        public void close(){
            dbHelper.close();
        }
    }
*/


    public void StartAddActivity(View Button) {
        Intent intent = new Intent (this, AddActivity.class);
        startActivity(intent);
    }
}
