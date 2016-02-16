package com.fyp.knode.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fyp.knode.model.Event;

/**
 * Created by Johnny on 15/02/2016.
 */
public class EventAdapter extends BaseAdapter {

    private Context mContext;
    private Event[] mEvents;

    public EventAdapter (Context context, Event[] events){

        mContext = context;
        mEvents = events;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
