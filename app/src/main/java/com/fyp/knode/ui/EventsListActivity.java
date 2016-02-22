package com.fyp.knode.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fyp.knode.Adapter.EventAdapter;
import com.fyp.knode.KnodeConstants.Constants;
import com.fyp.knode.MainActivity;
import com.fyp.knode.R;
import com.fyp.knode.model.Event;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventsListActivity extends ListActivity {
    private static final String TAG =EventsListActivity.class.getSimpleName();

    protected List<ParseObject> mUser;
    protected ParseRelation<ParseUser> mContactRelation;
    protected ParseObject mEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        mCurrentUser = ParseUser.getCurrentUser();
//        mContactRelation = mCurrentUser.getRelation(Constants.KEY_CONTACT_RELATION);
//
//        setProgressBarIndeterminateVisibility(true);
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.orderByAscending(Constants.KEY_USERNAME);
//        query.setLimit(Constants.KEY_USER_LIMIT);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> events, ParseException e) {
//                setProgressBarIndeterminateVisibility(false);
//                if (e == null) {
//                    mUser = contacts;
//                    String[] usernames = new String[mUser.size()];
//                    int i = 0;
//                    for (ParseUser user : mUser) {
//                        usernames[i] = user.getUsername();
//                        i++;
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventsListActivity.this,
//                            android.R.layout.simple_list_item_checked,
//                            usernames);
//                    setListAdapter(adapter);
//                } else {
//                    Log.e(TAG, e.getMessage());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(EventsListActivity.this);
//                    builder.setMessage(e.getMessage())
//                            .setTitle(R.string.something_went_wrong)
//                            .setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//        });
//    }



//    private void display() {
//        setContentView(R.layout.activity_events_list);
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        mRecyclerView.setHasFixedSize(true);
//
//        EventAdapter eventAdapter = new EventAdapter(mEvents);
//
//        mAdapter = eventAdapter;
//        mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
