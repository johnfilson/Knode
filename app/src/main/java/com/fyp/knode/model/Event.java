package com.fyp.knode.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.EventObject;

/**
 * Created by Johnny on 15/02/2016.
 */
@ParseClassName("EventObject")
public class Event extends ParseObject  {
    private String mIcon;
    private String mLocation;
    private Double mTime;

    private  String mEventName = getEventName();
    private String mOrganiserName = getOrganisesName();
    public Event() {}


    public String getEventName() {
        return getString("eventName");
    }
    public void setEventName(String mEventName) {
         put("eventName", mEventName)  ;
    }

    public String getOrganisesName() {
        return getString("organiserName");
    }
    public void setOrganisesName(String mOrganisesName) {
         put("organiserName", mOrganisesName);
    }

    public String getSummary() {
        return getString("description");
    }
    public void setSummary(String mSummary)
    {
        put("description", mSummary);
    }

    public String getHashtags() {
        return getString("hashTag");
    }
    public void setHashtags(String mHashtags) {
        put("hashTag", mHashtags);
    }

    public String getMaxAttendees() {
        return getString("maxAttend");
    }
    public void setMaxAttendees(String mMaxAttendees) {
        put("maxAttend", mMaxAttendees);
    }

    public String getPeopleWhomShouldAttend() {
        return getString("peopleWhomShouldAttend");
    }
    public void setPeopleWhomShouldAttend(String peopleWhomShouldAttend) {
        put("peopleWhomMayAttend", peopleWhomShouldAttend);
    }


    public static ParseQuery<Event> getQuery() {
        return ParseQuery.getQuery(Event.class);
    }



    //TODO Time, Location and Icon
    /*public String getLocation() {
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
    public String getIcon() {
        return mIcon;
    }
    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }*/
}


