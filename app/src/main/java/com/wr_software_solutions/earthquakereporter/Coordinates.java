package com.wr_software_solutions.earthquakereporter;

public class Coordinates {
    private double mLatidue;
    private double mLongitude;
    private String mPlace;

    public Coordinates(double mLatitude, double mLongitude, String mPlace) {
        this.mLatidue = mLatitude;
        this.mLongitude = mLongitude;
        this.mPlace = mPlace;
    }

    public double getmLatidue() {
        return mLatidue;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public String getmPlace() {
        return mPlace;
    }
}
