package com.wr_software_solutions.earthquakereporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.wr_software_solutions.earthquakereporter.EarthquakeActivity.mCurrentEarthquake;
import static com.wr_software_solutions.earthquakereporter.utilities.QueryUtils.mLatitude;
import static com.wr_software_solutions.earthquakereporter.utilities.QueryUtils.mLongitude;
import static com.wr_software_solutions.earthquakereporter.utilities.QueryUtils.mPlace;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double longitude = 0;
        double latitude = 0;
        String place = "";

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            longitude = mCurrentEarthquake.getmLongitude();
            latitude = mCurrentEarthquake.getmLatitude();
            place = mCurrentEarthquake.getmPlace();
        } else {
            latitude = mLatitude;
            longitude = mLongitude;
            place = mPlace;
        }



        // Add a marker in Sydney and move the camera
        LatLng quakeLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(quakeLocation).title("Earthquake at " + place));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(quakeLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 6.0f));
    }
}
