package com.wartech.earthquakerepoter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class MagnitudeColor {

    private Context mContext;

    public MagnitudeColor(Context mContext) {
        this.mContext = mContext;
    }

    public int getMagnitudeColor(double magnitude) {
        int mag = (int) Math.floor(magnitude);
        int color;
        switch (mag) {
            case 0:
            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;
            default:
                color = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(mContext, color);
    }
}
