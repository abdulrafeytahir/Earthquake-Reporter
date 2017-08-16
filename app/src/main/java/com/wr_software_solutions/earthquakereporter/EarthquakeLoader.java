package com.wr_software_solutions.earthquakereporter;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.wr_software_solutions.earthquakereporter.utilities.QueryUtils;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mURL;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mURL == null) {
            return null;
        }

        List<Earthquake> earthquakes = QueryUtils.extractEarthquakes(mURL, false);
        return earthquakes;
    }
}