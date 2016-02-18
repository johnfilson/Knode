package com.fyp.knode.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny on 15/02/2016.
 * EventAdapter for the EventList recyclerView
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private static final String TAG =EventAdapter.class.getSimpleName();

    List<Event> mEvents;
    Context mContext;

    public EventAdapter(ArrayList<Event> events){
        mEvents = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.event_list_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final EventAdapter.ViewHolder holder, int position) {
        final Event event = mEvents.get(position);

        if (!event.has("eventName")){
            holder.nameOfEvent.setText(event.getEventName());
            holder.organiserEvent.setText(event.getOrganisesName());
        }
        else {
            Log.i(TAG, "Didn't work");
        }
    }

    @Override
    public int getItemCount() {
        if(mEvents.size()>0 ){
        return mEvents.size();
        }else {
            return 0;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nameOfEvent;
        private TextView organiserEvent;
       // private Button joinButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameOfEvent = (TextView) itemView.findViewById(R.id.name_of_event_label);
            organiserEvent = (TextView) itemView.findViewById(R.id.eventOrganiserLabel);
         //   orderButton = (Button) itemView.findViewById(R.id.orderButton);
        }
    }

}
