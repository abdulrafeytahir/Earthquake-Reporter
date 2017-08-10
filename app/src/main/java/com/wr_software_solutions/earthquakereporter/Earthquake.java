package com.wr_software_solutions.earthquakereporter;


public class Earthquake {
    private double mMagnitue;
    private String mPlace;
    private long mTime;
    private String mURL;
    private String mTitle;
    private String mNumOfPeople;
    private double mPerceivedStrength;
    private int mTsunamiAlert;
    private double mDepth;
    private double mLongitude;
    private double mLatitude;

    public Earthquake(double mMagnitue, String mPlace, long mTime, String mURL, String mTitle, String mNumOfPeople,
                      double mPerceivedStrength, int mTsunamiAlert, double mDepth, double mLongitude, double mLatitude) {
        this.mMagnitue = mMagnitue;
        this.mPlace = mPlace;
        this.mTime = mTime;
        this.mURL = mURL;
        this.mTitle = mTitle;
        this.mNumOfPeople = mNumOfPeople;
        this.mPerceivedStrength = mPerceivedStrength;
        this.mTsunamiAlert = mTsunamiAlert;
        this.mDepth = mDepth;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
    }

    public double getmMagnitue() {
        return mMagnitue;
    }

    public String getmPlace() {
        return mPlace;
    }

    public long getmTime() {
        return mTime;
    }

    public String getmURL() {
        return mURL;
    }

    public int getmTsunamiAlert() {
        return mTsunamiAlert;
    }

    public double getmDepth() {
        return mDepth;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmNumOfPeople() {
        return mNumOfPeople;
    }

    public double getmPerceivedStrength() {
        return mPerceivedStrength;
    }
}
