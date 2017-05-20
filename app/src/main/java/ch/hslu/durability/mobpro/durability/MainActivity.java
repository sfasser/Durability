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
import android.view.View;
import android.widget.ListView;

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
        //DbAdapter dbAdapter = new DbAdapter(this);
        //dbAdapter.open();


    }


    /*public class DbAdapter{
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

    public class DBHelper extends SQLiteOpenHelper {
        public DBHelper(final Context context){
            super(context, DbAdapter.DB_NAME, null, DbAdapter.DB_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db){
            db.execSQL("CREATE TABLE tbl_myproducts (date not null, name TEXT, not null, id INTEGER PRIMARY KEY;)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }
*/


    public void StartAddActivity(View Button) {
        Intent intent = new Intent (this, AddActivity.class);
        startActivity(intent);
    }
}
