package ch.hslu.durability.mobpro.durability;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateActivity extends AppCompatActivity {

    private String name = "";
    private String barcodeNew = "";
    private final String apikey = "8d1e7de97777b8cc0611d5327a8a04c3";
    public String postUrl= "https://api.outpan.com/v2/products/" + barcodeNew + "/name?apikey=" + apikey;

    /*
    public String postBody="{\n" +
            "    \"name\": \"" + name + "\",\n" +
            "}";
    */
    private EditText eName = (EditText) findViewById(R.id.editTextName);

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

    }

    void postRequest(String postUrl) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("name", name)
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
    }

    public void safe (View view) {

        name = eName.getText().toString();

        try {
            postRequest(postUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
