package com.wr_software_solutions.earthquakereporter.sync;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;
import com.wr_software_solutions.earthquakereporter.Earthquake;
import com.wr_software_solutions.earthquakereporter.utilities.QueryUtils;

import java.util.List;


public class EarthquakeRemiderFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {


        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Context context = EarthquakeRemiderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_EARTHQUAKE_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mBackgroundTask != null) {
            mBackgroundTask.cancel(true);
        }
        return true;
    }

}
