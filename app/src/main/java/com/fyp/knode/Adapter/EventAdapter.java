package com.fyp.knode.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

    public EventAdapter(Context context, List<ParseObject> events) {
        super(context, R.layout.event_list_item, events);
        mContext = context;
        mEvents = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        //LayoutInflater from xml
        //convertView also get the Contexts
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.event_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.nameOfEvent = (TextView) convertView.findViewById(R.id.eventTitleLabel);
            viewHolder.organiserEvent = (TextView) convertView.findViewById(R.id.eventOrganiserLabel);
            viewHolder.joinLabel = (TextView) convertView.findViewById(R.id.join_label);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ParseObject event = mEvents.get(position);
        viewHolder.nameOfEvent.setText(event.getString(Constants.KEY_EVENT_NAME));
        viewHolder.organiserEvent.setText(event.getString(Constants.KEY_ORGANISER_NAME));
        return convertView;
    }

    private static class  ViewHolder {

        TextView nameOfEvent;
        TextView organiserEvent;
        TextView joinLabel;

    }
}
