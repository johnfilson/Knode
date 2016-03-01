package com.fyp.knode.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Johnny on 15/02/2016.
 * EventAdapter for the EventList recyclerView
 */
public class EventAdapter extends ArrayAdapter<ParseObject> {
    private static final String TAG = EventAdapter.class.getSimpleName();

    public  List<ParseObject> mEvents;
    Context mContext;
    private TextView nameOfEvent;
    private TextView organiserEvent;
    private TextView joinButton;
    private ParseObject events;

    public EventAdapter(Context context, List<ParseObject> events) {
        super(context, R.layout.event_list_item, events);
        mContext = context;
        mEvents = events;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int id = position; //index in the table of events
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.event_list_item, null);
        }
    if (id < mEvents.size()) {
        nameOfEvent = (TextView) v.findViewById(R.id.name_of_event_label);
        organiserEvent = (TextView) v.findViewById(R.id.eventOrganiserLabel);
//        joinButton = (TextView) v.findViewById(R.id.join_label);

        if (nameOfEvent == null && organiserEvent == null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("EventObject");
            query.selectKeys(Arrays.asList(Constants.KEY_EVENT_NAME, Constants.KEY_ORGANISER_NAME));
            nameOfEvent.setText(events.getString(Constants.KEY_EVENT_NAME));
            organiserEvent.setText(events.getString(Constants.KEY_ORGANISER_NAME));
        }
        else {

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setMessage("Error occured!");
                dialog.setTitle("Error!");
                dialog.setPositiveButton("OK", null);
                dialog.setCancelable(true);
                dialog.create().show();
            if(events != null){
                nameOfEvent.setText(events.getString(Constants.KEY_EVENT_NAME));
                organiserEvent.setText(events.getString(Constants.KEY_ORGANISER_NAME));
            }
        }
    }
        return v;

    }

}
