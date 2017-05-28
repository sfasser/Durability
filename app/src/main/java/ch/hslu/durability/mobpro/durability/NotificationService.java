package ch.hslu.durability.mobpro.durability;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import static android.content.ContentValues.TAG;

/*
Diese Klasse wurde für die Notification erstellt.
Da sich diese aber noch in der Testphase befindet, wird diese Klasse zur Zeit nicht verwendet.
 */

public class NotificationService extends Service {

    //Alarm alarm = new Alarm();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "Service started");
        //alarm.setAlarm(this);
        Notifi(this, "Eines deiner Produkte läuft bald ab!");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
    }

    public void Notifi(Context context, String Nachricht){
        //Es wird die Klasse Main.class beim Klick aufgerufen
        final Intent notificationIntent = new Intent(context, MainActivity.class);
        final PendingIntent pi = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_delete_forever_black_24dp)
                        .setContentTitle("Produkt verbrauchen")
                        .setOngoing(true)
                        .setContentIntent(pi)
                        .setContentText(Nachricht)
                        .setAutoCancel(true)
                        .setOngoing(false);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(666, mBuilder.build());
        onDestroy();
    }
}
