package com.wr_software_solutions.earthquakereporter.sync;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.wr_software_solutions.earthquakereporter.Earthquake;
import com.wr_software_solutions.earthquakereporter.UserLocation;
import com.wr_software_solutions.earthquakereporter.utilities.NotificationUtils;
import com.wr_software_solutions.earthquakereporter.utilities.QueryUtils;

import java.text.DecimalFormat;
import java.util.List;


public class ReminderTasks {

    public static String ACTION_EARTHQUAKE_REMINDER = "earthquake-reminder";
    public static String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static UserLocation mUserLocation;
    public static Earthquake mReminderEarthquake;

    public static void executeTask(Context context, String action) {

        int distance = 0;
        int earthquakeRange = 0;
        int magnitude;
        if (ACTION_EARTHQUAKE_REMINDER.equals(action)) {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            //Check the GPS status
            boolean gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (networkInfo != null && networkInfo.isConnected() && gps_enabled) {

                String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?";

                Uri baseUri = Uri.parse(USGS_REQUEST_URL);
                final Uri.Builder uriBuilder = baseUri.buildUpon();

                uriBuilder.appendQueryParameter("format", "geojson");
                uriBuilder.appendQueryParameter("limit", "1");
                uriBuilder.appendQueryParameter("minmag", "2");
                uriBuilder.appendQueryParameter("orderby", "time");


                try {
                    mUserLocation = new UserLocation(context);

                    List<Earthquake> currentEarthquake = QueryUtils.extractEarthquakes(uriBuilder.toString(), true);
                    mReminderEarthquake = currentEarthquake.get(0);
                    distance = distanceInKilometers(mReminderEarthquake.getmLatitude(),
                            mUserLocation.getmLocation().getLatitude(),
                            mReminderEarthquake.getmLongitude(),
                            mUserLocation.getmLocation().getLongitude());
                } catch (NullPointerException e) {
                    Log.e("ReminderTask", "Error occured while getting user location: " + e);
                }

                magnitude = (int) Math.floor(mReminderEarthquake.getmMagnitue());
                switch (magnitude) {
                    case 1:
                        earthquakeRange = 5;
                        break;
                    case 2:
                        earthquakeRange = 10;
                        break;
                    case 3:
                        earthquakeRange = 20;
                        break;
                    case 4:
                        earthquakeRange = 50;
                        break;
                    case 5:
                        earthquakeRange = 150;
                        break;
                    case 6:
                        earthquakeRange = 350;
                        break;
                    case 7:
                        earthquakeRange = 900;
                        break;
                    case 8:
                        earthquakeRange = 2500;
                        break;
                    case 9:
                        earthquakeRange = 7000;
                        break;
                    case 10:
                        earthquakeRange = 18000;
                        break;

                }
                if (distance <= earthquakeRange) {
                    NotificationUtils.earthquakeReminder(context, distance);
                }
            }
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotification(context);
        }
    }

    private static int distanceInKilometers(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        distance = Math.pow(distance, 2);

        distance = Math.sqrt(distance);

        return (int) Math.floor(distance);
    }
}
