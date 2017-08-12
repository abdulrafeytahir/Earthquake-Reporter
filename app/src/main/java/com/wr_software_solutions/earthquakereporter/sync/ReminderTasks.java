package com.wr_software_solutions.earthquakereporter.sync;

import android.content.Context;
import android.net.Uri;

import com.wr_software_solutions.earthquakereporter.Earthquake;
import com.wr_software_solutions.earthquakereporter.utilities.NotificationUtils;
import com.wr_software_solutions.earthquakereporter.utilities.QueryUtils;

import java.util.List;

/**
 * Created by user on 12-Aug-17.
 */

public class ReminderTasks {

    public static String ACTION_EARTHQUAKE_REMINDER = "earthquake-reminder";
    public static String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    public static void executeTask(Context context, String action) {
        if (ACTION_EARTHQUAKE_REMINDER.equals(action)) {
            String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?";

            Uri baseUri = Uri.parse(USGS_REQUEST_URL);
            final Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendQueryParameter("format", "geojson");
            uriBuilder.appendQueryParameter("limit", "1");
            uriBuilder.appendQueryParameter("minmag", "2");
            uriBuilder.appendQueryParameter("orderby", "time");

            List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(uriBuilder.toString(), true, context);

        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotification(context);
        }
    }
}
