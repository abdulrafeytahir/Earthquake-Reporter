package com.wartech.earthquakerepoter.sync;

import android.app.IntentService;
import android.content.Intent;

public class EarthquakeIntentService extends IntentService {

    public EarthquakeIntentService() {
        super("EarthquakeIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}