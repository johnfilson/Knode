package com.fyp.knode.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.MainActivity;
import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.fyp.knode.ui.EventDescriptionActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Johnny on 15/02/2016.
 * EventAdapter for the EventList recyclerView
 */
public class EventAdapter extends ArrayAdapter<ParseObject> {
    private static final String TAG = EventAdapter.class.getSimpleName();

    public  List<ParseObject> mEvents;
    protected ParseRelation<ParseObject> mEventRelation;

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

            final ParseObject event = mEvents.get(position);
            viewHolder.nameOfEvent.setText(event.getString(Constants.KEY_EVENT_NAME));
            viewHolder.organiserEvent.setText(event.getString(Constants.KEY_ORGANISER_NAME));
            viewHolder.nameOfEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), EventDescriptionActivity.class);
                    intent.putExtra(Constants.KEY_EVENT_NAME, event.getString(Constants.KEY_EVENT_NAME));
                    intent.putExtra(Constants.KEY_ORGANISER_NAME, event.getString(Constants.KEY_ORGANISER_NAME));
                    intent.putExtra(Constants.KEY_EVENT_HASHTAG, event.getString(Constants.KEY_EVENT_HASHTAG));
                    intent.putExtra(Constants.KEY_EVENT_DESCRIPTION, event.getString(Constants.KEY_EVENT_DESCRIPTION));
                    intent.putExtra(Constants.KEY_EVENT_PEOPLE_INTER, event.getString(Constants.KEY_EVENT_PEOPLE_INTER));
                    mContext.startActivity(intent);
                }
            });
            viewHolder.joinLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event.add("joined", ParseUser.getCurrentUser());
                    event.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d(TAG, "Saved in the background");
                                return;
                            } else {
                                Toast.makeText(mContext,
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra(Constants.KEY_EVENT_NAME, event.getString(Constants.KEY_EVENT_NAME));
                    intent.putExtra(Constants.KEY_ORGANISER_NAME, event.getString(Constants.KEY_ORGANISER_NAME));
                    intent.putExtra(Constants.KEY_EVENT_HASHTAG, event.getString(Constants.KEY_EVENT_HASHTAG));
                    intent.putExtra(Constants.KEY_EVENT_DESCRIPTION, event.getString(Constants.KEY_EVENT_DESCRIPTION));
                    intent.putExtra(Constants.KEY_EVENT_PEOPLE_INTER, event.getString(Constants.KEY_EVENT_PEOPLE_INTER));
                    mContext.startActivity(intent);
                }
            });

        return convertView;
    }

    private static class  ViewHolder {

        TextView nameOfEvent;
        TextView organiserEvent;
        TextView joinLabel;

    }
}
