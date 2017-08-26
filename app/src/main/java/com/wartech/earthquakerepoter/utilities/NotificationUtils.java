package com.wartech.earthquakerepoter.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.wartech.earthquakerepoter.MapsActivity;
import com.wartech.earthquakerepoter.R;

public class NotificationUtils {

    private static final int MAPS_NOTIFICATION_ID = 4214;
    private static final int MAPS_PENDING_INTENT_ID = 5196;

    public static void clearAllNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void earthquakeReminder(Context context, int distance) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(largeIcon(context))
                        .setContentTitle("Earthquake Notification")
                        .setContentText("An Earthquake has occurred at a distance of: " + distance)
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

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        return largeIcon;
    }
}
