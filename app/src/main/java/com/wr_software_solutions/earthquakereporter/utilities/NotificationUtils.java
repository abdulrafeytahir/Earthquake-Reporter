package com.wr_software_solutions.earthquakereporter.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.wr_software_solutions.earthquakereporter.MapsActivity;
import com.wr_software_solutions.earthquakereporter.R;

/**
 * Created by user on 12-Aug-17.
 */

public class NotificationUtils {

    private static final int MAPS_NOTIFICATION_ID = 4214;
    private static final int MAPS_PENDING_INTENT_ID = 5196;

    public static void clearAllNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void earthquakeReminder(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Earthquake Notification")
                        .setContentText("An earthquake occured in your vicinity")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("An earthquake occured in your vicinity"))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentIntent(contentIntent(context))
                        .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBuilder.setPriority(Notification.PRIORITY_HIGH);
        }
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MAPS_NOTIFICATION_ID, mBuilder.build());

    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MapsActivity.class);
        return PendingIntent.getActivity(
                context,
                MAPS_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
