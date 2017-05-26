package ch.hslu.durability.mobpro.durability;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static ch.hslu.durability.mobpro.durability.R.id.id;

public class AddActivity extends Activity implements OnClickListener {

    private FloatingActionButton addButton;
    private Button scanBtn;
    private Button saveBtn;
    private TextView txtProduct;
    private int year_x, month_x, day_x;
    private TextView txtdate;
    static final int DIALOG_ID = 0;
    private final String apikey = "8d1e7de97777b8cc0611d5327a8a04c3";

    private DBManager dbManager;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String scanContent = "";

    String url ="";
    String postUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        scanBtn = (Button)findViewById(R.id.button);
        saveBtn = (Button)findViewById(R.id.btnSave);
        txtdate = (TextView) findViewById(R.id.date);
        txtProduct = (TextView) findViewById(R.id.txtProduct);

        dbManager = new DBManager(this);
        dbManager.open();

        scanBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);


        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);



        showDialogOnButtonClick();
    }

    public void onClick(View v) {

        if(v.getId()==R.id.button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        if(v.getId()==R.id.btnSave) {
            final String name = txtProduct.getText().toString();
            final String date = txtdate.getText().toString();

            dbManager.insert(name, date);

            //für alarm -> Nach dem eifügen soll ein Alarm gestellt werden
            Calendar cur_cal = new GregorianCalendar();
            cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
            cal.set(Calendar.HOUR_OF_DAY, 13);
            cal.set(Calendar.MINUTE, 30);
            cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
            cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
            cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
            Intent intent = new Intent(this, NotificationService.class);
            PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
            AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent);



            Intent main = new Intent(AddActivity.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(main);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            scanContent = scanningResult.getContents();
            searchBarcode(scanContent);
        } else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void searchBarcode(String scanContent) {
        url = "https://api.outpan.com/v2/products/" + scanContent + "?apikey=" + apikey;
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                AddActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject json = new JSONObject(myResponse);
                            if(json.getString("name") != "null") {
                                txtProduct.setText(json.getString("name"));
                            } else {
                                showDialogEntry();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void showDialogEntry() {
        AlertDialog.Builder alert = new AlertDialog.Builder(AddActivity.this);

        alert.setTitle("Neues Produkt");
        alert.setMessage("Bitte gib den Namen des Produktes ein: ");

        final EditText input = new EditText(AddActivity.this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    postRequest(input.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.show();
    }

    private void postRequest(String input) throws IOException {

            postUrl = "https://api.outpan.com/v2/products/" + scanContent + "/name?apikey=" + apikey;
            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("name", input)
                    .build();

            Request request = new Request.Builder()
                    .url(postUrl)
                    .post(body)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("HttpService", "onFailure() Request was: " + call);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                }
            });
        txtProduct.setText(input);
    }

    public void showDialogOnButtonClick() {
        addButton = (FloatingActionButton) findViewById(R.id.addDate);

        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            txtdate.setText(day_x + "." + month_x + "." + year_x);
        }
    };
}
