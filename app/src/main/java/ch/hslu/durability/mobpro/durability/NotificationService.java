package ch.hslu.durability.mobpro.durability;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by Sandro Fasser on 26.05.2017.
 */

public class NotificationService extends Service {

    //Alarm alarm = new Alarm();

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
        //alarm.setAlarm(this);
        Notifi(this, "Hallo wie gehts");
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        Toast.makeText(this, "Alarm zerstört !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example
    }

    public void Notifi(Context context, String Nachricht){
        //Es wird die Klasse Main.class beim Klick aufgerufen
        final Intent notificationIntent = new Intent(context, MainActivity.class);
        final PendingIntent pi = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_delete_forever_black_24dp)
                        .setContentTitle("App XY")
                        .setOngoing(true)
                        .setContentIntent(pi)
                        .setContentText(Nachricht);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(666, mBuilder.build());
        onDestroy();
    }
}
