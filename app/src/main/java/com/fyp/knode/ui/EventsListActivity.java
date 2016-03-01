package com.fyp.knode.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fyp.knode.Adapter.EventAdapter;
import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventsListActivity extends ListActivity {
    private static final String TAG =EventsListActivity.class.getSimpleName();

    protected ParseUser mCurrentUser;
    protected ParseObject mEvent;
    protected ListView mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();

        setProgressBarIndeterminateVisibility(true);
        List<ParseObject> events = null ;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("EventObject");
        query.selectKeys(Arrays.asList(Constants.KEY_EVENT_NAME, Constants.KEY_ORGANISER_NAME));
        try {
            events = query.find();
            Log.d(TAG, events + "");

        } catch (ParseException e) {
            e.printStackTrace();
        }

Log.d(TAG, events.get(0).getString(Constants.KEY_EVENT_NAME));

        if(events == null) {
            TextView noEventsTextBox = new TextView(this);
            noEventsTextBox.setText( "You have not any upcoming events! Add friends to keep in touch with them. ;)");
        }else
        {
//            mList = (ListView) findViewById(R.id.list);
//            EventAdapter eventAdapter = new EventAdapter(EventsListActivity.this, events);
////            mList.setAdapter(eventAdapter);
            String[] usernames = new String[events.size()];
            int i = 0;
            for (ParseObject user : events) {
                usernames[i] = user.getString(Constants.KEY_EVENT_NAME)+ " "+  user.getString(Constants.KEY_ORGANISER_NAME);
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);
            setListAdapter(adapter);

            Log.d(TAG, events.get(0).getString(Constants.KEY_EVENT_NAME) + events.get(0).getString(Constants.KEY_ORGANISER_NAME));
            Log.d(TAG, events.get(1).getString(Constants.KEY_EVENT_NAME) +  events.get(1).getString(Constants.KEY_ORGANISER_NAME));
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eventlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            ParseUser.logOut();
            navigateToLogIn();
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}