package com.fyp.knode.model;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fyp.knode.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * Created by Johnny on 15/02/2016.
 */
@ParseClassName("EventObject")
public class Event extends ParseObject  {
    private String mIcon;
    private String mLocation;
    private Double mTime;
    private Context mContext;
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

//    public List<Event> getEventsName() throws ParseException {
//        ParseQuery<Event> query = ParseQuery.getQuery("EventObject");
//        query.whereEqualTo("eventName",getEventName());
//        query.findInBackground(new FindCallback<Event>() {
//            @Override
//            public void done(List<Event> objects, ParseException e) {
//                if (e == null) {
//                    String[] usernames = new String[objects.size()];
//                    int i = 0;
//                    for (Event user : objects) {
//                        usernames[i] = user.getString("eventName");
//                        i++;
//                    }
//                } else {
//                    Log.e("BAD Request", e.getMessage());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                    builder.setMessage(e.getMessage())
//                            .setTitle(R.string.something_went_wrong)
//                            .setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    return;
//                }
//            }
//        });
//
//    }





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


