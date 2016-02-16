package com.fyp.knode.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Johnny on 15/02/2016.
 */
public class Event implements Parcelable {

    private String mSummary;
    private String mIcon;
    private String mOrganisesName;
    private String mLocation;
    private Double mTime;


    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Double getmTime() {
        return mTime;
    }

    public void setmTime(Double mTime) {
        this.mTime = mTime;
    }

    public String getmOrganisesName() {
        return mOrganisesName;
    }

    public void setmOrganisesName(String mOrganisesName) {
        this.mOrganisesName = mOrganisesName;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public Event(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

}
