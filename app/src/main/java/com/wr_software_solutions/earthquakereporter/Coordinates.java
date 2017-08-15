package com.wr_software_solutions.earthquakereporter;

/**
 * Created by user on 15-Aug-17.
 */

public class Coordinates {
    private double mLatidue;
    private double getmLongitude;
    private String mPlace;

    public Coordinates(double mLatidue, double getmLongitude, String mPlace) {
        this.mLatidue = mLatidue;
        this.getmLongitude = getmLongitude;
        this.mPlace = mPlace;
    }

    public double getmLatidue() {
        return mLatidue;
    }

    public double getGetmLongitude() {
        return getmLongitude;
    }

    public String getmPlace() {
        return mPlace;
    }
}
