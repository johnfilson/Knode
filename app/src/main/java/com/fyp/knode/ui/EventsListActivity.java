package com.fyp.knode.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class EventsListActivity extends AppCompatActivity {
    private static final String TAG =EventsListActivity.class.getSimpleName();

    protected List<ParseObject> mEvents;
    protected ParseRelation<ParseUser> mContactRelation;
    protected ParseUser mCurrentUser;
    protected ParseObject mEvent;
    protected ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        mList = (ListView) findViewById(R.id.listEvent);
        mList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mContactRelation = mCurrentUser.getRelation(Constants.KEY_CONTACT_RELATION);

        setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("EventObject");
        query.orderByAscending(Constants.KEY_EVENT_NAME);
        query.setLimit(Constants.KEY_EVENT_LIMIT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> events, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    mEvents = events;
                    String eventName = events.get(50).get("eventName").toString();
                    String organiserName = events.get(50).get("organiserName").toString();

                    Log.d(TAG, eventName + organiserName);
                } else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventsListActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.something_went_wrong)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_eventlist, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
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