package ch.hslu.durability.mobpro.durability;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Sandro Fasser on 26.05.2017.
 */

public class NotificationService extends Service {

    Alarm alarm = new Alarm();

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.d(TAG, "Service started");
        alarm.setAlarm(this);
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
    }
}
