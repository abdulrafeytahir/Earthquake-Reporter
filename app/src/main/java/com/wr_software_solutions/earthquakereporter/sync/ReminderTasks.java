package com.wr_software_solutions.earthquakereporter.sync;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.wr_software_solutions.earthquakereporter.UserLocation;
import com.wr_software_solutions.earthquakereporter.utilities.NotificationUtils;
import com.wr_software_solutions.earthquakereporter.utilities.QueryUtils;

import static com.wr_software_solutions.earthquakereporter.R.id.distance;
import static com.wr_software_solutions.earthquakereporter.utilities.QueryUtils.mEarthquakeCoordinates;

/**
 * Created by user on 12-Aug-17.
 */

public class ReminderTasks {

    public static String ACTION_EARTHQUAKE_REMINDER = "earthquake-reminder";
    public static String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static UserLocation mUserLocation;

    public static void executeTask(Context context, String action) {
        if (ACTION_EARTHQUAKE_REMINDER.equals(action)) {
            String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?";

            Uri baseUri = Uri.parse(USGS_REQUEST_URL);
            final Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendQueryParameter("format", "geojson");
            uriBuilder.appendQueryParameter("limit", "1");
            uriBuilder.appendQueryParameter("minmag", "2");
            uriBuilder.appendQueryParameter("orderby", "time");

            double distance = 0;
            try {
                mUserLocation = new UserLocation(context);
                QueryUtils.extractEarthquakes(uriBuilder.toString(), true);

                distance = distanceInKilometers(mEarthquakeCoordinates.getmLatidue(),
                        mUserLocation.getmLocation().getLatitude(),
                        mEarthquakeCoordinates.getGetmLongitude(),
                        mUserLocation.getmLocation().getLongitude(),
                        0, 0);
            } catch (NullPointerException e) {
                Log.e("ReminderTask", "Error occured while getting user location: " + e);
            }

            if (distance < 30000) {
                NotificationUtils.earthquakeReminder(context, distance);
            } else {
                return;
            }


        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotification(context);
        }
    }

    public static double distanceInKilometers(double lat1, double lat2, double lon1,
                                              double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
