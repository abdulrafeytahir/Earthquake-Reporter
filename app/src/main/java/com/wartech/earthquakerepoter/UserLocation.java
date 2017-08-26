package com.wartech.earthquakerepoter;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class UserLocation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String LOG_TAG = UserLocation.class.getName();
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;
    private Context mContext;

    public UserLocation(Context context) {
        mContext = context;
        buildGoogleApiClient();
    }

    public Location getmLocation() {
        if (mLocation != null) {
            return mLocation;
        }
        return null;
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        Log.v(LOG_TAG, "mGoogleApiClient connected: " + mGoogleApiClient.isConnected());
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
            Log.e(LOG_TAG, "Security exception occured while gettling user location");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "GoogleApiClient connection has been suspended");
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, location.toString());
        mLocation = location;
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }
}
