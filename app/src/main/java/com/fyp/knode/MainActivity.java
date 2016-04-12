package com.fyp.knode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.fyp.knode.ui.ContactUsActivity;
import com.fyp.knode.ui.CreateEventActivity;
import com.fyp.knode.ui.EditContactsActivity;
import com.fyp.knode.ui.EventsListActivity;
import com.fyp.knode.ui.LoginActivity;
import com.fyp.knode.ui.Messager;
import com.fyp.knode.ui.TwitterTimeLineActivity;
import com.parse.ParseUser;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//
        mActivityTitle = getTitle().toString();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser== null) {
            navigateToLogIn();
        } else {
            String username = currentUser.getUsername();
            mUserName.setText(username);
            mBio.getText().toString();
            Log.i(TAG, currentUser.getUsername());
        }


    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //TODO NEW DRAWER VIEW
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent event = new Intent(MainActivity.this, MainActivity.class);
            startActivity(event);
        } else if (id == R.id.nav_events) {
            Intent event = new Intent(MainActivity.this, EventsListActivity.class);
            startActivity(event);
        } else if (id == R.id.nav_message) {
            Intent inbox = new Intent(MainActivity.this, Messager.class);
            startActivity(inbox);
        } else if (id == R.id.nav_contactus) {
            Intent contact = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(contact);
        } else if (id == R.id.nav_create_event) {
            Intent createEvent = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivity(createEvent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ///TODO DELETE OLD DRAWER VIEW



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mTwitter = menu.getItem(0);
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
        Log.e(TAG, e.getMessage() + "error in on resume");
        }

        AppEventsLogger.activateApp(this);
    }



}
