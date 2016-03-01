package com.fyp.knode.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Johnny on 15/02/2016.
 * EventAdapter for the EventList recyclerView
 */
public class EventAdapter extends ArrayAdapter<Event> {
    private static final String TAG = EventAdapter.class.getSimpleName();

    public final List<Event> mEvents;
    Context mContext;
    private TextView nameOfEvent;
    private TextView organiserEvent;
    private TextView joinButton;
    private Event events;

    public EventAdapter(Context context, List<Event> events) {
        super(context, R.layout.event_list_item, events);
        mContext = context;
        mEvents = events;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int id = position; //index in the table of events
        View v = convertView;
        final Event event = mEvents.get(id);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.event_list_item, null);
        }
    if (id < mEvents.size()) {
        nameOfEvent = (TextView) v.findViewById(R.id.name_of_event_label);
        organiserEvent = (TextView) v.findViewById(R.id.eventOrganiserLabel);
//        joinButton = (TextView) v.findViewById(R.id.join_label);

        if (nameOfEvent != null && organiserEvent != null) {
            try {
                nameOfEvent.setText(ParseObject.createWithoutData(Event.class, event.getEventName()).toString());
                organiserEvent.setText(ParseObject.createWithoutData(Event.class, event.getOrganisesName()).toString());
            } catch (NullPointerException e) {
                Log.d(TAG, e.getMessage());
            }
        }
        else {
            try {
                ParseQuery<Event> query  = ParseQuery.getQuery("EventObject");
                events = query.get("objectId");
            } catch (Exception e) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setMessage("Error occured!");
                dialog.setTitle("Error!");
                dialog.setPositiveButton("OK", null);
                dialog.setCancelable(true);
                dialog.create().show();
            }
            if(event != null){
                nameOfEvent.setText(events.getEventName());
                organiserEvent.setText(events.getOrganisesName());
            }
        }
    }
        return v;

    }

}
