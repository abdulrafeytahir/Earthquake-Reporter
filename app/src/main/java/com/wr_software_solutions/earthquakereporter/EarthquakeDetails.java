package com.wr_software_solutions.earthquakereporter;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import static com.wr_software_solutions.earthquakereporter.EarthquakeActivity.mCurrentEarthquake;


public class EarthquakeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_details);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mCurrentEarthquake.getmTitle());

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(String.valueOf(mCurrentEarthquake.getmPerceivedStrength()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        MagnitudeColor colorMagnitude = new MagnitudeColor(this);
        int magnitudeColor = colorMagnitude.getMagnitudeColor(mCurrentEarthquake.getmPerceivedStrength());
        magnitudeCircle.setColor(magnitudeColor);

        TextView depth = (TextView) findViewById(R.id.depth);
        String d = "Depth: " + String.valueOf(mCurrentEarthquake.getmDepth()) + " km";
        depth.setText(d.toString());

        String alert = null;
        TextView tsunamiAlert = (TextView) findViewById(R.id.tsunami_alert);
        if (mCurrentEarthquake.getmTsunamiAlert() == 0) {
            alert = "Tsunami alert issued: No";
        } else {
            alert = "Tsunami alert issued: Yes";
        }

        tsunamiAlert.setText(alert.toString());

        TextView peopleFeltIt = (TextView) findViewById(R.id.number_of_people);
        String numOfPeople = mCurrentEarthquake.getmNumOfPeople().toString() + " people felt it";
        peopleFeltIt.setText(numOfPeople.toString());

    }
}
