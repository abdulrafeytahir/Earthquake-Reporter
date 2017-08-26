package com.wr_software_solutions.earthquakereporter;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;

import static com.wr_software_solutions.earthquakereporter.EarthquakeActivity.LOG_TAG;
import static com.wr_software_solutions.earthquakereporter.EarthquakeActivity.mCurrentEarthquake;
import static com.wr_software_solutions.earthquakereporter.sync.ReminderTasks.mReminderEarthquake;
import static com.wr_software_solutions.earthquakereporter.sync.ReminderTasks.mUserLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLocation;
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Polyline mPolyline;
    private ArrayList<LatLng> latLngs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String earthquakePlace;

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            earthquakePlace = mCurrentEarthquake.getmPlace();
            Log.d(LOG_TAG, "Earthquake location coordinates: " + mCurrentEarthquake.getmLongitude() + " " + mCurrentEarthquake.getmLatitude());
            latLngs.add(new LatLng(mCurrentEarthquake.getmLatitude(), mCurrentEarthquake.getmLongitude()));
            mMarkerA = mMap.addMarker(new MarkerOptions().position(latLngs.get(0)).title(earthquakePlace));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs.get(0)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 6.0f));

        } else {
            mLocation = mUserLocation.getmLocation();
            Log.d(LOG_TAG, "User location coordinates" + mLocation.getLatitude() + mLocation.getLongitude());
            latLngs.add(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
            latLngs.add(new LatLng(mReminderEarthquake.getmLatitude(), mReminderEarthquake.getmLongitude()));
            earthquakePlace = mReminderEarthquake.getmPlace();

            mMarkerA = mMap.addMarker(new MarkerOptions().position(latLngs.get(1)).title(earthquakePlace));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngs.get(1)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(1), 6.0f));

            mMarkerB = mMap.addMarker(new MarkerOptions().position(latLngs.get(0)).title("User Location"));

            mPolyline = mMap.addPolyline(new PolylineOptions().geodesic(true));

            mPolyline.setPoints(Arrays.asList(mMarkerA.getPosition(), mMarkerB.getPosition()));
        }

    }
}
