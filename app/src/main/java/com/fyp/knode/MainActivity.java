package com.fyp.knode;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.fyp.knode.model.Event;
import com.fyp.knode.ui.ContactUsActivity;
import com.fyp.knode.ui.DrawerActivity;
import com.fyp.knode.ui.CreateEventActivity;
import com.fyp.knode.ui.EventsListActivity;
import com.fyp.knode.ui.LoginActivity;
import com.fyp.knode.ui.Messager;
import com.fyp.knode.ui.TwitterTimeLineActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.twitter.sdk.android.Twitter;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG =MainActivity.class.getSimpleName();
    public static final String KNODE_EVENTS = "KNODE_EVENTS";

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private MenuItem mTwitter;
    @Bind(R.id.username) TextView mUserName;
    @Bind(R.id.Bio) TextView mBio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String username = ParseUser.getCurrentUser().getUsername();
        mUserName.setText(username);

        mBio.getText().toString();



        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser== null) {
            navigateToLogIn();
        } else {
            Log.i(TAG, currentUser.getUsername());
        }

        addDrawerItems();
        setupDrawer();

    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void addDrawerItems() {
        String[] osArray = { "Event" ,"Message", "Contact Us", "Create Events" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case(0):
                        Intent event = new Intent(MainActivity.this, DrawerActivity.class);
                        startActivity(event);
                        break;
                    case (1):
                        Intent inbox = new Intent(MainActivity.this, Messager.class);
                        startActivity(inbox);
                        break;
                    case (2):
                        Intent contact = new Intent(MainActivity.this, ContactUsActivity.class);
                        startActivity(contact);
                        break;
                    case (3):
                        Intent createEvent = new Intent(MainActivity.this, CreateEventActivity.class);
                        startActivity(createEvent);
                        break;
                }

            }
        });
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close)
        {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.drawer_navi_title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mTwitter = menu.getItem(0);
        Log.d(TAG, menu.getItem(0).toString() + "0");
        Log.d(TAG, menu.getItem(1).toString()+ "1");
        Log.d(TAG, menu.getItem(2).toString()+ "2");
        Log.d(TAG, mTwitter.setVisible(true).toString());
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_tw_timeline:

                Intent twitter = new Intent(this, TwitterTimeLineActivity.class);
                startActivity(twitter);
                break;
            case R.id.action_logout:
                ParseUser.logOut();
                navigateToLogIn();
                break;
            case R.id.action_add_contacts:
                Intent intent = new Intent(this, EditContactsActivity.class);
                startActivity(intent);
                break;
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        try {
            JSONObject authData = ParseUser.getCurrentUser().getJSONObject("authData");
            if(authData.toString().startsWith("{\"tw")){
               mTwitter.setVisible(true);
            }
        } catch (NullPointerException e){

        }

        AppEventsLogger.activateApp(this);
    }


}
