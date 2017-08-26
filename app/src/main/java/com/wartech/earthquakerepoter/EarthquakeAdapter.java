package com.wartech.earthquakerepoter;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    EarthquakeAdapter(Activity context, List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        }

        Earthquake currentListItem = getItem(position);

        TextView magnitude = (TextView) listItemView.findViewById(R.id.mag);
        magnitude.setText(String.valueOf(currentListItem.getmMagnitue()));

        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        MagnitudeColor colorMagnitude = new MagnitudeColor(getContext());
        int magnitudeColor = colorMagnitude.getMagnitudeColor(currentListItem.getmMagnitue());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        String location = currentListItem.getmPlace();
        String separator = " of ";
        String distanceOfQuake;
        String locationOfQuake;
        if (location.contains(separator)) {
            String[] parts = location.split(separator);
            distanceOfQuake = parts[0] + separator;
            locationOfQuake = parts[1];
        } else {
            distanceOfQuake = "Near the";
            locationOfQuake = location;
        }

        TextView distance = (TextView) listItemView.findViewById(R.id.distance);
        distance.setText(distanceOfQuake);

        TextView place = (TextView) listItemView.findViewById(R.id.place);
        place.setText(locationOfQuake);

        long unixTime = currentListItem.getmTime();
        Date dateObject = new Date(unixTime);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String date = sdf.format(dateObject);

        TextView quakteDate = (TextView) listItemView.findViewById(R.id.date);
        quakteDate.setText(date);

        sdf = new SimpleDateFormat("hh:mm a");
        date = sdf.format(dateObject);

        TextView quakeTime = (TextView) listItemView.findViewById(R.id.time);
        quakeTime.setText(date);

        return listItemView;
    }
}
