package com.wr_software_solutions.earthquakereporter.sync;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.wr_software_solutions.earthquakereporter.R;

import static com.wr_software_solutions.earthquakereporter.EarthquakeActivity.LOG_TAG;

public class ReminderUtilities {

    private static final int REMINDER_INTERVAL_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "earthquake_reminder_tag";

    private static boolean sInitialized;

    private static Context mContext;

    synchronized public static void scheduleEarthquakeReminder(final Context context) {
        if (sInitialized)
            return;

        mContext = context;
        gpsEnabled();
    }

    private static void scheduleJob() {
        Driver driver = new GooglePlayDriver(mContext);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintReminderJob = dispatcher.newJobBuilder()
                .setService(EarthquakeReminderFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS, REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                ))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintReminderJob);

        sInitialized = true;
    }

    private static void gpsEnabled() {
        final LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        final boolean[] gps_enabled = {false};

        try {
            gps_enabled[0] = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.d(LOG_TAG, "Error occured in getting Provider " + ex);
        }

        if (gps_enabled[0] == false) {
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setMessage((R.string.gps_network_not_enabled));
            dialog.setPositiveButton((R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            dialog.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                }
            });
            dialog.show();
        }

        AsyncTask backgroundAlert = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                while (true) {
                    try {
                        Thread.sleep(500);
                        gps_enabled[0] = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (gps_enabled[0]) {
                        Log.d(LOG_TAG, "gps enabled in async task: " + gps_enabled[0]);
                        break;
                    }
                    Log.d(LOG_TAG, "gps enabled in async task: " + gps_enabled[0]);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (gps_enabled[0]) {
                    Log.d(LOG_TAG, "gps enabled: " + gps_enabled[0]);
                    scheduleJob();
                }
            }
        };
        backgroundAlert.execute();
    }
}
