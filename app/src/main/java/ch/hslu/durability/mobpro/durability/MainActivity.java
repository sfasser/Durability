package ch.hslu.durability.mobpro.durability;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHandler.KEY_ID, DatabaseHandler.KEY_NAME, DatabaseHandler.KEY_DATE};

    final int[] to = new int[]{R.id.id, R.id.name, R.id.date};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

    }


    public void StartAddActivity(View Button) {
        Intent intent = new Intent (this, AddActivity.class);
        startActivity(intent);
    }
}
