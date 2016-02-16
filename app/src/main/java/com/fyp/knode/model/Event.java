package com.fyp.knode.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

/**
 * Created by Johnny on 15/02/2016.
 */
public class Event implements Parcelable {

    private String mSummary;
    private String mIcon;
    private String mOrganisesName;
    private String mLocation;
    private Double mTime;
    private String mEventName;
    private String mHashtags;
    private String mMaxAttendees;


    ParseObject eventInfo = new ParseObject("EventObject");

    public ParseObject getEventInfo() {
        eventInfo.put("organiserName", getOrganisesName());
        eventInfo.put("description", getSummary());
        eventInfo.put("eventName",getEventName());
        eventInfo.put("hashTag", getHashtags());
        eventInfo.put("maxAttend", getMaxAttendees());
        return eventInfo;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Double getTime() {
        return mTime;
    }

    public void setTime(Double mTime) {
        this.mTime = mTime;
    }

    public String getOrganisesName() {
        return mOrganisesName;
    }

    public void setOrganisesName(String mOrganisesName) {
        this.mOrganisesName = mOrganisesName;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getHashtags() {
        return mHashtags;
    }

    public void setHashtags(String mHashtags) {
        this.mHashtags = mHashtags;
    }

    public Event(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEventName);
        dest.writeString(mOrganisesName);
        dest.writeString(mSummary);
        dest.writeString(mHashtags + "");
    }

    private Event (Parcel in) {
        mEventName =in.readString();
        mHashtags= in.readString();
        mOrganisesName = in.readString();
        mSummary = in.readString();
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


    public String getMaxAttendees() {
        return mMaxAttendees;
    }

    public void setMaxAttendees(String mMaxAttendees) {
        this.mMaxAttendees = mMaxAttendees;
    }
}
