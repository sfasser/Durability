package ch.hslu.durability.mobpro.durability;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    public long productid;

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

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView nameTextView = (TextView) view.findViewById(R.id.name);
                TextView dateTextView = (TextView) view.findViewById(R.id.date);

                String id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();
                String date = dateTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), DetailActivity.class);
                modify_intent.putExtra("name", name);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });

        // OnLongCLickListener For List Items
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView nameTextView = (TextView) view.findViewById(R.id.name);
                TextView dateTextView = (TextView) view.findViewById(R.id.date);

                String txtid = idTextView.getText().toString();
                productid = Long.parseLong(txtid);
                String name = nameTextView.getText().toString();
                String date = dateTextView.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Löschen");
                builder.setMessage("Wollen Sie den Eintrag löschen ?");
                builder.setIcon(R.drawable.ic_delete_forever_black_24dp);
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        dbManager.delete(productid);
                        Intent modify_intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(modify_intent);
                    }
                });
                builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true;
            }
        });

        startService(new Intent(this, NotificationService.class));

    }

    public void StartAddActivity(View Button) {
        Intent intent = new Intent (this, AddActivity.class);
        startActivity(intent);
    }
}
